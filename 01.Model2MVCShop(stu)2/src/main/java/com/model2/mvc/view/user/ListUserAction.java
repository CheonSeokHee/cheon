package com.model2.mvc.view.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		SearchVO searchVO=new SearchVO();
		

		int page=1;
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		String searchKeyword = null;
		
		if(request.getParameter("searchKeyword") != null) {
			searchKeyword =request.getParameter("searchKeyword");
		}
		String searchCondition = null;
		
		if (request.getParameter("searchCondition") != null) {
			searchCondition = request.getParameter("searchCondition");
						
		}
		
		            
		searchVO.setPage(page);
		searchVO.setSearchKeyword(searchKeyword);
		searchVO.setSearchCondition(searchCondition);
		
		String pageUnit=getServletContext().getInitParameter("pageSize"); //web.xml에서 가져옴 //getInitParameter --> servletConfig API 확인
		
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		UserService service=new UserServiceImpl();
		
		HashMap<String,Object> map=service.getUserList(searchVO);
		
		System.out.println("파라미터로 받은 total = ?" + request.getParameter("total"));
		
		if (searchVO.getPage() != 1) {
			map.put("count", Integer.parseInt( request.getParameter("total")));
			//request.getAttribute("total")
		}

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		return "forward:/user/listUser.jsp";
	}
}