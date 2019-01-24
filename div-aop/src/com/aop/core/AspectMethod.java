package com.aop.core;

import java.lang.reflect.Method;

public class AspectMethod {

	private String type;
	private Method method;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
}
