package com.booksaw.corruption.editor.options.menu;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.level.LevelManager;

public class NextLevel extends MessageOption {

	JTextField input;

	@Override
	public void saveData() {
		LevelManager.activeLevel.setNextLevel(input.getText());
	}

	@Override
	public String getTip() {
		return "Next level";
	}

	@Override
	public JComponent getInput() {

		input = new JTextField();
		input.setText(LevelManager.activeLevel.getNextLevel());

		return input;
	}

}
