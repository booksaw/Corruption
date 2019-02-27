package com.booksaw.corruption.level.meta;

/**
 * Used to create a piece of meta so all meta can be accessed in the same way 
 * @author James
 *
 */
public abstract class Meta {

	/**
	 * Stores the information that was in the file
	 */
	String info;

	/**
	 * Just for storing the info
	 * @param info the file information associated with the meta
	 */
	public Meta(String info) {
		this.info = info;
	}

	/**
	 * Runs when the level is being started
	 */
	public abstract void execute();

}
