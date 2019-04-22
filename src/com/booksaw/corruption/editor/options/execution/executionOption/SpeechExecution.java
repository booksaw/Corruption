package com.booksaw.corruption.editor.options.execution.executionOption;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.booksaw.corruption.editor.options.execution.ExecutionOption;
import com.booksaw.corruption.editor.options.execution.SetOptions;
import com.booksaw.corruption.execution.CommandList;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.Sprite;

public class SpeechExecution extends ExecutionOption {

	public SpeechExecution(JFrame f, String[] information, SetOptions set) {
		super(f, information, set);
	}

	JTextArea area;
	Sprite selection = null;

	@Override
	protected JPanel generatePanel() {
		JPanel p = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.gridwidth = 1;

		p.add(getCommandSelector(CommandList.SPEECH), c);

		area = new JTextArea();

		if (information.length > 1) {
			selection = (Sprite) Selectable.getSelectable(UUID.fromString(information[0]));
			setSelected(selection);
			area.setText(information[1]);
			area.setMaximumSize(new Dimension(100, 100));

		}

		c.gridx = 1;
		p.add(getSelectableSelector(), c);

		c.gridx = 2;
		c.anchor = GridBagConstraints.PAGE_END;
		p.add(area, c);
		return p;
	}

	@Override
	public void clickFind(Point p) {
		Sprite s = Sprite.getSprite(p, LevelManager.activeLevel.getSprites());
		if (s != null) {
			selection = s;
			setSelected(s);
		}
	}

	@Override
	public String toSave() {
		if (selection == null) {
			return "";
		}
		return "speech:" + selection.uuid + ":" + area.getText();
	}

}
