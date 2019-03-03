package com.booksaw.corruption.editor.options.background;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.background.Background;

public class YOption extends TextOption {

	Background b;
	
	public YOption(Background background) {
		this.b = background;
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getY() + "");
			return;
		}

		b.setY(value);
	}

	@Override
	public String getTip() {
		return "Y Co-ord";
	}

	@Override
	public String getDefault() {
		return b.getY() + "";
	}
}
