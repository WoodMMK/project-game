package Inputs;

//for clicking sounds
import Utilities.SoundManager;
import static Utilities.Constants.soundConstants.SOUND_BUTTON_HOLD; 
import static Utilities.Constants.soundConstants.SOUND_BUTTON_RELEASED;

import java.awt.Cursor;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
/*
Nantaphop Nawaphanpimol 6613120 
Rapeepat Boolsuk 6613269
Bhwin Thongrueang 6613266
Maimongkol Thokanokwan 6613268
*/
public class ButtonMouseListener extends MouseAdapter {

    private JButton button;
    private ImageIcon[] icon;

    public ButtonMouseListener(JButton button, ImageIcon[] i) {
        this.button = button;
        this.icon = i;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        button.setIcon(icon[1]);
        SoundManager.playOnce(SOUND_BUTTON_HOLD);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button.setIcon(icon[0]);
        SoundManager.playOnce(SOUND_BUTTON_RELEASED);
    }
}
