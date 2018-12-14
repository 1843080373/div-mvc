package com.mvc.bean;

import java.util.Map;

public class ModelAndView {

	private String view;
	
	private Map<String,Object> model;

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public ModelAndView(String view, Map<String, Object> model) {
		super();
		this.view = view;
		this.model = model;
	}

	public ModelAndView() {
		super();
	}
	
	public void addObject(String key,Object value){
		this.model.put(key, value);
	}

	public ModelAndView(String view) {
		super();
		this.view = view;
	}
	
	
}
