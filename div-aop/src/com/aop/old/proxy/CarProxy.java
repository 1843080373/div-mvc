package com.aop.old.proxy;

public class CarProxy implements RunThings{

	private RunThings car;

	public CarProxy(RunThings car) {
		super();
		this.car = car;
	}

	@Override
	public void run() {
		before();
		car.run();
		after();
	}
	
	public void before() {
		System.out.println("berfore");
	}
	public void after() {
		System.out.println("after");
	}
}
