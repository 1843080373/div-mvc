package com.mvc.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.bean.ModelAndView;
import com.mvc.core.AbstractController;

public class DemoController extends AbstractController {

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("/demo.jsp");
	}
}
