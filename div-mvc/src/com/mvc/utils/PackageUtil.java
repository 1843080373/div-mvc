package com.mvc.utils;

import java.util.Arrays;
import java.util.List;

public class PackageUtil {

	public static List<String> classNames(String packagePath) {
		return Arrays.asList("com.mvc.test.DemoController", "com.mvc.test.HelloController","com.mvc.test.IndexController");
	}

}
