package Entities;

import java.awt.image.BufferedImage;
import static Utilities.Constants.playerConstants.*;
import static Utilities.Constants.soundConstants.*;
import Gamecode.*;
import Utilities.*;
import Levels.LevelHandler;
import static Levels.LevelHandler.gravity;
import Utilities.Constants;
import Utilities.LodeSave;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/*
Nantaphop Nawaphanpimol 6613120 
Rapeepat Boolsuk 6613269
Bhwin Thongrueang 6613266
Maimongkol Thokanokwan 6613268
*/
public class Player extends Entity {

    private Game game;
    private ArrayList<Enemy> enemyGrop;

    private boolean invincible = false;
    private boolean knockedBack = false;
    private long iframeDuration = 2000;
    private long iframeStartTime;
    private long knockbackStartTime;
    private long knockbackDuration = 500;

    private int p_facing = 1;
    private boolean moveState = false, attack = false, isDamaged = false;
    private int player_action = idling;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private BufferedImage[][] animations;
    private BufferedImage img;
    private int boarder_left = 0;
    private int boarder_right = 1250;
    private float xHitboxOffset = 135 * 2, yHitboxOffset = 115 * 2;
    private boolean closeFram = false, aniDone = false;

    private MySound runningSound = null;

    private long airtimeStart = 0;
    private long airtimeDif;

    long keyPressLimit = 100;

    private boolean Up, Right, Left, Down;
    int flipX;
    int fixcam = 640;

    public Player(float x, float y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        getAnimations();
        createHitbox(x, LevelHandler.GroundPos, 30, 54);
        createAttackBox();
        //linkEnemy(game);
    }

    public void linkEnemy(ArrayList<Enemy> enemy) {
        this.enemyGrop = enemy;
    }

    private void createAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) 44 * 2, (int) 53 * 2);
        runningSound = new MySound(SOUND_RUNNING);
    }

    public void jumpSound() {
        MySound.getSound(SOUND_JUMP).playOnce();
    }

    public float getX() {
        return hitbox.x;
    }

    private void assignAni() {
        int startAni = player_action; // Store the current action for comparison
        if (curHP <= 0) {
            player_action = dead;
            return;
        }
        if (isDamaged) {
            player_action = damaged;
            return;
        }
        if (attack) {
            if (startAni != attacking) { // Trigger attack sound
                SoundManager.playOnce(SOUND_SWORD_ATTACK);
            }
            player_action = attacking;
        } else if (moveState && !isOnAir()) { // running animation
            if (startAni != running) { // Transition to running
                runningSound.playLoop();
            }
            player_action = running;
        } else if (!moveState && !isOnAir()) { // idling animation
            if (startAni == running) { // Transition from running to idle
                runningSound.stop();
            }
            player_action = idling;
        } else if (isOnAir()) { // jumping animation
            if (startAni != jumping) { // Transition to jumping
                runningSound.stop();
            }
            player_action = jumping;
        }

        if (player_action != startAni) {
            aniTick = 0;
            aniIndex = 0;
        }
    }

    public void update() {
        updateHit();
        changePos();
        updateAniTick();
        updateAttackBox();
        assignAni();
        if (this.getCurHP() <= 0 && !closeFram && aniDone) {
            game.setRun(false);
            closeFram = true;
            JOptionPane.showMessageDialog(null, "your journey end here", "game end",
                    JOptionPane.INFORMATION_MESSAGE);
            game.getGameWindow().getJFrame().dispose();
            game.getMenu().getJFrame().setVisible(true);
            game.getMenu().backToStartPanel();
        }
    }

    public void activeIframe() {
        // start iframe time
        invincible = true;
        iframeStartTime = System.currentTimeMillis();
    }

    public void applyKnockback(int sourceX) {
        knockedBack = true;
        knockbackStartTime = System.currentTimeMillis();

        // Determine knockback direction based on the position of the source
        if (hitbox.x < sourceX) {
            hitbox.x -= 50; // Knockback to the left
        } else if (hitbox.x > sourceX) {
            hitbox.x += 50; // Knockback to the right
        }
        hitbox.y -= 50; // Knock upwards
    }

    public void takeDamage(int sourceX) {
        if (!invincible) { // Only take damage if not currently invincible
            curHP -= 1; // deduct health
            SoundManager.playOnce(SOUND_getHit);
            attack = false;
            isDamaged = true;
            runningSound.stop();

            activeIframe();
            applyKnockback(sourceX);
            if (curHP <= 0) {
                activeIframe();
                SoundManager.playOnce(SOUND_GAME_OVER);
            }
        }
    }

    public void updateHit() {
        long currentTime = System.currentTimeMillis();

        // Check if invincibility frames have expired
        // Turn back to normal
        if (invincible && (currentTime - iframeStartTime) >= iframeDuration) {
            invincible = false;
        }

        // Check if knockback duration has ended
        if (knockedBack) {
            if ((currentTime - knockbackStartTime) >= knockbackDuration) {
                knockedBack = false;
                isDamaged = false;
            }
        }
    }

    private void updateAttackBox() {
        if (Right) {
            attackBox.x = hitbox.x + hitbox.width;
        } else if (Left) {
            attackBox.x = hitbox.x - hitbox.width - 15 * 2;
        }
        attackBox.y = hitbox.y;
    }

    public void render(Graphics g) {
        g.drawImage(animations[player_action][aniIndex], (int) (hitbox.x - xHitboxOffset) + flipX, (int) (hitbox.y - yHitboxOffset), width * p_facing, height, null);
    }

    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (attack && aniIndex >= 4 && aniIndex <= 5) { //apply hitbox at frame 4 - 5
                for (int i = 0; i < enemyGrop.size(); i++) {
                    if (attackBox.intersects(enemyGrop.get(i).getHitbox())) {
                        enemyGrop.get(i).hit();
                    }
                }
            }
            if (aniIndex >= getSpriteAmount(player_action)) {
                if (curHP <= 0) {
                    aniDone = true;
                }
                aniIndex = 0;
                attack = false;
            }
        }
    }

    public boolean isOnGround() {
        return hitbox.y == LevelHandler.GroundPos;
    }

    public boolean isOnAir() {
        return hitbox.y < LevelHandler.GroundPos;
    }

    public void Jump(boolean up) {
        if (jumpable && !isOnAir()) { // Allow jump only when on the ground
            if (!Up) {
                jumpSound();
            }
            this.Up = up;
        }
    }

    public void Drop() {
        if (isOnAir()) { // Ensure it only applies when above the ground
            this.Down = true;
        }
    }

    public void changePos() {
        moveState = false;

        if (!isDamaged) {// can't move while damaged
            // Horizontal Movements
            if (Right && !Left) {
                moveState = true;
                flipX = 0;
                hitbox.x += movespeed;
                p_facing = 1;
            } else if (!Right && Left) {
                moveState = true;
                flipX = width * 2;
                hitbox.x -= movespeed;
                p_facing = -1;
            }
            // Jumping
            if (Up) {
                hitbox.y -= jump_power * (1 - airtimeDif / 100); // Move upwards
                moveState = true;
            }
        }

        if (isOnAir()) {
            if (airtimeStart == 0) {
                airtimeStart = System.currentTimeMillis();
            }
            airtimeDif = System.currentTimeMillis() - airtimeStart;
            hitbox.y += gravity;
            if (airtimeDif > keyPressLimit) {
                Up = false; // Disable jump if air-time limit exceeds
            }
        } else {
            airtimeDif = 0;
            airtimeStart = 0;
            jumpable = true;
            hitbox.y = LevelHandler.GroundPos;
        }

        //move a character x
        if (Right && !Left) {
            moveState = true;
            flipX = 0;
            if (hitbox.x < boarder_right) {
                hitbox.x += movespeed;
            } else {
                hitbox.x = boarder_right;
            }
            p_facing = 1;
        } else if (!Right && Left) {
            moveState = true;
            flipX = width;
            if (hitbox.x > boarder_left) {
                hitbox.x -= movespeed;
            } else {
                hitbox.x = boarder_left;
            }
            p_facing = -1;
        }

    }

    public void getAnimations() {
        img = LodeSave.getAsset("Soldier.png");
        animations = new BufferedImage[7][9];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 100, i * 100, 100, 100);
            }
        }
    }

    public boolean isDamage() {
        return isDamaged;
    }

    public void setAttack(boolean attacking) {
        this.attack = attacking;
    }

    public void setRight(boolean right) {
        this.Right = right;
    }

    public void setLeft(boolean left) {
        this.Left = left;
    }

    public void setUp(boolean up) {
        if (up == false && isOnAir()) {// for keyReleased
            Up = false;
        }
        if (jumpable && !(isOnAir())) {
            SoundManager.playOnce(SOUND_JUMP);
            this.Up = true;
            airtimeStart = System.currentTimeMillis();
        }
    }

    public void setPHP() {
        if (Constants.difficult == 1) {
            maxHP = 5;
        }
        if (Constants.difficult == 2) {
            maxHP = 4;
        }
        if (Constants.difficult == 3) {
            maxHP = 3;
        }
        curHP = maxHP;
    }

}
