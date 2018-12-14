package com.mvc.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mvc.bean.MvcBean;
import com.mvc.utils.PackageUtil;

public class MvcContext {

	private static Map<String, Object> controllers = new HashMap<String, Object>();
	@SuppressWarnings("unchecked")
	public static void initContext(InputStream inputStream) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			// beans
			List<Element> beansElements = root.elements("bean");
			if (!beansElements.isEmpty()) {
				for (Element element : beansElements) {
					controllers.put(element.attributeValue("name"),
							element.attributeValue("class"));
				}
			}
			String scanner= root.element("scanner").attributeValue("package");
			List<String> list=PackageUtil.classNames(scanner);
			if(!list.isEmpty()){
				for (String str : list) {
					Class<?> clazz=Class.forName(str);
					if(clazz.getAnnotation(Controller.class)!=null){
						Method[] methods=clazz.getDeclaredMethods();
						for (Method method : methods) {
							RequestMapping requestMapping= method.getAnnotation(RequestMapping.class);
						    if(requestMapping!=null){
						    	controllers.put(requestMapping.value(), new MvcBean(str, method));
						    }
						}
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map<String, Object> getControllers() {
		return controllers;
	}
	
}
