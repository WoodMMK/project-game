package Entities;

import Levels.LevelHandler;
import static Utilities.Constants.enemyConstants.*;
import Utilities.LodeSave;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @author Gateaux
 */
public class Enemy extends Entity {

    private int e_Action = idling;
    private boolean moveState = false, attack = false;
    private int facing = -1;
    int flipX;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private float xSpawn = 200, ySpawn = 500;
    private float xHitboxOffset = 10, yHitboxOffset = 20;
    private BufferedImage[][] animations;
    private BufferedImage img;

    private int moveDuration = 0;
    private int idleDuration = 0;
    private final int maxMoveTime = 50;
    private final int maxIdleTime = 100;
    private boolean LR;

    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.maxHP = 100;
        this.curHP = maxHP;
        getAnimations();
        createHitbox(xSpawn, ySpawn, 70, 44);
        //createAttackBox();
    }

    public void update() {
        changePos();
        updateAniTick();
        assignAni();
    }

    public void render(Graphics g) {
        g.drawImage(animations[e_Action][aniIndex],(int) (hitbox.x - xHitboxOffset) + flipX,(int) (hitbox.y - yHitboxOffset), width * facing, height, null);
        showHitbox(g);
        //showAttackBox(g);
    }

    public void getAnimations() {
        int maxRow = 4;
        int maxColumn = 8;
        animations = new BufferedImage[maxRow][maxColumn];

        int frameWidth = 48;
        int frameHeight = 32;
        int numSheets = 4;

        for (int k = 0; k < numSheets; k++) {
            img = LodeSave.getAsset("Enemy/Canine_White_" + k + ".png");
            //System.out.printf("Loaded sprite sheet %d\n", k);

            int cols = img.getWidth() / frameWidth;
            int rows = img.getHeight() / frameHeight;
            int totalSprites = cols * rows;

            //System.out.printf("Sprite sheet dimensions: %d x %d (rows: %d, cols: %d, total sprites: %d)\n", img.getWidth(), img.getHeight(), rows, cols, totalSprites);

            int spriteIndex = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (spriteIndex >= maxColumn) {
                        break;
                    }
                    animations[k][spriteIndex] = img.getSubimage(j * frameWidth, i * frameHeight, frameWidth, frameHeight);
                    spriteIndex++;
                }
            }
        }
        System.out.println("Finished");
    }

    private void assignAni() {
        int startAni = e_Action;
        if (moveState) {
            e_Action = running;
        } else {
            e_Action = idling;
        }

        if (attack) {
            e_Action = attacking;
        }

        if (e_Action != startAni) {
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(e_Action)) {
                aniIndex = 0;
            }
        }
    }

    public void changePos() {
        
        hitbox.y = LevelHandler.GroundPos;
        
        if (moveState) {
            if (LR) {
                //right
                flipX = width;
                hitbox.x += movespeed;
                facing = -1;
            } else {
                //left
                flipX = 0;
                hitbox.x -= movespeed;
                facing = 1;
            }

            moveDuration--;
            if (moveDuration <= 0) {
                moveState = false;
                idleDuration = new Random().nextInt(maxIdleTime) + 60;
            }
        } else {
            idleDuration--;
            if (idleDuration <= 0) {
                Random Rand = new Random();
                LR = Rand.nextBoolean();

                moveDuration = new Random().nextInt(maxMoveTime) + 20; // Random move time
                moveState = true;
            }
        }

        
    }

}
