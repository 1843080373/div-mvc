package com.shi.action;
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

    /**
     * 保存商品操作
     * 
     * @return
     */
    public String saveProduct() {
        Product product =Product.builder().name(pname).price(price).build();
        productService.saveProduct(product);
        this.addActionMessage("保存成功...");
        log.info(JSONUtil.toJsonStr(product)+"保存成功...");
        return SUCCESS;
    }

    @Override
    public void validate() {
        if(pname == null || "".equals(pname.trim())) {
            this.addFieldError("pname", "商品名称不能为空");
        }
    }

}