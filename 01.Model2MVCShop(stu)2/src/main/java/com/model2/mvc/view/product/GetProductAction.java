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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetProductAction extends Action  {

	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int proNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		
		ProductVO vo = service.getProduct(proNo);
		
		request.setAttribute("vo", vo);
		
		
		Cookie[] cook = request.getCookies(); // 쿠키 가져오기
		
		Cookie cookiee = null;;
		
		System.out.println("-------------------쿠키 시작-------------------------" + cook.length);
		
		if(cook !=null && cook.length > 0) {   // 쿠키 출력
			for (int i = 0; i < cook.length; i++) {
				
				if (cook[i].getName().equals("history")) { // 쿠키에 값이 history가 존재한다면?
					cookiee = new  Cookie("history", cook[i].getValue() + "," + proNo); //쿠키로 history키에 저장
					
					}else {
						
					cookiee = new Cookie("history", String.valueOf(proNo));
				}
			}
		}
//		else {
//			cookiee = new Cookie("history", String.valueOf(proNo));
//			
//		}
		
		response.addCookie(cookiee); //클라이언트 응답에 쿠키 추가
		
		return "forward:/product/getProduct.jsp?menu=" + request.getParameter("menu");
	}

}
