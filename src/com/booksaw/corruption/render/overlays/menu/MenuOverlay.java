package com.booksaw.corruption.render.overlays.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.listeners.ListenerManager;
import com.booksaw.corruption.listeners.MenuOverlayListener;
import com.booksaw.corruption.render.GameMenu;
import com.booksaw.corruption.render.overlays.Overlay;

public abstract class MenuOverlay extends Overlay {

	List<MenuComponent> components = new ArrayList<>();

	public boolean setupRects = false;
	int selected = 0;

	public MenuOverlay() {

		addItems();

		components.get(0).setSelected(true);
	}

	@Override
	public void render(Graphics g) {

		if (!setupRects) {
			setupRects(g);
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Config.fontColor);
		g.setFont(Config.f);

		// showing "corruption"
		g.drawImage(GameMenu.logo, (getWidth() / 2) - (int) (getWidth() * 0.3),
				(getHeight() / 2) - (int) (getHeight() * 0.4), (int) (getWidth() * 0.6), 100, null);

		for (MenuComponent c : components) {
			c.render(g);
		}

	}

	@Override
	public void resize() {
		setupRects = false;
	}

	public void setupRects(Graphics g) {

		
		
		int y = (getHeight() / 2) - (int) (getHeight() * 0);
		int spacing = (((int) (getHeight() * 0.9)) - y) / components.size();

		for (MenuComponent c : components) {
			c.generateRectangle(y, g, getWidth());
			y += spacing;
		}
		setupRects = true;
	}

	public int getHeight() {
		return Corruption.main.getFrame().getContentPane().getHeight();
	}

	public int getWidth() {
		return Corruption.main.getFrame().getContentPane().getWidth();
	}

	public abstract void addItems();

	public void increase() {
		components.get(selected).setSelected(false);
		selected = (selected + 1) % components.size();
		components.get(selected).setSelected(true);
	}

	public void decrease() {

		components.get(selected).setSelected(false);
		selected = (selected - 1) % components.size();
		while (selected < 0) {
			selected += components.size();
		}

		components.get(selected).setSelected(true);

	}

	public void checkSelected(Point p) {
		Rectangle rect = new Rectangle(p, new Dimension(1, 1));

		for (MenuComponent c : components) {
			// moving the selected
			if (rect.intersects(c.getRectangle())) {
				c.hover(p);

				components.get(selected).setSelected(false);
				selected = components.indexOf(c);
				components.get(selected).setSelected(true);

				return;
			}
		}
	}

	public abstract void activate();

	public MenuComponent getSelected() {
		return components.get(selected);
	}

	MenuOverlayListener listener;

	@Override
	public void show() {
		super.show();
		listener = new MenuOverlayListener(this);
		ListenerManager.addListener(listener);
	}

	@Override
	public void hide() {
		super.hide();
		ListenerManager.removeListener(listener);
	}

}
