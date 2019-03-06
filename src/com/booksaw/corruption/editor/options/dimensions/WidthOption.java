package com.booksaw.corruption.editor.options.dimensions;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.Dimensions;

public class WidthOption extends TextOption {

	Dimensions b;

	public WidthOption(Dimensions d) {
		this.b = d;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getWidth() + "");
			return;
		}

		b.setWidth(value);
	}

	@Override
	public String getTip() {
		return "Width";
	}

	@Override
	public String getDefault() {
		return b.getWidth() + "";
	}
}
