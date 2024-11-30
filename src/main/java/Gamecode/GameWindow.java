package Gamecode;

/**
 *
 * @author Gateaux
 */
import Entities.Player;
import Utilities.Constants;
import Utilities.LodeSave;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameWindow {

    private JFrame jframe;
    private Font scoreFont = LodeSave.getFont("dpcomic.ttf", Font.BOLD, 40);
    private JLabel /*scoreLabel,*/ nameLabel;
    private Image[] heart;

    public GameWindow(GamePanel gamepanel) {
        jframe = new JFrame();
        jframe.add(gamepanel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(3);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
//        gamepanel.setLayout(null);

        nameLabel = new JLabel("name : " + Constants.playerName);;
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

//test heart
//        JButton test1B = new JButton("-heart");
//        test1B.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Constants.currHeart--;
//                gamepanel.requestFocus();
//            }
//        });
//        test1B.setBounds(300, 300, 100, 100);
//        gamepanel.add(test1B);
//
//        JButton test2B = new JButton("+heart");
//        test2B.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Constants.currHeart++;
//                gamepanel.requestFocus();
//            }
//        });
//        test2B.setBounds(410, 300, 100, 100);
//        gamepanel.add(test2B);
        //gamepanel.add(scoreLabel);
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
        if (player.getCurHP() < 1 && !closeFram) {
            game.setRun(false);
            closeFram = true;
            JOptionPane.showMessageDialog(null, "your journey end here", "game end",
                    JOptionPane.INFORMATION_MESSAGE);
            jframe.dispose();

            new Menu();
        }

    }
}
