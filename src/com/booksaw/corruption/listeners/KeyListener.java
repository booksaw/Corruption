package com.booksaw.corruption.listeners;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.menu.PauseOverlay;

/**
 * Listener to track what the user enters during the game
 * 
 * @author James
 *
 */
public class KeyListener implements java.awt.event.KeyListener, Listener {

	public static KeyListener listen;

	// all options for user input
	public boolean left = false, right = false, down = false, up = false, interact = false;

	/**
	 * Activating the key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if (PauseOverlay.paused) {
			return;
		}

		switch (e.getKeyCode()) {
		case 65:
			left = true;
			break;
		case 68:
			right = true;
			break;
		case 83:
			down = true;
			break;
		case 87:
			up = true;
			break;
		case 32:
			up = true;
			break;
		case 27:
			pause();
			break;
		case 82:
			LevelManager.activeLevel.reset();
			break;
		case 69:
			interact = true;
			break;
		}

	}

	/**
	 * Disactivating the key removed
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 65:
			left = false;
			break;
		case 68:
			right = false;
			break;
		case 83:
			down = false;
			break;
		case 87:
			up = false;
			break;
		case 32:
			up = false;
			break;
		case 69:
			interact = false;
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void activate(JFrame f) {
		f.addKeyListener(this);
		listen = this;

	}

	@Override
	public void disable(JFrame f) {
		f.removeKeyListener(this);
		listen = null;
	}

	public void pause() {
		Overlay.addOverlay(new PauseOverlay());

		up = false;
		right = false;
		down = false;
		left = false;
	}
}
