package com.booksaw.corruption.editor.options.execution;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.render.overlays.ActiveSelection;
import com.booksaw.corruption.selection.Selectable;

public abstract class ExecutionOption implements ActionListener {
	public static ExecutionOption selector;

	protected String[] information;
	protected Selectable selection;
	JFrame f;

	public ExecutionOption(JFrame f, String[] information) {
		this.information = information;
		this.f = f;
	}

	private JButton eyeDropper;

	public abstract JPanel getPanel();

	public abstract String toSave();

	public JComponent getSelectableSelector() {
		BufferedImage eyeDropperImg = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "eyedropper.png"));

		Icon eyeDropperIcon = new ImageIcon(eyeDropperImg);

		eyeDropper = new JButton(eyeDropperIcon);
		eyeDropper.addActionListener(this);
		return eyeDropper;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		CursorManager.setCursor(Cursor.CROSSHAIR_CURSOR);
		EditorMouseListener.selection = ActiveSelection.SPRITEDROPPER;
		f.setVisible(false);
		selector = this;

	}

	public void click(Point p) {
		CursorManager.resetCursor();
		EditorMouseListener.selection = ActiveSelection.MAIN;
		f.setVisible(true);

		selection = Selectable.findSelectable(new Rectangle(p.x, p.y, 1, 1));

	}

}
