package org.spring.springboot.dubbo.impl;

import org.spring.springboot.api.UserDubboService;
import org.spring.springboot.domain.User;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 城市业务 Dubbo 服务层实现层
 *
 * Created by bysocket on 28/02/2017.
 */
// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class UserDubboServiceImpl implements UserDubboService {

	@Override
    public User findByName(String userName) {
        return new User(userName,"是我的故乡");
    }
}