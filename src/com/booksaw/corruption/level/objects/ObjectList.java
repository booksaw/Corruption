package com.booksaw.corruption.level.objects;

import java.awt.Point;

public enum ObjectList {

	DOOR;

	public static GameObject getObject(ObjectList o) {
		switch (o) {
		case DOOR:
			return new Door(new Point(-1, -1), false);
		}

		return null;
	}

}
