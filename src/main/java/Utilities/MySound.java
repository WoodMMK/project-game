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
    static final String SPath = "src/main/resources/assets/SFX/";
    public static final String SOUND_SWORD_ATTACK = SPath + "attack-woosh.wav";
    public static final String SOUND_JUMP = SPath+"jump.wav";
    private Clip    clip;
    private FloatControl gainControl;
    
    public MySound(String soundFileName){
        try{
            File file = new java.io.File(soundFileName);
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.50f); //default sound level
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain) 
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}
