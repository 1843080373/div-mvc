package com.base.entity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import com.base.anno.RequestMethod;

public class ControllerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String beanName;
	private String requestMappingURL;
	private RequestMethod[] requestMethods;
	private Map<String,Class<?>> requestParams;
	private Boolean hasResponseBody;
	private Method method;
	private Class<?> controllerClazz;
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getRequestMappingURL() {
		return requestMappingURL;
	}

	public void setRequestMappingURL(String requestMappingURL) {
		this.requestMappingURL = requestMappingURL;
	}

	public RequestMethod[] getRequestMethods() {
		return requestMethods;
	}
	public String[] getReqMethods() {
		String[] ms=new String[requestMethods.length];
		for (int i = 0; i < requestMethods.length; i++) {
			ms[i] = requestMethods[i].getCode();
		}
		return ms;
	}

	public void setRequestMethods(RequestMethod[] requestMethods) {
		this.requestMethods = requestMethods;
	}

	public Map<String, Class<?>> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, Class<?>> requestParams) {
		this.requestParams = requestParams;
	}

	public Boolean getHasResponseBody() {
		return hasResponseBody;
	}

	public void setHasResponseBody(Boolean hasResponseBody) {
		this.hasResponseBody = hasResponseBody;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getControllerClazz() {
		return controllerClazz;
	}

	public void setControllerClazz(Class<?> controllerClazz) {
		this.controllerClazz = controllerClazz;
	}

	public ControllerBean() {
		super();
	}
	
}
