package com.booksaw.corruption.settings;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.booksaw.corruption.Corruption;

import java.awt.BorderLayout;

public class Settings extends JFrame {

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

	/**
	 * Create the frame.
	 */
	public Settings() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.NORTH);

		JPanel graphics = getGraphicsPanel();
		tabbedPane.addTab("Graphics", null, graphics, null);

		JPanel controls = getControlsPanel();
		tabbedPane.addTab("Controls", null, controls, null);

	}

	public JPanel getGraphicsPanel() {

		JPanel p = new JPanel();

		return p;
	}

	public JPanel getControlsPanel() {

		JPanel p = new JPanel();

		return p;
	}

}
