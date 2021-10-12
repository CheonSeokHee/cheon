package com.model2.mvc.view.purchase;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateTranCodeAction extends Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		System.out.println("======UpdateTranCodeAction ¡¯¿‘=======");
		
		
		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		PurchaseVO purchaseVO = new PurchaseVO();
		
		

		//purchaseVO.setTranCode(request.getParameter("tranCode"));
		
		
		String proTran =request.getParameter("tranCode");
		int tranNo = (Integer.parseInt(request.getParameter("tranNo")));
		
		purchaseVO = purchaseDAO.findPurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		if(proTran.equals("1")) {
			 proTran = "2";
			 purchaseVO.setTranCode(proTran);
			 purchaseDAO.updateTranCode(purchaseVO);	
			 
		}
		
		
		SearchVO searchVO = new SearchVO();
		
		searchVO.setPage(Integer.parseInt(request.getParameter("page")));
		
		searchVO.setPageUnit(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		
		ProductService productService = new ProductServiceImpl();
		
		HashMap<String, Object> map = productService.getProductList(searchVO);
		
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("map", map);
		
		
		return "forward:/product/listProduct.jsp";
		
	}

}
