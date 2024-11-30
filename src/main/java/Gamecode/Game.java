package Gamecode;

import Entities.*;
import Levels.level;
import Utilities.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Game implements Runnable {

    private GameWindow gamewindow;
    private GamePanel gamepanel;
    private Thread loopThread;
    private final int FPS_MAX = 120;
    private final int UPS_MAX = 200;
    private Player player;
    private level level;
    private ArrayList<Enemy> enemyGrop;
    private Enemy enemy;
    private boolean runnable = true;
    private boolean closeFram = false;
    private Random random = new Random();
    private int timer = 800;

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public GameWindow getGameWindow() {
        return gamewindow;
    }

    public Game() {
        player = new Player(0, 0, 300, 300, this);
        createSetEnemy();
        player.linkEnemy(enemyGrop);
        player.setPHP();
        level = new level(this);
        gamepanel = new GamePanel(this);
        gamewindow = new GameWindow(gamepanel);
        gamepanel.requestFocus();
        gamepanel.showWave();
        startGameLoop();
    }

    public void startGameLoop() {
        loopThread = new Thread(this);
        loopThread.start();
        System.out.println("start loop");
    }

    public void update() {
        //gamepanel.gupdate();
        boolean checkAlive = false;
        player.update();
        for (int i = 0; i < enemyGrop.size(); i++) {
            enemyGrop.get(i).update();
            if (enemyGrop.get(i).getCurHP() > 0) {
                checkAlive = true;
            }
        }
        if (!checkAlive) {
            timer = 800;
            Constants.wave++;
            gamepanel.showWave();
            createSetEnemy();
            player.linkEnemy(enemyGrop);
        }
        timer--;
        if (timer <= 0) {
            gamepanel.removeWave();
        }
        if (player.getCurHP() < 1 && !closeFram) {

            this.setRun(false);
            closeFram = true;
            JOptionPane.showMessageDialog(null, "your journey end here", "game end",
                    JOptionPane.INFORMATION_MESSAGE);
            
            gamewindow.getJFrame().dispose();

            new Menu();
        }
    }

    public void render(Graphics g) {
        level.draw(g);
        player.render(g);
        for (int i = 0; i < enemyGrop.size(); i++) {
            enemyGrop.get(i).render(g);
        }
    }

    public void setRun(boolean a) {
        runnable = a;
    }

    public boolean getRun() {
        return runnable;
    }

    public Thread getThread() {
        return loopThread;
    }

    public void createSetEnemy() {
        enemyGrop = new ArrayList<Enemy>();
        for (int i = 0; i < Constants.difficult; i++) {
            for (int j = 0; j < 4; j++) {
                int left_right = random.nextInt(0, 2);
                int posx;
                //posx = random.nextInt(0, 1000);
                if (left_right == 0) {
                    posx = random.nextInt(1800, 1900);
                } else {
                    posx = random.nextInt(-600, -500);
                }
                enemyGrop.add(new Enemy(posx, 0, 48 * 2, 32 * 2, this));
            }
        }
        for (int i = 0; i < enemyGrop.size(); i++) {
            enemyGrop.get(i).linkPlayer(player);
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_MAX;
        double timePerUpdate = 1000000000.0 / UPS_MAX;
        long previousFrame = System.nanoTime();
        long previouscheck = System.nanoTime();
        double fDiff = 0;
        double uDiff = 0;
        int frames = 0;
        int updates = 0;
        while (runnable) {
            long currentTime = System.nanoTime();
            fDiff += (currentTime - previousFrame) / timePerFrame;
            uDiff += (currentTime - previousFrame) / timePerUpdate;
            //System.out.printf("TPF : %f | TPU %f\n", timePerFrame, timePerUpdate);
            //System.out.printf("fdiff : %f | udiff %f\n", fDiff, uDiff);
            previousFrame = currentTime;
            //System.out.printf("%d\n", previousFrame);

            if (fDiff >= 1) {
                gamepanel.repaint();
                frames++;
                fDiff--;
            }
            if (uDiff >= 1) {
                update();
                updates++;
                uDiff--;
            }

            if (System.nanoTime() - previouscheck >= 1000000000) {
                previouscheck = System.nanoTime();
                //System.out.printf("FPS : %d | UPS : %d\n", frames, updates);
                frames = 0;
                updates = 0;
            }

        }
    }
}
