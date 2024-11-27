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
        public final static int jumping = 3;
        public final static int damaged = 5;
        public final static int dead = 6;
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
                case damaged:
                    return 4;
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
    public static class soundConstants{
        public static float gameVolume = 0.1f;
        static final String MusicPath = "src/main/resources/assets/music/";
        public static final String Music1 = MusicPath + "bgm0.wav";
        public static final String Music2 = MusicPath + "bgm1.wav";
        public static final String Music3 = MusicPath + "bgm2.wav";
        public static final String Music4 = MusicPath + "bgm3.wav";
        public static final String Music5 = MusicPath + "bgm4.wav";

        static final String FXPath = "src/main/resources/assets/SFX/";
        public static final String SOUND_SWORD_ATTACK = FXPath + "attack_woosh.wav";
        public static final String SOUND_JUMP = FXPath + "edited_jump.wav";
        public static final String SOUND_RUNNING = FXPath + "running_in_grass.wav";
        public static final String SOUND_getHit = FXPath + "getHit.wav";
        public static final String SOUND_HIT = null;
        public static final String SOUND_BUTTON_RELEASED = FXPath+ "mouse_released.wav";
        public static final String SOUND_BUTTON_HOLD = FXPath + "mouse_hold.wav";
        public static final String SOUND_GAME_OVER = FXPath + "gameover.wav";
    }
}
