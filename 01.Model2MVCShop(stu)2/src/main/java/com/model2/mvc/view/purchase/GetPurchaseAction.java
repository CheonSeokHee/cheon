package com.model2.mvc.view.purchase;

import com.model2.mvc.framework.Action;
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


public class GetPurchaseAction extends Action{

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
				System.out.println("GetPurchaseAction 시작");
				
				PurchaseService service = new PurchaseServiceImpl();
				
				PurchaseVO purchaseVO = service.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
				
				request.setAttribute("purVO", purchaseVO);
				
				
				System.out.println("GetPurchaseAction 종료");
				
				
				System.out.println(purchaseVO);
				
				return "forward:/purchase/getPurchase.jsp";
	}

}
