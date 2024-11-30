package Utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LodeSave {
    static String path = "src/main/resources/assets/";

    public static BufferedImage getAsset(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path + "/" + name));

        } catch (IOException e) {
            System.err.println(name + " err");
        }
        return img;
    }

    public static ImageIcon getIcon(String name) {
        ImageIcon icon = new ImageIcon(path + "/" + name);
        return icon;
    }

    public static Image getImage(String name) {
        Image pic = null;
        try {
            pic = ImageIO.read(new File(path + "/" + name));

        } catch (IOException e) {
            System.err.println(name + " err");
        }
        return pic;
    }

    public static Font getFont(String name, int style, int size) {
        Font font = null;
        try {
            File file = new File(path + "/" + name);
            font = Font.createFont(Font.TRUETYPE_FONT, file);
            font = font.deriveFont(style, size);
        } catch (IOException e) {
            System.err.println(name + " err " + e);
        } catch (FontFormatException ex) {
            System.err.println(name + " err ");
        }
        return font;
    }

}
