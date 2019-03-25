package com.booksaw.corruption;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * includes useful methods
 * 
 * @author James
 *
 */
public class Utils {

	/**
	 * Loads a image from a file
	 * 
	 * @param file the file to load for
	 * @return BufferedImage of the file
	 */
	public static BufferedImage getImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * At the moment not in use but may be used to scale a point to a point on the image screen
	 * @param p the point
	 * @param currentDimensions - the dimensions of the window at the moment
	 * @return the location of that point on the original window
	 */
	public static Point getScaledPoint(Point p, Dimension currentDimensions) {
//		double x = ((currentDimensions.width / Corruption.origionalDimensions.width) * p.x);
//		p.x = (int) x;
//
//		double y = (currentDimensions.height / Corruption.origionalDimensions.height) * p.y;
//		p.y = (int) y;
		return p;

	}

}
