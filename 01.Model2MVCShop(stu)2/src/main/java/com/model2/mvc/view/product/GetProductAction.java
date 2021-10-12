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
		
		
		Cookie[] cook = request.getCookies(); // ��Ű ��������
		
		Cookie cookiee = null;;
		
		System.out.println("-------------------��Ű ����-------------------------" + cook.length);
		
		if(cook !=null && cook.length > 0) {   // ��Ű ���
			for (int i = 0; i < cook.length; i++) {
				
				if (cook[i].getName().equals("history")) { // ��Ű�� ���� history�� �����Ѵٸ�?
					cookiee = new  Cookie("history", cook[i].getValue() + "," + proNo); //��Ű�� historyŰ�� ����
					
					}else {
						
					cookiee = new Cookie("history", String.valueOf(proNo));
				}
			}
		}
//		else {
//			cookiee = new Cookie("history", String.valueOf(proNo));
//			
//		}
		
		response.addCookie(cookiee); //Ŭ���̾�Ʈ ���信 ��Ű �߰�
		
		return "forward:/product/getProduct.jsp?menu=" + request.getParameter("menu");
	}

}
