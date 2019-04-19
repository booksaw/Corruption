package com.booksaw.corruption.editor.options.execution.executionOption;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.booksaw.corruption.editor.options.execution.ExecutionOption;
import com.booksaw.corruption.editor.options.execution.SetOptions;
import com.booksaw.corruption.execution.CommandList;
import com.booksaw.corruption.level.objects.GameObject;
import com.booksaw.corruption.selection.Selectable;

public class TriggerExecution extends ExecutionOption {

	public TriggerExecution(JFrame f, String[] information, SetOptions set) {
		super(f, information, set);
	}

	GameObject selection;
	JTextArea area;

	@Override
	protected JPanel generatePanel() {
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(getCommandSelector(CommandList.TRIGGER));

		area = new JTextArea();

		if (information.length > 1) {
			selection = (GameObject) Selectable.getSelectable(UUID.fromString(information[0]));
			setSelected(selection);

			String areaText = "";

			for (int i = 1; i < information.length; i++) {
				areaText = areaText + ((areaText.equals("")) ? information[i] : ":" + information[i]);
			}

			area.setText(areaText);

		}

		p.add(getSelectableSelector());

		p.add(area);

		return p;
	}

	@Override
	public void clickFind(Point p) {
		GameObject temp = GameObject.getObject(p);

		if (temp != null) {
			setSelected(temp);
			selection = temp;
		}

	}

	@Override
	public String toSave() {
		if (selection == null) {
			return "";
		}

		return "trigger:" + selection.uuid + ":" + area.getText();
	}

}
