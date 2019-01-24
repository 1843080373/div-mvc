package com.aop.old.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynCarProxy implements InvocationHandler {

	private RunThings target;
	
	public DynCarProxy(RunThings target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		System.out.println("before");
		Object o=arg1.invoke(target, arg2);
		System.out.println("after");
		return o;
	}

}
