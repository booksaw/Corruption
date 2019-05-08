package com.booksaw.corruption.listeners;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.booksaw.corruption.controls.ControlList;
import com.booksaw.corruption.controls.ControlsManager;
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

		if (ControlsManager.isKeyUsed(ControlList.LEFT, e)) {
			left = true;
		} else if (ControlsManager.isKeyUsed(ControlList.RIGHT, e)) {
			right = true;
		} else if (ControlsManager.isKeyUsed(ControlList.DOWN, e)) {
			down = true;
		} else if (ControlsManager.isKeyUsed(ControlList.UP, e)) {
			up = true;
		} else if (ControlsManager.isKeyUsed(ControlList.RESET, e)) {
			LevelManager.activeLevel.reset();
		} else if (ControlsManager.isKeyUsed(ControlList.INTERACT, e)) {
			interact = true;
		}

	}

	/**
	 * Deactivating the key removed
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (ControlsManager.isKeyUsed(ControlList.LEFT, e)) {
			left = false;
		} else if (ControlsManager.isKeyUsed(ControlList.RIGHT, e)) {
			right = false;
		} else if (ControlsManager.isKeyUsed(ControlList.DOWN, e)) {
			down = false;
		} else if (ControlsManager.isKeyUsed(ControlList.UP, e)) {
			up = false;
		} else if (ControlsManager.isKeyUsed(ControlList.INTERACT, e)) {
			interact = false;
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
