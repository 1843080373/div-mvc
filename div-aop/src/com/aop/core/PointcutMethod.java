package com.aop.core;

import java.lang.reflect.Method;

public class PointcutMethod {

	private Method execution;
	
	public PointcutMethod() {
		super();
	}

	public PointcutMethod(Method execution) {
		super();
		this.execution = execution;
	}

	public Method getExecution() {
		return execution;
	}

	public void setExecution(Method execution) {
		this.execution = execution;
	}

	private boolean isMatched() {
		return false;
	}
}
