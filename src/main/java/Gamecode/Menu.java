package Gamecode;

import Utilities.LodeSave;
import Utilities.MySound;
import Utilities.SoundManager;
import static Utilities.Constants.soundConstants.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author tansan
 */
public class Menu {

    private String title = "ONE NIGHT MIRACLE";
    private JFrame jframe;
    private JPanel startPanel, settingPanel, creditPanel;
    private JButton startB, settingB, backB, quitB, creditB, backB2;
    private JToggleButton[] soundB, difficultB;
    private ButtonGroup soundGroup, difficultGrop;

    private ImageIcon[] startI, settingI, backI, quitI, creditI, checkI;
    private JSlider volume;
    private JComboBox song;
    private String[] songList = {"1", "2", "3", "4", "5"};

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;
    private Image defaultBG, settingPlate;
    
    private Font headerFont = LodeSave.getFont("dpcomic.ttf", Font.BOLD, 60),
            defaultFont = LodeSave.getFont("dpcomic.ttf", Font.BOLD, 45),
            choicFont = LodeSave.getFont("dpcomic.ttf", Font.PLAIN, 25),
            creditFont = LodeSave.getFont("dpcomic.ttf", Font.BOLD, 35);

    public Menu() {
        SoundManager.playTheme(Music1);
        readPic();

        startPanel = new newPanelBaG(defaultBG);
        settingPanel = new newPanelBaG(defaultBG);

        mainPanel = new JPanel(cardLayout);
        Dimension windowSize = new Dimension(1280, 720);
        startPanel.setPreferredSize(windowSize);
        settingPanel.setPreferredSize(windowSize);
        mainPanel.setPreferredSize(windowSize);

        jframe = new JFrame();
        jframe.add(mainPanel);
        jframe.pack();
        jframe.setTitle(title);
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
        startB.setFocusPainted(false);
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
        settingB.setFocusPainted(false);
        settingB.addMouseListener(new buttonMouseListener(settingB, settingI));

        backB = new JButton(backI[0]);
        backB.addActionListener(backButtonActionListener);
        backB.setBorder(null);
        backB.setContentAreaFilled(false);
        backB.setFocusPainted(false);
        backB.addMouseListener(new buttonMouseListener(backB, backI));

        backB2 = new JButton(backI[0]);
        backB2.addActionListener(backButtonActionListener);
        backB2.setBorder(null);
        backB2.setFocusPainted(false);
        backB2.setContentAreaFilled(false);
        backB2.addMouseListener(new buttonMouseListener(backB2, backI));

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
        quitB.setFocusPainted(false);
        quitB.addMouseListener(new buttonMouseListener(quitB, quitI));

        creditB = new JButton(creditI[0]);
        creditB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditB.setIcon(creditI[1]);
                cardLayout.show(mainPanel, "credit");
            }
        });
        creditB.setBorder(null);
        creditB.setContentAreaFilled(false);
        creditB.setFocusPainted(false);
        creditB.addMouseListener(new buttonMouseListener(creditB, creditI));

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
                SoundManager.updateAllVolume((float) volume.getValue() / 100);
            }
        });
        soundB[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                SoundManager.updateAllVolume(0.0f);
            }
        });

        soundB[1].setBorder(null);
        soundB[1].setContentAreaFilled(false);
        soundB[1].setFocusPainted(false);
        soundB[0].setBorder(null);
        soundB[0].setContentAreaFilled(false);
        soundB[0].setFocusPainted(false);

        soundB[1].setIcon(checkI[0]);
        soundB[1].setSelectedIcon(checkI[1]);
        soundB[0].setIcon(checkI[0]);
        soundB[0].setSelectedIcon(checkI[1]);


        JLabel valueLabel = new JLabel("volume: 50");
        valueLabel.setFont(choicFont);
        valueLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        volume = new JSlider(1, 100, 50);
        volume.setBorder(null);
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valueLabel.setText("volume: " + volume.getValue());
                if(soundB[0].isSelected()){
                    SoundManager.updateAllVolume((float) volume.getValue() / 100);
                }
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
                g.drawImage(trackBG, trackRect.x - 5, trackRect.y, trackRect.width + 10, trackRect.height, null);
                g.drawImage(fillimg, trackRect.x, trackRect.y, filledWidth, trackRect.height, null);
                g.drawImage(trackimg, 0, (slider.getHeight() - trackHeight) / 2, trackWidth, trackHeight, null);

            }

            @Override
            public void paintThumb(Graphics g) {
                g = null;
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

        difficultB[0].setContentAreaFilled(false);
        difficultB[0].setOpaque(false);
        difficultB[0].setBorderPainted(false);
        difficultB[0].setFocusPainted(false);
        difficultB[1].setContentAreaFilled(false);
        difficultB[1].setOpaque(false);
        difficultB[1].setBorderPainted(false);
        difficultB[1].setFocusPainted(false);
        difficultB[2].setContentAreaFilled(false);
        difficultB[2].setOpaque(false);
        difficultB[2].setBorderPainted(false);
        difficultB[2].setFocusPainted(false);

        difficultB[1].setIcon(checkI[0]);
        difficultB[1].setSelectedIcon(checkI[1]);
        difficultB[0].setIcon(checkI[0]);
        difficultB[0].setSelectedIcon(checkI[1]);
        difficultB[2].setIcon(checkI[0]);
        difficultB[2].setSelectedIcon(checkI[1]);

        song = new JComboBox(songList);
        song.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selected = (String) song.getSelectedItem();
                int number = Integer.valueOf(selected);
                SoundManager.stopTheme();
                switch (number) {
                    case 1:
                        SoundManager.playTheme(Music1);
                        break;
                    case 2:
                        SoundManager.playTheme(Music2);
                        break;
                    case 3:
                        SoundManager.playTheme(Music3);
                        break;
                    case 4:
                        SoundManager.playTheme(Music4);
                        break;
                    case 5:
                        SoundManager.playTheme(Music5);
                        break;
                }
            }
        });

        startPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 25, 10, 100);
        c.anchor = GridBagConstraints.CENTER;
        //c.ipady = 10;
        c.gridx = 0;
        c.gridy = 0;
        startPanel.add(startB, c);
        c.gridy = 1;
        startPanel.add(settingB, c);
        c.gridy = 2;
        startPanel.add(quitB, c);
        c.gridy = 3;
        //c.ipadx = 100;
        c.weightx = 1;
        c.anchor = GridBagConstraints.EAST;
        startPanel.add(creditB, c);

        JPanel settingPlatePanel = new newPanelBaG(settingPlate);
        settingPanel.add(settingPlatePanel);
        c = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        b = new GridBagConstraints();
        settingPanel.setLayout(new GridBagLayout());
        settingPlatePanel.setOpaque(false);
        settingPlatePanel.setBorder(null);
        settingPlatePanel.setLayout(new GridBagLayout());
        settingPlatePanel.setPreferredSize(new Dimension(600, 450));
        JLabel settingLabel = newTitleLabel("Setting");
        settingLabel.setFont(headerFont);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.ipady = 30;
        c.anchor = GridBagConstraints.CENTER;
        settingPlatePanel.add(settingLabel, c);

        JLabel selectDifLabel = newTitleLabel("Select Difficulty");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        settingPlatePanel.add(selectDifLabel, c);
        JPanel difficultP = new JPanel();
        difficultP.setOpaque(false);
        difficultP.setLayout(new GridBagLayout());
        b.insets = new Insets(0, 15, 0, 0);
        b.gridx = 0;
        b.gridy = 0;
        b.anchor = GridBagConstraints.WEST;
        difficultP.add(difficultB[0], b);
        difficultB[0].setFont(choicFont);
        b.gridy = 1;
        difficultP.add(difficultB[1], b);
        difficultB[1].setFont(choicFont);
        b.gridy = 2;
        difficultP.add(difficultB[2], b);
        difficultB[2].setFont(choicFont);
        c.gridx = 2;
        c.gridy = 1;
        c.ipady = 20;
        settingPlatePanel.add(difficultP, c);
        c.ipady = 0;

        JLabel selectSongLabel = newTitleLabel("Select Song");
        c.gridx = 0;
        c.gridy = 2;
        settingPlatePanel.add(selectSongLabel, c);
        c.gridx = 2;
        c.insets = new Insets(0, 15, 0, 0);
        settingPlatePanel.add(song, c);
        c.insets = new Insets(0, 0, 0, 0);
        song.setFont(choicFont);

        JLabel on_offLebel = newTitleLabel("Sound");
        c.gridy = 3;
        c.gridx = 0;
        settingPlatePanel.add(on_offLebel, c);
        JPanel on_offPanel = new JPanel();
        on_offPanel.setLayout(new GridBagLayout());
        on_offPanel.setOpaque(false);
        on_offPanel.setBorder(null);
        b.gridy = 0;
        b.gridx = 0;
        on_offPanel.add(soundB[0], b);
        soundB[0].setFont(choicFont);
        b.gridx = 1;
        on_offPanel.add(soundB[1], b);
        soundB[1].setFont(choicFont);
        c.gridx = 2;
        c.gridy = 3;
        settingPlatePanel.add(on_offPanel, c);

        JLabel volumeLabel = newTitleLabel("Volume");
        c.gridx = 0;
        c.gridy = 4;
        settingPlatePanel.add(volumeLabel, c);
        JPanel soundSlide = new JPanel();
        soundSlide.setOpaque(false);
        soundSlide.setLayout(new GridBagLayout());
        b.gridy = 1;
        soundSlide.add(volume, b);
        b.gridy = 0;
        soundSlide.add(valueLabel, b);
        c.gridx = 2;
        c.gridy = 4;
        settingPlatePanel.add(soundSlide, c);

        c.gridy = 1;
        c.gridx = 0;
        c.ipady = 50;
        c.anchor = GridBagConstraints.CENTER;
        settingPanel.add(backB, c);

        JLabel creditLabelHead = newTitleLabel("Credits");
        creditPanel = new newPanelBaG(defaultBG);
        JPanel creditPlatePanel = new newPanelBaG(settingPlate);
        creditPanel.setLayout(new GridBagLayout());
        creditPanel.add(creditPlatePanel);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        creditPlatePanel.add(creditLabelHead, c);
        creditPlatePanel.setOpaque(false);
        creditPlatePanel.setBorder(null);
        creditPlatePanel.setLayout(new GridBagLayout());
        creditPlatePanel.setPreferredSize(new Dimension(750, 450));

        JPanel creditPlateNamePanel = new JPanel();
        creditPlateNamePanel.setLayout(new GridBagLayout());
        creditPlateNamePanel.setOpaque(false);
        creditPlateNamePanel.setBorder(null);
        JLabel creditLabelFrist[] = new JLabel[4];
        JLabel creditLabelID[] = new JLabel[4];
        JLabel creditLabelLast[] = new JLabel[4];
        creditLabelFrist[0] = newTitleLabel("Nantaphop");
        creditLabelFrist[1] = newTitleLabel("Rapeepat");
        creditLabelFrist[2] = newTitleLabel("Bhwin");
        creditLabelFrist[3] = newTitleLabel("Maimongkol");
        creditLabelLast[0] = newTitleLabel("Nawaphanpimol");
        creditLabelLast[1] = newTitleLabel("Boolsuk");
        creditLabelLast[2] = newTitleLabel("Thongrueang");
        creditLabelLast[3] = newTitleLabel("Thokanokwan");
        creditLabelID[0] = newTitleLabel("6613120");
        creditLabelID[1] = newTitleLabel("6613269");
        creditLabelID[2] = newTitleLabel("6613266");
        creditLabelID[3] = newTitleLabel("6613268");
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 20;
        for (int i = 0; i < creditLabelFrist.length; i++) {
            creditLabelFrist[i].setFont(creditFont);
            creditLabelFrist[i].setForeground(Color.WHITE);
            creditLabelID[i].setFont(creditFont);
            creditLabelID[i].setForeground(Color.WHITE);
            creditLabelLast[i].setFont(creditFont);
            creditLabelLast[i].setForeground(Color.WHITE);
            c.gridy = i + 1;
            c.gridx = 0;
            creditPlateNamePanel.add(creditLabelFrist[i], c);
            c.gridx = 1;
            creditPlateNamePanel.add(creditLabelLast[i], c);
            c.gridx = 2;
            creditPlateNamePanel.add(creditLabelID[i], c);
        }
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 60;
        creditPlatePanel.add(creditPlateNamePanel, c);


        c.anchor = GridBagConstraints.CENTER;        
        c.gridy = 1;
        c.gridx = 0;
        c.ipady = 20;
        creditPanel.add(backB2, c);
        
        mainPanel.add(startPanel, "main");
        mainPanel.add(settingPanel, "setting");
        mainPanel.add(creditPanel, "credit");

        jframe.validate();
    }

    public void readPic() {
        try {
            startI = new ImageIcon[2];
            backI = new ImageIcon[2];
            quitI = new ImageIcon[2];
            settingI = new ImageIcon[2];
            creditI = new ImageIcon[2];
            checkI = new ImageIcon[2];
            defaultBG = LodeSave.getImage("defaultBg.png");
            settingPlate = LodeSave.getImage("settingPlate.png");
            for (int i = 0; i < 2; i++) {
                checkI[i] = LodeSave.getIcon("button/Check" + i + ".png");
                startI[i] = LodeSave.getIcon("button/Play" + i + ".png");
                backI[i] = LodeSave.getIcon("button/Back" + i + ".png");
                quitI[i] = LodeSave.getIcon("button/Exit" + i + ".png");
                settingI[i] = LodeSave.getIcon("button/Opt" + i + ".png");
                creditI[i] = LodeSave.getIcon("button/credit" + i + ".png");
                reScaleIcon(startI[i]);
                reScaleIcon(checkI[i], 25, 25);
                reScaleIcon(backI[i]);
                reScaleIcon(quitI[i]);
                reScaleIcon(settingI[i]);
                reScaleIcon(creditI[i], 80, 80);
            }
        } catch (Exception e) {
            System.out.println("err " + e);
        }
    }

    public void reScaleIcon(ImageIcon i, int x, int y) {
        Image a = i.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
        i.setImage(a);
    }

    public void reScaleIcon(ImageIcon i) {
        double scale = 6.5;
        int Width = (int) (i.getIconWidth() * scale);
        int Height = (int) (i.getIconHeight() * scale);
        Image a = i.getImage().getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        i.setImage(a);
    }

    ActionListener backButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "main");
        }
    };

    private static class newPanelBaG extends JPanel {

        private Image backgroundImage;

        public newPanelBaG(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private JLabel newTitleLabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(defaultFont);
        label.setForeground(Color.WHITE);
        return label;
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
        SoundManager.playOnce(SOUND_BUTTON_HOLD);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button.setIcon(icon[0]);
        SoundManager.playOnce(SOUND_BUTTON_RELEASED);
    }
}
