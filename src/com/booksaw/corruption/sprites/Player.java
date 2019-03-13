package com.booksaw.corruption.sprites;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.listeners.KeyListener;

/**
 * This is the player class containing all code for the player
 * 
 */
public class Player extends Sprite {

	// where graphics and stuff are
	File assetFolder;

	// used for jumping
	double jumpHeight, weight = 0.002;
	// used to add a delay to jumps
	int minPrior = 100, priorJump = 0;
	// more jumping stuff (self explaniatory)
	final double maxJump = 0.6;

	// the speed left and right the player can travel
	private final double SPEED = 0.13;

	public Player(String info) {
		super(info);
	}

	public Player() {
		super();
	}

	/**
	 * Used to load the asset folder
	 */
	@Override
	protected void setup() {
		// path to the asset folder
		assetFolder = new File(Config.ASSETSPATH + File.separator + "player");

	}

	// unexciting methods to give sprite basic info about dimensions and images

	@Override
	protected Dimension getDimension() {
		return new Dimension(7, 11);
	}

	@Override
	protected Dimension getCrouchDimension() {
		return new Dimension(7, 10);
	}

	@Override
	protected BufferedImage getStanding() {
		return Utils.getImage(new File(assetFolder.getAbsolutePath() + File.separator + "sprite.png"));
	}

	@Override
	protected BufferedImage getWalking() {
		return Utils.getImage(new File(assetFolder.getAbsolutePath() + File.separator + "walking.png"));
	}

	@Override
	protected BufferedImage getCrouching() {
		return getWalking();
	}

	/**
	 * update all info ie position about the player
	 */
	@Override
	public void update(int time) {
		double tempX = x;
		// storing a temp value of x so any values

		calculateX(time);

		// setting the animation state (ie moving L-R or stationary)
		if (x == tempX) {
			setState(AnimationState.STATIONARY);
		} else {
			if (getState() != AnimationState.WALKING) {
				setState(AnimationState.WALKING);
			}

			if (x < tempX) {
				right = false;

			} else if (x > tempX) {
				right = true;
			}
		}

		calculateY(time);

		KeyListener listen = (KeyListener) Corruption.main.controller.getListeners().get(0);

		// setting crouching if possible
		if (listen.down) {
			isCrouching = true;
			if (getState() != AnimationState.CROUCHING) {
				setState(AnimationState.CROUCHING);
			}
		} else {

			isCrouching = false;
			// testing: if player uncrouches will collide with block above head
			if (!canGo(x, y)) {
				isCrouching = true;
			}

		}

		super.update(time);

//		if (tempY < y) {
//			// travelled up
//			double cam60 = GameCamera.cameraHeight * 0.4 + GameCamera.activeCamera.y;
//			if (y > cam60) {
//				GameCamera.activeCamera.closestY((int) (y - (int) (GameCamera.cameraHeight * 0.4)));
//			}
//
//		} else if (tempY > y) {
//			// travelled down
//			double cam40 = GameCamera.cameraHeight * 0.4 + GameCamera.activeCamera.y;
//			if (y < cam40) {
//				GameCamera.activeCamera.closestY((int) (y - (int) (GameCamera.cameraHeight * 0.4)));
//			}
//		}

	}

	/**
	 * Used to calculate the x values for the movement
	 * 
	 * @param time
	 */
	private void calculateX(int time) {

//		double tempY = y;
		// movement (left and right)
		KeyListener listen = (KeyListener) Corruption.main.controller.getListeners().get(0);

		if (listen.left) {
			x = changeX(x - (SPEED * time), x);
		}

		if (listen.right) {
			x = changeX(x + (SPEED * time), x);
		}

	}

	/**
	 * Used to calculate the y values for the movement
	 * 
	 * @param time
	 */
	private void calculateY(int time) {
		// if the player is on the ground
		if (canJump() && jumpHeight < 0) {
			priorJump += time;
			// resetting the height
			jumpHeight = 0;

			// if they actually want to jump and aren't crouching and they havent jumped to
			// recently
			KeyListener listen = (KeyListener) Corruption.main.controller.getListeners().get(0);
			if (!isCrouching && listen.up && minPrior <= priorJump) {
				// basically getting the person to jump
				jumpHeight = maxJump;
				y = changeY(y + (jumpHeight * time), y);
				jumpHeight -= weight;
				priorJump = 0;
			}

		} else {
			// if they can't jump accelerating them towards the ground
			y = changeY(y + (jumpHeight * time), y);
			jumpHeight -= (weight * time);
			// if they are going faster than terminal velocity
			if (Math.abs(jumpHeight) > maxJump) {
				jumpHeight = (jumpHeight < 0) ? -maxJump : maxJump;
			}

		}
	}

	/**
	 * 
	 * @return if they can jump
	 */
	private boolean canJump() {
		return canJump(y);
	}

	/**
	 * Tests if the player can jump at a specified coord
	 * 
	 * @param y the coord
	 * @return if they can jump
	 */
	private boolean canJump(double y) {
		// if they are on the ground
		if (y == 0) {
			return true;
		}
		// if there is a collision on the pixel below them
		if (canGo(x, y - 1)) {
			return false;
		}

		return true;
	}

	/**
	 * Returns the rectangle of the players location at the momemtn
	 */
	@Override
	public Rectangle getRectangle() {

		return getRectangle((int) x, (int) y);
	}

	/**
	 * Returns the rectangle of the player at the location specified
	 * 
	 * @param x the x coord of the player
	 * @param y the y coord of the player
	 * @return the rectangle
	 */
	protected Rectangle getRectangle(int x, int y) {
		// getting the correct dimensions
		Dimension dimensions = getDisplayDimension();

		// returning the rectnagle
		return new Rectangle((int) x, (int) y, (int) dimensions.getWidth() * (int) PIXELMULT,
				(int) dimensions.getHeight() * PIXELMULT);
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

	/**
	 * Used to change the value of y to the closest value of y possible This pushes
	 * the player out of blocks etc.
	 * 
	 * @param change where to move the player to
	 * @param y      the starting y coord
	 * @return the best y coord
	 */
	private double changeY(double change, double y) {
		// for explanation see changeX
		change = closestY(change);

		if (canGo(x, change)) {
			return change;
		}
		if (y > change)

			for (int i = (int) change; i < (int) y; i++) {
				if (canGo(x, i)) {
					return i;
				}
			}
		else {
			for (int i = (int) change; i > (int) y; i--) {
				if (canGo(x, i)) {
					jumpHeight = 0;

					return i;
				}
			}

		}
		return y;
	}

	@Override
	protected String getName() {
		return "player";
	}

	

}
