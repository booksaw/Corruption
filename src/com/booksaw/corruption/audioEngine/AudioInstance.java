package com.booksaw.corruption.audioEngine;

import java.io.File;

import com.booksaw.corruption.Config;

public enum AudioInstance {

	JUMP("jump.wav"), DEATH("death.wav"), DOOR("door.wav");

	String path;
	AudioClip clip;

	AudioInstance(String path) {
		this.path = path;
		clip = generateClip();
	}

	private AudioClip generateClip() {

		File f = new File(Config.ASSETSPATH + File.separator + "sound" + File.separator + path);

		if (!f.exists()) {
			return null;
		}

		return new AudioClip(f);

	}

	public AudioClip getClip() {
		return clip;
	}

}
