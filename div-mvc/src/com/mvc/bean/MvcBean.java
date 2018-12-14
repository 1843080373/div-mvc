package com.mvc.bean;

import java.lang.reflect.Method;

public class MvcBean {
	private String clazz;
	private Method method;
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public MvcBean(String clazz, Method method) {
		super();
		this.clazz = clazz;
		this.method = method;
	}
	public MvcBean() {
		super();
	}
	
}
