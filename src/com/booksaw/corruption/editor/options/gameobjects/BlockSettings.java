package com.booksaw.corruption.editor.options.gameobjects;

import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.editor.options.location.XOption;
import com.booksaw.corruption.editor.options.location.YOption;
import com.booksaw.corruption.level.objects.Block;

public class BlockSettings extends OptionPane {

	Block block;

	public BlockSettings(Block block) {
		super();
		this.block = block;

		intialize();
	}

	@Override
	public String getName() {
		return "Block settings";
	}

	@Override
	public void loadOptions() {
		included.add(new XOption(block));
		included.add(new YOption(block));
		included.add(new WidthOption(block));
		included.add(new HeightOption(block));
		included.add(new BlockColorOption(f, block));
	}

	@Override
	public void deleteThing() {
		block.delete();
	}

}
