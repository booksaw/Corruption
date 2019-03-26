package com.booksaw.corruption.level.interactable;

import com.booksaw.corruption.sprites.Sprite;

public abstract class InteractionOption {

	public abstract boolean run(String[] args, Sprite s, Interactable i);

}
