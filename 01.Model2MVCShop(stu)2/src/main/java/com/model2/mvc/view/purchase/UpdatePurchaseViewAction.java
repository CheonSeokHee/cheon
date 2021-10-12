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

public class UpdatePurchaseViewAction extends Action {

	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		System.out.println("=====updatePurcahseViewAction ¡¯¿‘========");
		
		System.out.println(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		
		PurchaseVO purvo = service.getPurchase(tranNo);
		
		request.setAttribute("purvo", purvo);
		
		System.out.println(purvo.getBuyer());
		
		System.out.println("=====updatePurcahseViewAction ≥°========");
		
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
