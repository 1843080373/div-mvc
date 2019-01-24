package com.aop.core;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.aop.anno.After;
import com.aop.anno.AfterThrowing;
import com.aop.anno.Around;
import com.aop.anno.Aspect;
import com.aop.anno.Autowired;
import com.aop.anno.Before;
import com.aop.anno.Component;
import com.aop.anno.Pointcut;
import com.aop.service.Zoo;
import com.aop.utils.JavaClazzUtils;

public class ApplicationContext {

	static Map<String, Object> beans = new HashMap<String, Object>();
	static Map<String, List<AspectBean>> aspectBeans = new HashMap<String, List<AspectBean>>();

	public ApplicationContext() {
		try {
			Properties pro = new Properties();
			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("applicationContext.properties");
			pro.load(inputStream);
			Set<Class<?>> allClass = JavaClazzUtils.getClasses(pro.getProperty("componentScan.basePackage"));
			for (Class<?> clazz : allClass) {
				if (clazz.isAnnotationPresent(Component.class)) {
					Component service = clazz.getAnnotation(Component.class);
					buildFileds(clazz);
					beans.put(!"".equals(service.value()) ? service.value() : fistCharLow(clazz.getSimpleName()),
							clazz.newInstance());
				} else {
					continue;
				}
			}
			for (Class<?> clazz : allClass) {
				if (clazz.isAnnotationPresent(Aspect.class)) {
					aspectBeans.put(fistCharLow(clazz.getSimpleName()), new ArrayList<AspectBean>());
					buildAspect(clazz);
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildAspect(Class<?> clazz) {
		AspectBean aspectBean = new AspectBean();
		Method[] methods = clazz.getDeclaredMethods();
		Map<String, PointcutMethod> pointcutMethods = new HashMap<String, PointcutMethod>();
		Map<String, List<AspectMethod>> aspectMethods = new HashMap<String, List<AspectMethod>>();
		List<AspectMethod> listAspectMethod = new LinkedList<>();
		for (Method method : methods) {
			Pointcut pointcut = method.getDeclaredAnnotation(Pointcut.class);
			if (pointcut != null) {
				pointcutMethods.put(pointcut.value(), new PointcutMethod(method));
			}
			Before Before = method.getDeclaredAnnotation(Before.class);
			if (Before != null) {
				AspectMethod aspectMethod = new AspectMethod();
				aspectMethod.setType("Before");
				aspectMethod.setMethod(method);
				listAspectMethod.add(aspectMethod);
				aspectMethods.put(Before.value(), listAspectMethod);
			}
			After After = method.getDeclaredAnnotation(After.class);
			if (After != null) {
				AspectMethod aspectMethod = new AspectMethod();
				aspectMethod.setType("After");
				aspectMethod.setMethod(method);
				listAspectMethod.add(aspectMethod);
				aspectMethods.put(After.value(), listAspectMethod);
			}
			Around Around = method.getDeclaredAnnotation(Around.class);
			if (Around != null) {
				AspectMethod aspectMethod = new AspectMethod();
				aspectMethod.setType("Around");
				aspectMethod.setMethod(method);
				listAspectMethod.add(aspectMethod);
				aspectMethods.put(Around.value(), listAspectMethod);
			}
			AfterThrowing AfterThrowing = method.getDeclaredAnnotation(AfterThrowing.class);
			if (AfterThrowing != null) {
				AspectMethod aspectMethod = new AspectMethod();
				aspectMethod.setType("AfterThrowing");
				aspectMethod.setMethod(method);
				listAspectMethod.add(aspectMethod);
				aspectMethods.put(AfterThrowing.value(), listAspectMethod);
			}
		}
		aspectBean.setPointcutMethods(pointcutMethods);
		aspectBean.setAspectMethods(aspectMethods);
		aspectBeans.get(fistCharLow(clazz.getSimpleName())).add(aspectBean);
	}

	private void buildFileds(Class<?> clazz) {
		try {
			Object instence = beans.get(fistCharLow(clazz.getSimpleName()));
			Field[] fs = clazz.getDeclaredFields();
			for (Field field : fs) {
				Autowired autowired = field.getAnnotation(Autowired.class);
				if (autowired != null) {
					System.out.println(
							instence + "-->" + (!"".equals(autowired.value()) ? autowired.value() : field.getName())
									+ "--->" + beans.get(field.getName()));
					field.setAccessible(true);
					field.set(instence, beans.get(!"".equals(autowired.value()) ? autowired.value() : field.getName()));
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

	public Object getBean(String beanName) {
		Object o = beans.get(beanName);
		List<String> allAspect = new ArrayList<>();
		for (Entry<String, List<AspectBean>> en : aspectBeans.entrySet()) {
			List<AspectBean> l = en.getValue();
			for (AspectBean st : l) {
				for (Entry<String, PointcutMethod> string : st.getPointcutMethods().entrySet()) {
					allAspect.add(string.getKey());
				}
			}
		}
		Class clazz = o.getClass();
		for (String string : allAspect) {
			if (string.contains(clazz.getPackage().getName())) {
				Object t = o;
		  
				o = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
					Object proxyObject=null;
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object rs = null;
						String proxyBean=null;
						Map<String, PointcutMethod> map = null;
						try {
							map = new HashMap<>();
							for (Entry<String, List<AspectBean>> aps : aspectBeans.entrySet()) {
								proxyBean=aps.getKey();
								for (AspectBean string2 : aps.getValue()) {
									map=string2.getPointcutMethods();
								}
							}
							proxyObject=beans.get(proxyBean);
							if (map != null) {
                                excuteAop(proxyObject, "Before",map);
							}
							rs = method.invoke(t, args);
							if (map != null) {
								 excuteAop(proxyObject, "After",map);
							}
						} catch (Exception e) {
							if (map != null) {
								 excuteAop(proxyObject,"AfterThrowing",map);
							}
							e.printStackTrace();
						}
						return rs;
					}

					private void excuteAop(Object o,String type, Map<String, PointcutMethod> map) {
						for (Entry<String, List<AspectBean>> aps : aspectBeans.entrySet()) {
							for (AspectBean aspectBean : aps.getValue()) {
								for (Entry<String, List<AspectMethod>> asp : aspectBean.getAspectMethods()
										.entrySet()) {
									for (Entry<String, PointcutMethod> poEntry : map.entrySet()) {
										if(asp.getKey().equals(poEntry.getValue().getExecution().getName()+"()")) {
											List<AspectMethod> mes=asp.getValue();
											for (AspectMethod aspectMethod : mes) {
												try {
													if(type.equals(aspectMethod.getType())) {
														aspectMethod.getMethod().invoke(o);
													}
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}
									}
								}
							}
						}	
					}
				});
			}
		}
		return o;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ApplicationContext();
		Zoo p = (Zoo) applicationContext.getBean("person");
		p.eat();
	}
}
