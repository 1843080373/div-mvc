package com.mvc.service;

import com.base.anno.Service;

@Service
public class HelloServiceImpl implements HelloService {

	public void save(String name) {
		System.out.println(name+" saved!");
	}

}
