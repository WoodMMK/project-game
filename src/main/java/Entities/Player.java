package Entities;

import java.awt.image.BufferedImage;
import static Utilities.Constants.playerConstants.*;
import static Utilities.Constants.soundConstants.*;
import Utilities.*;
import Levels.LevelHandler;
import static Levels.LevelHandler.gravity;

import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author tansan
 */
public class Player extends Entity {
    private int p_facing = 1;
    private boolean moveState = false, attack = false;
    private int p_Action = idling;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private BufferedImage[][] animations;
    private BufferedImage img;
    
    private MySound runningSound = null;
    
    private long airtimeStart=0;
    private long airtimeDif;
    long keyPressLimit = 100;
    
    private boolean Up, Right, Left, Jump;
    int flipX;
    int fixcam = 640;
    
    
    public Player(int x, int y, int width, int hight) {
        super(x, y, width, hight);
        getAnimations();
        runningSound = new MySound(SOUND_RUNNING);
    }
    
    public int getX() {
        return x;
    }
    private void assignAni() {
        int startAni = p_Action;
        if (moveState) {
            if(p_Action == idling){
                runningSound.stop();
                System.out.println("player stop moving");
            }
            p_Action = running;
        } else {
            if(p_Action == running){
                SoundManager.stopSound(SOUND_RUNNING);
                System.out.println("player start moving");
            }
            p_Action = idling;
        }
        
        if (attack) {
            p_Action = attacking;
            if (startAni != attacking) { // Trigger when change to attacking
                SoundManager.playOnce(SOUND_SWORD_ATTACK);
            }
        }
        if (p_Action != startAni) {
            aniTick = 0;
            aniIndex = 0;
        }
    }
       
    
    public void update() {
        changePos();
        updateAniTick();
        assignAni();
    }

    public void render(Graphics g) {
        g.drawImage(animations[p_Action][aniIndex], x + flipX, y, width * p_facing, hight, null);
    }

    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(p_Action)) {
                aniIndex = 0;
                attack = false;
            }
        }
    }

    
    public boolean isOnAir(){
        return this.y < LevelHandler.GroundPos;
    }

    public void changePos() {
        moveState = false;

    // Gravity and air-time management
    if (isOnAir()) {
        if (airtimeStart == 0) {
            airtimeStart = System.currentTimeMillis(); 
        }
        airtimeDif = System.currentTimeMillis() - airtimeStart;
        y += gravity;

        if (airtimeDif > keyPressLimit) {
            Up = false; // Disable jump if air-time limit exceeds
        }
    } else { // is on ground
        airtimeDif = 0;
        airtimeStart = 0;
        jumpable = true; // Allow jump again on the ground
        y = LevelHandler.GroundPos;
    }

    // Jumping
    if (Up) {
        y -= jump_power * (1-airtimeDif/100); // Move upwards
        moveState = true;
    }

    // Horizontal movement
    if (Right && !Left) {
        moveState = true;
        flipX = 0;
        x += movespeed;
        p_facing = 1;
        
    } else if (!Right && Left) {
        moveState = true;
        flipX = width;
        x -= movespeed;
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

    public void setUp(boolean up){
        if(up == false && isOnAir()){// for keyReleased
            Up = false;
        }
        if (jumpable && !(isOnAir())){
            SoundManager.playOnce(SOUND_JUMP);
            this.Up = true;
            airtimeStart = System.currentTimeMillis();
        }
    }

    // wait for implement in code 
    // >>>>>
    public void hitSound(){
        SoundManager.playOnce("hit sound path");
    }
    public void getHitSound(){
        SoundManager.playOnce("hit sound path");
    }
    // <<<<<<
}
