package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.editor.options.TextOption;
import com.booksaw.corruption.level.objects.Block;

public class XOption extends TextOption {

	Block b;

	public XOption(Block b) {
		this.b = b;
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
