package com.booksaw.corruption.render.overlays;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.listeners.ListenerManager;
import com.booksaw.corruption.listeners.PauseListener;
import com.booksaw.corruption.render.GameMenu;
import com.booksaw.corruption.renderControler.MenuController;

public class PauseOverlay extends Overlay {

	public static boolean paused = false;

	// collision boxes for the selections
	public static Rectangle resumeRec, quitRec;

	public static PauseOverlay pause;

	// so the rectangles are only genned on a window resize
	private boolean genRects = false;
	private PauseListener listener;

	public static OPTIONS active = OPTIONS.RESUME;

	Font f = new Font("Dialogue", Font.PLAIN, 20);

	public PauseOverlay() {
		pause = this;
	}

	@Override
	public void render(Graphics g) {

		if (!genRects) {
			setupRects();
		}

		g.setColor(Color.BLACK);
		JFrame fr = Corruption.main.getFrame();
		g.fillRect(0, 0, fr.getWidth(), fr.getHeight());

		// showing "corruption"
		g.drawImage(GameMenu.logo, (getWidth() / 2) - (int) (getWidth() * 0.3),
				(getHeight() / 2) - (int) (getHeight() * 0.4), (int) (getWidth() * 0.6), 100, null);

		// text colour
		g.setColor(Color.GREEN);
		g.setFont(f);

		// for the new button
		String text = Language.getMessage("pause.resume");
		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getWidth() / 2) - (width / 2),
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.35));
		// if its selected drawing the triangles
		if (active == OPTIONS.RESUME) {
			int x = (getWidth() / 2) - (width / 2) - 30;
			int y = (getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.35) - f.getSize() + 5;
			g.drawImage(GameMenu.triangle, x, y, 15, f.getSize() - 5, null);

			x = (getWidth() / 2) + (width / 2) + 15;
			g.drawImage(GameMenu.triangle, x + 15, y, x, y + f.getSize() - 5, 0, 0, GameMenu.triangle.getWidth(),
					GameMenu.triangle.getHeight(), null);

		}

		// for the load button
		// for more see new
		text = Language.getMessage("pause.quit");
		width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getWidth() / 2) - (width / 2),
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.5));

		if (active == OPTIONS.QUIT) {
			int x = (getWidth() / 2) - (width / 2) - 30;
			int y = (getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.5) - f.getSize() + 5;
			g.drawImage(GameMenu.triangle, x, y, 15, f.getSize() - 5, null);

			x = (getWidth() / 2) + (width / 2) + 15;
			g.drawImage(GameMenu.triangle, x + 15, y, x, y + f.getSize() - 5, 0, 0, GameMenu.triangle.getWidth(),
					GameMenu.triangle.getHeight(), null);
		}

	}

	public void setupRects() {

		resumeRec = new Rectangle(0,
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.35) - f.getSize() + 5,
				getWidth(), f.getSize() * 3);

		quitRec = new Rectangle(0,
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.5) - f.getSize() + 5, getWidth(),
				f.getSize() * 3);

		// so rects arent re genned when not needed
		genRects = true;

	}

	public int getHeight() {
		return Corruption.main.getFrame().getContentPane().getHeight();
	}

	public int getWidth() {
		return Corruption.main.getFrame().getContentPane().getWidth();
	}

	public enum OPTIONS {
		RESUME, QUIT;
	}

	/**
	 * If the user presses w it increases the selected option
	 */
	public void increase() {
		switch (active) {
		case RESUME:
			active = OPTIONS.QUIT;
			break;
		case QUIT:
			active = OPTIONS.RESUME;
			break;
		}
	}

	/**
	 * if the user presses s it increases the selected option
	 */
	public void decrease() {
		increase();
	}

	/**
	 * If the user clicks or presses enter, it starts the menu option
	 */
	public void activate() {
		switch (active) {
		case RESUME:
			Overlay.removeOverlay(this);
			break;
		case QUIT:
			Overlay.removeOverlay(this);
			Corruption.main.setActive(new MenuController());
			break;
		}
	}

	public void resize() {
		genRects = false;
	}

	@Override
	public void hide() {
		paused = false;
		ListenerManager.removeListener(listener);
	}

	@Override
	public void show() {
		paused = true;

		listener = new PauseListener();
		ListenerManager.addListener(listener);

	}

	/**
	 * Sets the option for mouse usage only
	 * 
	 * @param o the option to set it to
	 */
	public void setOption(OPTIONS o) {
		active = o;

	}

}