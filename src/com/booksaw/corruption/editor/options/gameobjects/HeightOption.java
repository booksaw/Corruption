package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.level.objects.Block;

public class HeightOption extends BlockOption {

	public HeightOption(Block b) {
		super(b);
	}

	@Override
	public void saveData() {
		int value = -1;

		try {
			value = Integer.parseInt(text.getText());
		} catch (Exception e) {
			text.setText(b.getHeight() + "");
			return;
		}

		b.setHeight(value);
	}

	@Override
	public String getTip() {
		return "Height";
	}

	@Override
	public String getDefault() {
		return b.getHeight() + "";
	}
	

}
