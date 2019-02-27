package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.level.objects.Block;

public class XOption extends BlockOption {

	public XOption(Block b) {
		super(b);
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
