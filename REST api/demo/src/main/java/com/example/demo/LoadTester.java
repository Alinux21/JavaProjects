package com.example.demo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class LoadTester {
    private static final int NUM_REQUESTS = 1000;
    private static final int NUM_THREADS = 100;
    private static final MeterRegistry registry = new SimpleMeterRegistry();
    private static final ConcurrentHashMap<Integer, Integer> responseCodes = new ConcurrentHashMap<>();

    //testing urls
    private static final List<String> urls = Arrays.asList(
            "http://localhost:8081/books",
            "http://localhost:8081/authors",
            "http://localhost:8081/invalidurl" //will return 404
    );

    public static void main(String[] args) throws InterruptedException {

        Runnable task = () -> {
            for (String urlStr : urls) {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    long startTime = System.currentTimeMillis();
                    int responseCode = conn.getResponseCode();
                    long responseTime = System.currentTimeMillis() - startTime;

                    //recording response time
                    registry.timer("request.duration").record(responseTime, TimeUnit.MILLISECONDS);

                    //incrementing response code count
                    responseCodes.compute(responseCode, (key, val) -> val == null ? 1 : val + 1);

                    if (responseCode != 200) {
                        System.out.println("Request to " + urlStr + " failed with response code: " + responseCode);
                        registry.counter("request.failures").increment();
                    } else {
                        System.out.println("Request to " + urlStr + " successful with response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //executor service with a fixed thread pool for testing multiple requests
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        long startTime = System.currentTimeMillis();

        //submitting tasks to the executor service
        for (int i = 0; i < NUM_REQUESTS; i++) {
            executor.submit(task);
        }

        //shutting the executor service down
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);//waiting for all tasks to finish

        long endTime = System.currentTimeMillis();

        //requests per minute
        long duration = endTime - startTime;
        double requestsPerMinute = (double) NUM_REQUESTS / duration * 60 * 1000;
        System.out.println("Sent " + NUM_REQUESTS + " requests in " + duration + " ms");
        System.out.println("Requests per minute: " + requestsPerMinute);

        //metrics
        System.out.println("Average response time: " + registry.timer("request.duration").mean(TimeUnit.MILLISECONDS) + " ms");
        System.out.println("Failed requests: " + registry.counter("request.failures").count());
        System.out.println("Response status codes count: " + responseCodes);

    }
}