package com.example.demo;

import com.example.demo.jdbc.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookService {
    final Logger log = LoggerFactory.getLogger(BookService.class);
    final String uri = "http://localhost:8081/books";

    @GetMapping(value = "/getAllBooks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getBooks() {
        log.info("Start");
        Flux<Book> productFlux = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Book.class);

        productFlux.subscribe(p -> log.info(p.toString()));
        log.info("Stop");
        return productFlux;
    }

    @PostMapping(value = "/addBook", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Book>> addBook(int year, String name, String author, String genre) {
        return WebClient.create()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("year", String.valueOf(year))
                        .with("name", name)
                        .with("author", author)
                        .with("genre", genre))
                .retrieve()
                .toEntity(Book.class);
    }

    @PostMapping(value = "/addBookObj", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Book>> createBook(Book book) {
        return WebClient.create()
                .post()
                .uri("/books/obj")
                .bodyValue(book)
                .retrieve()
                .toEntity(Book.class);
    }

    @PutMapping(value = "/updateBook/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<String>> updateBook(@PathVariable int id, @RequestParam String name) {
        return WebClient.create()
                .put()
                .uri(uri + "/" + id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("name", name))
                .retrieve()
                .toEntity(String.class);
    }

    @DeleteMapping(value = "/deleteBook/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<String>> deleteBook(@PathVariable int id) {
        return WebClient.create()
                .delete()
                .uri(uri + "/" + id)
                .retrieve()
                .toEntity(String.class);
    }


}