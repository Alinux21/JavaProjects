package homework;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    static boolean gridCreated = false;
    final MainFrame frame;
    JLabel label;
    JSpinner spinner;
    JSpinner spinner2;
    JButton createButton;
    int rows,cols;



    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        label = new JLabel("Grid size:");
        spinner = new JSpinner(new SpinnerNumberModel(10, 2, 25, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(10, 2, 25, 1));
        createButton = new JButton("Create");

        // add ActionListener to create the grid when pressed
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGrid();
            }
        });

        setLayout(new FlowLayout());
        add(label);
        add(spinner);
        add(spinner2);
        add(createButton);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }


    private void createGrid() {
        if(gridCreated==false){
         rows = (int) spinner.getValue();
         cols = (int) spinner2.getValue();
        frame.canvas.createGrid(rows, cols); // call createGrid method of DrawingPanel
        gridCreated=true;
        }
    }
}
