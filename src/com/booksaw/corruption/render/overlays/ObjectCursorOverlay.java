package com.booksaw.corruption.render.overlays;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.render.GameCamera;

public class ObjectCursorOverlay extends Overlay {

	public static ObjectCursorOverlay objectOverlay;
	public GameObject o;

	public ObjectCursorOverlay(GameObject o) {
		CursorManager.hideCursor();
		this.o = o;
		objectOverlay = this;
		EditorMouseListener.selection = ActiveSelection.OBJECTCURSOR;
	}

	@Override
	public void render(Graphics g) {

		Point p = MouseInfo.getPointerInfo().getLocation();
		Container f = Corruption.main.getFrame().getContentPane();
		p.y = f.getLocationOnScreen().y - p.y + GameCamera.cameraHeight;
		p.x = p.x = p.x - f.getLocationOnScreen().x;
		o.setLocation(p);

		o.paint(g, new Rectangle(0, 0, GameCamera.cameraWidth, GameCamera.cameraHeight));

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
