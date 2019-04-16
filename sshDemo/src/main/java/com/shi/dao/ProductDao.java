package com.shi.dao;
import com.shi.entity.Product;

/**
 * 商品操作-持久层接口
 *
 */
public interface ProductDao {
    void saveProduct(Product product);
}