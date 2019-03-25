package com.booksaw.corruption;

/**
 * Anything which can be updated implements this
 * @author James
 *
 */
public interface Updatable {
	
	/**
	 * Used to update an object
	 * @param time the time since the last update (as clock cycles are not always consistant)
	 */
	public abstract void update(int time);
}
