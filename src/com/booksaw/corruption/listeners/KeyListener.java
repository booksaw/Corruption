package com.booksaw.corruption.listeners;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/**
 * Listener to track what the user enters during the game
 * 
 * @author James
 *
 */
public class KeyListener implements java.awt.event.KeyListener, Listener {

	// all options for user input
	public boolean left = false, right = false, down = false, up = false;

	/**
	 * Activating the key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
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
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void activate(JFrame f) {
		f.addKeyListener(this);

	}

	@Override
	public void disable(JFrame f) {
		f.removeKeyListener(this);

	}

}
