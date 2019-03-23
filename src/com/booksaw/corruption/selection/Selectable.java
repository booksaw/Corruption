package com.booksaw.corruption.selection;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.Renderable;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.level.Dimensions;
import com.booksaw.corruption.level.Location;
import com.booksaw.corruption.listeners.EditorKeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;

public abstract class Selectable extends Renderable implements Location, Dimensions {

	protected double x, y;

	protected static final int circleD = 10;

	private static Selectable s = null;

	private static List<Selectable> selectable = new ArrayList<>();

	protected static CursorMode mode;
	private static Point starting;
	protected boolean resizable = true;

	public static void clearSelection() {

		for (Selectable s : selectable) {
			s.setSelected(false, false, false);
		}
		selectable = new ArrayList<>();
	}

	public static void deleteSelected() {
		for (Selectable s : selectable) {
			s.delete();
		}
	}

	public static List<Selectable> getSelectables() {
		return selectable;
	}

	public static Selectable getSelectable(Point p) {
		p.x = p.x + GameCamera.activeCamera.x;
		if (s != null) {
			return s;
		}

		Rectangle r = new Rectangle(p.x, GameCamera.cameraHeight - p.y, 1, 1);
		for (Selectable s : selectable) {
			if (s.getRectangle().intersects(r)) {
				return s;
			}
		}
		return null;
	}

	public static void setSelectable(List<Selectable> selectable) {
		Selectable.selectable = selectable;
	}

	public void sortSelectable() {
		Selectable[] s = selectable.toArray(new Selectable[selectable.size()]);

		boolean changed = false;
		Selectable temp;

		for (int i = 0; i < s.length && !changed; i++) {
			changed = false;
			for (int j = 1; j < s.length - i; j++) {
				if (s[j - 1].getPriority() < s[j].getPriority()) {
					temp = s[j - 1];
					s[j - 1] = s[j];
					s[j] = temp;
					changed = true;
				}
			}
		}

		selectable = new ArrayList<>(Arrays.asList(s));
	}

	protected boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		setSelected(selected, true, false);

	}

	public void setSelected(boolean selected, boolean remove, boolean ctrl) {

		EditorKeyListener listener = null;
		if (remove) {
			for (Listener l : Corruption.main.controller.getListeners()) {
				if (l instanceof EditorKeyListener) {
					listener = (EditorKeyListener) l;
					break;
				}
			}

			if (listener != null && !listener.ctrl && !ctrl) {
				clearSelection();
			}

			if (selected) {
				selectable.add(this);
			} else if (remove) {
				selectable.remove(this);
			}
		}
		this.selected = selected;
		sortSelectable();

	}

	public void hover(Point p) {

		p = Utils.getScaledPoint(p, new Dimension(GameCamera.cameraWidth, GameCamera.cameraHeight));
		p.y = GameCamera.cameraHeight - (p.y);
//		p.x = p.x - GameCamera.activeCamera.x;
		if (resizable) {
			if (p.x + GameCamera.activeCamera.x >= ((x + (getWidth())) - circleD)
					&& p.x <= ((x + (getWidth())) + circleD)) {
				// on right of block
				if (p.y >= ((y + (getHeight())) - circleD) && p.y <= ((y + (getHeight())) + circleD)) {
					// top right
					mode = CursorMode.TR;
					CursorManager.setCursor(Cursor.NE_RESIZE_CURSOR);
					return;
				} else if (p.y >= ((y) - circleD) && p.y <= ((y) + circleD)) {
					// bottom right
					mode = CursorMode.BR;
					CursorManager.setCursor(Cursor.SE_RESIZE_CURSOR);
					return;
				}

			} else if (p.x + GameCamera.activeCamera.x >= ((x) - circleD) && p.x <= ((x) + circleD)) {
				// on left
				if (p.y >= ((y + (getHeight())) - circleD) && p.y <= ((y + (getHeight())) + circleD)) {
					// top left
					mode = CursorMode.TL;
					CursorManager.setCursor(Cursor.NW_RESIZE_CURSOR);
					return;
				} else if (p.y >= ((y) - circleD) && p.y <= ((y) + circleD)) {
					// bottom left
					mode = CursorMode.BL;
					CursorManager.setCursor(Cursor.SW_RESIZE_CURSOR);
					return;
				}

			}
		}

		CursorManager.setCursor(Cursor.MOVE_CURSOR);
		mode = CursorMode.MOVE;
	}

	public void click(Point p) {
		s = this;
		starting = Utils.getScaledPoint(p, new Dimension(GameCamera.cameraWidth, GameCamera.cameraHeight));
		starting.y = GameCamera.cameraHeight - starting.y;
	}

	public void release(Point p) {
		p.y = GameCamera.cameraHeight - p.y;
		s = null;
	}

	public void drag(Point p) {
		p = Utils.getScaledPoint(p, new Dimension(GameCamera.cameraWidth, GameCamera.cameraHeight));
		p.y = (GameCamera.cameraHeight) - (p.y);
		p.x = p.x - GameCamera.activeCamera.x;
		if (starting == null) {
			return;
		}
		Point offset = new Point(p.x - starting.x, p.y - starting.y);

		switch (mode) {
		case MOVE:
			for (Selectable s : selectable) {
				s.applyOffset(offset);
			}
			starting = p;
			break;
		case BL:
			if (getWidth() - offset.x <= 0 || getX() + getWidth() < p.x) {
				return;
			}

			if (getHeight() - offset.y <= 0 || getY() + getHeight() < p.y) {
				return;
			}
			starting = p;
			setWidth(getWidth() - offset.x);
			setHeight(getHeight() - offset.y);
			setX(p.x);
			setY(p.y);
			break;
		case BR:

			if (getHeight() - offset.y <= 0 || getY() + getHeight() < p.y) {
				return;
			}
			starting = p;
			setWidth(getWidth() + offset.x);
			setHeight(getHeight() - offset.y);
			setY(p.y);
			break;
		case TL:
			if (getWidth() - offset.x <= 0 || getX() + getWidth() < p.x) {
				return;
			}

			starting = p;
			setWidth(getWidth() - offset.x);
			setHeight(getHeight() + offset.y);
			setX(p.x);
			break;
		case TR:
			setWidth(getWidth() + offset.x);
			setHeight(getHeight() + offset.y);
			starting = p;
			break;
		}
	}

	public abstract void delete();

	public abstract void applyOffset(Point p);

	public abstract Rectangle getRectangle();

	protected abstract void paintComp(Graphics g, Rectangle c);

	@Override
	public void paint(Graphics g, Rectangle r) {
		paintComp(g, r);

		if (selected) {
			int cameraX = r.x;
			int cameraHeight = r.height;
			int cameraY = r.y;

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

	enum CursorMode {
		MOVE, TR, TL, BR, BL;
	}

}
