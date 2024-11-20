package Entities;

import java.awt.image.BufferedImage;
import static Utilities.Constants.playerConstants.*;
import Levels.LevelHandler;
import static Levels.LevelHandler.gravity;
import Utilities.*;
import java.awt.Graphics;

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
    
    private long jumpPressedlimit = 150;
    private long airtime;
    private boolean Up, Right, Left,Down;
    private boolean Jumpable = false, DbJumpable = false;
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
        return y >= LevelHandler.GroundPos;
    }
    
    public void changePos() {
        moveState = false;
        if (Up && airtime > jumpPressedlimit) {
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
        if(Down){
            moveState = true;
            y += movespeed;
        }
        
        if(Up){//need airtime to limit each jump
            if (Jumpable) {
                if(airtime < this.jumpPressedlimit){
                    System.out.println("Jumping");
                    
                    y += jump_power * -1;
                    moveState = true;
                }else{
                    Jumpable = false;
                    airtime = 0;
                }
            }
            if (DbJumpable && airtime > 50) {
                if(airtime < this.jumpPressedlimit){
                    Jumpable = false;
                    System.out.println("Double Jumping");
                    y += jump_power * -1;
                    moveState = true;
                }else{
                    DbJumpable = false;
                }
            }
        }
        
        
        if (!isOnGround()) {
            airtime += 1;
            moveState = true;
            y += gravity;
        }

        //jump reset
        if (isOnGround()) {
            airtime = 0;
            this.Jumpable = true;
            this.DbJumpable = true;
            y = LevelHandler.GroundPos;
            jumpcount = 0;
        }
        System.out.println("current y: " + y);
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
//        if(Jumpable){
//            this.Up = up;
//        }
//        else if(!DbJumpable){
//            this.Up = up;
//            DbJumpable = true;
//        } 
        this.Up = up;
        new MySound(MySound.SOUND_JUMP).playOnce();
        
    }

//    public void setJump(boolean jump) {
//        this.Jumpable = jump;
//    }
    public void setDown(boolean down){
        this.Down = down;
    }
}
