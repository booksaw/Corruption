package com.booksaw.corruption.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.renderControler.EditorController;
import com.booksaw.corruption.renderControler.GameController;

/**
 * Used to render the menu (what appears when opening)
 * 
 * @author James
 *
 */
public class GameMenu extends RenderInterface {

	// so other classes can access
	public static GameMenu menu;
	// images for the menu
	public static BufferedImage logo, triangle;

	// collision boxes for the selections
	public static Rectangle newRec, loadRec, editorRec;
	// so the rectangles are only genned on a window resize
	private boolean genRects = false;

	// for loading the images
	static {
		logo = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "logo.png"));
		triangle = Utils.getImage(new File(Config.ASSETSPATH + File.separator + "triangle.png"));

	}

	private static final long serialVersionUID = -5002185708953339970L;
	// font of the textSF
	Font f = new Font("Dialogue", Font.PLAIN, 20);

	// setting up static reference
	public GameMenu() {
		menu = this;
	}

	/**
	 * Used to setup the rectangles for mouse movements
	 */
	public void setupRects() {

		newRec = new Rectangle(0,
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.35) - f.getSize() + 5,
				getWidth(), f.getSize() * 3);

		loadRec = new Rectangle(0,
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.5) - f.getSize() + 5, getWidth(),
				f.getSize() * 3);

		editorRec = new Rectangle(0,
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.65) - f.getSize() + 5,
				getWidth(), f.getSize() * 3);

		// so rects arent re genned when not needed
		genRects = true;

	}

	// used to store which option is selected
	public static OPTIONS active = OPTIONS.NEW;

	/**
	 * used to render the grapnics
	 */
	@Override
	public void draw(Graphics g) {
		// checking collision of rectangles
		if (!genRects) {
			setupRects();
		}

		// background
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		// showing "corruption"
		g.drawImage(logo, (getWidth() / 2) - (int) (getWidth() * 0.3), (getHeight() / 2) - (int) (getHeight() * 0.4),
				(int) (getWidth() * 0.6), 100, null);

		// text colour
		g.setColor(Color.GREEN);
		g.setFont(f);

		// for the new button
		String text = Language.getMessage("menu.new");
		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getWidth() / 2) - (width / 2),
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.35));
		// if its selected drawing the triangles
		if (active == OPTIONS.NEW) {
			int x = (getWidth() / 2) - (width / 2) - 30;
			int y = (getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.35) - f.getSize() + 5;
			g.drawImage(triangle, x, y, 15, f.getSize() - 5, null);

			x = (getWidth() / 2) + (width / 2) + 15;
			g.drawImage(triangle, x + 15, y, x, y + f.getSize() - 5, 0, 0, triangle.getWidth(), triangle.getHeight(),
					null);

		}

		// for the load button
		// for more see new
		text = Language.getMessage("menu.load");
		width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getWidth() / 2) - (width / 2),
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.5));

		if (active == OPTIONS.LOAD) {
			int x = (getWidth() / 2) - (width / 2) - 30;
			int y = (getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.5) - f.getSize() + 5;
			g.drawImage(triangle, x, y, 15, f.getSize() - 5, null);

			x = (getWidth() / 2) + (width / 2) + 15;
			g.drawImage(triangle, x + 15, y, x, y + f.getSize() - 5, 0, 0, triangle.getWidth(), triangle.getHeight(),
					null);

		}

		// for the editor button
		// for more see new
		text = Language.getMessage("menu.editor");
		width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getWidth() / 2) - (width / 2),
				(getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.65));

		if (active == OPTIONS.EDITOR) {
			int x = (getWidth() / 2) - (width / 2) - 30;
			int y = (getHeight() / 2) - (int) (getHeight() * 0.4) + (int) (getHeight() * 0.65) - f.getSize() + 5;
			g.drawImage(triangle, x, y, 15, f.getSize() - 5, null);

			x = (getWidth() / 2) + (width / 2) + 15;
			g.drawImage(triangle, x + 15, y, x, y + f.getSize() - 5, 0, 0, triangle.getWidth(), triangle.getHeight(),
					null);

		}

	}

	/**
	 * All the menu buttons
	 * 
	 * @author James
	 *
	 */
	public enum OPTIONS {
		NEW, LOAD, EDITOR;
	}

	/**
	 * If the user presses w it increases the selected option
	 */
	public void increase() {
		switch (active) {
		case NEW:
			active = OPTIONS.EDITOR;
			break;
		case LOAD:
			active = OPTIONS.NEW;
			break;
		case EDITOR:
			active = OPTIONS.LOAD;
			break;
		}
	}

	/**
	 * if the user presses s it increases the selected option
	 */
	public void decrease() {
		switch (active) {
		case NEW:
			active = OPTIONS.LOAD;
			break;
		case LOAD:
			active = OPTIONS.EDITOR;
			break;
		case EDITOR:
			active = OPTIONS.NEW;
			break;
		}
	}

	/**
	 * If the user clicks or presses enter, it starts the menu option
	 */
	public void activate() {
		switch (active) {
		case NEW:
			GameController c = new GameController();
			c.show();
			break;
		case EDITOR:
			EditorController e = new EditorController();
			e.show();
			break;
		case LOAD:
			break;
		}
	}

	/**
	 * Sets the option for mouse usage only
	 * 
	 * @param o the option to set it to
	 */
	public void setOption(OPTIONS o) {
		active = o;

	}

	/**
	 * Used to regenerate the rectangle collisions if the window is resized
	 */
	@Override
	public void resize() {
		genRects = false;
	}

}
