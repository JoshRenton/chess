package utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Icons {
    private static final File iconDir = new File("icons");

    public static ImageIcon loadIcon(String iconFilename) {
        File file = new File(iconDir.getPath() + "/" + iconFilename);
        try {
            Image img = ImageIO.read(file);
            // TODO: Remove hard-coded values
            Image scaledImg = img.getScaledInstance(64, 64, Image.SCALE_DEFAULT);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            System.err.println("Failed to read icon file: " + file.getPath());
            return null;
        }
    }
}
