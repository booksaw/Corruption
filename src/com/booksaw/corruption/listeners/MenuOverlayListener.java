package com.booksaw.corruption.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.controls.ControlList;
import com.booksaw.corruption.controls.ControlsManager;
import com.booksaw.corruption.render.overlays.menu.MenuOverlay;

public class MenuOverlayListener implements Listener, KeyListener, MouseListener, MouseMotionListener {

	MenuOverlay overlay;

	public MenuOverlayListener(MenuOverlay overlay) {
		this.overlay = overlay;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Clicking activates the menu
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		overlay.activate();
		Corruption.main.getFrame().repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	/**
	 * So w and s can navigate the menu
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (ControlsManager.isKeyUsed(ControlList.UP, e)) {
			overlay.increase();
			Corruption.main.getFrame().repaint();
		} else if (ControlsManager.isKeyUsed(ControlList.DOWN, e)) {
			overlay.decrease();
			Corruption.main.getFrame().repaint();
		} else if (e.getKeyCode() == 10) {
			overlay.activate();
			Corruption.main.getFrame().repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * For registering listers
	 */
	@Override
	public void activate(JFrame f) {
		f.addKeyListener(this);
		f.getContentPane().addMouseListener(this);
		f.getContentPane().addMouseMotionListener(this);
	}

	/**
	 * Removing listeners
	 */
	@Override
	public void disable(JFrame f) {
		f.getContentPane().removeMouseListener(this);
		f.removeKeyListener(this);
		f.getContentPane().removeMouseListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * So the user can move to select menu options
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!overlay.setupRects) {
			return;
		}

		// used as sometimes the rectangles are not fully genned yet (multiple tasks at
		// the same time)
		overlay.checkSelected(e.getPoint());
	}

}
