package com.shi.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shi.entity.Product;
import com.shi.service.ProductService;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring.xml" })
@Slf4j
public class ProductTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ProductService productService;

	@Test
	public void testList() {
		Product product = Product.builder().id(1).build();
		List<Product> list = productService.listProduct(product);
		log.info(JSONUtil.toJsonStr(list));
	}
}
