package Inputs;

import Gamecode.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Utilities.Constants.Dir.*;

/**
 *
 * @author Gateaux
 */
public class KeyboardInputs implements KeyListener{
    
    private GamePanel gamepanel;
    
    public KeyboardInputs(GamePanel gamepanel){
        this.gamepanel = gamepanel;
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                gamepanel.changeMoveState(false);
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                gamepanel.setDir(up);
                break;
            case KeyEvent.VK_S:
                gamepanel.setDir(down);
                break;
            case KeyEvent.VK_A:
                gamepanel.setDir(left);
                break;
            case KeyEvent.VK_D:
                gamepanel.setDir(right);
                break;
        }
    }
    
}
