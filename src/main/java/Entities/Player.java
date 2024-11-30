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

/**
 *
 * @author tansan
 */
public class Player extends Entity {
    private Game game;
    //private Enemy enemy;
    private ArrayList<Enemy> enemyGrop;

    private boolean invincible = false;
    private boolean knockedBack = false;
    private long iframeDuration = 2000;
    private long iframeStartTime;
    private long knockbackStartTime;
    private long knockbackDuration = 500;

    private int p_facing = 1;
    private boolean moveState = false, attack = false, isDamaged = false;
    private int p_Action = idling;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private BufferedImage[][] animations;
    private BufferedImage img;
    private int boarder_left = 0;
    private int boarder_right = 1250;
    private float xHitboxOffset = 135, yHitboxOffset = 115;

    private MySound runningSound = null;

    private long airtimeStart = 0;
    private long airtimeDif;

    long keyPressStartTime = 0;
    long keyPressLimit = 100;

    private boolean Up, Right, Left, Down;
    int flipX;
    int fixcam = 640;

    public Player(float x, float y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        getAnimations();
        createHitbox(x, y, 30, 54);
        createAttackBox();
        //linkEnemy(game);
    }
    
    public void linkEnemy(ArrayList<Enemy> enemy){
        this.enemyGrop = enemy;
        //System.out.println(this.enemy);
    }

    private void createAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) 44, (int) 53);
        runningSound = new MySound(SOUND_RUNNING);
    }

    public void jumpSound() {
        MySound.getSound(SOUND_JUMP).playOnce();
    }

    public float getX() {
        return hitbox.x;
    }

    private void assignAni() {
        int startAni = p_Action; // Store the current action for comparison
        if (isDamaged) {
            //System.out.println("in assign Ani");
            p_Action = damaged;
            return;
        }
        // Handle attacking animation (highest priority)
        if (attack) {
            if (startAni != attacking) { // Trigger attack sound only when transitioning to attacking
                SoundManager.playOnce(SOUND_SWORD_ATTACK);
                //System.out.println("Player attack");
            }
            p_Action = attacking;
        } // Handle running animation (only when grounded and not attacking)
        else if (moveState && !isOnAir()) {
            if (startAni != running) { // Transition to running
                runningSound.playLoop();
                //System.out.println("Player start running");
            }
            p_Action = running;
        } // Handle idling animation (when not moving, not attacking, and grounded)
        else if (!moveState && !isOnAir()) {
            if (startAni == running) { // Transition from running to idle
                runningSound.stop();
                //System.out.println("Player stop running");
            }
            p_Action = idling;
        } // Handle jumping animation (when in the air and not attacking)
        else if (isOnAir()) {
            if (startAni != jumping) { // Transition to jumping
                runningSound.stop(); // Ensure running sound stops
                //System.out.println("Player is on air");
            }
            p_Action = jumping;
        }

        // Reset animation tick if the action changes
        if (p_Action != startAni) {
            aniTick = 0;
            aniIndex = 0;
        }
    }

    public void update() {
        updateHit();
        changePos();
        updateAniTick();
        updateAttackBox();
        //updateHit();
        assignAni();
    }

    public void activeIframe() {
        invincible = true;
        iframeStartTime = System.currentTimeMillis();
        System.out.println("now in iframe");
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
        hitbox.y -= 50; // Knockback upwards
        System.out.println("Player knocked back!");
    }

    public void takeDamage(int sourceX) {
        if (!invincible) { // Only take damage if not currently invincible
            curHP -= 1; // deduct hit damage
            System.out.println("took damage, Current HP: " + curHP);
            SoundManager.playOnce(SOUND_getHit);
            isDamaged = true;
            runningSound.stop();

            activeIframe(); // Activate invincibility frames
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
        if (invincible && (currentTime - iframeStartTime) >= iframeDuration) {
            invincible = false;
            //isDamaged = false; // <----------
            System.out.println("No longer invincible.");
        }

        // Check if knockback has ended
        if (knockedBack) {
            if ((currentTime - knockbackStartTime) >= knockbackDuration) {
                knockedBack = false;
                isDamaged = false; // <----------
                //System.out.println("end knockback");
            }
        }

        // Check for hit only if not invincible
        if (!invincible && hitbox.intersects(200, 500, 1000, 100)) { // Check if hit
            takeDamage(200);
        }
    }

    private void updateAttackBox() {
        if (Right) {
            attackBox.x = hitbox.x + hitbox.width;
        } else if (Left) {
            attackBox.x = hitbox.x - hitbox.width - 15;
        }
        attackBox.y = hitbox.y;
    }

    public void render(Graphics g) {
        g.drawRect(200, 500, 1000, 100);
        g.drawImage(animations[p_Action][aniIndex], (int) (hitbox.x - xHitboxOffset) + flipX, (int) (hitbox.y - yHitboxOffset), width * p_facing, height, null);
        //System.out.printf("%f %f\n", hitbox.x, hitbox.y);
        showHitbox(g);
        showAttackBox(g);
    }

    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (attack && aniIndex >= 4) {
                for (int i = 0; i < enemyGrop.size(); i++) {
                    if (attackBox.intersects(enemyGrop.get(i).getHitbox())) {
                        enemyGrop.get(i).setHit(true);
                    }
                }
            }
            if (aniIndex >= getSpriteAmount(p_Action)) {
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
            //this.Jump = jump;
            //airtimeStart = System.currentTimeMillis(); // Start jump timing
            this.keyPressStartTime = System.currentTimeMillis();
        }
    }

    public void Drop() {
        if (isOnAir()) { // Ensure it only applies when above the ground
            this.Down = true;
        }
    }

    public void changePos() {
        moveState = false;
        long jump_timedif = System.currentTimeMillis() - keyPressStartTime;

        if (!isDamaged) {
            // Horizontal Movements
            if (Right && !Left) {
                moveState = true;
                flipX = 0;
                hitbox.x += movespeed;
                p_facing = 1;
            } else if (!Right && Left) {
                moveState = true;
                flipX = width;
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

            //y += gravity;);
            //hitbox.y += gravity * airtimeDif / 1000;
            //hitbox.y += gravity * 2;
            hitbox.y += gravity;
            if (airtimeDif > keyPressLimit) {
                Up = false; // Disable jump if air-time limit exceeds
            }
        } else {
            airtimeDif = 0;
            airtimeStart = 0;
            //jumpcount = 0;
            jumpable = true;
            hitbox.y = LevelHandler.GroundPos;
        }

        //move a character x
        if (Right && !Left) {
            moveState = true;
            flipX = 0;
            if (hitbox.x < boarder_right) {
                //x += movespeed;
                hitbox.x += movespeed;
            } else {
                //x = 1132;
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
            //hitbox.x -= movespeed;
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

    // wait for implement in code 
    // >>>>>
    public void hitSound() {
        SoundManager.playOnce("hit sound path");
    }

    public void getHitSound() {
        SoundManager.playOnce("hit sound path");
    }
    // <<<<<<
    public void setPHP() {
        if(Constants.difficult ==1){
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
