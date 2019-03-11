package com.booksaw.corruption.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.booksaw.corruption.Selectable;

public class EditorKeyListener implements Listener, KeyListener {

	public boolean ctrl;

	@Override
	public void activate(JFrame f) {
		f.addKeyListener(this);
	}

	@Override
	public void disable(JFrame f) {
		f.removeKeyListener(this);

	}

	@Override
	public void keyPressed(KeyEvent e) {

//		System.out.println(e.getKeyCode());

		switch (e.getKeyCode()) {

		// both delete keys
		case 127:
		case 8:
			Selectable.deleteSelected();
			break;
		case 17:
			ctrl = true;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 17:
			ctrl = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
