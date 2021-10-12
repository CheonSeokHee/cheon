package com.model2.mvc.view.purchase;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

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


public class ListPurchaseAction extends Action {
       
    

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		SearchVO searchVO = new SearchVO();
		
		int page = 1;
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		searchVO.setPage(page);
		
		
		searchVO.setPageUnit(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		
		PurchaseService service = new PurchaseServiceImpl();
		
		HashMap<String,Object> map = 
		service.getPurchaseList(searchVO, ((UserVO)request.getSession(true).getAttribute("user")).getUserId());
		
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("map", map);
		
		
		System.out.println("================ListPurchaseAction ³¡=================");
		
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
