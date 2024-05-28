package homework;

import java.awt.Point;
import java.util.*;

public class GameLogicHandler {
    DrawingPanel canvas;

    public GameLogicHandler(DrawingPanel canvas) {
        this.canvas = canvas;
    }

    public boolean isValidMove(Point selectedNode) {
        // check if the selected node is adjacent to the previously selected node

        if (!hasAdjacentStick(selectedNode)) {
            System.out.println("The selected node does not have an adjacent stick!");
            return false;
        }

        if (canvas.selectedNodes.size() == 0) { // first node on the canvas
            canvas.selectedNodes.add(selectedNode);
            return true;
        }

        Point previouslySelectedNode = canvas.selectedNodes.get(canvas.selectedNodes.size() - 1);

        // check if the selected node is unselected

        if (isNodeSelected(selectedNode)) {
            System.out.println("The node has already been selected!");
            return false;
        }

        // check if the selected node has an adjacent stick
        if (!hasAdjacentStick(selectedNode)) {
            System.out.println("The selected node does not have an adjacent stick!");
            return false;
        }

        if (!isAdjacent(selectedNode, previouslySelectedNode)) {
            System.out.println("Selected node is not adjacent to the previous node!");
            return false;
        }

        canvas.selectedNodes.add(selectedNode);
        return true;
    }

    private boolean isAdjacent(Point node1, Point node2) {
        // check if there is a stick that connects node1 and node2

        for (Line stick : canvas.sticks) {
            if ((stick.start().equals(node1) && stick.end().equals(node2)) ||
                    (stick.start().equals(node2) && stick.end().equals(node1))) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAdjacentStick(Point node) {

        // check if selected node has an adjacent stick

        for (Line stick : canvas.sticks) {
            if (stick.start().equals(node) || stick.end().equals(node)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNodeSelected(Point node) {
        return canvas.selectedNodes.contains(node);
    }

    public boolean hasMovesLeft(Point node) {

        for (Point neighbour : getNeighbours(node)) { // iterate through the neighbours of the current node
            if (availableValidMove(neighbour)) {
                return true;
            }
        }

        return false; // if there's no valid move to a neighbour -> game over
    }

    private boolean availableValidMove(Point selectedNode) {

        Point previouslySelectedNode = canvas.selectedNodes.get(canvas.selectedNodes.size() - 1);

        if (isNodeSelected(selectedNode) || !hasAdjacentStick(selectedNode)
                || !isAdjacent(selectedNode, previouslySelectedNode)) {
            return false;
        }

        return true;
    }

    private List<Point> getNeighbours(Point node) {
        List<Point> neighbours = new ArrayList<>();

        // the possible relative positions of neighbors
        int[] dx = { -1, 0, 1, 0 }; // relative x-coordinates of neighbors
        int[] dy = { 0, -1, 0, 1 }; // relative y-coordinates of neighbors

        int cellWidth = (canvas.canvasWidth - 2 * canvas.padding) / canvas.cols;
        int cellHeight = (canvas.canvasHeight - 2 * canvas.padding) / canvas.rows;

        // iterate through each possible neighbor position and calculate its x and y
        // coordinates
        for (int i = 0; i < dx.length; i++) {
            int nx = node.x + dx[i] * cellWidth;
            int ny = node.y + dy[i] * cellHeight;

            // check if the neighbor is within the bounds of the grid
            if (nx >= canvas.padding && nx < canvas.canvasWidth - canvas.padding && ny >= canvas.padding
                    && ny < canvas.canvasHeight - canvas.padding) {
                Point neighbour = new Point(nx, ny);
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }
 
}
