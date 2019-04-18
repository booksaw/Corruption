package com.booksaw.corruption.sprites;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;

public class BlueNPC extends Sprite {

	String assetFolder;

	public BlueNPC(String info) {
		super(info);

		state = AnimationState.STATIONARY;

	}

	public BlueNPC() {
		super();
	}

	@Override
	protected void setup() {
		assetFolder = Config.ASSETSPATH + File.separator + "bluenpc" + File.separator;
	}

	@Override
	protected Dimension getDimension() {
		return new Dimension(7, 11);
	}

	@Override
	protected Dimension getCrouchDimension() {
		return new Dimension(7, 10);
	}

	@Override
	public BufferedImage getStanding() {
		return Utils.getImage(new File(assetFolder + "sprite.png"));
	}

	@Override
	protected BufferedImage getWalking() {
		return Utils.getImage(new File(assetFolder + "walking.png"));
	}

	@Override
	protected BufferedImage getCrouching() {
		return getWalking();
	}

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

		// returning the rectangle
		return new Rectangle((int) x, (int) y, (int) dimensions.getWidth() * (int) PIXELMULT,
				(int) dimensions.getHeight() * PIXELMULT);
	}

	@Override
	protected String getName() {
		return "bluenpc";
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
