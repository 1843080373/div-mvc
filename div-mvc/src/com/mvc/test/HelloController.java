package com.mvc.test;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.bean.ModelAndView;
import com.mvc.core.AbstractController;

public class HelloController extends AbstractController {

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("/hello.jsp",new HashMap<String, Object>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("name", "张三");
			}
		});
	}
}
