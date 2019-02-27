package com.booksaw.corruption.listeners;

import java.util.ArrayList;
import java.util.List;

import com.booksaw.corruption.Corruption;

/**
 * Used to manage what listenerers are running
 * 
 * @author James
 *
 */
public class ListenerManager {

	// list of all active listener
	public static List<Listener> activeListeners = new ArrayList<>();;

	/**
	 * Removes all active listener
	 */
	public static void clearListeners() {
		for (Listener l : activeListeners) {
			l.disable(Corruption.main.getFrame());
		}
		activeListeners = new ArrayList<>();
	}

	/**
	 * Used for adding a new listener
	 * 
	 * @param l
	 */
	public static void addListener(Listener l) {
		l.activate(Corruption.main.getFrame());
		activeListeners.add(l);
	}

	/**
	 * Used for removing a new listener
	 * 
	 * @param l
	 */
	public static void removeListener(Listener l) {
		l.disable(Corruption.main.getFrame());
		activeListeners.remove(l);

	}

}
