package com.booksaw.corruption.editor.options.background;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.background.Background;

public class XOption extends TextOption {

	Background b;

	public XOption(Background background) {
		this.b = background;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getX() + "");
			return;
		}

		b.setX(value);
	}

	@Override
	public String getTip() {
		return "X Co-ord";
	}

	@Override
	public String getDefault() {
		return b.getX() + "";
	}
}
