package com.booksaw.corruption;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class CursorManager {

	public static boolean normal = true;

	public static void setCursor(BufferedImage img) {

		// Create a new blank cursor.
		Cursor imgCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "cursor image");

		Corruption.main.getFrame().setCursor(imgCursor);
		normal = true;
	}

	public static void resetCursor() {
		Corruption.main.getFrame().setCursor(Cursor.getDefaultCursor());
		normal = true;
	}

	public static void hideCursor() {

		BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor imgCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "blank cursor");
		Corruption.main.getFrame().setCursor(imgCursor);
		normal = true;
	}

	public static void setCursor(int cursortype) {
		normal = false;
		Corruption.main.getFrame().setCursor(new Cursor(cursortype));
	}
}
