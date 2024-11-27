package Entities;

import java.awt.image.BufferedImage;
import static Utilities.Constants.playerConstants.*;
import Utilities.MySound;
import Levels.LevelHandler;
import static Levels.LevelHandler.gravity;
import Utilities.LodeSave;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

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
    private float xHitboxOffset = 135, yHitboxOffset = 115;
    
    private long airtimeStart=0;
    private long airtimeDif;
//    
//    long keyPressStartTime = 0;
    long keyPressLimit = 100;
    
    private boolean Up, Right, Left, Jump,Down;
    int flipX;
    int fixcam = 640;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.maxHP = 100;
        this.curHP = maxHP;
        getAnimations();
        createHitbox( x, y, 30, 54);
        createAttackBox();
    }
    
    private void createAttackBox(){
        attackBox = new Rectangle2D.Float(x, y, (int) 44, (int) 54);
    }

    public void attackSound() {
        //System.out.println(MySound.volume);
        MySound.getSound(MySound.SOUND_SWORD_ATTACK).playOnce();
    }
    public void jumpSound(){
        MySound.getSound(MySound.SOUND_JUMP).playOnce();
    }
    
    public int getX() {
    public float getX() {
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
        updateAttackBox();
        updateHit();
        assignAni();
    }
    
    public void updateHit(){
        //inside intersects should be enemy attackbox
        if(hitbox.intersects(200, 500, 100, 100)){
            curHP -= 1;
            //System.out.printf("%d\n", curHP);
        }
    }

    public void render(Graphics g) {
        g.drawRect(200, 500, 100, 100);
        g.drawImage(animations[p_Action][aniIndex],(int) (hitbox.x - xHitboxOffset) + flipX,(int) (hitbox.y - yHitboxOffset), width * p_facing, height, null);
        //System.out.printf("%f %f\n", hitbox.x, hitbox.y);
        showHitbox(g);
        showAttackBox(g);
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
    
    private void updateAttackBox(){
        if(Right)
            attackBox.x = hitbox.x + hitbox.width;
        else if(Left)
            attackBox.x = hitbox.x - hitbox.width - 15;
        attackBox.y = hitbox.y;
    }

    public boolean isOnGround() {
        return hitbox.y == LevelHandler.GroundPos;
    }
    public boolean isOnAir(){
        return hitbox.y < LevelHandler.GroundPos;
    }

    public void changePos() {
        moveState = false;
        

        if(isOnAir()){     
            if (airtimeStart == 0){
                airtimeStart = System.currentTimeMillis(); 
            }
            airtimeDif = System.currentTimeMillis()-airtimeStart;
            
            //y += gravity;);
            hitbox.y += gravity * airtimeDif/1000;
        }
        else{
            airtimeDif = 0;
            airtimeStart = 0;
            jumpcount = 0;
            hitbox.y = LevelHandler.GroundPos;
        }
        
        if(Up && jump_timedif > keyPressLimit) {
            keyPressStartTime = 0;
            setUp(false);
        }
        
        //move a character x
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
        airtimeDif = System.currentTimeMillis() - airtimeStart;
        y += gravity;

        if (airtimeDif > keyPressLimit) {
            Up = false; // Disable jump if air-time limit exceeds
        //move a character y
        if (Up) {
            hitbox.y += jump_power * -1;
            moveState = true;
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
        if (x < boarder_right)
        x += movespeed;
        else
            x =1132;
        p_facing = 1;
        //System.out.printf("x %d y %d go Right\n",x,y);
    } else if (!Right && Left) {
        moveState = true;
        flipX = width;
        if (x > boarder_left)
        x -= movespeed;
        else
        x=-131;
        p_facing = -1;
        //System.out.printf("x %d y %d go Left\n",x,y);
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
