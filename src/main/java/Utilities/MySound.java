package Utilities;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import static Utilities.Constants.soundConstants.*;
/**
 *
 * @author woody
 */

public class MySound {
    private Clip clip;
    private FloatControl gainControl;

    public MySound(String soundFileName) {
        try {
            java.io.File file = new java.io.File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(gameVolume);
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
        else {
            System.err.println("Cannot play loop: Clip not initialized.");
        }
    }

    public void setVolume(float gain) {
        if (gain < 0.0f) gain = 0.0f;
        if (gain > 1.0f) gain = 1.0f;
        float volume = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(volume);
        System.out.println("changed volume");
    }
    public static MySound getSound(String soundFileName) {
        return new MySound(soundFileName);
    }
    
}
