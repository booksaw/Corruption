package com.booksaw.corruption.render.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map.Entry;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.level.objects.ObjectList;
import com.booksaw.corruption.listeners.EditorMouseListener;

public class ObjectOverlay extends Overlay {

	HashMap<GameObject, Rectangle> objects = new HashMap<>();
	private boolean show = false;

	public ObjectOverlay() {
		int x = 70;
		int y = 50;

		for (ObjectList l : ObjectList.values()) {
			GameObject o = ObjectList.getObject(l);
			Dimension d = new Dimension(o.getWidth(), o.getHeight());

			o.setX(x);
			o.setY((int) (-y - (d.getHeight() /* * Sprite.PIXELMULT */
			)));

			objects.put(o, new Rectangle(x, y, (int) d.getWidth()/* * Sprite.PIXELMULT */,
					(int) d.getHeight() /* * Sprite.PIXELMULT */));

			x += d.getWidth() + 20;
		}

		EditorMouseListener.selection = ActiveSelection.OBJECT;

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRoundRect(50, 30, (int) Corruption.origionalDimensions.getWidth() - 100,
				(int) Corruption.origionalDimensions.getHeight() - 100, 20, 20);

		for (GameObject o : objects.keySet()) {
			o.paint(g, new Rectangle(0, 0, 0, 0));

		}

	}

	public boolean click(Point p) {
		Rectangle r = new Rectangle(p, new Dimension(1, 1));

		for (Entry<GameObject, Rectangle> temp : objects.entrySet()) {
			System.out.println(temp.getKey());
			if (r.intersects(temp.getValue())) {
				select(temp.getKey());
				return true;
			}
		}

		return false;

	}

	public void select(GameObject o) {
		System.out.println("selecting");
		show = true;
		Overlay.addOverlay(new ObjectCursorOverlay(o));

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