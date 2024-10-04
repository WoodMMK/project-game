package Gamecode;

/**
 *
 * @author Gateaux
 */

import javax.swing.JFrame;  

public class GameWindow {
    
    private JFrame jframe;
    
    public GameWindow(GamePanel gamepanel){
        jframe = new JFrame();
        jframe.add(gamepanel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(3);
        jframe.setVisible(true);
    }
}
