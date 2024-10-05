package Inputs;

import Gamecode.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Utilities.Constants.Dir.*;
import static Utilities.Constants.playerConstants.*;

/**
 *
 * @author Gateaux
 */
public class KeyboardInputs implements KeyListener{
    long keyPressStartTime = 0;
    long keyPressLimit = 500; // 1 second
    private GamePanel gamepanel;
    
    public KeyboardInputs(GamePanel gamepanel){
        this.gamepanel = gamepanel;
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        // we don't use this function
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                gamepanel.setJumpPressed(false);
                jumpcount++;
                gamepanel.setYDir(0);break;
            case KeyEvent.VK_S: 
                gamepanel.setYDir(0);break;
            case KeyEvent.VK_A: 
                gamepanel.setXDir(0);break;
            case KeyEvent.VK_D: 
                gamepanel.setXDir(0);break;
        }
        
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                gamepanel.jumplimit();
                gamepanel.jump();break;
            case KeyEvent.VK_S:
                gamepanel.drop();break;
            case KeyEvent.VK_A:
                gamepanel.walk(left);break;
            case KeyEvent.VK_D:
                gamepanel.walk(right);break;
        }      
    }
    
}
