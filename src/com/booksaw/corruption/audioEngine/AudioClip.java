package com.booksaw.corruption.audioEngine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioClip {

	File file;

	public AudioClip(File file) {
		this.file = file;
	}

	public AudioInputStream getAudioStream() {
		try {
			return AudioSystem.getAudioInputStream(file);
		} catch (Exception e) {

		}

		return null;
	}

}
