package bonus;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.alg.coloring.Coloring;
import org.graph4j.alg.coloring.eq.EquitableColoringAlgorithm;
import org.graph4j.alg.coloring.eq.GreedyEquitableColoring;
import org.graph4j.generate.GraphGenerator;
import org.graph4j.util.VertexSet;

public class Main {

    private Graph<Book, Edge<Integer>> bookGraph;

    public static void main(String[] args) {

        Main app = new Main();

        try {

            //Create the support graph for creating the reading lists
            app.createBookGraph();

            //Create the reading lists based on the graph with greedy coloring
            app.createReadingLists().stream().forEach(readingList -> System.out.println("\n"+readingList));

        } catch (SQLException ex) {

            System.out.println(ex);
            System.out.println("Commiting rollback...");

            try {
                Database.getConnection().rollback();
            } catch (SQLException e) {
                System.out.println("Error at committing rollback:" + e);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public void createBookGraph() throws SQLException {

        var booksDAO = new BookDAO();
        Set<Book> books = booksDAO.findAll(50).stream().collect(Collectors.toSet());

        new GraphGenerator();
        // Create the graph
        bookGraph = GraphGenerator.empty(books.size());
        {
            AtomicInteger count = new AtomicInteger(0);

            // Add the books as vertices
            books.stream().forEach(book -> {
                bookGraph.addVertex(book);
                bookGraph.setVertexLabel(count.getAndIncrement(), book);
            });
        }
        // Create the edges beetwen books that have a common author or common genre
        for (Book book1 : books) {
            for (Book book2 : books) {
                if (!book1.equals(book2)) {
                    // Add edege between books that have the same genre
                    if (book1.getGenre().equals(book2.getGenre())) {
                        bookGraph.addEdge(book1, book2);
                    }

                    List<String> Book1Authors = Arrays.asList(book1.getAuthor().split(","));
                    List<String> Book2Authors = Arrays.asList(book2.getAuthor().split(","));

                    // Add edge between books that have a common author
                    for (String author : Book1Authors) {
                        if (Book2Authors.contains(author)) {
                            bookGraph.addEdge(book1, book2);
                            break;
                        }
                    }
                }

            }
        }

    }

    public List<ReadingList> createReadingLists() throws SQLException {
        // Apply equitable coloring algorithm
        EquitableColoringAlgorithm coloring = new GreedyEquitableColoring(bookGraph);
        Coloring greedyColoring = coloring.findColoring();

        // Get the coloring information
        Map<Integer, VertexSet> colorClasses = greedyColoring.getColorClasses();

        List<ReadingList> readingLists = new ArrayList<>();

        //Each Integer represents a color therefore a different Reading List
        for (Map.Entry<Integer, VertexSet> entry : colorClasses.entrySet()) {   

            Set<Book> currentReadingListBooks = new HashSet<>();

            entry.getValue().forEach(vertex -> {

                Book book = bookGraph.getVertexLabel(vertex);
                currentReadingListBooks.add(book);

            });

            ReadingList readingList = new ReadingList("ReadingList" + entry.getKey(), LocalDateTime.now(),
                    currentReadingListBooks);
            readingLists.add(readingList);

        }
        return readingLists;

    }

}