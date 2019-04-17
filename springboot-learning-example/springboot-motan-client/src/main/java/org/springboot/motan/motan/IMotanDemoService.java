package org.springboot.motan.motan;

import org.spring.springboot.api.IMotanDemo;
import org.spring.springboot.api.IMotanUserService;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

/**
 * 城市 Dubbo 服务消费者
 *
 * Created by bysocket on 28/02/2017.
 */
@Component
public class IMotanDemoService {

	@MotanReferer
	private IMotanDemo motanDemo;
	@MotanReferer
	private IMotanUserService motanUserService;
    public void say(String name) {
        System.out.println(motanDemo.say(name));
        System.out.println(motanUserService.findUserByName(name));
    }
}