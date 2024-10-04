package Utilities;

/**
 *
 * @author Gateaux
 */
public class Constants {
    
    public static class playerConstants{
        public final static int idling = 0;
        public final static int running = 1;
        
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
    
    public static class Dir{
        public final static int up = 1;
        public final static int down = 2;
        public final static int left = 3;
        public final static int right = 4;
    }
    
}
