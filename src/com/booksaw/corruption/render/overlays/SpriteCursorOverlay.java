package com.booksaw.corruption.render.overlays;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.sprites.Sprite;

public class SpriteCursorOverlay extends Overlay {

	public static SpriteCursorOverlay cursorOverlay;
	public Sprite s;

	public SpriteCursorOverlay(Sprite s) {
		CursorManager.hideCursor();
		this.s = s;
		cursorOverlay = this;
		EditorMouseListener.selection = ActiveSelection.SPRITECURSOR;
	}

	@Override
	public void render(Graphics g) {

		Point p = MouseInfo.getPointerInfo().getLocation();
		Container f = Corruption.main.getFrame().getContentPane();
		p.y = f.getLocationOnScreen().y - p.y + GameCamera.cameraHeight;
		p.x = p.x = p.x - f.getLocationOnScreen().x;
		s.setLocation(p);

		s.draw(g, 0, 0, GameCamera.cameraHeight);

	}

	@Override
	public void hide() {
		CursorManager.resetCursor();
		EditorMouseListener.selection = ActiveSelection.MAIN;
	}

	@Override
	public void resize() {

	}

}
