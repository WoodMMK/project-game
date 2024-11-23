package Utilities;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author woody
 */

public class MySound {
    static final String MusicPath = "src/main/resources/assets/music/";
    public static final String Music1 = MusicPath + "bgm0.wav";
    public static final String Music2 = MusicPath + "bgm1.wav";
    public static final String Music3 = MusicPath + "bgm2.wav";
    public static final String Music4 = MusicPath + "bgm3.wav";
    public static final String Music5 = MusicPath + "bgm4.wav";
    
    static final String FXPath = "src/main/resources/assets/SFX/";
    public static final String SOUND_SWORD_ATTACK = FXPath + "swoosh.wav";
    public static final String SOUND_JUMP = FXPath + "edited_jump.wav";
    public static final String SOUND_RUNNING = FXPath + "running_in_grass.wav";
    public static final String SOUND_getHit = null;
    public static final String SOUND_HIT = null;
    public static final String SOUND_BUTTON_RELEASED = FXPath+ "mouse_released.wav";
    public static final String SOUND_BUTTON_HOLD = FXPath + "mouse_hold.wav";
    
    private Clip clip;
    private static FloatControl gainControl;
    private static Clip currentMusic;

    public MySound(String soundFileName) {
        try {
            java.io.File file = new java.io.File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            currentMusic = clip;
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(Constants.volume);
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

    public static void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
        else{
            System.err.println("Cannot play stop: there is no clip");
        }
    }

    public static void setVolume(float gain) {
        if (gain < 0.0f) gain = 0.0f;
        if (gain > 1.0f) gain = 1.0f;
        Constants.volume = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        float curr = gainControl.getValue();
        float max = gainControl.getMaximum();
        System.out.println(Constants.volume);
        gainControl.setValue(Constants.volume);
    }

    
}
