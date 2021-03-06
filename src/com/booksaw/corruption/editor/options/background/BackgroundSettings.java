package com.booksaw.corruption.editor.options.background;

import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.editor.options.dimensions.HeightOption;
import com.booksaw.corruption.editor.options.dimensions.WidthOption;
import com.booksaw.corruption.editor.options.location.XOption;
import com.booksaw.corruption.editor.options.location.YOption;
import com.booksaw.corruption.level.background.ColoredBackground;

public class BackgroundSettings extends OptionPane {

	ColoredBackground background;

	public BackgroundSettings(ColoredBackground background) {
		super();
		this.background = background;

		intialize();
	}

	@Override
	public String getName() {
		return "Background settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(background));
		included.add(new YOption(background));
		included.add(new WidthOption(background));
		included.add(new HeightOption(background));
		included.add(new BackgroundColorOption(f, background));
	}

	@Override
	public void deleteThing() {
		background.delete();
	}

}
