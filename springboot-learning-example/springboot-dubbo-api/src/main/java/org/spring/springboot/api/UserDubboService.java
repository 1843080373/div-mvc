package org.spring.springboot.api;

import org.spring.springboot.domain.User;

public interface UserDubboService {

	User findByName(String userName);

}
