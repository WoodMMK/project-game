package Gamecode;


import Inputs.ButtonMouseListener;
import Inputs.KeyboardInputs;
import Utilities.Constants;
import Utilities.LodeSave;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {

    private BufferedImage[] bg;
    private Game game;
    private JLabel scoreLabel;
    private JLabel waveLabel;
    private Font scoreFont = LodeSave.getFont(Constants.fontName, Font.BOLD, 40);

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));

        scoreLabel = new JLabel("score : 0");
        scoreLabel.setFont(scoreFont);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(1020, 30, 1000, 50);
        this.setLayout(null);
        this.add(scoreLabel);
    }

    public Game getGame() {
        return game;
    }

    public void showWave() {
        Font waveFont = LodeSave.getFont(Constants.fontName, Font.BOLD, 60);
        waveLabel = new JLabel("WAVE " + Constants.wave);
        waveLabel.setFont(waveFont);
        waveLabel.setBounds(550, 0, 500, 500);
        waveLabel.setForeground(Color.WHITE);
        this.add(waveLabel);
        this.repaint();
    }

    public void removeWave() {
        this.remove(waveLabel);
        this.repaint();
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
