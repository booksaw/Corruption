package com.booksaw.corruption.level.objects;

import java.awt.Point;

import com.booksaw.corruption.level.objects.Spike.Direction;

public enum ObjectList {

	DOOR, SPIKE, SLIME;

	public static GameObject getObject(ObjectList o) {
		switch (o) {
		case DOOR:
			return new Door(new Point(-1, -1), false);
		case SPIKE:
			return new Spike(new Point(-1, -1), Direction.UP);
		case SLIME:
			return new Slime(new Point(-1, -1));
		}

		return null;
	}

	public static ObjectList getObjectEnum(GameObject o) {
		if (o instanceof Door) {
			return DOOR;
		} else if (o instanceof Spike) {
			return SPIKE;
		} else if (o instanceof Slime) {
			return SLIME;
		}

		return null;
	}

}
