package com.booksaw.corruption.editor.options.npc;

import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.editor.options.location.XOption;
import com.booksaw.corruption.editor.options.location.YOption;
import com.booksaw.corruption.sprites.Sprite;

public class NPCSettings extends OptionPane {

	Sprite s;

	public NPCSettings(Sprite s) {
		super();

		this.s = s;

		intialize();
	}

	@Override
	public String getName() {
		return "NPC Settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(s));
		included.add(new YOption(s));
		included.add(new NPCDirection(s));
	}

	@Override
	public void deleteThing() {

	}

}
