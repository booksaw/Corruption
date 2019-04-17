package com.booksaw.corruption.execution.commands;

import java.util.UUID;

import com.booksaw.corruption.execution.Command;
import com.booksaw.corruption.execution.ExecutionSet;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.render.overlays.SpeechBubble;
import com.booksaw.corruption.sprites.Sprite;

public class CommandSpeach extends Command {

	public CommandSpeach(ExecutionSet set) {
		super(set);
	}

	@Override
	public void execute(String command, String[] args) {
		Sprite s = Sprite.getSprite(UUID.fromString(args[0]), LevelManager.activeLevel.getSprites());
		SpeechBubble.showBubble(s, args[1], this);

	}

}
