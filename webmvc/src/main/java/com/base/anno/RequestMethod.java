package com.base.anno;

public enum RequestMethod {

	GET,POST;
	public String getCode() {
		if(GET==this) {
			return "GET";
		}
		return "POST";
	}
}
