package com.booksaw.corruption.listeners;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.controls.ControlList;
import com.booksaw.corruption.controls.ControlsManager;
import com.booksaw.corruption.render.GameMenu;
import com.booksaw.corruption.render.GameMenu.OPTIONS;

/**
 * All listeners required for the menu
 * 
 * @author James
 *
 */
public class MenuListener implements Listener, KeyListener, MouseListener, MouseMotionListener {

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
		GameMenu.menu.activate();
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
			GameMenu.menu.increase();
			Corruption.main.getFrame().repaint();
		} else if (ControlsManager.isKeyUsed(ControlList.DOWN, e)) {
			GameMenu.menu.decrease();
			Corruption.main.getFrame().repaint();
		} else if (e.getKeyCode() == 10) {
			GameMenu.menu.activate();
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
		Rectangle r = new Rectangle(e.getPoint().x, e.getPoint().y, 1, 1);
		if (r.intersects(GameMenu.newRec)) {
			GameMenu.menu.setOption(OPTIONS.NEW);
			Corruption.main.getFrame().repaint();
		} else if (r.intersects(GameMenu.loadRec)) {
			GameMenu.menu.setOption(OPTIONS.LOAD);
			Corruption.main.getFrame().repaint();
		} else if (r.intersects(GameMenu.editorRec)) {
			GameMenu.menu.setOption(OPTIONS.EDITOR);
			Corruption.main.getFrame().repaint();
		} else if (r.intersects(GameMenu.settingsRec)) {
			GameMenu.menu.setOption(OPTIONS.SETTINGS);
			Corruption.main.getFrame().repaint();
		}

	}

}
