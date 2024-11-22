/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author woody
 */
public class SoundManager {
     // Cache to store preloaded sounds
    private static float gameGain;
    private static final Map<String, MySound> soundCache = new HashMap<>();

    // preloaded sounds
    // so, ready since load Soundmanager
    static {
        preloadSound(MySound.SOUND_SWORD_ATTACK);
        preloadSound(MySound.SOUND_JUMP);
        preloadSound(MySound.SOUND_getHit);
        preloadSound(MySound.SOUND_HIT);
        preloadSound(MySound.SOUND_BUTTON_HOLD);
        preloadSound(MySound.SOUND_BUTTON_RELEASED);
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
            System.err.println("Sound not found: " + soundFilePath);
        }
    }

    public static void playLoop(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.playLoop();
        } else {
            System.err.println("Sound not found: " + soundFilePath);
        }
    }

    // for sound setting 
    public static void stopSound(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.stop();
        } else {
            System.err.println("Sound not found: " + soundFilePath);
        }
    }

    public static void settingSound(float gain){
        gameGain = gain;
    }
    // gain = Volume level (0.0 to 1.0)
    public static void setVolume(String soundFilePath, float gain) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.setVolume(gain);
        } else {
            System.err.println("Sound not found: " + soundFilePath);
        }
    }
}
