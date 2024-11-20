package Gamecode;

import Utilities.LodeSave;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author tansan
 */
public class Menu {

    private JFrame jframe;
    private JPanel startPanel, settingPanel;
    private JButton startB, settingB, backB, quitB;
    private JToggleButton[] soundB, difficultB;
    private ButtonGroup soundGroup, difficultGrop;

    private ImageIcon[] startI, settingI, backI, quitI;
    private JSlider volume;
    private JComboBox song;
    private String[] songList = {"1", "2", "3", "4", "5"};

    public Menu() {
        readPic();
        startPanel = new JPanel();
        settingPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        Dimension windowSize = new Dimension(1280, 720);
        startPanel.setPreferredSize(windowSize);
        settingPanel.setPreferredSize(windowSize);
        mainPanel.setPreferredSize(windowSize);

        jframe = new JFrame();
        jframe.add(mainPanel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setLocation(100, 100);

        startB = new JButton(startI[0]);
        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startB.setIcon(startI[1]);
                new Game();
                jframe.dispose();
            }
        });
        startB.addMouseListener(new buttonMouseListener(startB, startI));
        startB.setBorder(null);
        startB.setContentAreaFilled(false);

        settingB = new JButton(settingI[0]);
        settingB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "setting");
            }
        });
        settingB.setBorder(null);
        settingB.setContentAreaFilled(false);
        settingB.addMouseListener(new buttonMouseListener(settingB, settingI));

        backB = new JButton(backI[0]);
        backB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(mainPanel);
            }
        });
        backB.setBorder(null);
        backB.setContentAreaFilled(false);
        backB.addMouseListener(new buttonMouseListener(backB, backI));

        quitB = new JButton(quitI[0]);
        quitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframe.dispose();
                System.exit(0);
            }
        });
        quitB.setBorder(null);
        quitB.setContentAreaFilled(false);
        quitB.addMouseListener(new buttonMouseListener(quitB, quitI));

        soundB = new JToggleButton[2];
        soundGroup = new ButtonGroup();
        soundB[0] = new JRadioButton("On");
        soundB[0].setName("On");
        soundB[1] = new JRadioButton("Off");
        soundB[1].setName("Off");
        soundB[0].setSelected(true);
        soundGroup.add(soundB[0]);
        soundGroup.add(soundB[1]);
        soundB[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //sound on
            }
        });
        soundB[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //sound off
            }
        });

        JLabel valueLabel = new JLabel("sound volume: 50");
        valueLabel.setPreferredSize(new Dimension(200, 50));
        volume = new JSlider(0, 100, 50);
        volume.setOrientation(JSlider.HORIZONTAL);
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                // Print the current value of the slider
                valueLabel.setText("sound volume: " + volume.getValue());
            }
        });
        volume.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int sliderWidth = volume.getWidth();
                int value = (int) ((double) mouseX / sliderWidth * (volume.getMaximum() - volume.getMinimum()));
                volume.setValue(volume.getMinimum() + value);
            }
        });
        volume.setUI(new BasicSliderUI(volume) {
            @Override
            public void paintTrack(Graphics g) {
                Image trackimg = LodeSave.getAsset("bar.png");
                Image fillimg = LodeSave.getAsset("fill.png");
                Image trackBG = LodeSave.getAsset("barBG.png");
                int trackHeight = slider.getHeight();
                int trackWidth = slider.getWidth();
                int currentValue = slider.getValue();
                int filledWidth = (int) (((double) currentValue - slider.getMinimum())
                        / (slider.getMaximum() - slider.getMinimum()) * trackWidth);

//                g.setColor(Color.BLUE); 
//                g.fillRect(trackRect.x, trackRect.y, filledWidth, trackRect.height);
                g.drawImage(trackBG, trackRect.x-5, trackRect.y, trackRect.width+10, trackRect.height, null);
                g.drawImage(fillimg, trackRect.x, trackRect.y, filledWidth, trackRect.height, null);
                g.drawImage(trackimg, 0, (slider.getHeight() - trackHeight) / 2, trackWidth, trackHeight, null);

            }

            @Override
            public void paintThumb(Graphics g) {
                g=null;
//                g.setColor(Color.RED);
//                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }
            @Override
            public void paintFocus(Graphics g) {
                //remove highlight when select
            }
        });

        difficultB = new JToggleButton[3];
        difficultGrop = new ButtonGroup();
        difficultB[0] = new JRadioButton("Easy");
        difficultB[0].setName("Easy");
        difficultB[1] = new JRadioButton("Normal");
        difficultB[1].setName("Normal");
        difficultB[2] = new JRadioButton("Hard");
        difficultB[2].setName("Hard");
        difficultB[0].setSelected(true);
        difficultGrop.add(difficultB[0]);
        difficultGrop.add(difficultB[1]);
        difficultGrop.add(difficultB[2]);

        song = new JComboBox(songList);
        song.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //change song
            }
        });

        startPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 10, 0);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        startPanel.add(startB, c);
        c.gridy = 1;
        startPanel.add(settingB, c);
        c.gridy = 2;
        startPanel.add(quitB, c);

        settingPanel.setLayout(new GridBagLayout());
        JPanel difficultP = new JPanel();
        difficultP.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        difficultP.add(difficultB[0], c);
        c.gridy = 1;
        difficultP.add(difficultB[1], c);
        c.gridy = 2;
        difficultP.add(difficultB[2], c);
        c.gridy = 0;
        settingPanel.add(difficultP, c);

        settingPanel.add(song);

        JPanel soundPanel = new JPanel();
        soundPanel.setLayout(new GridBagLayout());
        JPanel on_offP = new JPanel();
        on_offP.setLayout(new GridBagLayout());
        c.gridy = 0;
        c.gridx = 0;
        on_offP.add(soundB[0], c);
        c.gridx = 1;
        on_offP.add(soundB[1], c);
        c.gridx = 0;
        soundPanel.add(on_offP, c);

        JPanel soundSlide = new JPanel();
        soundSlide.setLayout(new GridBagLayout());
        c.gridx = 0;
        soundSlide.add(volume);
        c.gridx = 1;
        soundSlide.add(valueLabel, c);

        soundPanel.add(soundSlide, c);

        c.gridy = 1;
        c.gridx = 0;
        settingPanel.add(soundPanel, c);

        c.gridy = 2;
        c.gridx = 0;
        settingPanel.add(backB, c);

        mainPanel.add(startPanel, "main");
        mainPanel.add(settingPanel, "setting");

        jframe.validate();
    }

    public void readPic() {
        try {
            startI = new ImageIcon[2];
            backI = new ImageIcon[2];
            quitI = new ImageIcon[2];
            settingI = new ImageIcon[2];
            for (int i = 0; i < 2; i++) {
                startI[i] = LodeSave.getIcon("button/Play" + i + ".png");
                backI[i] = LodeSave.getIcon("button/Back" + i + ".png");
                quitI[i] = LodeSave.getIcon("button/Exit" + i + ".png");
                settingI[i] = LodeSave.getIcon("button/Opt" + i + ".png");
                reScaleIcon(startI[i]);
                reScaleIcon(backI[i]);
                reScaleIcon(quitI[i]);
                reScaleIcon(settingI[i]);
            }
        } catch (Exception e) {
            System.out.println("err " + e);
        }
    }

    public void reScaleIcon(ImageIcon i) {
        double scale = 0.5;
        int Width = (int) (i.getIconWidth() * scale);
        int Height = (int) (i.getIconHeight() * scale);
        Image a = i.getImage().getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        i.setImage(a);
    }

}

class buttonMouseListener extends MouseAdapter {

    private JButton button;
    private ImageIcon[] icon;

    public buttonMouseListener(JButton button, ImageIcon[] i) {
        this.button = button;
        this.icon = i;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        button.setIcon(icon[1]);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button.setIcon(icon[0]);
    }

}
