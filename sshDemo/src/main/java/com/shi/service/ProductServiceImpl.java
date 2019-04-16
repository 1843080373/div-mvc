package com.shi.service;
import java.util.List;

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

	@Override
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}

	@Override
	public void deleteProduct(Product product) {
		productDao.deleteProduct(product);
	}

	@Override
	public List<Product> listProduct(Product product) {
		return productDao.listProduct(product);
	}

}