package Gamecode;

/**
 *
 * @author Gateaux
 */

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import Level.LevelHandler;
import static Level.LevelHandler.gravity;
import static Utilities.Constants.playerConstants.*;
import static Utilities.Constants.Dir.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private BufferedImage img;
    private BufferedImage Bg01,Bg02,Bg03,Bg04,Bg05,Bg06,Bg07;
    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private int p_Action = idling;
    
    private int xPos, yPos;
    private boolean left_pressed, right_pressed;
    private int p_speedX = 0;
    private int p_speedY = 0;
    
    private boolean moveState = false;
    private int p_facing = 1;
    
    private long deltatime = 0;
    private long keyPressStartTime = 0;
    private long keyPressLimit = 200;
    
    
    public GamePanel(){
        importImg();
        getAnimations();
        setPanelSize();  
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(new MouseInputs());
        addMouseMotionListener(new MouseInputs());             
    }
    
    public void setPanelSize(){
        Dimension windowsize = new Dimension(1280, 720); 
        setPreferredSize(windowsize);
    }
    
    public void importImg(){
        String path = "src/main/resources/assets/";
        String path_bg = "src/main/resources/assets/BackGround/";
        try{            
            img = ImageIO.read(new File( path + "Soldier.png"));
            Bg01 = ImageIO.read(new File( path_bg + "Background 1.png"));
            Bg02 = ImageIO.read(new File( path_bg + "Background 2.png"));
            Bg03 = ImageIO.read(new File( path_bg + "Background 3.png"));
            Bg04 = ImageIO.read(new File( path_bg + "Background 4.png"));
            Bg05 = ImageIO.read(new File( path_bg + "Background 5.png"));
            Bg06 = ImageIO.read(new File( path_bg + "Background 6.png"));
            Bg07 = ImageIO.read(new File( path_bg + "Background 7.png"));
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
    
    public void getAnimations(){
        animations = new BufferedImage[7][9];
        for(int i = 0; i < animations.length; i++){
            for(int j = 0; j < animations[i].length; j++)
            animations[i][j] = img.getSubimage(j * 100,i * 100, 100, 100);
        }
    }
    
    public void walk(int dir){
        if (dir==right) right_pressed = true;
        else if (dir == left) left_pressed = true;
        
        if(left_pressed && right_pressed){
            this.p_speedX = 0;
        }else{
            this.p_speedX = dir;
        }
    }
    
    
    public void drop(){
        this.p_speedY = down;
    }
    
    
    public void setXDir(int dir){
        if(dir == 0){
            left_pressed = false;
            right_pressed = false;
        }
        this.p_speedX = dir;
    }
    
    public void setYDir(int dir){
        this.p_speedY = dir;
    }
    
    public void changeMoveState(){
        this.moveState = (this.p_speedX != 0 || this.p_speedY != 0);
    }
    
    public boolean isMoving(){
        return (p_speedY!=0)&&(p_speedX!=0);
    }
    
    public boolean isOnground(){
        return yPos == LevelHandler.GroundPos;
    }
    
    public void jump(){
        if(jumpcount<maxjump ){
            jumpcount++;
            this.p_speedY = up;
            this.keyPressStartTime = System.currentTimeMillis();
        }
    }
    
    public void changePos(){
        //change x-axis position
        xPos += movespeed*p_speedX;
        
        //change y-axir position
        deltatime = System.currentTimeMillis() - keyPressStartTime;
        if (this.p_speedY == up &&  (deltatime> keyPressLimit)){
            // if press jump over time limit
            jumpcount++;
            keyPressStartTime = 0;
            setYDir(0);
        }
        else {
            if(p_speedY == down){
                yPos += movespeed*p_speedY;
            }
            else if(p_speedY == up){
                yPos += jump_power*p_speedY;
            }
        }
        //apply basic gravity
        yPos += gravity;
        
        //set floor && jump reset 
        if (yPos >= LevelHandler.GroundPos) {
            yPos = LevelHandler.GroundPos;
            jumpcount =0;
        }
    }
    
    private void updateAniTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(p_Action)){
                aniIndex = 0;
            }
        }
    }
    
    private void assignAni(){
        if(moveState){
            p_Action = running;
        }
        else{
            p_Action = idling;
        }
    }
    
    public void gupdate(){
        updateAniTick();
        changePos();
        changeMoveState();
        assignAni();
    }
    
    public void setFacing(int facing){
        p_facing = facing;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
         if (Bg01 != null) {
            g.drawImage(Bg01, 0, -1150,2100,2000, null);
        }
         if (Bg02 != null) {
             g.drawImage(Bg02, 0,-1150, 2100,2000, null);
        }
         if (Bg03 != null) {
            g.drawImage(Bg03, 0,-1150, 2100,2000, null);
        }
         if (Bg04 != null) {
            g.drawImage(Bg04, 0,-1150,2100,2000, null);
        }
         if (Bg05 != null) {
            g.drawImage(Bg05,0,-1150,2100,2000, null);
        }
         if (Bg06 != null) {
            g.drawImage(Bg06,0,-1150,2100,2000, null);
        }
         if (Bg07 != null) {
            g.drawImage(Bg07,0,-1150,2100,2000, null);
        }
        g.drawImage(animations[p_Action][aniIndex], 0 + xPos, 0 + yPos, 100 * 3, 100 * 3, null);
        super.paintComponent(g);
        
        if(p_facing == right)
            g.drawImage(animations[p_Action][aniIndex], 0 + xPos, 0 + yPos, 100 * 3, 100 * 3, null);
        else if(p_facing == left)
            g.drawImage(animations[p_Action][aniIndex], 300 + xPos, 0 + yPos, -100 * 3, 100 * 3, null);
    }

    
}
