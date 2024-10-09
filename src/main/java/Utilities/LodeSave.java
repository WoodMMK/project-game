package Utilities;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author tansan
 */
public class LodeSave {
    public static BufferedImage getAsset(String name) {
        String path = "src/main/resources/assets/";
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path + "/" + name));

        } catch (IOException e) {
            System.err.println(name + " err");
        }
        return img;
    }

}
