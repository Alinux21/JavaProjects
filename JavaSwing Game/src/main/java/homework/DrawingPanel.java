package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;
import javax.imageio.ImageIO;

public class DrawingPanel extends JPanel {
    private boolean game_finished = false;
    final MainFrame frame;
    int canvasWidth = 555, canvasHeight = 550;
    int stoneSize = 30;
    int cols, rows;
    int padding = 25;
    transient GameLogicHandler handler;
    transient BufferedImage image;
    transient Graphics2D offscreen;
     final List<Point> selectedNodes = new ArrayList<>();
     final List<Line> sticks = new ArrayList<>();
     final Random random = new Random();
     Color currentPlayerColor = Color.RED;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        this.image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.offscreen = image.createGraphics();
        this.handler = new GameLogicHandler(this);
        clearOffscreenImage();
        runGame();

    }

    public void runGame() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!game_finished) {

                    // round mouse coordinates to detect when clicking on a node

                    int roundedX = roundToNearestMultiple(e.getX() - padding, (canvasWidth - 2 * padding) / cols)
                            + padding;
                    int roundedY = roundToNearestMultiple(e.getY() - padding, (canvasHeight - 2 * padding) / rows)
                            + padding;

                    // check if the mouse click is within the canvas boundaries
                    if (roundedX >= 20 && roundedX < canvasWidth && roundedY >= 20 && roundedY < canvasHeight) {
                        Point selectedNode = new Point(roundedX, roundedY);
                        // process the mouse click only if it's within the canvas boundaries
                        if (handler.isValidMove(selectedNode)) {
                            if (!handler.hasMovesLeft(selectedNode)) {
                                drawStone(roundedX, roundedY);
                                finishGame();
                            } else {
                                drawStone(roundedX, roundedY);
                                repaint();
                            }
                        }
                    }
                }
            }
        });
    }

    public void createGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        paintGrid(rows, cols);
        generateNodesAndSticks(rows, cols);
        paintSticks();
        repaint();
    }

    private void clearOffscreenImage() {
        if (offscreen != null) {
            offscreen.setColor(Color.WHITE);
            offscreen.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        }
    }

    private void paintGrid(int rows, int cols) {
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);

        // draw the grid lines with padding
        offscreen.setColor(Color.BLACK);
        int cellWidth = (canvasWidth - 2 * padding) / cols;
        int cellHeight = (canvasHeight - 2 * padding) / rows;

        for (int row = 0; row <= rows; row++) {
            int y = padding + row * cellHeight;
            offscreen.drawLine(padding, y, canvasWidth - padding, y);
        }

        for (int col = 0; col <= cols; col++) {
            int x = padding + col * cellWidth;

            offscreen.drawLine(x, padding, x, canvasHeight - padding);
        }

        // draw ovals at the intersections
        offscreen.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row <= rows; row++) {
            for (int col = 0; col <= cols; col++) {
                int x = padding + col * cellWidth - stoneSize / 2;
                int y = padding + row * cellHeight - stoneSize / 2;
                offscreen.drawOval(x, y, stoneSize, stoneSize);
            }
        }

    }

    private void generateNodesAndSticks(int rows, int cols) {

        int cellWidth = (canvasWidth - 2 * padding) / cols;
        int cellHeight = (canvasHeight - 2 * padding) / rows;

        double stickProbability = 0.9; // probability of creating a stick

        for (int i = 0; i < rows - 1; i++) { // iterate through the rows and cols for random generating sticks
            for (int j = 0; j < cols - 1; j++) {
                // horizontal stick
                if (random.nextDouble() < stickProbability) {
                    Point start = new Point(padding + j * cellWidth, padding + i * cellHeight);
                    Point end = new Point(padding + (j + 1) * cellWidth, padding + i * cellHeight);
                    sticks.add(new Line(start, end));
                }
                // vertical stick
                if (random.nextDouble() < stickProbability) {
                    Point start = new Point(padding + j * cellWidth, padding + i * cellHeight);
                    Point end = new Point(padding + j * cellWidth, padding + (i + 1) * cellHeight);
                    sticks.add(new Line(start, end));
                }
            }
        }

    }

    // helps with rounding the mouse coordinates
    private int roundToNearestMultiple(int value, int multiple) {
        return Math.round((float) value / multiple) * multiple;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

    private void paintSticks() {
        offscreen.setStroke(new BasicStroke(3)); // makes a stick appear bolder
        offscreen.setColor(Color.BLACK);
        for (Line stick : sticks) {
            offscreen.drawLine(stick.start().x, stick.start().y, stick.end().x, stick.end().y);
        }
    }

    private void drawStone(int mouseX, int mouseY) {
        int cellWidth = (canvasWidth - 2 * padding) / cols;
        int cellHeight = (canvasHeight - 2 * padding) / rows;

        // calculate the grid cell corresponding to the mouse click
        int col = (mouseX - padding) / cellWidth;
        int row = (mouseY - padding) / cellHeight;

        // calculate the center coordinates of the determined grid cell
        int x = padding + col * cellWidth;
        int y = padding + row * cellHeight;

        // draw a stone (red or blue) at the center of the determined grid cell
        Graphics2D g2d = (Graphics2D) offscreen;
        g2d.setColor(currentPlayerColor);
        g2d.fillOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);

        // toggle between red and blue stones for the next player's turn
        currentPlayerColor = (currentPlayerColor == Color.RED) ? Color.BLUE : Color.RED;
    }

    private void finishGame() {
        repaint(); // paints the last selected node
        String message = "\nGame over!\nWinner:"
                + (currentPlayerColor == Color.RED ? "Player Blue " : "Player Red");
        System.out.println(message);
        JOptionPane.showMessageDialog(this, message);
        PngExporter exporter = new PngExporter(frame, image); // export the png image of the board
        exporter.export();
        game_finished = true;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // serialize other fields

        // save the image to PNG file
        String imagePath = "saves\\image.png";

        File outputFile = new File(imagePath);
        ImageIO.write(image, "PNG", outputFile);

        out.writeObject(imagePath);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // deserialize other fields

        // read the file path from the stream
        String imagePath = (String) in.readObject();

        // load the image from the file
        File inputFile = new File(imagePath);
        this.image = ImageIO.read(inputFile);
        this.offscreen = image.createGraphics();
    }

}