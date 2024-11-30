package Gamecode;

import Entities.Player;
import Utilities.*;
import java.awt.*;
import javax.swing.*;

public class GameWindow {

    private JFrame jframe;
    private Font scoreFont = LodeSave.getFont(Constants.fontName, Font.BOLD, 40);
    private JLabel nameLabel;
    private Image[] heart;

    public GameWindow(GamePanel gamepanel) {
        jframe = new JFrame();
        jframe.add(gamepanel);
        jframe.setTitle("BEAT that dogs");
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
        gamepanel.setLayout(null);

        nameLabel = new JLabel("name : " + Constants.playerName);
        nameLabel.setFont(scoreFont);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(30, 30, 1000, 50);

        heart = new Image[2];
        heart[0] = LodeSave.getImage("heart0.png");
        heart[1] = LodeSave.getImage("heart1.png");
        heart[0] = reScaleImage(heart[0], 3.0);
        heart[1] = reScaleImage(heart[1], 3.0);
        JPanel heartPanel = new heartPanel(heart, gamepanel, jframe);
        heartPanel.setBounds(30, 100, 500, 100);
        heartPanel.setOpaque(false);
        heartPanel.setBorder(null);

        gamepanel.add(nameLabel);
        gamepanel.add(heartPanel);
    }

    public Image reScaleImage(Image i, Double scale) {
        int Width = (int) (i.getWidth(null) * scale);
        int Height = (int) (i.getHeight(null) * scale);
        Image a = i.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        return a;
    }

    public JFrame getJFrame() {
        return jframe;
    }
}

class heartPanel extends JPanel {

    private Image[] heart;
    private Player player;
    private JFrame jframe;
    private Game game;
    private boolean closeFram;

    public heartPanel(Image[] i, GamePanel gamepanel, JFrame jframe) {
        heart = i;
        this.player = gamepanel.getGame().getPlayer();
        this.game = gamepanel.getGame();
        this.jframe = jframe;
        closeFram = false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int count = 0, border = 0;
        if (heart != null) {
            for (int i = 0; i < player.getMaxHP(); i++) {
                if (count < player.getCurHP()) {
                    g.drawImage(heart[0], i * heart[0].getWidth(null) + border, 0, heart[0].getWidth(null), heart[0].getHeight(null), this);
                    count++;
                } else {
                    g.drawImage(heart[1], i * heart[1].getWidth(null) + border, 0, heart[1].getWidth(null), heart[1].getHeight(null), this);
                }
                border += 10;
            }
        }
    }
}
