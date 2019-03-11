package com.booksaw.corruption;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.listeners.EditorKeyListener;
import com.booksaw.corruption.listeners.Listener;

public abstract class Selectable {

	private static List<Selectable> selectable = new ArrayList<>();

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

	public static List<Selectable> getSelectable() {
		return selectable;
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
		if (!remove) {
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

	public abstract void delete();

	public abstract void applyOffset(Point p);

}
