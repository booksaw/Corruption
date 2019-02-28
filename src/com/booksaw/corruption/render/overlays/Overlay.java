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
		activeOverlays.add(overlay);
	}

	public static void removeOverlay(Overlay overlay) {
		System.out.println(overlay);
		overlay.hide();
		activeOverlays.remove(overlay);
		
		
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
}
