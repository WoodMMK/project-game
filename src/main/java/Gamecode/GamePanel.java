package Gamecode;

/**
 *
 * @author Gateaux
 */
import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import Utilities.Constants;
import Utilities.LodeSave;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private BufferedImage[] bg;
    private Game game;
    private JLabel scoreLabel;
    private Font scoreFont = LodeSave.getFont("dpcomic.ttf", Font.BOLD, 40);

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(new MouseInputs());
        addMouseMotionListener(new MouseInputs());

        scoreLabel = new JLabel("score : 0");;
        scoreLabel.setFont(scoreFont);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(1020, 30, 1000, 50);
        this.setLayout(null);
        this.add(scoreLabel);
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
        scoreLabel.setText("score : " + Constants.score);
    }
}
