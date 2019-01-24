package com.aop.core;

import java.util.List;
import java.util.Map;

public class AspectBean {

	private Map<String,PointcutMethod> pointcutMethods;
	
	private Map<String,List<AspectMethod>> aspectMethods;

	
	public Map<String, PointcutMethod> getPointcutMethods() {
		return pointcutMethods;
	}

	public void setPointcutMethods(Map<String, PointcutMethod> pointcutMethods) {
		this.pointcutMethods = pointcutMethods;
	}

	public Map<String, List<AspectMethod>> getAspectMethods() {
		return aspectMethods;
	}

	public void setAspectMethods(Map<String, List<AspectMethod>> aspectMethods) {
		this.aspectMethods = aspectMethods;
	}
	
}
