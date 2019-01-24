package com.aop.old.proxy;

import java.lang.reflect.Proxy;

public class Test {

	public static void main(String[] args) {
		Car car = new Car();
		RunThings dynCarProxy = (RunThings) Proxy.newProxyInstance(Test.class.getClassLoader(),
				car.getClass().getInterfaces(), new DynCarProxy(car));
		dynCarProxy.run();
	}

	@SuppressWarnings("unused")
	private static void staticProxy() {
		CarProxy carProxy = new CarProxy(new Car());
		carProxy.run();
	}

}
