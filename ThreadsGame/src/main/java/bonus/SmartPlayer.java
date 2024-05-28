package bonus;

import java.util.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.jgrapht.alg.tour.PalmerHamiltonianCycle;

public class SmartPlayer implements Player {
    private final String name;
    private Game game;
    private boolean running;
    private final List<Tile> tiles = new ArrayList<>();

    Graph<Integer, DefaultEdge> tileGraph = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
    List<Integer> vertices = new ArrayList<>();
    List<List<Integer>> edges = new ArrayList<>();

    List<DefaultEdge> sequence;
    int sequenceLength = 0;
    private boolean myTurn = false;

    public SmartPlayer(String name) {
        this.name = name;

        // vertices.add(1);
        // vertices.add(2);
        // vertices.add(3);
        // vertices.add(4);

        // List<Integer> edge = new ArrayList<>();
        // edge.add(1);edge.add(2);
        // edges.add(edge);

        // edge = new ArrayList<>();
        // edge.add(2);edge.add(3);
        // edges.add(edge);

        // edge = new ArrayList<>();
        // edge.add(3);edge.add(4);
        // edges.add(edge);

        // edge = new ArrayList<>();
        // edge.add(4);edge.add(1);
        // edges.add(edge);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public int getSequenceLength() {
        return this.sequenceLength;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void checkSequence() {

        int numVertices = vertices.size(); // iterate through all possible combinations of vertices
        for (int mask = 1; mask < (1 << numVertices); mask++) {
            Graph<Integer, DefaultEdge> subGraph = new SimpleGraph<>(DefaultEdge.class);

            for (int j = 0; j < numVertices; j++) { // construct a subgraph using the selected vertices
                if ((mask & (1 << j)) > 0) {
                    subGraph.addVertex(vertices.get(j));
                }
            }

            for (List<Integer> edge : edges) {
                Integer vertex1 = edge.get(0);
                Integer vertex2 = edge.get(1);
                if (subGraph.containsVertex(vertex1) && subGraph.containsVertex(vertex2)) {
                    subGraph.addEdge(vertex1, vertex2);
                }
            }

            boolean satisfiesOreCondition = true;
            satisfiesOreCondition = GraphTests.hasOreProperty(subGraph);

            if (satisfiesOreCondition) {
                try {
                    HamiltonianCycleAlgorithm<Integer, DefaultEdge> alg = new PalmerHamiltonianCycle<>();
                    List<DefaultEdge> potentialSequence = alg.getTour(subGraph).getEdgeList();

                    // check if the potential sequence is valid
                    if (isValidSequence(potentialSequence, subGraph)) {
                        // if there's no existing sequence or the new sequence has the same starting
                        // vertex as the end vertex of the old sequence, add the new sequence
                        if (sequence == null || subGraph.getEdgeSource(potentialSequence.get(0))
                                .equals(subGraph.getEdgeTarget(sequence.get(sequence.size() - 1)))) {
                            this.sequence = potentialSequence;
                            break; // stop iteration if a valid hamiltonian cycle found
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // no hamiltonian cycle found in this subgraph
                }
            }
        }
    }

    private boolean isValidSequence(List<DefaultEdge> potentialSequence, Graph<Integer, DefaultEdge> subGraph) {

        Integer prevVertex = subGraph.getEdgeTarget(potentialSequence.get(0));

        for (int i = 1; i < potentialSequence.size(); i++) {
            Integer sourceVertex = subGraph.getEdgeSource(potentialSequence.get(i));
            Integer targetVertex = subGraph.getEdgeTarget(potentialSequence.get(i));
            if (sourceVertex != prevVertex)
                return false;
            prevVertex = targetVertex;

        }
        return true;
    }

    public void addTileInGraph(Tile tile) {

        // add the vertices from the tiles if not already present
        if (!vertices.contains(tile.first())) {
            vertices.add(tile.first());
        }

        if (!vertices.contains(tile.second())) {
            vertices.add(tile.second());
        }

        List<Integer> edge = new ArrayList<>();
        edge.add(tile.first());
        edge.add(tile.second());

        // Add the edge to the edges list
        edges.add(edge);
    }

    @Override
    public synchronized void run() {
        while (running && game.gameRunning) {
            while (!myTurn) {

                try {
                    while (!myTurn && game.gameRunning) {
                        System.out.println(this.name + " waiting ...");
                        wait(); // wait until it's my turn or game is stopped
                    }

                    if (!game.gameRunning) {
                        break; // exit the loop if the game is stopped
                    }
                } catch (InterruptedException e) {
                    // thread interrupted, exit gracefully
                    return;
                }
            }

            System.out.println(this.name + "'s turn");

            List<Tile> extractedTiles;
            synchronized (game.getBag()) { // Ensure thread-safe access to the bag
                extractedTiles = game.getBag().extractTiles(1);
            }

            if (vertices.isEmpty()) { // the graph is empty
                vertices.add(extractedTiles.get(0).first());
                vertices.add(extractedTiles.get(0).second());
                List<Integer> edge = new ArrayList<>();
                edge.add(extractedTiles.get(0).first());
                edge.add(extractedTiles.get(0).second());
                edges.add(edge);
            } else {
                if (!extractedTiles.isEmpty())
                    addTileInGraph(extractedTiles.get(0));
                checkSequence();
            }

            if (extractedTiles.isEmpty() || sequenceLength == game.getBag().n) {
                running = false;
            }

            // sequenceLength = this.sequence.getLength(); // MODIFY THIS FOR GETTING THE
            // SEQUENCE LENGTH

            extractedTiles.forEach(tile -> this.tiles.add(tile));
            game.nextTurn(); // inform that this turn has ended
            myTurn = false; // reset player's myTurn flag
            notifyAll(); // notify other players waiting for their turn
        }

        if (game.gameRunning) { // if the game was not interrupted
            System.out.println(this.name + " seq: " + sequence);

        }
    }

    public synchronized void takeTurn() {
        myTurn = true;
        notify(); // notify this player that it's their turn
    }
}
