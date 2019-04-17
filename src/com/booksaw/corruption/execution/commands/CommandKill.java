package com.booksaw.corruption.execution.commands;

import java.util.UUID;

import com.booksaw.corruption.execution.Command;
import com.booksaw.corruption.execution.ExecutionSet;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.sprites.Sprite;

public class CommandKill extends Command {

	public CommandKill(ExecutionSet set) {
		super(set);
	}

	@Override
	public void execute(String command, String[] options) {
		Sprite.getSprite(UUID.fromString(options[0]), LevelManager.activeLevel.getSprites()).reset(true);
	}

}
