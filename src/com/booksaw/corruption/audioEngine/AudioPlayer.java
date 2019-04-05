package com.booksaw.corruption.audioEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

	public static synchronized void playSound(AudioClip audioClip) {

		Thread thread = new Thread() {
			public void run() {

				try {

					AudioInputStream stream = audioClip.getAudioStream();

					Clip clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();
				} catch (Exception e) {

				}
			}
		};
		thread.start();

	}

	public static void playSound(AudioInstance i) {
		playSound(i.getClip());
	}

}
