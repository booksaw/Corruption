package com.booksaw.corruption.controls;

public enum ControlList {

	UP("movement.up"), DOWN("movement.down"), LEFT("movement.left"), RIGHT("movement.right"), RESET("level.reset"),
	INTERACT("level.interact"), DELETE("editor.delete"), CONTROL("editor.control"), TESTMODE("editor.testMode"),
	TRIGGERS("editor.triggers"), HIDE("editor.hide");

	String ref;

	private ControlList(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return ref;
	}

}
