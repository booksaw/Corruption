package com.booksaw.corruption.sprites;

public enum SpriteList {
	PLAYER;

	public static Sprite getSprite(SpriteList sprite) {
		switch (sprite) {
		case PLAYER:
			return new Player();
		}

		return null;
	}

}
