package homework;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        // change the default layout manager (just for fun)
        setLayout(new GridLayout(1, 4));
        add(exitBtn);
        add(loadBtn);
        add(saveBtn);

        // configure listeners for all buttons
        exitBtn.addActionListener(this::exitGame);
        saveBtn.addActionListener(this::saveGame);
        loadBtn.addActionListener(this::loadGame);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

    private void saveGame(ActionEvent e) {
        try {
            // serialization
            String filename = "saves\\save.ser";
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(frame.canvas);

            out.close();
            file.close();
            System.out.println("Object has been serialized");
        }

        catch (IOException ex) {
            System.out.println("IOException is caught: " + ex);
        }
    }

    private void loadGame(ActionEvent e) {
        // deserialization
        DrawingPanel object1 = new DrawingPanel(frame);
        try {
            // reading the object from file
            String filename = "saves\\save.ser";
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
    
            object1 = (DrawingPanel) in.readObject();
            
            in.close();
            file.close();
    
            // update the old DrawingPanel with the deserialized one
            frame.setDrawingPanel(object1);
    
            System.out.println("Object has been deserialized ");
        } catch (IOException ex) {
            System.out.println("IOException is caught" + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }
    
    
}
