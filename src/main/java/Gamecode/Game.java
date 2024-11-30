package Gamecode;

import Entities.*;
import Levels.level;
import Utilities.Constants;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Gateaux
 */
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
    private Random random = new Random();

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Game() {
        //enemy = new Enemy(0, 0, 48 * 2, 32 * 2, this);
        player = new Player(0, 0, 300, 300, this);
        //enemy.linkPlayer(player);
        createSetEnemy();
        player.linkEnemy(enemyGrop);
        player.setPHP();
        level = new level(this);
        gamepanel = new GamePanel(this);
        gamewindow = new GameWindow(gamepanel);
        gamepanel.requestFocus();

        startGameLoop();
    }

    private void startGameLoop() {
        loopThread = new Thread(this);
        loopThread.start();
    }

    public void update() {
        //gamepanel.gupdate();
        boolean checkAlive = false;
        player.update();
        for (int i = 0; i < enemyGrop.size(); i++) {
            enemyGrop.get(i).update();
            if(enemyGrop.get(i).getCurHP()>0){
                checkAlive = true;
            }
        }
        if(!checkAlive){
            createSetEnemy();
            player.linkEnemy(enemyGrop);
        }
        //enemy.update();
        //level.update();
    }

    public void render(Graphics g) {
        level.draw(g);
        player.render(g);
        //boolean checkAlive = false;
        for (int i = 0; i < enemyGrop.size(); i++) {
            enemyGrop.get(i).render(g);
//            if (enemyGrop.get(i).getCurHP() > 0) {
//                checkAlive = true;
//            }
        }
//        if (!checkAlive) {
//            createSetEnemy();
//            player.linkEnemy(enemyGrop);
//        }
        //enemy.render(g);
    }

    void setRun(boolean a) {
        runnable = a;
    }

    public void createSetEnemy() {
        enemyGrop = new ArrayList<Enemy>();
        for (int i = 0; i < Constants.difficult; i++) {
            for (int j = 0; j < 4; j++) {
                int left_right = random.nextInt(0, 2);
                int posx;
                posx = random.nextInt(0, 1000);
//        if (left_right == 0) {
//            posx = 1000;
//        } else {
//            posx = 0;
//        }
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
