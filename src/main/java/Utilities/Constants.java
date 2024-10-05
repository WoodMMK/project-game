package Utilities;

/**
 *
 * @author Gateaux
 */
public class Constants {
    
    public static class playerConstants{
        public final static int idling = 0;
        public final static int running = 1;
        public final static int maxjump = 2;
        public static int jumpcount = 0;
        public final static int jump_power = 2;
        public final static int movespeed = 1;
        
        public static int getSpriteAmount(int p_Action){
            switch(p_Action){
                case idling:
                    return 6;
                case running:
                    return 8;
                default:
                    return 0;
            }
        }
    }
    
    public static class Level{
        public final static int gravity = 2;
    }
    
    public static class Dir{
        public final static int up = -1;
        public final static int down = 1;
        public final static int left = -1;
        public final static int right = 1;
       
    }
    
}
