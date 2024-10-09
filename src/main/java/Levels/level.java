package Levels;

import Gamecode.Game;
import Utilities.LodeSave;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author tansan
 */
public class level {

    private Game game;
    private BufferedImage tileSet;
    private BufferedImage tile;
    private BufferedImage[] bg;

    public level(Game game) {
        this.game = game;
        tile = LodeSave.getAsset("Background/tile.png");
        int count = 0;
        //tileSet = new BufferedImage[6][10];
//        for (int i = 0; i < tileSet.length; i++) {
//            for (int j = 0; j < tileSet[i].length; j++) {
//                tileSet[count] = tile.getSubimage(j * 32, i * 32, 32, 32);
//            }
//        }

        bg = new BufferedImage[5];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = LodeSave.getAsset("Background/" + (i + 1) + ".png");
        }
    }

    public void draw(Graphics g) {

        for (int i = 0; i < bg.length; i++) {
            if (bg != null) {
                g.drawImage(bg[i], 0, -1150, 2100, 2000, null);
            }
        }
        //g.drawImage(tileSet, 100, 100, null);
    }

    public void update() {
    }
}
