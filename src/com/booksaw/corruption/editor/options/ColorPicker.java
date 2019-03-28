package com.booksaw.corruption.editor.options;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.CursorManager;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.listeners.EditorMouseListener;
import com.booksaw.corruption.render.overlays.ActiveSelection;

public abstract class ColorPicker extends MessageOption implements ActionListener {
	public static ColorPicker p;
	private static List<Color> recentColors = new ArrayList<>();
	private static final int maxSize = 16;

	public void addColor(Color c) {
		if (c == null || c == Color.WHITE) {
			return;
		}

		if (recentColors.contains(c)) {
			recentColors.remove(c);
		}

		recentColors.add(c);

		while (maxSize < recentColors.size()) {
			recentColors.remove(0);
		}
	}

	private JButton colorChooser;
	private JButton eyeDropper;
	protected Color c;
	private JFrame f;

	public ColorPicker(JFrame f) {
		this.f = f;
		c = Color.BLACK;
	}

	@Override
	public void saveData() {

		saveColor();
	}

	public abstract void saveColor();

	@Override
	public JComponent getInput() {
		JPanel p = new JPanel(new GridLayout(1, 0));
		colorChooser = new JButton("...");
		colorChooser.addActionListener(this);
		colorChooser.setToolTipText(Language.getMessage("editor.color"));
		colorChooser.setActionCommand("chooser");
		p.add(colorChooser);

		BufferedImage eyeDropperImg = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "eyedropper.png"));

		Icon eyeDropperIcon = new ImageIcon(eyeDropperImg);

		eyeDropper = new JButton(eyeDropperIcon);
		eyeDropper.addActionListener(this);
		eyeDropper.setActionCommand("eyedropper");
		p.add(eyeDropper);
		ColorPicker.p = this;

		return p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("chooser")) {
//		Color temp = JColorChooser.showDialog(f, Language.getMessage("editor.color.title"), c);
			JColorChooser j = new JColorChooser();

			ArrayList<AbstractColorChooserPanel> panels = new ArrayList<>(Arrays.asList(j.getChooserPanels()));
			panels.remove(0);
			panels.add(0, new MyChooserPanel());
			j.setChooserPanels(panels.toArray(new AbstractColorChooserPanel[0]));
			MyPreviewPanel pre = new MyPreviewPanel(j);
			ColorSelectionModel model = j.getSelectionModel();

			model.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					ColorSelectionModel model = (ColorSelectionModel) evt.getSource();
					pre.curColor = model.getSelectedColor();
				}
			});

			j.setPreviewPanel(pre);
			JDialog d = JColorChooser.createDialog(null, "", true, j, null, null);

			d.setVisible(true);

			Color temp = j.getColor();

			if (temp != null) {
				c = temp;
				addColor(c);
			}

		} else {
			CursorManager.setCursor(Cursor.CROSSHAIR_CURSOR);
			EditorMouseListener.selection = ActiveSelection.EYEDROPPER;
			f.setVisible(false);
		}
	}

	public void click(Color c) {
		CursorManager.resetCursor();
		EditorMouseListener.selection = ActiveSelection.MAIN;
		this.c = c;
		f.setVisible(true);
		addColor(c);
	}

	@SuppressWarnings("serial")
	class MyPreviewPanel extends JComponent {
		Color curColor;

		public MyPreviewPanel(JColorChooser chooser) {
			curColor = chooser.getColor();

			setPreferredSize(new Dimension(50, 50));
		}

		public void paint(Graphics g) {
			g.setColor(curColor);
			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	@SuppressWarnings("serial")
	public class MyChooserPanel extends AbstractColorChooserPanel {

		public void buildChooser() {
			setLayout(new GridLayout(4, 4));

			for (int i = 0; i < 16; i++) {

				Color c = null;
				try {
					c = recentColors.get(i);
				} catch (Exception e) {

				}

				makeAddButton(c);
			}

		}

		public void updateChooser() {
		}

		public String getDisplayName() {
			return "Recent";
		}

		public Icon getSmallDisplayIcon() {
			return null;
		}

		public Icon getLargeDisplayIcon() {
			return null;
		}

		private void makeAddButton(Color color) {
			JButton button;
			if (color != null) {
				button = new CustomColorButton(color);
			} else {
				button = new JButton();
			}
			button.setAction(setColorAction);
			button.setPreferredSize(new Dimension(64, 64));
			button.setBackground(color);
			add(button);
		}

		AbstractAction setColorAction = new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				JButton button = (JButton) evt.getSource();
				getColorSelectionModel().setSelectedColor(button.getBackground());
			}
		};

	}

	@SuppressWarnings("serial")
	public class CustomColorButton extends JButton implements MouseListener {

		private Color normalColor = null;

		public CustomColorButton(Color normalRedColor) {

			this.normalColor = normalRedColor;

			addMouseListener(this);
			setContentAreaFilled(false);
		}

		/**
		 * Overpainting component, so it can have different colours
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(normalColor);
			g2d.fillRect(0, 0, getWidth(), getHeight());

			super.paintComponent(g);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
