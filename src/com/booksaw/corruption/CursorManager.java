package com.booksaw.corruption;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * Used to keep the curosr selecting the correct object
 * 
 * @author James
 *
 */
public class CursorManager {

	/**
	 * Boolean for if the cursor is the standard pointer at the moment
	 */
	public static boolean normal = true;
	public static boolean hidden = false;

	/**
	 * Sets the cursor to the image given
	 * 
	 * @param img the image to set
	 */
	public static void setCursor(BufferedImage img) {

		// Create a new blank cursor.
		Cursor imgCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "cursor image");

		Corruption.main.getFrame().setCursor(imgCursor);
		normal = true;
		hidden = false;
	}

	/**
	 * Used to set the cursor back to the normal cursor
	 */
	public static void resetCursor() {
		Corruption.main.getFrame().setCursor(Cursor.getDefaultCursor());
		normal = true;
		hidden = false;
	}

	/**
	 * Used to set the cursor to an blank image
	 */
	public static void hideCursor() {

		BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor imgCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "blank cursor");
		Corruption.main.getFrame().setCursor(imgCursor);
		normal = true;
		hidden = true;
	}

	/**
	 * Used to set the cursor to a given cursor type
	 * 
	 * @param cursortype the Cursor.THING to set the cursor to
	 */
	public static void setCursor(int cursortype) {
		normal = false;
		hidden = false;
		Corruption.main.getFrame().setCursor(new Cursor(cursortype));
	}
}
