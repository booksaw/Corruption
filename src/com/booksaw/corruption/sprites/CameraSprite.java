package com.booksaw.corruption.sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.KeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;

/**
 * this is a sprite with no collision, no texture, used for things like binding
 * cameras etc.
 * 
 * @author James
 *
 */
public class CameraSprite extends Sprite {
	private final double SPEED = 0.5;

	@Override
	protected Dimension getDimension() {
		return new Dimension(0, 0);
	}

	@Override
	protected Dimension getCrouchDimension() {
		return new Dimension(0, 0);
	}

	@Override
	protected BufferedImage getStanding() {
		return null;
	}

	@Override
	protected BufferedImage getWalking() {
		return null;
	}

	@Override
	protected BufferedImage getCrouching() {
		return null;
	}

	@Override
	protected Rectangle getRectangle() {
		return new Rectangle(-1, -1, 0, 0);
	}

	@Override
	public void draw(Graphics g, int cameraX, int cameraY, int cameraHeight) {
	}

	@Override
	protected void bindToCamera(int x) {
//		int temp = GameCamera.activeCamera.x;

		// BINDING CAMERA TO PLAYER
		GameCamera.activeCamera.closestX(x);

		// updating dragged block
//		if (DraggedBlock.block != null && temp != GameCamera.activeCamera.x) {
//			DraggedBlock.block.update();
//
//		}
	}

	private void calculateX(int time) {

//		double tempY = y;
		// movement (left and right)
		KeyListener listen = null;

		for (Listener l : Corruption.main.controller.getListeners()) {
			if (l instanceof KeyListener) {
				listen = (KeyListener) l;
				break;
			}
		}
		if (listen.left) {
			x = changeX(x - (SPEED * time), x);
			if (x + GameCamera.cameraWidth > LevelManager.activeLevel.levelDimensions.width) {
				x = LevelManager.activeLevel.levelDimensions.width - GameCamera.cameraWidth;
			}
		}

		if (listen.right) {
			x = changeX(x + (SPEED * time), x);
		}

	}

	/**
	 * Used to change the value of x to the closest value of x possible This pushes
	 * the player out of blocks etc.
	 * 
	 * @param change where to move the player to
	 * @param x      the starting x coord
	 * @return the best x coord
	 */
	private double changeX(double change, double x) {
		// makes sure the x coord is on the screen
		change = closestX(change);

		// if the player can go there return just change
		if (canGo(change, y)) {
			return change;
		}

		// if the player has travelled right
		if (x > change)
			// increase from the change to the starting x value to find the first location
			// before the colission occurred.
			for (int i = (int) change + 1; i > (int) x; i++) {
				if (canGo(i, y)) {
					return i;
				}
			}
		else {
			// travelled left
			// same concept as right just opposite direction
			for (int i = (int) change - 1; i < (int) x; i--) {
				if (canGo(i, y)) {
					return i;
				}
			}

		}

		// returns the start location if no better location can be found
		return x;

	}

	@Override
	protected Rectangle getRectangle(int x, int y) {
		return getRectangle();
	}

	@Override
	public void update(int time) {

		calculateX(time);
		super.update(time);
	}

	@Override
	protected String getName() {
		return "camera";
	}

}
