package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.objects.Block;

public class WidthOption extends TextOption {

	Block b;

	public WidthOption(Block b) {
		this.b = b;
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
