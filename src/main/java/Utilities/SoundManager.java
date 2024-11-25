/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.util.HashMap;
import java.util.Map;
import static Utilities.Constants.soundConstants.*;
/**
 *
 * @author woody
 */
public class SoundManager {
     // Cache to store preloaded sounds
    private static Map<String, MySound> soundCache = new HashMap<>();
    private static MySound currentTheme = null;
    // preloaded sounds
    // so, ready since load Soundmanager
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
        preloadSound(SOUND_HIT);
        preloadSound(SOUND_BUTTON_HOLD);
        preloadSound(SOUND_BUTTON_RELEASED);
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
    
    public static void playTheme(String soundFilePath) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.playLoop();
            currentTheme = sound;
        } else {
            System.err.println("Sound not found: " + soundFilePath);
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
    public static void updateAllVolume(float newVolume){
        gameVolume = newVolume;
        for (MySound s : soundCache.values()){
            s.setVolume(gameVolume);
        }
    }
    
    // gain = Volume level (0.0 to 1.0)
    // update individual sound's volume
    public static void setVolume(String soundFilePath, float gain) {
        MySound sound = soundCache.get(soundFilePath);
        if (sound != null) {
            sound.setVolume(gain);
        } else {
            System.err.println("Sound not found: " + soundFilePath);
        }
    }
}
