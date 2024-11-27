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

        this.game = game;
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


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
