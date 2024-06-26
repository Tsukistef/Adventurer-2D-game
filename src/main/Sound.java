package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	
	URL soundURL[] = new URL[30];
	
	public Sound () {
		soundURL[0] = getClass().getResource("/sound/firstlevel_song.wav");
		soundURL[1] = getClass().getResource("/sound/coin_sound.wav");
		soundURL[2] = getClass().getResource("/sound/decrease_sound.wav");
		soundURL[3] = getClass().getResource("/sound/door_sound.wav");
		soundURL[4] = getClass().getResource("/sound/increase_sound.wav");
		soundURL[5] = getClass().getResource("/steps_sound.wav");
		soundURL[6] = getClass().getResource("/sound/chest_opening.wav");
	}
	
	public void setFile(int i) {
	try {
		AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
		clip = AudioSystem.getClip();
		clip.open(ais);
		} catch(Exception e) {
			}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
