package homework;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("My Game");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(610, 700));

        this.configPanel = new ConfigPanel(this);
        add(configPanel, BorderLayout.NORTH);

        this.controlPanel = new ControlPanel(this);
        add(controlPanel, BorderLayout.SOUTH);

        pack();

        this.canvas = new DrawingPanel(this);
        add(canvas, BorderLayout.CENTER);
    }

    public void setDrawingPanel(DrawingPanel panel) {
        this.canvas = panel;
        this.canvas.handler=new GameLogicHandler(panel); //add a new game handler to the new DrawingPanel

        getContentPane().removeAll(); // remove all existing components
    
        // build the frame from scratch
        getContentPane().add(configPanel,BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(controlPanel,BorderLayout.SOUTH);

        // add the interactions with the mouse
        canvas.runGame();

        // validate and repaint the frame to ensure proper layout
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
