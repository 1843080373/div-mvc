package org.springboot.motan.server.ws;
import org.spring.springboot.api.IMotanDemo;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;

/**
 * MotanDemoService
 * 
 * @author 	alanwei
 * @since 	2016-09-15
 */
@MotanService
public class MotanDemoService implements IMotanDemo {

	@Override
	public String say(String val) {
		return "hello " + val;
	}

}