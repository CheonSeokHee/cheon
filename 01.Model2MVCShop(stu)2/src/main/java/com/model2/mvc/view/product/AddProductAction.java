package com.model2.mvc.view.product;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddProductAction extends Action {
 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductVO proVO = new ProductVO();
		
		proVO.setProdName(request.getParameter("prodName"));
		proVO.setProdDetail(request.getParameter("prodDetail"));
		proVO.setManuDate(request.getParameter("manuDate"));
		proVO.setPrice(Integer.parseInt(request.getParameter("price")));
		proVO.setFileName(request.getParameter("fileName"));
		
		System.out.println(proVO);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(proVO);
		
		request.setAttribute("vo", proVO);
		
		return"forward:/product/addProduct.jsp";
		
	}

}
