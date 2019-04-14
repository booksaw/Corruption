package com.booksaw.corruption.level.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.GameCamera;

public class DraggedBlock extends Block {
	boolean searching = false;
	public static DraggedBlock block;
	Point starting, present;

	public DraggedBlock(Point startingLocation) {
		super(new Rectangle(startingLocation.x, GameCamera.cameraHeight - startingLocation.y, 0, 0), Color.BLACK);
		block = this;
		LevelManager.activeLevel.addObject(this);

		LevelManager.activeLevel.getSaveManager().changes();
		startingLocation.y = GameCamera.cameraHeight - (startingLocation.y + GameCamera.activeCamera.y);
		startingLocation.x = startingLocation.x + GameCamera.activeCamera.x;
		starting = startingLocation;
		present = starting;
	}

	public void finalise() {
		block = null;

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
		searching = true;

		if (GameObject.getObject(r) == null) {
			width = r.width;
			height = r.height;
			x = r.x;
			y = r.y;
		}

	}

	@Override
	public Rectangle getRectangle() {
		// so it dosen't claim it collides with itself
		if (searching) {
			searching = false;
			return new Rectangle(-1, -1, 0, 0);
		}

		return super.getRectangle();
	}

	@Override
	public void renderS(Graphics g, Rectangle camera) {

		super.renderS(g, camera);
	}

//	/**
//	 * Used if the game camera location is changed.
//	 * Can use the same point as the point hasnt been influenced by camera offset yet
//	 */
//	public void update() {
//		setPoint(present);
//	}

}
