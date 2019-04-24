package com.booksaw.corruption.editor.options.execution.executionOption;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.UUID;

import javax.swing.JComponent;
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
		JPanel p = new JPanel(new FlowLayout());
		JComponent comp = getCommandSelector(CommandList.SPEECH);
		p.add(getRemove());
		p.add(comp);

		area = new JTextArea();

		if (information.length > 1) {
			selection = (Sprite) Selectable.getSelectable(UUID.fromString(information[0]));
			setSelected(selection);
			area.setText(information[1]);
			area.setMaximumSize(new Dimension(100, 100));

		}

		p.add(getSelectableSelector());

		p.add(area);
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
