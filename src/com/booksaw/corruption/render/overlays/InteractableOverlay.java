package com.booksaw.corruption.render.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.configuration.YamlConfiguration;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.interactable.Interactable;
import com.booksaw.corruption.listeners.EditorMouseListener;

public class InteractableOverlay extends Overlay {

	HashMap<Interactable, Rectangle> interactables = new HashMap<>();
	private boolean show = false;

	public InteractableOverlay() {
		int x = 70;
		int y = 50;

		YamlConfiguration config = new YamlConfiguration(new File(Interactable.PATH + "interactables.yml"));

		for (String str : config.getStringList("list")) {
			Interactable i = new Interactable(str, LevelManager.activeLevel);
			Rectangle d = i.getRectangle();

			i.setX(x);
			i.setY(-y - (d.height));

			interactables.put(i, new Rectangle(x, y, (int) d.getWidth(), (int) d.getHeight()));

			x += d.getWidth() + 20;
		}

		EditorMouseListener.selection = ActiveSelection.INTERACTABLE;

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRoundRect(50, 30, (int) Corruption.origionalDimensions.getWidth() - 100,
				(int) Corruption.origionalDimensions.getHeight() - 100, 20, 20);

		for (Interactable i : interactables.keySet()) {
			i.paint(g, new Rectangle(0, 0, 0, 0));

		}

	}

	public boolean click(Point p) {
		Rectangle r = new Rectangle(p, new Dimension(1, 1));

		for (Entry<Interactable, Rectangle> temp : interactables.entrySet()) {

			if (r.intersects(temp.getValue())) {
				select(temp.getKey());
				return true;
			}
		}

		return false;

	}

	public void select(Interactable i) {
		show = true;
		Overlay.addOverlay(new InteractableCursorOverlay(i));

	}

	@Override
	public void hide() {

		if (!show) {
			EditorMouseListener.selection = ActiveSelection.MAIN;
		}
	}

	@Override
	public void resize() {

	}

}
