package com.model2.mvc.view.product;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
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


public class UpdateProductViewAction extends Action  {

	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		int proNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		
		ProductVO vo = service.getProduct(proNo);
		
		request.setAttribute("proVO", vo);
		
		
		return "forward:/product/updateProduct.jsp";
	}

}
