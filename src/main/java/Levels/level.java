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
    private BufferedImage[] bg, cd;
    int cameraX;


    public level(Game game) {
        this.game = game;

        bg = new BufferedImage[5];
        cd = new BufferedImage[2];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = LodeSave.getAsset("Background/Background_" + (i + 1) + ".png");
        }
        for (int i = 0; i < cd.length; i++) {
            cd[i] = LodeSave.getAsset("Background/Ground" + (i + 1) + ".png");
        }
    }

    public void draw(Graphics g) {
        int count = bg.length;
        cameraX = (int) game.getPlayer().getX() - 640;
        for (int i = 0; i < bg.length; i++) {
            if (bg != null) {
                g.drawImage(bg[i], (cameraX - 700) / (count * 2), -1400, 2400, 2400, null);
                count--;
            }
        }
        for (int j = 0; j < cd.length; j++) {
            if (cd != null) {
                g.drawImage(cd[j], cameraX / 3 - 300, 460, 928 * 2, 180 * 2, null);
            }

        }
    }

}
