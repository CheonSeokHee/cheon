package com.model2.mvc.view.purchase;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddPurchaseAction extends Action  {
       
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//int tranNO = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("action 진입");
		
		
		
		System.out.println(request.getParameter("prodNo"));
		
		PurchaseVO purVO = new PurchaseVO();
		
		ProductService productService = new ProductServiceImpl();
		
		//purVO.setTranNo(tranNO);
		purVO.setBuyer((UserVO)request.getSession(true).getAttribute("user"));
		
		purVO.setPaymentOption(request.getParameter("paymentOption"));
		purVO.setReceiverName(request.getParameter("receiverName"));
		purVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purVO.setDivyAddr(request.getParameter("divyAddr"));
		purVO.setDivyRequest(request.getParameter("divyRequest"));
		
		System.out.println("-----------addPurchase 쿼리 찍어보기----------");
		
		purVO.setTranCode(request.getParameter("tranCode"));
		System.out.println(request.getParameter("tranCode"));
		
		purVO.setDivyDate(request.getParameter("receiverDate"));
		System.out.println(request.getParameter("receiverDate"));
		
		
		purVO.setPurchaseProd(productService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		System.out.println(productService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		
		
		System.out.println("파라미터 잘 받았음");
		
		PurchaseService service = new PurchaseServiceImpl();
		
		service.addPurchase(purVO);
		
		
		request.setAttribute("purVO",  purVO);
		
		//return "redirect:/addPurchaseView.do?tranNo="+ tranNO;
		return  "forward:/purchase/addPurchase.jsp";
	}

}
