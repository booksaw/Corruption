package com.booksaw.corruption.editor.options;

import com.booksaw.corruption.editor.options.sprites.XOption;
import com.booksaw.corruption.editor.options.sprites.YOption;
import com.booksaw.corruption.sprites.Sprite;

public class SpriteSettings extends OptionPane {

	Sprite s;

	public SpriteSettings(Sprite s) {
		super();
		this.s = s;

		intialize();
	}

	@Override
	public String getName() {
		return "Sprite settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(s));
		included.add(new YOption(s));
	}

	@Override
	public void deleteThing() {

	}

}
