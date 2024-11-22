package Inputs;

import Gamecode.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Gateaux
 */
public class KeyboardInputs implements KeyListener {

    private GamePanel gamepanel;

    public KeyboardInputs(GamePanel gamepanel) {
        this.gamepanel = gamepanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // we don't use this function
    }

    @Override
    public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            gamepanel.getGame().getPlayer().Jump(false); // Stop jumping
            break;
        case KeyEvent.VK_S:
            gamepanel.getGame().getPlayer().Drop(); // Reset drop
            break;
        case KeyEvent.VK_A:
            gamepanel.getGame().getPlayer().setLeft(false);
            break;
        case KeyEvent.VK_D:
            gamepanel.getGame().getPlayer().setRight(false);
            break;
    }
}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamepanel.getGame().getPlayer().Jump(true);
                break;
            case KeyEvent.VK_S:
                //wait implementation
                gamepanel.getGame().getPlayer().Drop();
                break;
            case KeyEvent.VK_A:
                gamepanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_D:
                gamepanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_J:
                gamepanel.getGame().getPlayer().setAttack(true);
                break;
        }
    }

}
