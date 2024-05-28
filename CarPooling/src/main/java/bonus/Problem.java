package bonus;

import java.util.*;
import java.util.stream.Collectors;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm.Matching;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Problem {

    private List<Person> drivers = new ArrayList<>();
    private Set<Person> passengers = new TreeSet<>();
    private Map<Driver, Passenger> match = new TreeMap<>();
    private Graph<Person, DefaultEdge> bipartiteGraph = new SimpleGraph<>(DefaultEdge.class);

    public Problem(List<Person> drivers, Set<Person> passengers) {
        this.drivers = drivers;
        this.passengers = passengers;

        setupVertices();
        setupEdges();

    }

    public void getMaxCardSet() {

        List<Person> listOfPersons = new ArrayList<>();
        drivers.stream().forEach(driver -> listOfPersons.add(driver));
        passengers.stream().forEach(passenger -> listOfPersons.add(passenger));

        List<Destination> usedDestinations = new ArrayList<>();

        List<Person> maxIndSet = new ArrayList<>();

        for (Person person : listOfPersons) {
            if (person instanceof Driver) {

                Driver driver = (Driver) person;
                List<Destination> destinations = driver.getRoute().stream().filter(
                        destination -> driver.getRoute().indexOf(destination) > 0).collect(Collectors.toList()); // first

                boolean driverHasUsedDest = false;

                for (Destination dest : destinations) {
                    if (usedDestinations.contains(dest)) {
                        driverHasUsedDest = true;
                        break;
                    }
                }

                if (!driverHasUsedDest) {
                    maxIndSet.add(driver);
                    destinations.stream().forEach(dest -> usedDestinations.add(dest));
                }
            } else if (person instanceof Passenger) {
                Passenger passenger = (Passenger) person;

                if (!usedDestinations.contains(passenger.getDestination())) {
                    maxIndSet.add(passenger);
                    usedDestinations.add(passenger.getDestination());
                }

            }
        }

        maxIndSet.stream().forEach(person -> System.out.println(person));

    }

    private void setupVertices() {

        drivers.stream().forEach(driver -> bipartiteGraph.addVertex(driver));

        passengers.stream().forEach(passenger -> bipartiteGraph.addVertex(passenger));

    }

    private void setupEdges() {

        drivers.stream().map(person -> (Driver) person)
                .forEach(driver -> {

                    List<Destination> driverRoute = driver.getRoute();
                    passengers.stream().map(person -> (Passenger) person)
                            .filter(passenger -> passenger.isAssigned() == false)
                            .forEach(passenger -> {

                                Destination passStart = passenger.getStart();
                                Destination passEnd = passenger.getDestination();

                                if (driverRoute.contains(passStart) && driverRoute.contains(passEnd)) {
                                    if (driverRoute.indexOf(passStart) < driverRoute.indexOf(passEnd)) {
                                        bipartiteGraph.addEdge(passenger, driver);
                                    }
                                }

                            });

                });

    }

    public int hopcroftKarp() {

        Set<Person> driverSet = new TreeSet<>(drivers);

        HopcroftKarpMaximumCardinalityBipartiteMatching<Person, DefaultEdge> g = new HopcroftKarpMaximumCardinalityBipartiteMatching<Person, DefaultEdge>(
                bipartiteGraph, passengers, driverSet);

        Matching<Person, DefaultEdge> maxCardMathcing = g.getMatching();
        System.out.println("\nHopcroft-Karp Maxium matching:\n");
        maxCardMathcing.getEdges().stream().forEach(edge -> System.out.println(edge));

        return (int) maxCardMathcing.getWeight();

    }

    public int greedyMatch() {

        drivers.stream().map(person -> (Driver) person)
                .forEach(driver -> {

                    List<Destination> driverRoute = driver.getRoute();
                    passengers.stream().map(person -> (Passenger) person)
                            .filter(passenger -> passenger.isAssigned() == false)
                            .forEach(passenger -> {

                                Destination passStart = passenger.getStart();
                                Destination passEnd = passenger.getDestination();

                                if (driverRoute.contains(passStart) && driverRoute.contains(passEnd)) {
                                    if (driverRoute.indexOf(passStart) < driverRoute.indexOf(passEnd)) {
                                        match.put(driver, passenger);
                                        passenger.setAssigned();
                                    }
                                }

                            });

                });

        System.out.println("Greedy Matching:");
        match.forEach((k, v) -> System.out.println(v + "---" + k));

        return match.size();

    }

    public List<Person> getDrivers() {
        return drivers;
    }

    public Set<Person> getPassengers() {
        return passengers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=_=_=Problem=_=_=\n");
        sb.append("Drivers:\n");
        drivers.stream().forEach(d -> sb.append(d + "\n"));
        sb.append("\n");

        sb.append("\nPassengers:\n");
        passengers.stream().forEach(p -> sb.append(p + "\n"));
        sb.append("\n=_=_=_=_=_=_=_=_=\n\n");

        return sb.toString();
    }

}