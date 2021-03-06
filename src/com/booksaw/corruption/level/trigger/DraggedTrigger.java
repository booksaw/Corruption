package com.booksaw.corruption.level.trigger;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.GameCamera;

public class DraggedTrigger extends Trigger {
	public static DraggedTrigger trigger;
	Point starting, present;

	public DraggedTrigger(Point startingLocation) {
		super(new Rectangle(startingLocation.x, GameCamera.cameraHeight - startingLocation.y, 0, 0));
		trigger = this;
		LevelManager.activeLevel.addTrigger(this);

		LevelManager.activeLevel.getSaveManager().changes();
		startingLocation.y = GameCamera.cameraHeight - (startingLocation.y + GameCamera.activeCamera.y);
		startingLocation.x = startingLocation.x + GameCamera.activeCamera.x;
		starting = startingLocation;
		present = starting;
		showTriggers = true;

	}

	public void finalise() {
		trigger = null;

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

	}

	@Override
	public void paintComp(Graphics g, Rectangle camera) {

		super.paintComp(g, camera);
	}

//	/**
//	 * Used if the game camera location is changed.
//	 * Can use the same point as the point hasnt been influenced by camera offset yet
//	 */
//	public void update() {
//		setPoint(present);
//	}

}
