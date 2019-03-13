package com.booksaw.corruption.level.background;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.booksaw.corruption.render.GameCamera;

public class ColoredBackground extends Background {

	Color c;

	public ColoredBackground(String ref) {

		// getting object data from the split
		String[] split = ref.split(";");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		width = Integer.parseInt(split[2]);
		height = Integer.parseInt(split[3]);

		c = new Color(Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6]));

	}

	public ColoredBackground(Rectangle position, Color c) {
		x = position.x;
		y = position.y;
		width = position.width;
		height = position.height;

		this.c = c;
	}

	@Override
	public void draw(Graphics g, Rectangle camera) {
		g.setColor(c);
		g.fillRect((x - camera.x), GameCamera.cameraHeight - y - height - camera.y, width, height);

		if (selected) {
			int cameraX = camera.x;
			int cameraHeight = camera.height;
			int cameraY = camera.y;

			g.setColor(Color.WHITE);
			g.drawRect((int) (x - cameraX), (int) (cameraHeight - (y + cameraY + (getHeight()))), (int) (getWidth()),
					(int) (getHeight()));
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval((int) x - circleD / 2 - cameraX, (int) (cameraHeight - ((int) y + circleD / 2)), circleD,
					circleD);
			g.fillOval((int) ((int) x + (getWidth())) - circleD / 2 - cameraX,
					(int) (cameraHeight - ((int) y + circleD / 2)), circleD, circleD);
			g.fillOval((int) (int) x - circleD / 2 - cameraX,
					(int) (cameraHeight - ((int) y + circleD / 2 + (getHeight()))), circleD, circleD);
			g.fillOval((int) ((int) x + (getWidth())) - circleD / 2 - cameraX,
					(int) (cameraHeight - ((int) y + circleD / 2 + (getHeight()))), circleD, circleD);

		}

	}

	@Override
	public String toString() {

		return "background:colored:" + x + ";" + y + ";" + width + ";" + height + ";" + c.getRed() + ";" + c.getGreen()
				+ ";" + c.getBlue();

	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

}
