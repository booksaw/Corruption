package com.booksaw.corruption;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.listeners.Listener;
import com.booksaw.corruption.listeners.ListenerManager;
import com.booksaw.corruption.render.RenderInterface;
import com.booksaw.corruption.render.overlays.Overlay;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.MenuController;
import com.booksaw.corruption.renderControler.RenderController;

/**
 * Main class of the project
 * @author James
 *
 */
public class Corruption implements ActionListener, ComponentListener {

	// Statically reference this object
	public static Corruption main;

	// The starting dimensions of the frame (as used elsewhere in the program) any
	// changing be careful of GameCamera class
	public static final Dimension origionalDimensions = new Dimension(1024, 576);

	private long previousTick;
	// time since first tick
	public static int time;
	// to keep track to make sure 2 times are not running
	Timer t;

	// main frame
	private JFrame f;

	// Which renderer is currently active
	public RenderController controller;

	/**
	 * Construcor to start the game, and show the frame
	 */
	public Corruption() {
		Config.load();
		// so things can find statically (only 1 is made per program so this isen't
		// dangerous)
		main = this;
		// sets up the language
		Language.loadLanguage();

		// basic JFrame configuration
		f = new JFrame("Corruption");
		f.setIconImage(Config.logo.getImage());
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (!(controller instanceof EditorController)) {
				} else if (LevelManager.activeLevel != null && LevelManager.activeLevel.hasChanged()) {
					int result = JOptionPane.showConfirmDialog(Corruption.main.getFrame(),
							Language.getMessage("pause.save"), Language.getMessage("title"),
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, Config.logo);
					if (result == 0) {
						LevelManager.activeLevel.save();
					} else if (result == -1 || result == 2) {
						return;
					}
				}

				f.dispose();
				System.exit(0);

			}
		});

		f.addComponentListener(this);

		// this sets the active renderer to the menu
		setActive(new MenuController(), origionalDimensions);

		// more frame stuff
		f.setResizable(true);
		// centres frame
		f.setLocationRelativeTo(null);
		// shows
		f.setVisible(true);
		// rerenders after changes
		f.repaint();

	}

	/**
	 * Used to start the clock, using a specific method to ensure the clock is only started once
	 */
	public void startClock() {
		if (t != null && t.isRunning()) {
			return;
		}

		t = new Timer(1, this);
		previousTick = System.currentTimeMillis();
		t.start();
	}

	/**
	 * Used to set the active render controller
	 * @param renderController the new render controller
	 */
	public void setActive(RenderController renderController) {
		setActive(renderController, new Dimension(f.getContentPane().getWidth(), f.getContentPane().getHeight()));
	}

	/**
	 * USED ONLY AT THE BEGINING, DO NOT GIVE DIMENSION USE
	 * setActive(RenderInterface) Used to change and setup the new renderer
	 * 
	 * @param newRender What to change it to
	 * @param d         - the starting dimension of the frame
	 */
	private void setActive(RenderController controller, Dimension d) {
		Overlay.clearOverlays();
		// clears everything from last frame
		ListenerManager.clearListeners();
		if (this.controller != null)
			this.controller.disable();
		// adding the renderer
		this.controller = controller;
		RenderInterface activeRenderer = controller.getRenderer();
		activeRenderer.setPreferredSize(d);
		f.setContentPane(new JPanel());
		f.setContentPane(activeRenderer);
		f.pack();

		// setting up any listeners
		List<Listener> listeners = controller.generateListeners();

		for (Listener l : listeners)
			ListenerManager.addListener(l);
		// repainting frame
		f.repaint();
	}

	/**
	 * The clock, runs once per 1ms maximum. REMEMBER TO USE INPUT TIME AS A
	 * MULTIPLIER SO GAME IS NOT LINKED TO CLEARLY TO CLOCK CYCLE
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// how long since last tick
		time = (int) (System.currentTimeMillis() - previousTick);

		// setting the old tick
		previousTick = System.currentTimeMillis();
		controller.update(time);

		f.repaint();
	}

	/**
	 * Gives the frame, used encapsulation (private frame) as could be dangerous to
	 * change parts of the frame which shouldent be touched may want to encapsulate
	 * 
	 * @return The frame
	 */
	public JFrame getFrame() {
		return f;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	/**
	 * This is used to detect resizes of the window
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		controller.resize();
		Overlay.resizeAll();
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

}
