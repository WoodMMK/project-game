package Gamecode;

/**
 *
 * @author Gateaux
 */
import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import Utilities.LodeSave;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private BufferedImage[] bg;
    private Game game;

    public GamePanel(Game game) {

        importImg();
        this.game = game;
        //getAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(new MouseInputs());

        addMouseMotionListener(new MouseInputs());

    }

    public Game getGame() {
        return game;
    }

    public void setPanelSize() {
        Dimension windowsize = new Dimension(1280, 720);
        setPreferredSize(windowsize);
    }

    public void importImg() {
        //String path = "src/main/resources/assets/";
        //String path_bg = "src/main/resources/assets/BackGround/";
        bg = new BufferedImage[5];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = LodeSave.getAsset("Background/" + (i + 1) + ".png");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < bg.length; i++) {
            if (bg != null) {
                g.drawImage(bg[i], 0, -1150, 2100, 2000, null);
            }
        }
        //super.paintComponent(g);
        game.render(g);
    }
}
