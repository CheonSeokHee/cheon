package com.model2.mvc.view.purchase;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddPurchaseViewAction extends Action {

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		System.out.println("action 진입");
		System.out.println(request.getParameter("prodNo"));
		
		
		
		//int tranNO = Integer.parseInt(request.getParameter("prodNo"));
		
		
		ProductVO provo = new ProductVO();
		
		ProductService service  = new ProductServiceImpl();
		
		
		provo = service.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		System.out.println("action 종료");
		//request.setAttribute("purVO", vo);
		
		request.setAttribute("vo", provo);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
