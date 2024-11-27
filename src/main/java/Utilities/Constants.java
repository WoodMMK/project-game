package Utilities;


/**
 *
 * @author Gateaux
 */
public class Constants {
    public static float volume = 0.5f;
    public static class playerConstants{
        public final static int idling = 0;
        public final static int running = 1;
        public final static int attacking = 2;
        public static boolean jumpable = true;
        public final static int jump_power = 10;
        public final static int movespeed = 1;

        
        public static int getSpriteAmount(int p_Action){
            switch(p_Action){
                case idling:
                    return 6;
                case running:
                    return 7;
                case attacking:
                    return 6;
                default:
                    return 0;
            }
        }
    }
}
