package com.booksaw.corruption.editor.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.level.LevelManager;

public abstract class OptionPane implements ActionListener, KeyListener {

	protected List<Option> included = new ArrayList<>();
	protected JFrame f;
	protected boolean deletable = true;

	public OptionPane() {

		f = new JFrame(getName());
		f.setIconImage(Config.logo.getImage());
	}

	public abstract String getName();

	public abstract void loadOptions();

	public void setVisible(boolean visible) {
		f.setVisible(visible);

	}

	public void intialize() {
		f.setResizable(false);

		loadOptions();
		JPanel wrapper = new JPanel(new GridBagLayout());

		JPanel container = new JPanel(new GridLayout(0, 1));
		for (Option op : included) {
			JPanel panel = op.getPanel();
			panel.setBorder(new LineBorder(Color.BLACK, 1));
			container.add(panel);
		}

		JScrollPane pane = new JScrollPane(container);
		pane.setMaximumSize(new Dimension(500, 500));

		GridBagConstraints con = new GridBagConstraints();

		con.gridx = 0;
		con.gridy = 0;
		con.gridheight = 3;
		con.fill = GridBagConstraints.HORIZONTAL;

		wrapper.add(pane, con);

		JComponent c = getEnding();

		con.gridx = 0;
		con.gridy = 3;
		con.gridheight = 2;
		con.anchor = GridBagConstraints.PAGE_END;
		wrapper.add(c, con);

		f.addKeyListener(this);
		f.setContentPane(wrapper);
		f.pack();
		System.out.println(c.getHeight());
		f.setLocationRelativeTo(Corruption.main.getFrame());

	}

	public void run() {
		for (Option op : included) {
			op.saveData();
		}
		LevelManager.activeLevel.getSaveManager().changes();
		setVisible(false);
	}

	public void cancel() {
		setVisible(false);
	}

	public void delete() {
		deleteThing();
		setVisible(false);
	}

	public abstract void deleteThing();

	public JPanel getEnding() {

		JPanel toReturn = new JPanel(new GridLayout(0, (deletable) ? 3 : 2));

		// creating cancel button
		JButton cancel = new JButton(Language.getMessage("editor.cancel"));
		cancel.addActionListener(this);
		cancel.setActionCommand("cancel");
		cancel.setToolTipText(Language.getMessage("editor.tool.cancel"));
		toReturn.add(cancel);

		if (deletable) {
			// creating delete button
			JButton delete = new JButton(Language.getMessage("editor.delete"));
			delete.addActionListener(this);
			delete.setActionCommand("delete");
			delete.setToolTipText(Language.getMessage("editor.tool.delete"));
			toReturn.add(delete);
		}

		// creating ok button
		JButton ok = new JButton(Language.getMessage("editor.ok"));
		ok.addActionListener(this);
		ok.setActionCommand("ok");
		ok.setToolTipText(Language.getMessage("editor.tool.ok"));
		toReturn.add(ok);

//		toReturn.setPreferredSize(new Dimension(350, 50));
//
//		toReturn.setMaximumSize(new Dimension(350, 50));
//		toReturn.setMinimumSize(new Dimension(350, 50));
//
//		toReturn.setSize(new Dimension(350, 10));

		return toReturn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			cancel();
			break;
		case "ok":
			run();
			break;
		case "delete":
			delete();
			break;
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 0) {
			run();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("OptionPane");
	}

}
