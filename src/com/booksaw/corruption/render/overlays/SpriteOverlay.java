package com.booksaw.corruption.render.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map.Entry;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.sprites.Sprite;
import com.booksaw.corruption.sprites.SpriteList;

public class SpriteOverlay extends Overlay {

	public static boolean active = false;

	HashMap<Sprite, Rectangle> sprites = new HashMap<>();

	public SpriteOverlay() {
		active = true;
		int x = 70;
		int y = 50;

		for (SpriteList l : SpriteList.values()) {
			Sprite s = SpriteList.getSprite(l);
			Dimension d = s.getDisplayDimension();

			s.setX(x);
			s.setY(-y - (d.getHeight() * Sprite.PIXELMULT));

			sprites.put(s,
					new Rectangle(x, y, (int) d.getWidth() * Sprite.PIXELMULT, (int) d.getHeight() * Sprite.PIXELMULT));

			x += d.getWidth() + 20;
		}

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRoundRect(50, 30, (int) Corruption.origionalDimensions.getWidth() - 100,
				(int) Corruption.origionalDimensions.getHeight() - 100, 20, 20);

		for (Sprite s : sprites.keySet()) {
			s.draw(g, 0, 0, 0);

		}

	}

	public boolean click(Point p) {
		Rectangle r = new Rectangle(p, new Dimension(1, 1));

		for (Entry<Sprite, Rectangle> temp : sprites.entrySet()) {
			if (r.intersects(temp.getValue())) {
				select(temp.getKey());
				return true;
			}
		}

		return false;

	}

	public void select(Sprite s) {

	}

	@Override
	public void hide() {
		active = false;
	}

}
