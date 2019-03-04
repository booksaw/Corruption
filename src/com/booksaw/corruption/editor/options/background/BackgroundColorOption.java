package com.booksaw.corruption.editor.options.background;

import javax.swing.JFrame;

import com.booksaw.corruption.editor.options.ColorPicker;
import com.booksaw.corruption.level.background.ColoredBackground;

public class BackgroundColorOption extends ColorPicker {

	ColoredBackground b;

	public BackgroundColorOption(JFrame f, ColoredBackground background) {
		super(f);
		this.b = background;
		c = background.getC();
	}

	@Override
	public void saveData() {
		b.setC(c);
	}

	@Override
	public String getTip() {
		return "Pick Color";
	}

}
