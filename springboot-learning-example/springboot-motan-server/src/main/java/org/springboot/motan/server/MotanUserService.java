package org.springboot.motan.server;

import org.spring.springboot.api.IMotanUserService;
import org.spring.springboot.domain.User;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
@MotanService
public class MotanUserService implements IMotanUserService {

	@Override
	public User findUserByName(String name) {
		return new User(name, "很好");
	}

}
