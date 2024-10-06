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
    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private int p_Action = idling;
    
    private int xPos, yPos;
    private int p_xDir = 0;
    private int p_yDir = 0;
    
    private boolean moveState = false;
    
    long keyPressStartTime = 0;
    long keyPressLimit = 200;
    
    
    public GamePanel(){
        importImg();
        setPanelSize();
        getAnimations();
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
        try{
//            if(p_xDir == -1){
//                img = ImageIO.read(new File( path + "Soldier.png"));
//            }else{
//                
//            }
            img = ImageIO.read(new File( path + "Soldier.png"));
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
        if(dir + p_xDir == 0){
            this.p_xDir = 0;
        }else{
            this.p_xDir = dir;
        }
    }
    
    
    public void drop(){
        this.p_yDir = down;
    }
    
    public void setXDir(int dir){
        this.p_xDir = dir;
    }
    
    public void setYDir(int dir){
        this.p_yDir = dir;
    }
    
    public void changeMoveState(){
        this.moveState = (this.p_xDir != 0 || this.p_yDir != 0);
    }
    
    public boolean isMoving(){
        return (p_yDir!=0)&&(p_xDir!=0);
    }
    
    public boolean isOnground(){
        return yPos == LevelHandler.GroundPos;
    }
    
    public void jump(){
        if(jumpcount<maxjump && isOnground()){
            jumpcount++;
            this.p_yDir = up;
            this.keyPressStartTime = System.currentTimeMillis();
        }
    }
    
    public void changePos(){
        long timedif = System.currentTimeMillis() - keyPressStartTime;
        if (this.p_yDir == up &&  timedif> keyPressLimit){
            jumpcount++;
            keyPressStartTime = 0;
            setYDir(0);
        }
        
        //move a character
        xPos += movespeed*p_xDir;
        if(p_yDir == down){
            yPos += movespeed*p_yDir;
        }
        else if(p_yDir == up){
            yPos += jump_power*p_yDir;
        }
        
        yPos += gravity;
        
        //jump reset
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
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(animations[p_Action][aniIndex], 0 + xPos, 0 + yPos, 100 * 3, 100 * 3, null);
    }

    
}
