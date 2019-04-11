package com.booksaw.corruption.sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.KeyListener;
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
	public Rectangle getRectangle() {
		return new Rectangle(-1, -1, 0, 0);
	}

	@Override
	public void paint(Graphics g, Rectangle r) {
	}

	@Override
	public void bindToCamera(int x) {
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

		if (KeyListener.listen.left) {
			x = changeX(x - (SPEED * time), x);
			if (x + GameCamera.cameraWidth > LevelManager.activeLevel.levelDimensions.width) {
				x = LevelManager.activeLevel.levelDimensions.width - GameCamera.cameraWidth;
			}
		}

		if (KeyListener.listen.right) {
			x = changeX(x + (SPEED * time), x);
		}

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
