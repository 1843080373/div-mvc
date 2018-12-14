package com.mvc.test;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.bean.ModelAndView;
import com.mvc.core.Controller;
import com.mvc.core.RequestMapping;
import com.mvc.core.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(value="/index1.do",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("/index.jsp",new HashMap<String, Object>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("nickName", "张无忌");
			}
		});
	}
	
	@RequestMapping(value="/hello2.do",method=RequestMethod.GET)
	public ModelAndView hello2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("/hello.jsp",new HashMap<String, Object>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("name", "张三丰");
			}
		});
	}
}
