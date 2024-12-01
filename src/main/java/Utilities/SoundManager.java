package Utilities;

import java.util.HashMap;
import java.util.Map;
import static Utilities.Constants.soundConstants.*;
/*
Nantaphop Nawaphanpimol 6613120 
Rapeepat Boolsuk 6613269
Bhwin Thongrueang 6613266
Maimongkol Thokanokwan 6613268
*/
public class SoundManager {
    private static final Map<String, MySound> soundCache = new HashMap<>();
    private static MySound currentTheme = null;
    
    static {
        preloadSound(Music1);
        preloadSound(Music2);
        preloadSound(Music3);
        preloadSound(Music4);
        preloadSound(Music5);
        
        preloadSound(SOUND_SWORD_ATTACK);
        preloadSound(SOUND_JUMP);
        preloadSound(SOUND_RUNNING);
        preloadSound(SOUND_getHit);
        preloadSound(SOUND_BUTTON_HOLD);
        preloadSound(SOUND_BUTTON_RELEASED);
        preloadSound(SOUND_GAME_OVER);
    }

    private static void preloadSound(String soundFilePath) {
        if (soundFilePath != null) {
            soundCache.put(soundFilePath, new MySound(soundFilePath));
        }
    }
    
    public static void playOnce(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.playOnce();
        } else {
            System.err.println("playOnce, Sound not found: " + soundFilePath);
        }
    }
    
    public static void playTheme(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.playLoop();
            currentTheme = sound;
        } else {
            System.err.println("playTheme Sound not found: " + soundFilePath);
        }
    }
    
    public static void stopTheme(){
        if(currentTheme != null){
            currentTheme.stop();
        }
    }
    
    public static void playLoop(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.playLoop();
        } else {
            System.err.println("playLoop method, Sound not found: " + soundFilePath);
        }
    }

    public static void stopSound(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.stop();
        } else {
            System.err.println("stopSound method, Sound not found: " + soundFilePath);
        }
    }
    public static void updateAllVolume(float newVolume){
        gameVolume = newVolume;
        for (MySound s : soundCache.values()){
            s.setVolume(gameVolume);
        }
    }
    
    public static void setVolume(String soundFilePath, float gain) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.setVolume(gain);
        } else {
            System.err.println("Sound not found: " + soundFilePath);
        }
    }
}
