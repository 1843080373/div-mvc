package com.shi.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.shi.entity.Product;
import com.shi.service.ProductService;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 商品操作-控制层
 *
 */
@Controller
@Scope("prototype")
@Slf4j
@Getter
@Setter
public class ProductAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ProductService productService;
	private String pname;
	private double price;
	private int id;

	/**
	 * list商品操作
	 * 
	 * @return
	 */
	public String listProduct() {
		Product product = Product.builder().build();
		List<Product> list = productService.listProduct(product);
		log.info(JSONUtil.toJsonStr(list));
		ServletActionContext.getRequest().setAttribute("list", list);
		return "list";
	}

	/**
	 * 保存商品操作
	 * 
	 * @return
	 */
	public String saveProduct() {
		Product product = Product.builder().name(pname).price(price).build();
		productService.saveProduct(product);
		this.addActionMessage("保存成功...");
		log.info(JSONUtil.toJsonStr(product) + "保存成功...");
		return "add";
	}

	/**
	 * get商品操作
	 * 
	 * @return
	 */
	public String getProduct() {
		Product product = Product.builder().id(id).build();
		List<Product> list = productService.listProduct(product);
		log.info(JSONUtil.toJsonStr(list));
		Product p = list.get(0);
		ServletActionContext.getRequest().setAttribute("p", p);
		return "update";
	}

	/**
	 * update商品操作
	 * 
	 * @return
	 */
	public String updateProduct() {
		log.info("-------updateProduct------.");
		Product product = Product.builder().id(id).name(pname).price(price).build();
		productService.updateProduct(product);
		this.addActionMessage("update成功...");
		log.info(JSONUtil.toJsonStr(product) + "update成功...");
		return "list";
	}

	/**
	 * delete商品操作
	 * 
	 * @return
	 */
	public String deleteProduct() {
		Product product = Product.builder().id(id).build();
		productService.deleteProduct(product);
		this.addActionMessage("delete成功...");
		log.info(JSONUtil.toJsonStr(product) + "delete成功...");
		return "list";
	}
}