package Utilities;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author woody
 */
public class MySound {

    //public static double volume;
    static final String SPath = "src/main/resources/assets/sfx/";
    public static final String SOUND_SWORD_ATTACK = SPath + "swoosh.wav";
    public static final String SOUND_JUMP = SPath + "jump.wav";
    static final String MusicPath = "src/main/resources/assets/music/";
    public static final String Music1 = MusicPath + "0.wav";
    public static final String Music2 = MusicPath + "1.wav";
    public static final String Music3 = MusicPath + "2.wav";
    public static final String Music4 = MusicPath + "3.wav";
    public static final String Music5 = MusicPath + "4.wav";

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
            //gainControl.setValue(0.50f); // Default sound level
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

    // Static factory method
    public static MySound getSound(String soundFileName) {
        return new MySound(soundFileName);
    }
}
