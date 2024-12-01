package Entities;

import Levels.LevelHandler;
import static Utilities.Constants.enemyConstants.*;
import Gamecode.*;
import Utilities.Constants;
import Utilities.LodeSave;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
/*
Nantaphop Nawaphanpimol 6613120 
Rapeepat Boolsuk 6613269
Bhwin Thongrueang 6613266
Maimongkol Thokanokwan 6613268
*/
public class Enemy extends Entity {

    private Player player;
    private boolean thisEntityDie = false;
    private int enemyScore;
    private double enemySpeed;

    private int e_Action = idling;
    private boolean moveState = true, attack = false, aniDone = false;
    private int facing = -1;
    int flipX;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private float xSpawn = 200, ySpawn = 500;
    private float xHitboxOffset = 10 * 2, yHitboxOffset = 20 * 2;
    private BufferedImage[][] animations;
    private BufferedImage img;

    private int moveDuration = 0;
    private int idleDuration = 0;
    private final int maxMoveTime = 50;
    private final int maxIdleTime = 100;
    private boolean LR;

    private Random random = new Random();
    private int randomInt;

    public Enemy(float x, float y, int width, int height, Game game) {
        super(x, y, width, height);
        randomInt = random.nextInt(0, 4);
        getAnimations();
        createHitbox(x, ySpawn, 70, 44);
        createAttackBox();
    }

    public void linkPlayer(Player player) {
        this.player = player;
    }

    public void update() {
        changePos();
        updateHit();
        updateAniTick();
        assignAni();
        updateAttackBox();
        if (curHP <= 0 && !thisEntityDie) {
            thisEntityDie = true;
            Constants.score += enemyScore;
        }
    }

    public void render(Graphics g) {
        if (!aniDone) {
            g.drawImage(animations[e_Action][aniIndex], (int) (hitbox.x - xHitboxOffset) + flipX, (int) (hitbox.y - yHitboxOffset), width * facing, height, null);
        }
    }

    public void getAnimations() {
        int maxRow = 4;
        int maxColumn = 8;
        animations = new BufferedImage[maxRow][maxColumn];

        int frameWidth = 48;
        int frameHeight = 32;
        int numSheets = 4;

        for (int k = 0; k < numSheets; k++) {
            if (randomInt == 0) {
                img = LodeSave.getAsset("Enemy/Canine_White_" + k + ".png");
                this.setHP(1);
                enemyScore = 100;
                enemySpeed = 0.7 * 1.3;
            }
            if (randomInt == 1) {
                img = LodeSave.getAsset("Enemy/Canine_Black_" + k + ".png");
                //this.setHP(4);
                this.setHP(1);
                enemyScore = 400;
                enemySpeed = 1.3 * 1.3;
            }
            if (randomInt == 2) {
                img = LodeSave.getAsset("Enemy/Canine_Brown_" + k + ".png");
                //this.setHP(2);
                this.setHP(1);
                enemyScore = 200;
                enemySpeed = 0.85 * 1.3;
            }
            if (randomInt == 3) {
                img = LodeSave.getAsset("Enemy/Canine_Gray_" + k + ".png");
                //this.setHP(3);
                this.setHP(1);
                enemyScore = 300;
                enemySpeed = 1 * 1.3;
            }
            int cols = img.getWidth() / frameWidth;
            int rows = img.getHeight() / frameHeight;
            int totalSprites = cols * rows;
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

        if (curHP <= 0) {
            e_Action = dead;
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
            Constants.enemyAniEnd = false;
            if(attack){
                if(aniIndex >= 2 && aniIndex <= 4){
                    if(attackBox.intersects(player.getHitbox())){
                        player.takeDamage((int)hitbox.x);
                    }
                }
            }
            if (aniIndex >= getSpriteAmount(e_Action)) {
                if (curHP <= 0) {
                    aniDone = true;
                    Constants.enemyAniEnd = true;
                }
                aniIndex = 0;
                attack = false;
            }
        }
    }

    public void changePos() {

        hitbox.y = LevelHandler.GroundPos + 11 * 2;
        
        if (player.hitbox.x >= hitbox.x+ 70)
            LR = true;
        else if(player.hitbox.x <= hitbox.x - 70)
            LR = false;
        
        if (moveState) {
            if (LR) {
                //right
                flipX = width;
                hitbox.x += enemySpeed;
                facing = -1;
            } else {
                //left
                flipX = 0;
                hitbox.x -= enemySpeed;
                facing = 1;
            }
        }
    }

    private void createAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) 30 * 2, (int) 44 * 2);
    }

    private void updateAttackBox() {
        if(attackBox.intersects(player.getHitbox())){
            attack = true;
        }
        if (LR) {
            attackBox.x = hitbox.x + hitbox.width - 12 * 2;
        } else if (!LR) {
            attackBox.x = hitbox.x - hitbox.width + 50 * 2;
        }
        attackBox.y = hitbox.y;
    }

    public void hit() {
        curHP--;
    }

    private void updateHit(/*boolean hit*/) {
        if(curHP <= 0){
            attack = false;
            e_Action = dead;
            moveState = false;
        }
    }

}
