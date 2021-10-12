package com.model2.mvc.view.purchase;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
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


public class UpdateTranCodeByProdAction extends Action {
       
   
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		System.out.println("======UpdateTranCodeByProdAction ¡¯¿‘=======");
		
		//int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		//PurchaseService service = new PurchaseServiceImpl();
		//ProductService service1 = new ProductServiceImpl();
		
	    //ProductVO productVO = service1.getProduct(0)
		ProductVO productVO = new ProductVO();
		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		
		PurchaseVO purchaseVO = new PurchaseVO();
		
		String proTran =request.getParameter("ProtranCode");
		
		purchaseVO = purchaseDAO.findPurchase2(Integer.parseInt(request.getParameter("prodNo")));
		
		System.out.println("22222222222222222222222222222222");
	
		System.out.println(purchaseVO);
		System.out.println(Integer.parseInt(request.getParameter("prodNo")));
		
		
		purchaseVO.setTranCode(proTran);
		
		//System.out.println(request.getParameter("tranCode"));
		
	
		
		if(proTran.equals("0")) {
			 proTran = "1";
			 
			 purchaseVO.setTranCode(proTran);
			 productVO.setProTranCode(proTran);
			 purchaseDAO.updateTranCode(purchaseVO);	
		}
						
		
		SearchVO searchVO = new SearchVO();
		
		System.out.println(Integer.parseInt(request.getParameter("page")));
		System.out.println(request.getParameter("searchCondition"));
		System.out.println(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		
		
		searchVO.setPage(Integer.parseInt(request.getParameter("page")));
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setPageUnit(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		
		
		
		
		ProductService productService = new ProductServiceImpl();
		
		HashMap<String, Object> map = productService.getProductList(searchVO);
		
		
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("map", map);
		//request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/product/listProduct.jsp?menu=manage";
	}
}
