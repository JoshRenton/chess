package utility;

import javax.swing.*;
import java.awt.*;

/*
    Extends the JButton class to ensure that the button is square by default
 */
public class Square extends JButton {
    private final Coordinate coordinates;
    public Square(ImageIcon icon, Coordinate coordinates) {
        super(icon);
        this.coordinates = coordinates;

        // Make squares have no border padding
        Insets margins = new Insets(0, 0, 0, 0);
        this.setMargin(margins);

        this.setOpaque(true);
        this.setBorderPainted(false);
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    @Override
    public Dimension getPreferredSize() {
        // TODO: Un-hard code these values
        return new Dimension (100, 100);
    }

}
