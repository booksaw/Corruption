package com.booksaw.corruption.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.editor.options.BlockSettings;
import com.booksaw.corruption.editor.options.SpriteSettings;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.objects.Block;
import com.booksaw.corruption.level.objects.DraggedBlock;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.overlays.ActiveSelection;
import com.booksaw.corruption.render.overlays.EditorOverlay;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.render.overlays.SpriteCursorOverlay;
import com.booksaw.corruption.render.overlays.SpriteOverlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.RenderController;
import com.booksaw.corruption.sprites.Sprite;

public class EditorMouseListener implements Listener, MouseListener, MouseMotionListener {

	public static ActiveSelection selection = ActiveSelection.MAIN;

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			DraggedBlock.block.setPoint(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent w) {
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
		if (selection == ActiveSelection.SPRITE || selection == ActiveSelection.SPRITECURSOR) {
			return;
		}
		if (SwingUtilities.isLeftMouseButton(e))
			new DraggedBlock(e.getPoint());

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		Point p = Utils.getScaledPoint(e.getPoint(), GameCamera.activeCamera.getSize());
		System.out.println(selection);
		switch (selection) {
		case MAIN:
			mainClick(e, p);
			break;
		case SPRITE:
			spriteClick(p);
			break;
		case SPRITECURSOR:
			spriteCursorClick(e, p);
			break;
		}

	}

	public void save() {
		LevelManager.activeLevel.save();
	}

	public void insert() {

		RenderController temp = Corruption.main.controller;

		if (!(temp instanceof EditorController)) {
			return;
		}

		((EditorController) temp).insert();

	}

	private void mainClick(MouseEvent e, Point p) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (DraggedBlock.block == null) {
				mainClickFinalize(e, e.getPoint());
				return;
			}
			if (DraggedBlock.block.getWidth() == 0 || DraggedBlock.block.getHeight() == 0) {
				mainClickFinalize(e, e.getPoint());
				return;
			}

			DraggedBlock.block.finalise();
			return;
		}

		if (SwingUtilities.isRightMouseButton(e)) {
			mainClickFinalize(e, e.getPoint());
			return;
		}
	}

	private void mainClickFinalize(MouseEvent e, Point p) {
		if (p.getX() > GameCamera.cameraWidth - EditorOverlay.SQUARE
				&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE && p.getX() < GameCamera.cameraWidth
				&& p.getY() < GameCamera.cameraHeight) {
			save();
			return;
		}

		if (p.getX() > GameCamera.cameraWidth - (EditorOverlay.SQUARE * 2)
				&& p.getY() > GameCamera.cameraHeight - EditorOverlay.SQUARE
				&& p.getX() < GameCamera.cameraWidth - EditorOverlay.SQUARE && p.getY() < GameCamera.cameraHeight) {
			insert();
			return;
		}

		Point temp = new Point(p.x, GameCamera.cameraHeight - p.y);

		GameObject o = GameObject.getObject(temp);
		if (o != null && (o instanceof Block)) {
			new BlockSettings((Block) o).setVisible(true);
			return;
		}

		Sprite s = Sprite.getSprite(temp, LevelManager.activeLevel.getSprites());
		if (s != null) {
			new SpriteSettings(s).setVisible(true);
			return;
		}
	}

	private void spriteClick(Point p) {
		Overlay store = null;
		for (Overlay o : Overlay.getActiveOverlays()) {
			if (!(o instanceof SpriteOverlay)) {
				continue;
			}

			store = o;
			((SpriteOverlay) o).click(p);
			break;
		}

		Overlay.removeOverlay(store);

	}

	private void spriteCursorClick(MouseEvent e, Point p) {
		if (SwingUtilities.isLeftMouseButton(e)) {

			selection = ActiveSelection.MAIN;
			Overlay.removeOverlay(SpriteCursorOverlay.cursorOverlay);
			LevelManager.activeLevel.addSprite(SpriteCursorOverlay.cursorOverlay.s);

		} else if (SwingUtilities.isRightMouseButton(e)) {
			selection = ActiveSelection.MAIN;
			Overlay.removeOverlay(SpriteCursorOverlay.cursorOverlay);
		}
	}

	@Override
	public void activate(JFrame f) {
		f.getContentPane().addMouseListener(this);
		f.getContentPane().addMouseMotionListener(this);

	}

	@Override
	public void disable(JFrame f) {
		f.getContentPane().removeMouseListener(this);
		f.getContentPane().removeMouseMotionListener(this);

	}

}
