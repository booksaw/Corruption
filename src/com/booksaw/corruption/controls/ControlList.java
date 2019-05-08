package com.booksaw.corruption.controls;

public enum ControlList {

	UP("movement.up"), DOWN("movement.down"), LEFT("movement.left"), RIGHT("movement.right"), RESET("level.reset"),
	INTERACT("level.interact");

	String ref;

	private ControlList(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return ref;
	}

}
