package com.booksaw.corruption.editor.options.npc;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.sprites.Sprite;

public class NPCDirection extends MessageOption {

	JCheckBox right;

	Sprite npc;

	public NPCDirection(Sprite npc) {
		this.npc = npc;

	}

	@Override
	public void saveData() {
		npc.right = right.isSelected();
	}

	@Override
	public String getTip() {
		return "Right";
	}

	@Override
	public JComponent getInput() {
		right = new JCheckBox();
		right.setSelected(npc.right);

		return right;
	}

}
