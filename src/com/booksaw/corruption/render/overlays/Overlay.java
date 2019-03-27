package com.booksaw.corruption.render.overlays;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Overlay {

	private static List<Overlay> activeOverlays = new ArrayList<>();

	public static List<Overlay> getActiveOverlays() {
		return activeOverlays;
	}

	public static void addOverlay(Overlay overlay) {
		overlay.show();
		activeOverlays.add(overlay);
	}

	public static void removeOverlay(Overlay overlay) {

		if (overlay == null) {
			return;
		}

		overlay.hide();
		activeOverlays.remove(overlay);

	}

	public static void resizeAll() {
		for (Overlay temp : activeOverlays) {
			temp.resize();
		}
	}

	public static void clearOverlays() {
		activeOverlays = new ArrayList<>();
	}

	public abstract void render(Graphics g);

	/**
	 * Used for code in the subclass which is run when the overlay is removed
	 */
	public void hide() {
	}

	public void show() {
	}

	public abstract void resize();
}
