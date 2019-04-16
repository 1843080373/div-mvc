package com.shi.service;
import java.util.List;

import com.shi.entity.Product;

/**
 * 商品操作-服务层接口
 *
 */
public interface ProductService {
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    List<Product> listProduct(Product product);
}