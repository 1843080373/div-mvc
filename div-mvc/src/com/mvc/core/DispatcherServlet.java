package com.mvc.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mvc.bean.ModelAndView;
import com.mvc.bean.MvcBean;

public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config
				.getInitParameter("contextConfigLocation");
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(contextConfigLocation);
		MvcContext.initContext(inputStream);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, Object> controllers = MvcContext.getControllers();
		String requestURI = req.getRequestURI().substring(
				req.getContextPath().length());
		Object controllerClassPath = controllers.get(requestURI);
		if (controllerClassPath == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "找不到资源");
		} else {
			ModelAndView modelAndView = null;
			if (controllerClassPath instanceof MvcBean) {
				try {
					MvcBean mvcBean=(MvcBean) controllerClassPath;
					modelAndView = (ModelAndView) mvcBean.getMethod().invoke(
									Class.forName(mvcBean.getClazz()).newInstance(), req, resp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					AbstractController handlerController = (AbstractController) Class
							.forName((String) controllerClassPath)
							.newInstance();
					modelAndView = handlerController.handleRequestInternal(req,
							resp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String view = modelAndView.getView();
			Map<String, Object> model = modelAndView.getModel();
			if (model != null) {
				for (String key : model.keySet()) {
					req.setAttribute(key, model.get(key));
				}
			}
			req.getRequestDispatcher(view).forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
}
