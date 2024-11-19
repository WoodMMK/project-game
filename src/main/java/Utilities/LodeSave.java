package Utilities;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author tansan
 */
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
    public static ImageIcon getIcon(String name){
        ImageIcon icon = new ImageIcon(path + "/" + name);
        return icon;
    }

}
