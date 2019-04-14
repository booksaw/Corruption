package com.booksaw.corruption.editor.options.player;

import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.editor.options.location.XOption;
import com.booksaw.corruption.editor.options.location.YOption;
import com.booksaw.corruption.sprites.Sprite;

public class PlayerSettings extends OptionPane {

	Sprite s;

	public PlayerSettings(Sprite s) {
		super();
		this.s = s;

		intialize();
	}

	@Override
	public String getName() {
		return "Player settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(s));
		included.add(new YOption(s));
		included.add(new ActiveSprite(s));
		included.add(new ControlSprite(s));
	}

	@Override
	public void deleteThing() {
		s.delete();
	}

}
