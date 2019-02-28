package com.booksaw.corruption;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class CursorManager {

	public static void setCursor(BufferedImage img) {

		// Create a new blank cursor.
		Cursor imgCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "cursor image");

		Corruption.main.getFrame().setCursor(imgCursor);
	}

	public static void resetCursor() {
		Corruption.main.getFrame().setCursor(Cursor.getDefaultCursor());
	}

	public static void hideCursor() {

		BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor imgCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "blank cursor");

		Corruption.main.getFrame().setCursor(imgCursor);
	}
}
