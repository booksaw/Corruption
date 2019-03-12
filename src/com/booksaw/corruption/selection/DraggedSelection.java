package com.booksaw.corruption.selection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.EditorKeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.overlays.Overlay;

public class DraggedSelection extends Overlay {
	public static DraggedSelection selection;
	Point starting, present;
	int x, y, width, height;

	public DraggedSelection(Point startingLocation) {
		this.x = startingLocation.x;
		this.y = startingLocation.y;
		width = 0;
		height = 0;
		selection = this;
		Overlay.addOverlay(this);

		startingLocation.y = GameCamera.cameraHeight - (startingLocation.y + GameCamera.activeCamera.y);
		startingLocation.x = startingLocation.x + GameCamera.activeCamera.x;
		starting = startingLocation;
		present = starting;

		for (Listener l : Corruption.main.controller.getListeners()) {
			if (l instanceof EditorKeyListener) {
				if (((EditorKeyListener) l).ctrl)
					Selectable.clearSelection();
				break;
			}
		}

	}

	public void finalise() {
		selection = null;

		Overlay.removeOverlay(this);

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setPoint(Point p) {
		present = p;
		p.y = GameCamera.cameraHeight - (p.y + GameCamera.activeCamera.y);
		p.x = p.x + GameCamera.activeCamera.x;

		Rectangle r = new Rectangle(((p.x > starting.x) ? starting.x : p.x), ((p.y > starting.y) ? starting.y : p.y),
				Math.abs(p.x - starting.x), Math.abs(p.y - starting.y));

		width = r.width;
		height = r.height;
		x = r.x;
		y = r.y;

		for (Selectable s : LevelManager.activeLevel.getSprites()) {
			run(s, r);
		}

		for (Selectable s : LevelManager.activeLevel.getLevelObjects()) {
			run(s, r);
		}
	}

	public void run(Selectable s, Rectangle r) {
		if (s.getRectangle().intersects(r)) {
			s.setSelected(true);
		} else {
			s.setSelected(false);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect((x - GameCamera.activeCamera.x), GameCamera.cameraHeight - y - height - GameCamera.activeCamera.y,
				width, height);
	}

	@Override
	public void resize() {

	}

//	/**
//	 * Used if the game camera location is changed.
//	 * Can use the same point as the point hasnt been influenced by camera offset yet
//	 */
//	public void update() {
//		setPoint(present);
//	}

}
