package Utilities;


/**
 *
 * @author Gateaux
 */
public class Constants {
    public static float volume = 0.5f;
    public static String playerName = "";
    public static int maxHeart = 5, currHeart = 5;

    public static class playerConstants{
        public final static int idling = 0;
        public final static int running = 1;
        public final static int attacking = 2;
        public static boolean jumpable = true;
        public final static int jump_power = 10;
        public final static int movespeed = 5;

        
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
    
    public static class enemyConstants{
        public final static int idling = 0;
        public final static int running = 1;
        public final static int attacking = 2;
        public final static int dead = 3;
        public final static int movespeed = 2;
        
        public static int getSpriteAmount(int p_Action){
            switch(p_Action){
                case idling:
                    return 4;
                case running:
                    return 6;
                case attacking:
                    return 7;
                case dead:
                    return 8;
                default:
                    return 0;
            }
        }
    }
}
