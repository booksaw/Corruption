package com.booksaw.corruption.render;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.sprites.Sprite;

/**
 * This class is used to render the gameplay
 * 
 * @author James
 */
public class GameCamera extends RenderInterface {
	private static final long serialVersionUID = -2929277030612645138L;

	// so the game camera is accessible by other objects
	public static GameCamera activeCamera;
	// dimensions of the camera
	public static int cameraWidth = Corruption.origionalDimensions.width,
			cameraHeight = Corruption.origionalDimensions.height;

	// location of the camera in the world
	public int x, y;

	/**
	 * literally just so we can static reference
	 */
	public GameCamera() {

		image = true;
		activeCamera = this;
	}

	/**
	 * For rendering
	 */
	@Override
	public void draw(Graphics g) {

		// we create a new buffered image of the graphics
		// this is so if the window is resizesed it is renders the same (as the frame
		// will be forced to keep to the aspect ratio
		// and pixelation is not an issue as graphics are pixelated anyway so no quality
		// is lost as no high quality images

		// setting the background of the active level
		g.setColor(LevelManager.activeLevel.backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		// filling the player
		for (Sprite s : LevelManager.activeLevel.getSprites()) {
			s.draw(g, x, y, cameraHeight);
		}
		// looping through all the objects and givng them a turn to render
		for (GameObject o : LevelManager.activeLevel.getLevelObjects()) {
			o.render(g, new Rectangle(x, y, cameraWidth, cameraHeight));
		}

	}

	/**
	 * Used for camera movement to find and set x to the closest valid x coord to
	 * the specified
	 * 
	 * @param x coord to change the camera to
	 */
	public void closestX(int x) {
		// if the camera is off the screen left or right, move it to the closest value
		if (x < 0) {
			x = 0;
		} else if (x + cameraWidth > LevelManager.activeLevel.getLevelDimensions().width) {
			x = LevelManager.activeLevel.getLevelDimensions().width - cameraWidth;
		}
		// setting the x value to be the changed y
		this.x = x;
	}

	/**
	 * Used for camera movement to find and set y to the closest valid y coord to
	 * the specified
	 * 
	 * @param y coord to change the cameara to
	 */
	public void closestY(int y) {
		// if the camera is off the screen, move it to the closest value
		if (y < 0) {
			y = 0;
		} else if (y + cameraHeight > LevelManager.activeLevel.getLevelDimensions().height) {
			y = LevelManager.activeLevel.getLevelDimensions().height - cameraHeight;
		}
		// setting the y value to be the changed y
		this.y = y;
	}

	/**
	 * Extended from RenderInterface no need at present
	 */
	@Override
	public void resize() {
	}
}
