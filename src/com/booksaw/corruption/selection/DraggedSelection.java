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

/**
 * Used so the user can drag out a selection in the editor
 * 
 * @author James
 *
 */
public class DraggedSelection extends Overlay {
	public static DraggedSelection selection;
	Point starting, present;
	int x, y, width, height;

	/**
	 * 
	 * @param startingLocation the location that the user started the drag
	 */
	public DraggedSelection(Point startingLocation) {
		this.x = startingLocation.x;
		this.y = startingLocation.y;
		width = 0;
		height = 0;
		if (selection != null) {
			Overlay.removeOverlay(selection);
		}

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

	/**
	 * Removing the overlay etc
	 */
	public void finalise() {
		selection = null;

		Overlay.removeOverlay(this);

	}

	/**
	 * Giving the width of the selection
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Giving the height of the selection
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setting the second point of the selection
	 * 
	 * @param p the second point
	 */
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

		for (Selectable s : LevelManager.activeLevel.getBackgrounds()) {
			run(s, r);
		}

		for (Selectable s : LevelManager.activeLevel.getInteractables()) {
			run(s, r);
		}
	}

	/**
	 * Used to test of that selectable object is intersecting, then acts on it
	 * 
	 * @param s
	 * @param r
	 */
	public void run(Selectable s, Rectangle r) {
		if (s.getRectangle().intersects(r)) {
			s.setSelected(true, true, true);
		} else {
			s.setSelected(false, true, true);
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

}
