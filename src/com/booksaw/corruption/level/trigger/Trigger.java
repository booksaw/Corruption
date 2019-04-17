package com.booksaw.corruption.level.trigger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.UUID;

import com.booksaw.corruption.execution.ExecutionChain;
import com.booksaw.corruption.level.Dimensions;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.Location;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.selection.Selectable;

public class Trigger extends Selectable implements Dimensions, Location {

	public static boolean showTriggers = false;

	public static void manageTriggers(Rectangle r) {
		Trigger t = getTrigger(r, LevelManager.activeLevel.getTriggers());
		if (t == null || t.active) {
			return;
		}

		t.trigger();

	}

	public static Trigger getTrigger(Point p, List<Trigger> triggers) {
		return getTrigger(new Rectangle(p, new Dimension(1, 1)), triggers);
	}

	public static Trigger getTrigger(Rectangle r, List<Trigger> triggers) {

		for (Trigger temp : triggers) {
			if (temp.getRectangle().intersects(r)) {
				return temp;
			}
		}

		return null;
	}

	public boolean active = false;

	public Trigger(String ref) {
		super();
		priority = 1;

		// getting object data from the split
		String[] split = ref.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		width = Integer.parseInt(split[2]);
		height = Integer.parseInt(split[3]);

		try {
			uuid = UUID.fromString(split[4]);
		} catch (Exception e) {
			uuid = generateUUID();
		}

	}

	public Trigger(Rectangle position) {
		super();
		x = position.x;
		y = position.y;
		width = position.width;
		height = position.height;
		uuid = generateUUID();
	}

	int width, height;

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public void applyOffset(Point p) {
		x += p.x;
		y += p.y;
	}

	@Override
	public void delete() {
		LevelManager.activeLevel.removeTrigger(this);
	}

	public boolean needsSaving() {

		if (width < 5 || height < 5)
			return false;
		return true;
	}

	public void trigger() {
		active = true;
		new ExecutionChain("commands." + uuid, LevelManager.activeLevel.getSaveManager().config);
	}

	@Override
	protected void paintComp(Graphics g, Rectangle camera) {

		if (showTriggers) {
			g.setColor(Color.WHITE);
			g.drawRect(((int) x - camera.x), GameCamera.cameraHeight - (int) y - height - camera.y, width, height);

		}

	}

	@Override
	public String toString() {

		return "trigger:" + (int) x + ";" + (int) y + ";" + width + ";" + height + ";" + uuid;

	}

	@Override
	public String getCopy() {
		return toString();
	}
}
