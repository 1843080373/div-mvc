package com.mvc.controller;

import com.base.anno.Autowired;
import com.base.anno.Controller;
import com.base.anno.RequestMapping;
import com.base.anno.RequestMethod;
import com.base.anno.RequestParam;
import com.base.anno.ResponseBody;
import com.base.entity.ModelAndView;
import com.mvc.service.HelloService;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private HelloService helloService;

	@RequestMapping(value = "/hello.spring", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView hello() {
		return new ModelAndView("hello.jsp");
	}

	
	@ResponseBody
	@RequestMapping(value = "/user.spring", method = RequestMethod.GET)
	public User user(@RequestParam("name") String name) {
		return helloService.save(new User(name));
	}
	
	@ResponseBody
	@RequestMapping(value = "/index.spring", method = RequestMethod.GET)
	public String index(@RequestParam("name") String name) {
		helloService.save(name);
		return name;
	}
	
	@ResponseBody
	@RequestMapping(value = "/good.spring", method = RequestMethod.GET)
	public String good() {
		return "good";
	}

}
