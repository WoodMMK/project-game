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
    
    private int xPos, yPos;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 20;
    private int p_Action = idling;
    private int p_Dir = 0;
    private boolean moveState = false;
    
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
    
    public void setDir(int dir){
        this.p_Dir = dir;
        moveState = true;
    }
    
    public void changeMoveState(boolean movestate){
        this.moveState = movestate;
    }
    
    public void changePos(){
        if(moveState){
            switch(p_Dir){
                case up:
                    yPos -= 5;
                    break;
                case down:
                    yPos += 5;
                    break;
                case left:
                    xPos -= 5;
                    break;
                case right:
                    xPos += 5;
                    break;
            }
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
        assignAni();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);        
        g.drawImage(animations[p_Action][aniIndex], 0 + xPos, 0 + yPos, 100 * 3, 100 * 3, null);
    }

    
}
