package Entities;

import java.awt.image.BufferedImage;
import static Utilities.Constants.playerConstants.*;
import Utilities.MySound;
import Levels.LevelHandler;
import static Levels.LevelHandler.gravity;
import Utilities.LodeSave;
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
    private int boarder_left = -131;
    private int boarder_right = 1132;
    
    private long airtimeStart=0;
    private long airtimeDif;
//    
//    long keyPressStartTime = 0;
    long keyPressLimit = 100;
    
    private boolean Up, Right, Left, Jump,Down;
    int flipX;
    int fixcam = 640;

    public Player(int x, int y, int width, int hight) {
        super(x, y, width, hight);
        getAnimations();
    }

    public void attackSound() {
        //System.out.println(MySound.volume);
        MySound.getSound(MySound.SOUND_SWORD_ATTACK).playOnce();
    }
    public void jumpSound(){
        MySound.getSound(MySound.SOUND_JUMP).playOnce();
    }
    
    public int getX() {
        return x;
    }
    private void assignAni() {
        int startAni = p_Action;
        if (moveState) {
            p_Action = running;
        } else {
            p_Action = idling;
        }
        
        if (attack) {
            p_Action = attacking;
            if (startAni != attacking) { // Trigger when change to attacking
                attackSound(); 
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
    } else {
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

    // Dropping
    if (isOnAir()) {
    y += gravity; // Default gravity effect
        if (Down) {
            y += movespeed; // Extra downward acceleration
        }
    }
    

    // Horizontal movement
    if (Right && !Left) {
        moveState = true;
        flipX = 0;
        if(x<boarder_right)
        x += movespeed;
        else
            x =1132;
        p_facing = 1;
        System.out.printf("x %d y %d go Right\n",x,y);
    } else if (!Right && Left) {
        moveState = true;
        flipX = width;
        if(x>boarder_left)
        x -= movespeed;
        else
        x=-131;
        p_facing = -1;
        System.out.printf("x %d y %d go Left\n",x,y);
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

    public void Jump(boolean up) {
        if (jumpable && !isOnAir()) { // Allow jump only when on the ground
            if(!Up){
                jumpSound();
            }
            this.Up = up;
        airtimeStart = System.currentTimeMillis(); // Start jump timing
        }
    }
    public void Drop(){
        if (isOnAir()) { // Ensure it only applies when above the ground
            this.Down = true;
        }
    }

    
}
