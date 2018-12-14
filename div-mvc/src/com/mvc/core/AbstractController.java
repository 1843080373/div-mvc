package com.mvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.bean.ModelAndView;

public abstract class AbstractController {
    public abstract ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception;
}
