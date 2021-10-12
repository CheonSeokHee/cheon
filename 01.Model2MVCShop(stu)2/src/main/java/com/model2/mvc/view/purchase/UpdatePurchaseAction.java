package com.model2.mvc.view.purchase;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

import java.io.IOException;
import java.net.PortUnreachableException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdatePurchaseAction extends Action {
  

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		System.out.println("=====updatePurcahseAction ¡¯¿‘========");
		
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		
		PurchaseVO purVO = service.getPurchase(tranNo);
		
		purVO.setTranNo(tranNo);
		
		purVO.setPaymentOption(request.getParameter("paymentOption"));
		purVO.setReceiverName(request.getParameter("receiverName"));
		purVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purVO.setDivyAddr(request.getParameter("receiverAddr"));
		purVO.setDivyRequest(request.getParameter("receiverRequest"));
		purVO.setDivyDate(request.getParameter("divyDate"));
		
		
		
		service.updatePurcahse(purVO);
		
		
		System.out.println(purVO);
		
		request.setAttribute("purvo", purVO);
		
		System.out.println("=====updatePurcahseAction ≥°========");
		
		
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}
