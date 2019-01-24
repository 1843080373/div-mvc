package com.aop.service;

import com.aop.anno.Component;

@Component
public class Person implements Zoo{

	public void eat() {
		System.out.println("eating ...");
		//System.out.println(1/0);
	}
}
