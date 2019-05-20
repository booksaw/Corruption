package com.booksaw.corruption.sprites;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.trigger.Trigger;

public class Gaurd extends Sprite {

	String assetPath;
	double jumpHeight = 0, weight = 0.002;
	final double maxJump = 0.625;
	final double SPEED = 0.05;
	private Trigger t;

	public Gaurd(String info) {
		super(info);

		List<String> commands = new ArrayList<>();
		commands.add("kill:" + uuid);
		t = new Trigger(new Rectangle((int) x - getWidth(), (int) y, getWidth() * 2, getHeight()), true, commands);
		LevelManager.activeLevel.addTrigger(t);
	}

	public Gaurd() {
		super();
	}

	@Override
	protected void setup() {
		needsUpdating = true;
		assetPath = Config.ASSETSPATH + File.separator + "gaurd" + File.separator;
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
	protected Dimension getDeadDimension() {
		Dimension d = getDimension();
		return new Dimension(d.height, d.width);
	}

	@Override
	public BufferedImage getStanding() {
		return Utils.getImage(new File(assetPath + "sprite.png"));
	}

	@Override
	protected BufferedImage getWalking() {
		return Utils.getImage(new File(assetPath + "walking.png"));
	}

	@Override
	protected BufferedImage getCrouching() {
		return getWalking();
	}

	@Override
	public Rectangle getRectangle() {
		return getRectangle((int) x, (int) y);
	}

	@Override
	protected Rectangle getRectangle(int x, int y) {
		return new Rectangle(x, y, getWidth(), getHeight());
	}

	@Override
	protected String getName() {
		return "gaurd";
	}

	@Override
	public void update(int time) {

		if (state == AnimationState.DEAD) {
			return;
		}

		super.update(time);

		checkVision();
		calculateDirection(time);

		if (right) {
			x = changeX(x + (SPEED * time), x);
		} else {
			x = changeX(x - (SPEED * time), x);
		}

		y = changeY(y + (jumpHeight * time), y);
		jumpHeight -= (weight * time);

		if (jumpHeight > 0 && !canGo(x, y + 1)) {
			jumpHeight = 0;
		}

		// if they are going faster than terminal velocity
		if (Math.abs(jumpHeight) > maxJump) {
			jumpHeight = (jumpHeight < 0) ? -maxJump : maxJump;
		}

		doTrigger();

	}

	public void calculateDirection(int time) {
		if (right) {
			if (canGo(x + (SPEED * time) + getWidth(), y - 1) && !canGo(x, y - 1)) {
				right = false;
			} else if (!canGo(x + 1, y)) {
				right = false;
			}
		} else {

			if (canGo(x - (SPEED * time) - getWidth(), y - 1) && !canGo(x, y - 1)) {
				right = true;
			} else if (!canGo(x - 1, y)) {
				right = true;
			}
		}
	}

	public void checkVision() {
		if (right) {
			int x = (int) this.x + getWidth();
			while (canGo(x, y) && this.x + 300 > x) {
				Sprite s = getSprite(new Point(x, (int) y), LevelManager.activeLevel.getSprites());
				if (s != null) {
					see(s);
					break;
				}

				s = getSprite(new Point(x, (int) y + getHeight()), LevelManager.activeLevel.getSprites());
				if (s != null) {
					see(s);
					break;
				}

				x++;
			}

		} else {
			int x = (int) this.x - 1;
			while (canGo(x, y) && this.x - 300 < x) {
				Sprite s = getSprite(new Point(x, (int) y), LevelManager.activeLevel.getSprites());
				if (s != null) {
					see(s);
					break;
				}

				s = getSprite(new Point(x, (int) y + getHeight()), LevelManager.activeLevel.getSprites());
				if (s != null) {
					see(s);
					break;
				}

				x--;
			}
		}
	}

	public void see(Sprite s) {
		if (!s.isDetectable()) {
			return;
		}

		LevelManager.activeLevel.reset();
	}

	public void doTrigger() {
		if (right)
			t.setX((int) x - getWidth());
		else
			t.setX((int) x);
		t.setY((int) y);

	}

	@Override
	public void reset(boolean fail) {
		if (!fail) {
			super.reset(false);
		} else
			state = AnimationState.DEAD;
	}

	@Override
	public BufferedImage getDead() {
		// rotate anticlockwise 90
		BufferedImage i = Utils.rotateClockwise90(getStanding());
		i = Utils.rotateClockwise90(i);
		return Utils.rotateClockwise90(i);
	}

}
