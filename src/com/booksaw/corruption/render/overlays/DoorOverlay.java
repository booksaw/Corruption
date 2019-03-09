package com.booksaw.corruption.render.overlays;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.objects.Door;
import com.booksaw.corruption.render.GameCamera;

public class DoorOverlay extends Overlay {

	Door d;
	public static DoorOverlay doorOverlay;

	public DoorOverlay() {
		doorOverlay = this;
		d = new Door(new Point(-100, -100), false);
	}

	@Override
	public void render(Graphics g) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		Container f = Corruption.main.getFrame().getContentPane();
		p.y = f.getLocationOnScreen().y - p.y + GameCamera.cameraHeight;
		p.x = p.x = p.x - f.getLocationOnScreen().x;
		d.setLocation(p);

		d.render(g, new Rectangle(0, 0));
	}

	@Override
	public void resize() {

	}

	public void place() {
		d.setX(d.getX() + GameCamera.activeCamera.x);
		d.setY(d.getY() + GameCamera.activeCamera.y);
		LevelManager.activeLevel.addObject(d);
		d = new Door(new Point(-100, -100), false);

	}

//	@Override
//	public void hide() {
//		CursorManager.resetCursor();
//	}
//
//	@Override
//	public void show() {
//		CursorManager.hideCursor();
//	}

}
