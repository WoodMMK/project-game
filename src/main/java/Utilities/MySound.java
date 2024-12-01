package Utilities;

import Utilities.Constants.soundConstants;
import java.io.IOException;
import javax.sound.sampled.*;
/*
Nantaphop Nawaphanpimol 6613120 
Rapeepat Boolsuk 6613269
Bhwin Thongrueang 6613266
Maimongkol Thokanokwan 6613268
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
            float volume = (float) (Math.log(soundConstants.gameVolume) / Math.log(10.0) * 20.0);
            gainControl.setValue(volume);
        } catch (IOException e) {
            System.err.println("Error: Unable to read the audio file. " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Error: Audio player is unavailable. " + e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Error: The audio file format is unsupported. " + e.getMessage());
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
        } else {
            System.err.println("Cannot play loop: Clip not initialized.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.err.println("Cannot play loop: Clip not initialized.");
        }
    }

    public void setVolume(float gain) {
        if (gain < 0.0f) {
            gain = 0.0f;
        }
        if (gain > 1.0f) {
            gain = 1.0f;
        }
        float volume = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(volume);
    }

    public static MySound getSound(String soundFileName) {
        return new MySound(soundFileName);
    }
}
