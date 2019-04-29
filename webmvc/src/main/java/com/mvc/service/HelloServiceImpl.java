package com.mvc.service;

import java.util.Random;

import com.base.anno.Service;
import com.mvc.controller.User;

@Service
public class HelloServiceImpl implements HelloService {
	@Override
	public void save(String name) {
		System.out.println(name+" saved!");
	}

	@Override
	public User save(User user) {
		user.setAge(new Random().nextInt(100));
		return user;
	}

}
