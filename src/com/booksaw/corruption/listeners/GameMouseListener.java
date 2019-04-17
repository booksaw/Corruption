package com.booksaw.corruption.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.booksaw.corruption.render.overlays.SpeechBubble;

public class GameMouseListener implements Listener, MouseListener {

	@Override
	public void activate(JFrame f) {
		f.getContentPane().addMouseListener(this);
	}

	@Override
	public void disable(JFrame f) {
		f.getContentPane().removeMouseListener(this);
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

		List<SpeechBubble> tempList = new ArrayList<>();

		for (SpeechBubble b : SpeechBubble.bubbles) {
			tempList.add(b);
		}

		for (SpeechBubble b : tempList) {
			b.activate();
		}
	}

}
