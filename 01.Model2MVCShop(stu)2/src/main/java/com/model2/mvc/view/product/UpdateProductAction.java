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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateProductAction
 */
@WebServlet("/UpdateProductAction")
public class UpdateProductAction extends Action{

	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductVO proVO = new ProductVO();
		
		proVO.setProdNo(prodNo);
		proVO.setProdName(request.getParameter("prodName"));
		proVO.setProdDetail(request.getParameter("prodDetail"));
		proVO.setManuDate(request.getParameter("manuDate"));
		proVO.setPrice(Integer.parseInt(request.getParameter("price")));
		proVO.setFileName(request.getParameter("fileName"));
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(proVO);
		
	
		//return "redirect:/listProduct.do?prodNo="+proNo;
		return "redirect:/getProduct.do?prodNo="+ prodNo+"&menu=manage";
	}
}
