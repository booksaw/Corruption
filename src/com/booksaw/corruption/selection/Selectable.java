package com.booksaw.corruption.selection;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.level.Dimensions;
import com.booksaw.corruption.level.Location;
import com.booksaw.corruption.listeners.EditorKeyListener;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.render.GameCamera;

public abstract class Selectable implements Location, Dimensions {

	protected static final int circleD = 10;

	private static Selectable s;

	private static List<Selectable> selectable = new ArrayList<>();

	private static CursorMode mode;
	private static Point starting;

	public static void clearSelection() {

		for (Selectable s : selectable) {
			s.setSelected(false, false);
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

	protected boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		setSelected(selected, true);

	}

	public void setSelected(boolean selected, boolean remove) {

		EditorKeyListener listener = null;
		if (remove) {
			for (Listener l : Corruption.main.controller.getListeners()) {
				if (l instanceof EditorKeyListener) {
					listener = (EditorKeyListener) l;
					break;
				}
			}

			if (!listener.ctrl) {
				clearSelection();
			}

			if (selected) {
				selectable.add(this);
			} else if (remove) {
				selectable.remove(this);
			}
		}
		this.selected = selected;

	}

	public void hover(Point p) {

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
		p.y = GameCamera.cameraHeight - p.y;
		switch (mode) {
		case MOVE:
			Point offset = new Point(p.x - starting.x, p.y - starting.y);
			applyOffset(offset);
			starting = p;
			break;
		}
	}

	public abstract void delete();

	public abstract void applyOffset(Point p);

	public abstract Rectangle getRectangle();

	enum CursorMode {
		MOVE;
	}

}
