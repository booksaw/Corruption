package com.booksaw.corruption.editor.options.background;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.background.Background;

public class WidthOption extends TextOption {

	Background	 b;

	public WidthOption(Background background) {
		this.b = background;
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
