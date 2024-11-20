package Entities;

import java.awt.image.BufferedImage;
import static Utilities.Constants.playerConstants.*;
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
    
    private long airtimeStart=0;
    private long airtimeDif;
    
    long keyPressStartTime = 0;
    long keyPressLimit = 100;
    private boolean Up, Right, Left, Jump ,inAir;
    int flipX;
    int fixcam = 640;

    public Player(int x, int y, int width, int hight) {
        super(x, y, width, hight);
        getAnimations();
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

    public boolean isOnGround() {
        return this.y == LevelHandler.GroundPos;
    }
    public boolean isOnAir(){
        return this.y < LevelHandler.GroundPos;
    }

    public void jump() {
        if (jumpcount < maxjump) {
            jumpcount++;
            this.keyPressStartTime = System.currentTimeMillis();
        }
    }

    public void changePos() {
        moveState = false;
        long jump_timedif = System.currentTimeMillis() - keyPressStartTime;

        if(isOnAir()){     
            if (airtimeStart == 0){
                airtimeStart = System.currentTimeMillis(); 
            }
            airtimeDif = System.currentTimeMillis()-airtimeStart;
            
            //y += gravity;);
            y += gravity * airtimeDif/1000;
        }
        else{
            airtimeDif = 0;
            airtimeStart = 0;
            jumpcount = 0;
            y = LevelHandler.GroundPos;
        }
        
        if(Up && jump_timedif > keyPressLimit) {
            keyPressStartTime = 0;
            setUp(false);
        }
        
        //move a character x
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

        //move a character y
        if (Up) {
            y += jump_power * -1;
            moveState = true;
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
        this.Up = up;
    }

    public void setJump(boolean jump) {
        this.Jump = jump;
    }
    
}
