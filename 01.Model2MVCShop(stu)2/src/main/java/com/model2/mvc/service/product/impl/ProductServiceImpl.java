package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.dao.UserDAO;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	
	
	public ProductServiceImpl() {
		productDao = new ProductDao();
	}

	
	@Override
	public void addProduct(ProductVO productVO) throws Exception {
		
		productDao.insertProduct(productVO);
		
	}

	@Override
	public ProductVO getProduct(int prodNo) throws Exception {
	
		return productDao.findProduct(prodNo);
	}

	@Override
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		
		return productDao.getProductList(searchVO);
	}

	@Override
	public void updateProduct(ProductVO productVO) throws Exception {
		productDao.updateProduct(productVO);
		
	}
}
