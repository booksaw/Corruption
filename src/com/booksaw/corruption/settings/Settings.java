package com.booksaw.corruption.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.controls.Control;
import com.booksaw.corruption.controls.ControlsManager;
import com.booksaw.corruption.language.Language;

public class Settings extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5274623199845825074L;

	/**
	 * Launch the application.
	 */
	public static void displaySettings() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
					frame.setLocationRelativeTo(Corruption.main.getFrame());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Dimension d = new Dimension(450, 300);

	/**
	 * Create the frame.
	 */
	public Settings() {
		setSize(d.width, d.height);
		setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.NORTH);

		JPanel graphics = getGraphicsPanel();
		tabbedPane.addTab("Graphics", null, graphics, Language.getMessage("settings.graphics.tip"));

		JComponent controls = getControlsPanel(d.height - tabbedPane.getUI().getTabBounds(tabbedPane, 0).height - 60);
		tabbedPane.addTab("Controls", null, controls, Language.getMessage("settings.controls.tip"));

	}

	public JPanel getGraphicsPanel() {

		JPanel p = new JPanel();

		return p;
	}

	public JComponent getControlsPanel(int height) {

		JPanel p = new JPanel(new GridLayout(0, 1));
		p.add(getControlHeader());

		HashMap<String, Control> controls = ControlsManager.getControls();
		List<String> used = new ArrayList<>();

		for (Entry<String, Control> control : controls.entrySet()) {
			String[] split = control.getKey().split("\\.");
			if (!used.contains(split[0])) {

				p.add(getLabel(split[0]));

				for (Entry<String, Control> temp : controls.entrySet()) {
					String[] s = temp.getKey().split("\\.");
					if (s[0].equals(split[0]) && !(s[s.length - 1].equals("control"))
							&& !(s[s.length - 1].equals("delete"))) {
						p.add(temp.getValue().getPanel());
					}
				}

				used.add(split[0]);
			}

		}

		JScrollPane sp = new JScrollPane(p);
		sp.setPreferredSize(new Dimension(d.width, height));
		sp.setMaximumSize(new Dimension(d.width, height));
		sp.setMinimumSize(new Dimension(d.width, height));

		JPanel wrapper = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		wrapper.add(sp, c);

		c.gridy = 2;
		wrapper.add(getControlFooter(), c);
		return wrapper;
	}

	private JPanel getControlHeader() {
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JLabel(Language.getMessage("settings.action")));

		p.add(new JLabel(Language.getMessage("settings.key")));

		return p;

	}

	private JPanel getControlFooter() {
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JPanel());

		p.add(new JPanel());

		JButton b = new JButton(Language.getMessage("settings.save"));
		b.addActionListener(this);

		p.add(b);

		return p;

	}

	private JPanel getLabel(String label) {
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JPanel());
		p.add(new JLabel(label));
		p.add(new JPanel());

		return p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Entry<String, Control> temp : ControlsManager.getControls().entrySet()) {
			temp.getValue().save();
		}
		ControlsManager.save();
	}

}
