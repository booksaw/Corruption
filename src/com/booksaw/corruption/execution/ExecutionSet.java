package com.booksaw.corruption.execution;

import java.util.ArrayList;
import java.util.List;

public class ExecutionSet {

	private ExecutionChain chain;
	private List<Command> options = new ArrayList<>();

	private boolean initial = true;

	public ExecutionSet(ExecutionChain chain, List<String> executions) {
		this.chain = chain;

		for (String str : executions) {
			options.add(Command.runCommand(str, this));
		}

		initial = false;

		complete();

	}

	/**
	 * Run when a single instruction is completed
	 */
	public void complete() {
		if (initial) {
			return;
		}

		if (!isFullyComplete()) {
			return;
		}
		advance();
	}

	/**
	 * Checks if the entire set is completed
	 * 
	 * @return
	 */
	private boolean isFullyComplete() {

		for (Command cmd : options) {
			if (!cmd.isComplete()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Used to move onto the next execution set
	 */
	private void advance() {
		chain.advance();
	}

}
