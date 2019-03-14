package com.booksaw.corruption.level.objects;

import java.awt.Point;

import com.booksaw.corruption.level.objects.Spike.Direction;

public enum ObjectList {

	DOOR, SPIKE;

	public static GameObject getObject(ObjectList o) {
		switch (o) {
		case DOOR:
			return new Door(new Point(-1, -1), false);
		case SPIKE:
			return new Spike(new Point(-1, -1), Direction.UP);
		}

		return null;
	}

}
