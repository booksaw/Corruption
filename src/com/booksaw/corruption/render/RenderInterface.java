package com.booksaw.corruption.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.booksaw.corruption.Corruption;
import com.booksaw.corruption.render.overlays.Overlay;

/**
 * All renderers include so some basic things can be established also makes it
 * easier to code generically
 * 
 * @author James
 *
 */
public abstract class RenderInterface extends JPanel {

	private static final long serialVersionUID = 1260834563687428294L;
	boolean image = false;

	/**
	 * Used whenever the window is resized so any static references can be updated
	 * 
	 * @return
	 */
	public abstract void resize();

	@Override
	public void paint(Graphics gr) {
		Graphics g = gr;
		BufferedImage frame = null;
		if (image) {
			frame = new BufferedImage(Corruption.origionalDimensions.width, Corruption.origionalDimensions.height,
					BufferedImage.TYPE_INT_RGB);
			g = frame.getGraphics();

		}

		draw(g);

		for (Overlay temp : Overlay.getActiveOverlays()) {
			temp.render(g);
		}

		if (image) {
			// drawing the image generated to the graphics
			gr.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
		}

	}

	public abstract void draw(Graphics g);

}
