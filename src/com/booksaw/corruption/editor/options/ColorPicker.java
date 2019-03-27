package com.booksaw.corruption.editor.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.booksaw.corruption.language.Language;

public abstract class ColorPicker extends MessageOption implements ActionListener {

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
	protected Color c;
//	private JFrame f;

	public ColorPicker(JFrame f) {
//		this.f = f;
		c = Color.BLACK;
	}

	@Override
	public void saveData() {

		saveColor();
	}

	public abstract void saveColor();

	@Override
	public JComponent getInput() {

		colorChooser = new JButton("...");
		colorChooser.addActionListener(this);
		colorChooser.setToolTipText(Language.getMessage("editor.color"));

		return colorChooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
	class MyChooserPanel extends AbstractColorChooserPanel {
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
////			button.setForeground(color);
//			button.setOpaque(true);
//			button.setBorderPainted(false);
//			button.setContentAreaFilled(true);
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
		private boolean hovered = false;
		private boolean clicked = false;

		private Color normalColor = null;
		private Color lightColor = null;
		private Color darkColor = null;

		public CustomColorButton(Color normalRedColor) {

			this.normalColor = normalRedColor;
			this.lightColor = normalRedColor.brighter();
			this.darkColor = normalRedColor.darker();

			addMouseListener(this);
			setContentAreaFilled(false);
		}

		/**
		 * Overpainting component, so it can have different colors
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			GradientPaint gp = null;

			if (clicked)
				gp = new GradientPaint(0, 0, darkColor, 0, getHeight(), darkColor.darker());
			else if (hovered)
				gp = new GradientPaint(0, 0, lightColor, 0, getHeight(), lightColor.darker());
			else
				gp = new GradientPaint(0, 0, normalColor, 0, getHeight(), normalColor.darker());

			g2d.setPaint(gp);

			// Draws the rounded opaque panel with borders
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // For High
																										// quality
			g2d.fillRect(0, 0, getWidth(), getHeight());

			g2d.setColor(darkColor.darker().darker());
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

			super.paintComponent(g);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			hovered = true;
			clicked = false;

			repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			hovered = false;
			clicked = false;

			repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			hovered = true;
			clicked = true;

			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			hovered = true;
			clicked = false;

			repaint();
		}
	}
}
