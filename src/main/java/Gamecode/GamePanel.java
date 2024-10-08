package Gamecode;

/**
 *
 * @author Gateaux
 */

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
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
    private int p_xDir = 0;
    private int p_yDir = 0;
    
    private boolean moveState = false;
    private int p_facing = 1;
    
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
//            if(p_xDir == -1){
//                img = ImageIO.read(new File( path + "Soldier.png"));
//            }else{
//                
//            }
            
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
        if((p_yDir!=0)&&(p_xDir!=0))
            return true;
        else{
            return false;
        }
    }
    
    public void changePos(){
        if((p_yDir != 0) && (p_xDir != 0)){
            int diag_dist = (int)Math.floor(Math.sqrt(Math.pow((double)(movespeed*p_yDir),2) + Math.pow((double)(movespeed*p_xDir),2)));
            xPos += diag_dist*p_xDir; //x = -5
            yPos += diag_dist*p_yDir; //y = +5
        }else{
            yPos += movespeed*p_yDir;
            xPos += movespeed*p_xDir;
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
