package Gamecode;

/**
 *
 * @author Gateaux
 */
public class Game implements Runnable{
    
    private GameWindow gamewindow;
    private GamePanel gamepanel;
    private Thread loopThread;
    private final int FPS_MAX = 120;
    private final int UPS_MAX = 200;
      
    public Game(){
        gamepanel = new GamePanel();
        gamewindow = new GameWindow(gamepanel);
        gamepanel.requestFocus();
        startGameLoop();
        
    }
    
    private void startGameLoop() {
        loopThread = new Thread(this);
        loopThread.start();
    }
    
    public void update(){
        gamepanel.gupdate();
    }
    
    @Override
    public void run(){
        double timePerFrame = 1000000000.0 / FPS_MAX;
        double timePerUpdate = 1000000000.0 / UPS_MAX;        
        long previousFrame = System.nanoTime();
        long previouscheck = System.nanoTime();
        double fDiff = 0;
        double uDiff = 0;
        int frames = 0;
        int updates = 0;
        
        
        while(true){
            long currentTime = System.nanoTime();
            
            fDiff += (currentTime - previousFrame) / timePerFrame;
            uDiff += (currentTime - previousFrame) / timePerUpdate;
            //System.out.printf("TPF : %f | TPU %f\n", timePerFrame, timePerUpdate);
            //System.out.printf("fdiff : %f | udiff %f\n", fDiff, uDiff);
            previousFrame = currentTime;
            //System.out.printf("%d\n", previousFrame);
            
            if(fDiff >= 1){
                gamepanel.repaint();
                frames++;
                fDiff--;
            }
            if(uDiff >= 1){
                update();
                updates++;
                uDiff--;
            }
            
            if(System.nanoTime() - previouscheck >= 1000000000){
                previouscheck = System.nanoTime();
                System.out.printf("FPS : %d | UPS : %d\n", frames, updates);
                frames = 0;
                updates = 0;
            }
        }
    
    }
}
