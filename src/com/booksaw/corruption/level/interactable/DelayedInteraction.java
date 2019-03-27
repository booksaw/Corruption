package com.booksaw.corruption.level.interactable;

import java.util.List;

import com.booksaw.corruption.Updatable;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.sprites.Sprite;

public class DelayedInteraction implements Updatable {

	int delay, current;
	Interactable i;
	Sprite s;
	String ref;

	public DelayedInteraction(Interactable i, int delay, Sprite s, String ref) {
		this.i = i;
		this.delay = delay;
		this.s = s;
		this.ref = ref;

		LevelManager.activeLevel.getUpdatable().add(this);
	}

	@Override
	public void update(int time) {

		current += time;

		if (current >= delay) {
			List<String> strL = i.config.getStringList(ref);
			if (strL != null) {
				for (String str : i.config.getStringList(ref)) {
					Interaction.execute(str, s, i);
				}
			}

			LevelManager.activeLevel.getToRemove().add(this);
		}

	}

}
