package homework;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngExporter {

    private BufferedImage image;
    private Component component;
    private String filePath = "board.png";

    public PngExporter(Component component, BufferedImage image) {
        this.image = image;
        this.component = component;
    }

    public void export() {

        image = new BufferedImage(component.getWidth(), component.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        component.printAll(graphics2D);
        graphics2D.dispose();

        try {
            ImageIO.write(image, "png", new File(filePath));
            JOptionPane.showMessageDialog(null, "Game board image exported successfully!", "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting game board image: " + e.getMessage(), "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
