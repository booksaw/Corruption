package com.booksaw.corruption.sprites;

/**
 * Used to list all the sprites for the editor menu
 * 
 * @author James
 *
 */
public enum SpriteList {
	PLAYER, BLUENPC, GAURD;

	/**
	 * Used to get an instance of the sprite object
	 * 
	 * @param sprite
	 * @return
	 */
	public static Sprite getSprite(SpriteList sprite) {
		switch (sprite) {
		case PLAYER:
			return new Player();
		case BLUENPC:
			return new BlueNPC();
		case GAURD:
			return new Gaurd();
		}

		return null;
	}

}
