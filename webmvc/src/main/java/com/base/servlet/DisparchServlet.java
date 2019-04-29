package com.base.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.base.anno.Autowired;
import com.base.anno.Controller;
import com.base.anno.RequestMapping;
import com.base.anno.RequestParam;
import com.base.anno.ResponseBody;
import com.base.anno.Service;
import com.base.entity.ControllerBean;
import com.base.entity.ModelAndView;
import com.base.utils.JavaClazzUtils;

public class DisparchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Properties pro = new Properties();

	private static Set<Class<?>> allClass = new LinkedHashSet<Class<?>>();

	private static Map<String, Object> beans = new HashMap<String, Object>();

	private static Map<String, ControllerBean> webBeans = new HashMap<String, ControllerBean>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI();
		String contextPath = req.getContextPath();
		String realPath = path.substring(contextPath.length());
		System.out.println("realPath=" + realPath);
		String reqMethod=req.getMethod();
		System.out.println("reqMethod=" + reqMethod);
		if (!handleMetod(webBeans,reqMethod,realPath)) {
			resp.sendError(404, "您要查找的资源不存在");
			return;
		}
		handleRequest(req, resp, realPath);
	}

	private void handleRequest(HttpServletRequest req, HttpServletResponse resp, String realPath) {
		ControllerBean controllerBean = webBeans.get(realPath);
		Map<String, Class<?>> paMap = controllerBean.getRequestParams();
		LinkedList<Object> params = new LinkedList<Object>();
		if (paMap != null) {
			Set<String> nameSet = paMap.keySet();
			for (String name : nameSet) {
				params.add(req.getParameter(name));
			}
		}
		try {
			Object o = null;
			Method m = controllerBean.getMethod();
			System.out.println(m.getName());
			if (params.size() > 0) {
				o = m.invoke(beans.get(controllerBean.getBeanName()), params.toArray());
			} else {
				o = m.invoke(beans.get(controllerBean.getBeanName()));
			}
			System.out.println(JSONObject.toJSONString(o));
			if (controllerBean.getHasResponseBody()) {
				PrintWriter out = resp.getWriter();
				if (o!=null&&isBaseType(o)) {
					out.println(o.toString());
				} else {
					out.println(JSONObject.toJSONString(o));
				}
				out.flush();
			} else {
				if (o instanceof ModelAndView) {
					ModelAndView modelAndView = (ModelAndView) o;
					Map<String, Object> model = modelAndView.getModel();
					if(model!=null) {
						for (Entry<String, Object> object : model.entrySet()) {
							req.setAttribute(object.getKey(), object.getValue());
						}
					}
					System.out.println("-----------------------"+modelAndView.getView());
					req.getRequestDispatcher("/"+modelAndView.getView()).forward(req, resp);
				} else {
					req.getRequestDispatcher(o.toString()).forward(req, resp);
				}
			}
		} catch (InvocationTargetException e) {
			System.out.println("此处接收被调用方法内部未被捕获的异常");
			Throwable t = e.getTargetException();// 获取目标异常
			t.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean handleMetod(Map<String, ControllerBean> webBeans, String reqMethod,String realPath) {
		if(!webBeans.containsKey(realPath)) {
			return false;
		}
		ControllerBean controllerBean=webBeans.get(realPath);
		for (int i = 0; i < controllerBean.getReqMethods().length; i++) {
			if(reqMethod.equals(controllerBean.getReqMethods()[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断object是否为基本类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isBaseType(Object object) {
		Class<? extends Object> className = object.getClass();
		if (className.equals(java.lang.Integer.class) || className.equals(java.lang.Byte.class)
				|| className.equals(java.lang.Long.class) || className.equals(java.lang.Double.class)
				|| className.equals(java.lang.Float.class) || className.equals(java.lang.Character.class)
				|| className.equals(java.lang.Short.class) || className.equals(java.lang.Boolean.class)
				|| className.equals(java.lang.String.class)) {
			return true;
		}
		return false;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 1。读取配置文件，解析包下面的子类
		doConfig(config.getInitParameter("contextConfigLocation"));
	}

	private void doIOC(String basePackage) {
		try {
			allClass = JavaClazzUtils.getClasses(basePackage);
			for (Class<?> clazz : allClass) {
				if (clazz.isAnnotationPresent(Service.class)) {
					Service service = clazz.getAnnotation(Service.class);
					buildFileds(clazz);
					beans.put(!"".equals(service.value()) ? service.value()
							: fistCharLow(clazz.getInterfaces()[0].getSimpleName()), clazz.newInstance());
				} else {
					continue;
				}
			}

			for (Class<?> clazz : allClass) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					Controller controller = clazz.getAnnotation(Controller.class);
					beans.put(!"".equals(controller.value()) ? controller.value() : fistCharLow(clazz.getSimpleName()),
							clazz.newInstance());
					buildFileds(clazz);
					buildWebURL(clazz);
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("webBeans=" + JSONObject.toJSONString(webBeans));
	}

	private void buildWebURL(Class<?> clazz) {
		RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
		String baseUrl = "";
		if (requestMapping != null) {
			baseUrl = requestMapping.value();
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			RequestMapping requestMappingMethod = method.getDeclaredAnnotation(RequestMapping.class);
			if (requestMappingMethod != null) {
				ControllerBean controllerBean = new ControllerBean();
				ResponseBody responseBody = method.getDeclaredAnnotation(ResponseBody.class);
				controllerBean.setHasResponseBody(responseBody != null);
				controllerBean.setRequestMethods(requestMappingMethod.method());
				controllerBean.setMethod(method);
				controllerBean.setRequestMappingURL(requestMappingMethod.value());
				Map<String, Class<?>> requestParams = null;
				Parameter[] parameters = method.getParameters();
				if (parameters != null && parameters.length > 0) {
					requestParams = new HashMap<String, Class<?>>();
					for (Parameter parameter : parameters) {
						RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
						String paramName = parameter.getName();
						if (requestParam != null) {
							paramName = requestParam.value();
						}
						requestParams.put(paramName, parameter.getType());
					}
				}
				controllerBean.setRequestParams(requestParams);
				controllerBean.setControllerClazz(clazz);
				controllerBean.setBeanName(fistCharLow(clazz.getSimpleName()));
				webBeans.put(baseUrl + controllerBean.getRequestMappingURL(), controllerBean);
			}
		}
	}

	private void buildFileds(Class<?> clazz) {
		try {
			Object instence = beans.get(fistCharLow(clazz.getSimpleName()));
			Field[] fs = clazz.getDeclaredFields();
			for (Field field : fs) {
				Autowired autowired = field.getAnnotation(Autowired.class);
				if (autowired != null) {
					System.out.println(instence + "-->" + field.getName() + "--->" + beans.get(field.getName()));
					field.setAccessible(true);
					field.set(instence, beans.get(field.getName()));
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String fistCharLow(String simpleName) {
		char[] chars = simpleName.toCharArray();
		chars[0] = (char) (chars[0] + 32);
		return new String(chars);
	}

	private void doConfig(String contextConfigLocation) {
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
			pro.load(is);
			String basePackage = (String) pro.get("componentScan.basePackage");
			System.out.println("componentScan.basePackage=" + basePackage);
			// 2.完成bean工厂，依赖注入
			doIOC(basePackage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
