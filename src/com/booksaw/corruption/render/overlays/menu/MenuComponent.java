package com.booksaw.corruption.render.overlays.menu;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.render.GameMenu;

public class MenuComponent {

	String text, reference;
	Rectangle box;

	boolean selected = false, selectable;
	MenuOverlay main;

	public MenuComponent(MenuOverlay main, String text, String reference, boolean selectable) {

		this.text = text;
		this.main = main;
		this.reference = reference;
		this.selectable = selectable;

	}

	public void generateRectangle(int startingY, Graphics g, int width) {

		int textWidth = g.getFontMetrics(Config.f).stringWidth(text);

		box = new Rectangle((width / 2) - textWidth / 2, startingY, (width / 2) + textWidth / 2, Config.f.getSize());

	}

	public Rectangle getRectangle() {
		return box;
	}

	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the object as selected
	 * 
	 * @param selected  - if it should be setting it as selected or deselected
	 * @param direction - if another increase or decrease is required to occur as it
	 *                  is not selectable
	 */
	public void setSelected(boolean selected, int direction) {
		if (!selectable && selected) {
			if (direction == 1) {
				main.increase();
			} else if (direction == -1) {
				main.decrease();
			}
		}

		this.selected = selected;
	}

	public void render(Graphics g) {
		// for the new button
		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (main.getWidth() / 2) - (width / 2), box.y + box.height);
		// if its selected drawing the triangles
		if (selected) {
			int x = (main.getWidth() / 2) - (width / 2) - 30;
			int y = box.y + box.height - Config.f.getSize() + 5;
			g.drawImage(GameMenu.triangle, x, y, 15, Config.f.getSize() - 5, null);

			x = (main.getWidth() / 2) + (width / 2) + 15;
			g.drawImage(GameMenu.triangle, x + 15, y, x, y + Config.f.getSize() - 5, 0, 0, GameMenu.triangle.getWidth(),
					GameMenu.triangle.getHeight(), null);

		}
	}

	public String getReference() {
		return reference;
	}

	public void hover(Point p) {

	}

}
