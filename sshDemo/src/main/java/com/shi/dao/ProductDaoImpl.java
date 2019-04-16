package com.shi.dao;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shi.entity.Product;



/**
 * 商品信息-服务层实现
 *
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    private HibernateTemplate template;
    @Autowired
    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.template = new HibernateTemplate(sessionFactory);
    }
    @Override
    public void saveProduct(Product product) {
        template.save(product);
    }

	@Override
	public void updateProduct(Product product) {
		template.update(product);
	}

	@Override
	public void deleteProduct(Product product) {
		template.delete(product);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> listProduct(Product product) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		if(product!=null&&0!=product.getId()) {
			criteria.add(Restrictions.eq("id", product.getId()));
		}
		return (List<Product>) template.findByCriteria(criteria);
	}
}