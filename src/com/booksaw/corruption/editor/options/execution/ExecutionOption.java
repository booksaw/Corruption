package com.booksaw.corruption.editor.options.execution;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.execution.CommandList;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.render.GameCamera;
import com.booksaw.corruption.render.overlays.ActiveSelection;
import com.booksaw.corruption.selection.Selectable;
import com.booksaw.corruption.sprites.Sprite;

public abstract class ExecutionOption implements ActionListener {
	public static ExecutionOption selector;

	protected String[] information;
	JFrame f;

	Selectable selected = null;
	SetOptions set;

	public ExecutionOption(JFrame f, String[] information, SetOptions set) {
		this.information = information;
		this.f = f;
		this.set = set;
	}

	JComboBox<CommandList> box;

	public JComponent getCommandSelector(CommandList value) {
		box = new JComboBox<CommandList>(CommandList.values());
		box.setSelectedItem(value);
		box.addActionListener(this);
		return box;

	}

	private JButton eyeDropper, remove;

	protected abstract JPanel generatePanel();

	JPanel p;

	public JPanel getPanel() {

		if (p == null) {
			p = generatePanel();
		}

		return p;
	}

	public abstract String toSave();

	public JComponent getSelectableSelector() {

		eyeDropper = new JButton();
		setupIcon();
		eyeDropper.addActionListener(this);
		eyeDropper.setActionCommand("eyedropper");
		if (box != null) {
			eyeDropper.setPreferredSize(box.getPreferredSize());
		}
		return eyeDropper;
	}

	public JComponent getRemove() {
		remove = new JButton();
		remove.addActionListener(this);
		BufferedImage img = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "remove.png"));
		remove.setIcon(new ImageIcon(img));
		remove.setActionCommand("remove");

		if (box != null) {
			remove.setPreferredSize(box.getPreferredSize());
		}

		return remove;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("eyedropper")) {
			CursorManager.setCursor(Cursor.CROSSHAIR_CURSOR);
			EditorMouseListener.selection = ActiveSelection.SPRITEDROPPER;
			f.setVisible(false);
			selector = this;
			return;
		} else if (e.getActionCommand().equals("remove")) {
			set.remove(this);
		}

		set.replace(this, CommandList.getExecutionOption((CommandList) box.getSelectedItem(), new String[0], f, set));
		f.repaint();

	}

	public void click(Point p) {
		p.y = GameCamera.cameraHeight - p.y;
		p.x = p.x + GameCamera.activeCamera.x;
		CursorManager.resetCursor();
		EditorMouseListener.selection = ActiveSelection.MAIN;
		f.setVisible(true);
		clickFind(p);
	}

	public void clickFind(Point p) {
	}

	public void setSelected(Selectable s) {
		selected = s;
		if (selected != null && eyeDropper != null) {
			setupIcon();
			f.repaint();
		}

	}

	public void setupIcon() {
		Icon eyeDropperIcon = null;
		if (selected != null && (selected instanceof Sprite)) {
			BufferedImage image = ((Sprite) selected).getStanding();
			eyeDropperIcon = new ImageIcon(image);
		}
		eyeDropper.setIcon(eyeDropperIcon);

	}
}
