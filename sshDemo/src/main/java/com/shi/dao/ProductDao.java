package com.shi.dao;
import java.util.List;

import com.shi.entity.Product;

/**
 * 商品操作-持久层接口
 *
 */
public interface ProductDao {
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    List<Product> listProduct(Product product);
}