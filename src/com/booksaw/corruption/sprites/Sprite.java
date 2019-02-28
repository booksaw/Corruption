package com.booksaw.corruption.sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.Updatable;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.render.GameCamera;

/**
 * Class all sprites extend, used to have basic information about sprites
 * 
 * @author James
 *
 */
public abstract class Sprite implements Updatable {

	public static Sprite getSprite(Point p, List<Sprite> sprites) {
		return getSprite(new Rectangle(p, new Dimension(1, 1)), sprites);
	}

	public static Sprite getSprite(Rectangle r, List<Sprite> sprites) {

		for (Sprite temp : sprites) {
			if (temp.getRectangle().intersects(r)) {
				return temp;
			}
		}

		return null;
	}

	// stores which animation is being run
	private AnimationState state;
	// keeps track of which image of the animation being used
	protected int animationStage = 0, countOnStage = 0;
	// max number of counts on each image
	protected final int MAXCOUNT = 400;

	// the multiplier for pixels
	public static final int PIXELMULT = 5;

	// location of sprite
	protected double x, y;
	// for hit boxes
	protected Dimension dimensions, crouchDimensions;
	// images in all the standard states
	public BufferedImage standing, walking, crouching;
	// names are clear
	boolean right, isCrouching;
	// number of animation stages for each state
	protected int standingMax = 1, crouchingMax = 2, walkingMax = 2;

	protected boolean activePlayer = false;

	/**
	 * Runs at begining of constructor if any setup is required overried preffered
	 * to constructor as simpler to write for used for things like setting up an
	 * asset folder
	 */
	protected void setup() {
	}

	/**
	 * Used to get the dimensions of the sprite
	 * 
	 * @return
	 */
	protected abstract Dimension getDimension();

	/**
	 * Gives the crouching dimensions
	 * 
	 * @return
	 */
	protected abstract Dimension getCrouchDimension();

	/**
	 * Standing image
	 * 
	 * @return
	 */
	protected abstract BufferedImage getStanding();

	/**
	 * Walking image
	 * 
	 * @return
	 */
	protected abstract BufferedImage getWalking();

	/**
	 * Crouching image
	 * 
	 * @return
	 */
	protected abstract BufferedImage getCrouching();

	/**
	 * Hitbox of character
	 * 
	 * @return
	 */
	protected abstract Rectangle getRectangle();

	/**
	 * Gives the hitbox of a character if it was at a specific location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected abstract Rectangle getRectangle(int x, int y);

	public Sprite() {
		// any sprite specific setup
		setup();
		// starting state
		state = AnimationState.WALKING;
		// defaults
		right = true;
		isCrouching = false;
		// getting the dimensions
		dimensions = getDimension();
		crouchDimensions = getCrouchDimension();
		// setting the images
		standing = getStanding();
		walking = getWalking();
		crouching = getCrouching();

	}

	public Sprite(String ref) {
		this();
		loadDetails(ref);
	}

	protected void loadDetails(String ref) {
		String[] split = ref.split(";");

		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);

	}

	/**
	 * Used for image updates
	 * 
	 * @param time
	 * @return
	 */
	public BufferedImage getActiveImage(int time) {
		return getActiveImage(true, time);
	}

	/**
	 * Used for images updates
	 * 
	 * @param update the frame or not
	 * @param time   how long since last clock
	 * @return the image to use
	 */
	public BufferedImage getActiveImage(boolean update, int time) {
		// dealing with updating information
		// this is pulled from their varibles
		if (update) {
			countOnStage += time;
			if (countOnStage >= MAXCOUNT) {
				animationStage++;
				if (animationStage >= getMax(state)) {
					resetAnimation();
				}
				countOnStage = 0;
			}
		}
		// getting which image
		switch (state) {
		case CROUCHING:
			return crouching;
		case STATIONARY:
			return standing;
		case WALKING:
			return walking;
		}
		return null;

	}

	/*
	 * Gets the state of the animation
	 */
	public AnimationState getState() {
		return state;
	}

	/**
	 * Sets what state the sprite is
	 * 
	 * @param state
	 */
	public void setState(AnimationState state) {
		resetAnimation();
		this.state = state;
	}

	/**
	 * 
	 * @return x coord of sprite
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * 
	 * @return y coord of sprite
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * 
	 * @param x coord of sprite
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y coord of sprite
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	public void setLocation(Point p) {
		y = p.y;
		x = p.x;
	}

	/**
	 * gives the dimensions of the active state
	 * 
	 * @return
	 */
	public Dimension getDisplayDimension() {
		if (isCrouching)
			return crouchDimensions;
		return dimensions;

	}

	/**
	 * gives the max number on each state
	 * 
	 * @param s
	 * @return
	 */
	private int getMax(AnimationState s) {
		switch (state) {
		case CROUCHING:
			return crouchingMax;
		case STATIONARY:
			return standingMax;
		case WALKING:
			return walkingMax;
		}
		return 1;

	}

	/**
	 * @return which stage of the animation the sprite is on
	 */
	public int getAnimationStage() {
		return animationStage;
	}

	/**
	 * Restarts the animation
	 */
	private void resetAnimation() {
		animationStage = 0;
		countOnStage = 0;
	}

	/**
	 * 
	 * @return if the player is moving right
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * used to draw the sprite
	 * 
	 * @param g            the graphics to draw to
	 * @param cameraX      location of camera x coord
	 * @param cameraY      location of camera y coord
	 * @param cameraHeight the height of the camera
	 */
	public void draw(Graphics g, int cameraX, int cameraY, int cameraHeight) {
		Dimension d = getDisplayDimension();

		g.drawImage(getActiveImage(Corruption.time),
				(isRight()) ? ((int) x - cameraX) : ((int) x - cameraX) + (int) (d.getWidth() * PIXELMULT),
				(cameraHeight - ((int) y + (int) (d.getHeight() * PIXELMULT)) - cameraY),
				(!isRight()) ? ((int) x - cameraX) : ((int) x - cameraX) + (int) (d.getWidth() * PIXELMULT),
				cameraHeight - (int) y - cameraY,

				(int) (getAnimationStage() * d.getWidth()), 0, (int) ((getAnimationStage() + 1) * d.getWidth()),
				(int) d.getHeight(), null);
	}

	/**
	 * Used to bind the sprite to the camera.
	 * 
	 * @param tempX
	 */
	protected void bindToCamera(int x) {
		// BINDING CAMERA TO PLAYER
		if (right) {
			// travelled right
			double cam60 = GameCamera.cameraWidth * 0.6 + GameCamera.activeCamera.x;
			if (x > cam60) {
				GameCamera.activeCamera.closestX((int) (x - (int) (GameCamera.cameraWidth * 0.6)));
			}

		} else {
			// travelled left
			double cam40 = GameCamera.cameraWidth * 0.4 + GameCamera.activeCamera.x;
			if (x < cam40) {
				GameCamera.activeCamera.closestX((int) (x - (int) (GameCamera.cameraWidth * 0.4)));
			}

		}
	}

	public void setActiveplayer(boolean active) {
		activePlayer = active;
	}

	@Override
	public void update(int time) {
		if (activePlayer) {
			bindToCamera((int) x);
		}
	}

	/**
	 * Calculates the closest x coord (stops players going off screen)
	 * 
	 * @param x the x you are testing
	 * @return the closest x value to it
	 */
	protected double closestX(double x) {
		// checking off the screen
		if (x < 0) {
			return 0;
		} else if (x + (dimensions.getWidth() * PIXELMULT) > LevelManager.activeLevel.getLevelDimensions().getWidth()) {
			return LevelManager.activeLevel.getLevelDimensions().getWidth() - (dimensions.getWidth() * PIXELMULT);
		}
		return x;

	}

	/**
	 * Finds the closest y coord to the specified one
	 * 
	 * @param y the coord trying to be changed to
	 * @return the coord of y
	 */
	protected double closestY(double y) {
		// same concept as closestX (see that for detailed breakdown)
		if (y < 0) {
			return 0;
		} else if (y + (dimensions.getHeight() * PIXELMULT) > LevelManager.activeLevel.getLevelDimensions()
				.getHeight()) {
			return LevelManager.activeLevel.getLevelDimensions().getHeight() - (dimensions.getHeight() * PIXELMULT);
		} else
			return y;
	}

	/**
	 * Checks if the player can go to that location
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @return if the player can go there or not
	 */
	protected boolean canGo(double x, double y) {
		// gens the rectangle for that location
		Rectangle r = getRectangle((int) x, (int) y);

		// checks intersections with each rectangle
		for (GameObject o : GameObject.getObjects()) {
			if (o.getRectangle().intersects(r)) {
				return false;
			}
		}
		return true;
	}

	protected abstract String getName();

	@Override
	public String toString() {
		if (this instanceof CameraSprite) {
			return "";
		}
		return "sprite:" + getName() + ":" + ((int) x) + ";" + ((int) y);
	}
	
	public BufferedImage generateCursorImage() {
		
		BufferedImage cursorImg = new BufferedImage(dimensions.width * PIXELMULT, dimensions.height * PIXELMULT, BufferedImage.TYPE_INT_RGB);
		Graphics g = cursorImg.getGraphics();
		
		double ratio = dimensions.getWidth() / dimensions.getHeight(); 
		
		g.drawImage(standing, 0, 0, (int)(cursorImg.getWidth() * PIXELMULT * ratio), cursorImg.getHeight() * PIXELMULT, 0, 0, dimensions.width * PIXELMULT, dimensions.height * PIXELMULT, null);
		
		
		return cursorImg;
		
	}
}
