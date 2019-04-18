package org.spring.springboot.api;

import org.spring.springboot.domain.User;

public interface IMotanUserService {

	User findUserByName(String name);

}
