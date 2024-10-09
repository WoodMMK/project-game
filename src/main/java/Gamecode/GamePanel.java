package Gamecode;

/**
 *
 * @author Gateaux
 */
import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import Utilities.LodeSave;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private BufferedImage[] bg;
    private Game game;

    public GamePanel(Game game) {

        importImg();
        this.game = game;
        //getAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(new MouseInputs());

        addMouseMotionListener(new MouseInputs());

    }

    public Game getGame() {
        return game;
    }

    public void setPanelSize() {
        Dimension windowsize = new Dimension(1280, 720);
        setPreferredSize(windowsize);
    }

    public void importImg() {
        //String path = "src/main/resources/assets/";
        //String path_bg = "src/main/resources/assets/BackGround/";
        bg = new BufferedImage[5];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = LodeSave.getAsset("Background/" + (i + 1) + ".png");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < bg.length; i++) {
            if (bg != null) {
                g.drawImage(bg[i], 0, -1150, 2100, 2000, null);
            }
        }
        //super.paintComponent(g);
        game.render(g);
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
        int cameraX = xPos - 640;
        int cameraY = yPos - 360;
          if (Bg01 != null) {
            g.drawImage(Bg01,(cameraX-700)/30,-1400,2400,2400, null);
        }
         if (Bg02 != null) {
            g.drawImage(Bg02,(cameraX-700)/27,-1400,2400,2400, null);
        }
         if (Bg03 != null) {
            g.drawImage(Bg03,(cameraX-700)/25,-1400,2400,2400, null);
        }
         if (Bg04 != null) {
            g.drawImage(Bg04,(cameraX-700)/20,-1400,2400,2400, null);
        }
         if (Bg05 != null) {
            g.drawImage(Bg05,(cameraX-700)/10,-1400,2400,2400, null);
        }
         if (Bg06 != null) {
            g.drawImage(Bg06,(cameraX-700)/5,-1400,2400,2400, null);
        }
         if (Bg07 != null) {
            g.drawImage(Bg07,(cameraX-700)/3,-1400,2400,2400, null);
        }
        
        if (p_facing == right)
        g.drawImage(animations[p_Action][aniIndex], xPos, yPos, 100 * 3, 100 * 3, null);
         else if (p_facing == left)
        g.drawImage(animations[p_Action][aniIndex], xPos + 300, yPos, -100 * 3, 100 * 3, null);
    }
}
