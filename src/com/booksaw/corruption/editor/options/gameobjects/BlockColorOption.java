package com.booksaw.corruption.editor.options.gameobjects;

import javax.swing.JFrame;

import com.booksaw.corruption.editor.options.ColorPicker;
import com.booksaw.corruption.level.objects.Block;

public class BlockColorOption extends ColorPicker {

	Block b;

	public BlockColorOption(JFrame f, Block b) {
		super(f);
		this.b = b;
		c = b.getC();
	}

	@Override
	public void saveColor() {
		b.setC(c);
	}

	@Override
	public String getTip() {
		return "Pick Color";
	}

}
