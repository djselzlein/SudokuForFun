package com.example.sudokufinal2.undo;

import java.util.Stack;

public class SingletonStack {

	private static Stack<StackElement> stack;

	private SingletonStack() {
	}

	public static Stack<StackElement> getStack() {
		if (stack == null) {
			stack = new Stack<StackElement>();
		}
		return stack;
	}

	public static void clearStack() {
		if (stack != null) {
			stack.clear();
		}
	}

}
