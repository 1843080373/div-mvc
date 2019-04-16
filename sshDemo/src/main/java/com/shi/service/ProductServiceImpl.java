package com.shi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shi.dao.ProductDao;
import com.shi.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Override
    public void saveProduct(Product product) {
        productDao.saveProduct(product);
    }

}