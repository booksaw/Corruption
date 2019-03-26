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
import com.booksaw.corruption.level.Location;
import com.booksaw.corruption.level.interactable.Interactable;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.level.objects.Mode;
import com.booksaw.corruption.listeners.KeyListener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.selection.Selectable;

/**
 * Class all sprites extend, used to have basic information about sprites
 * 
 * @author James
 *
 */
public abstract class Sprite extends Selectable implements Updatable, Location {

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

	Interactable currentInteractable = null;

	// stores which animation is being run
	private AnimationState state;
	// keeps track of which image of the animation being used
	protected int animationStage = 0, countOnStage = 0;
	// max number of counts on each image
	protected final int MAXCOUNT = 400;

	// the multiplier for pixels
	public static final int PIXELMULT = 5;

	// starting location of the sprite
	protected Point startingLocation;

	// for hit boxes
	protected Dimension dimensions, crouchDimensions;
	// images in all the standard states
	public BufferedImage standing, walking, crouching;
	// names are clear
	boolean right, isCrouching;
	// number of animation stages for each state
	protected int standingMax = 1, crouchingMax = 2, walkingMax = 2;

	public boolean activePlayer = false;
	public boolean controllable = false;

	/**
	 * Runs at beginning of constructor if any setup is required override preferred
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
	public abstract Rectangle getRectangle();

	/**
	 * Gives the hitbox of a character if it was at a specific location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected abstract Rectangle getRectangle(int x, int y);

	public Sprite() {
		priority = 5;
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
		resizable = false;

	}

	public Sprite(String ref) {
		this();
		loadDetails(ref);
	}

	protected void loadDetails(String ref) {
		String[] split = ref.split(";");

		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		activePlayer = Boolean.parseBoolean(split[2]);
		controllable = Boolean.parseBoolean(split[3]);

		startingLocation = new Point((int) x, (int) y);

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

	/**
	 * 
	 * @param x coord of sprite
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y coord of sprite
	 */
	public void setY(int y) {
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
	@Override
	public void paintComp(Graphics g, Rectangle r) {

		int cameraHeight = r.height;
		int cameraX = r.x;
		int cameraY = r.y;
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

	/**
	 * Used to set this to be the sprite the camera follows
	 * 
	 * @param active
	 */
	public void setActiveplayer(boolean active) {
		if (active) {
			// used to catch errors if this is run on level load
			try {
				LevelManager.activeLevel.clearActivePlayer();
			} catch (Exception e) {

			}
		}

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
		for (GameObject o : LevelManager.activeLevel.getLevelObjects()) {

			if (o.getRectangle().intersects(r)) {
				if (o.collisionMode == Mode.SOLID) {
					return false;
				} else if (o.collisionMode == Mode.DEATH) {
					reset(true);
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * Used to get the file name of the sprite
	 * 
	 * @return
	 */
	protected abstract String getName();

	/**
	 * Used to save all the information about the sprite
	 */
	@Override
	public String toString() {
		if (this instanceof CameraSprite) {
			return "";
		}
		return "sprite:" + getName() + ":" + ((int) startingLocation.x) + ";" + ((int) startingLocation.y) + ";"
				+ activePlayer + ";" + controllable;
	}

	public BufferedImage generateCursorImage() {

		BufferedImage cursorImg = new BufferedImage(dimensions.width * PIXELMULT, dimensions.height * PIXELMULT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = cursorImg.getGraphics();

		double ratio = dimensions.getWidth() / dimensions.getHeight();

		g.drawImage(standing, 0, 0, (int) (cursorImg.getWidth() * PIXELMULT * ratio), cursorImg.getHeight() * PIXELMULT,
				0, 0, dimensions.width * PIXELMULT, dimensions.height * PIXELMULT, null);

		return cursorImg;

	}

	/**
	 * Used to move the player back to their start location
	 */
	public void reset() {
		reset(false);
	}

	/**
	 * Tracking how long since prev reset, so player cannot die on the same spikes
	 * twice without actually messing up twice
	 */
	private long prevReset = 0L;

	public void reset(boolean fail) {

		x = startingLocation.x;
		y = startingLocation.y;

		if (fail && (System.currentTimeMillis() - prevReset) > 20) {
			LevelManager.activeLevel.fails++;
		}

		prevReset = System.currentTimeMillis();
	}

	/**
	 * Sets the current location of the sprite to be the starting location
	 */
	public void setStartingLocation() {
		setStartingLocation((int) x, (int) y);
	}

	/**
	 * Sets the current starting location of the sprite to be the location given
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 */
	public void setStartingLocation(int x, int y) {
		startingLocation = new Point(x, y);
	}

	@Override
	public void applyOffset(Point p) {
		x += p.x;
		y += p.y;
		startingLocation = new Point((int) x, (int) y);

	}

	@Override
	public void delete() {

		LevelManager.activeLevel.removeSprite(this);

	}

	@Override
	public int getWidth() {
		return (int) dimensions.getWidth() * PIXELMULT;
	}

	@Override
	public int getHeight() {
		return (int) dimensions.getHeight() * PIXELMULT;
	}

	@Override
	public void setWidth(int width) {

	}

	@Override
	public void setHeight(int height) {

	}

	/**
	 * Used to make checks for interactables should be run at the beginning of the
	 * update method
	 * 
	 * @return true if interactable is handling the update, false if sprite should
	 *         still handle it
	 */
	public boolean sortInteractables() {

		if (currentInteractable != null && currentInteractable.getRectangle().intersects(getRectangle())) {
			return currentInteractable.updateInteraction(this);

		}

		if (!((KeyListener) Corruption.main.controller.getListeners().get(0)).interact) {
			return false;
		}

		currentInteractable = Interactable.getInteractable(getRectangle(), LevelManager.activeLevel.getInteractables());

		if (currentInteractable == null) {
			return false;
		}
		return currentInteractable.interact(this);
	}

}
