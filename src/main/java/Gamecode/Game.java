package Gamecode;

import Entities.*;
import Levels.level;
import Utilities.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
/*
Nantaphop Nawaphanpimol 6613120 
Rapeepat Boolsuk 6613269
Bhwin Thongrueang 6613266
Maimongkol Thokanokwan 6613268
*/
public class Game implements Runnable {

    private GameWindow gamewindow;
    private MainApplication menu;
    private GamePanel gamepanel;
    private Thread loopThread;
    private final int FPS_MAX = 120;
    private final int UPS_MAX = 200;
    private Player player;
    private level level;
    private ArrayList<Enemy> enemyGroup;
    private Enemy enemy;
    private boolean runnable = true;
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

    public Game(MainApplication menu) {
        player = new Player(450, 50, 300, 300, this);
        this.menu = menu;
        Constants.wave = 1;
        Constants.score = 0;
        createSetEnemy();
        player.linkEnemy(enemyGroup);
        player.setPHP();
        level = new level(this);
        gamepanel = new GamePanel(this);
        gamewindow = new GameWindow(gamepanel);
        gamepanel.requestFocus();
        gamepanel.showWave();

        startGameLoop();
    }

    public MainApplication getMenu() {
        return menu;
    }

    public ArrayList<Enemy> getEnemyGroup() {
        return enemyGroup;
    }

    public void startGameLoop() {
        loopThread = new Thread(this);
        loopThread.start();
    }

    public void update() {
        Constants.numberOfEnemy = 0;
        for (int i = 0; i < this.getEnemyGroup().size(); i++) {
            if (this.getEnemyGroup().get(i).getCurHP() > 0) {
                Constants.numberOfEnemy++;
            }
        }
        boolean checkAlive = false;
        player.update();
        for (int i = 0; i < enemyGroup.size(); i++) {
            enemyGroup.get(i).update();
            if (enemyGroup.get(i).getCurHP() > 0) {
                checkAlive = true;
            }
        }
        if (!checkAlive && Constants.enemyAniEnd) {
            timer = 800;
            Constants.wave++;
            gamepanel.showWave();
            createSetEnemy();
            player.linkEnemy(enemyGroup);
        }
        timer--;
        if (timer <= 0) {
            gamepanel.removeWave();
        }
    }

    public void render(Graphics g) {
        level.draw(g);
        player.render(g);
        for (int i = 0; i < enemyGroup.size(); i++) {
            enemyGroup.get(i).render(g);
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
        enemyGroup = new ArrayList<>();
        for (int i = 0; i < Constants.difficult; i++) {
            for (int j = 0; j < 4; j++) {
                int left_right = random.nextInt(0, 2);
                int posx;
                if (left_right == 0) {
                    posx = random.nextInt(1800, 1900);
                } else {
                    posx = random.nextInt(-600, -500);
                }
                enemyGroup.add(new Enemy(posx, 0, 48 * 2, 32 * 2, this));
            }
        }
        for (int i = 0; i < enemyGroup.size(); i++) {
            enemyGroup.get(i).linkPlayer(player);
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
            previousFrame = currentTime;

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
                frames = 0;
                updates = 0;
            }

        }
    }
}
