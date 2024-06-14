package utility;

import javax.swing.*;
import java.awt.*;

/*
    Extends the JButton class to ensure that the button is square by default
 */
public class Square extends JButton {

    public Square(String text) {
        super(text);

        // Make squares have no border padding
        Insets margins = new Insets(0, 0, 0, 0);
        this.setMargin(margins);

        this.setOpaque(true);
        this.setBorderPainted(false);
    }
    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int s = (int)(Math.max(d.getWidth(), d.getHeight()));
        // TODO: Un-hard code these values
        return new Dimension (100, 100);
    }

}
