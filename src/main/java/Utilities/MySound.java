/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author woody
 */

public class MySound {
    static final String SPath = "src/main/resources/assets/sfx/";
    public static final String SOUND_SWORD_ATTACK = SPath + "swoosh.wav";
    public static final String SOUND_JUMP = SPath + "jump2.wav";
    public static final String SOUND_getHit = null;
    public static final String SOUND_HIT = null;
    public static final String SOUND_BUTTON_RELEASED = SPath+ "mouse-released.wav";
    public static final String SOUND_BUTTON_HOLD = SPath + "mouse-hold.wav";
    private Clip clip;
    private FloatControl gainControl;

    public MySound(String soundFileName) {
        try {
            java.io.File file = new java.io.File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.50f); // Default sound level
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playOnce() {
       if (clip != null) {
        clip.setMicrosecondPosition(0);
        clip.start();
        } else {
            System.err.println("Cannot play once: Clip not initialized.");
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        else {
            System.err.println("Cannot play loop: Clip not initialized.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
        else{
            System.err.println("Cannot play stop: there is no clip");
        }
    }

    public void setVolume(float gain) {
        if (gain < 0.0f) gain = 0.0f;
        if (gain > 1.0f) gain = 1.0f;
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    
}

