package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.user.vo.UserVO;


public class UserDAO {
	
	public UserDAO(){
	}

	public void insertUser(UserVO userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into USERS values (?,?,?,'user',?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserId());
		stmt.setString(2, userVO.getUserName());
		stmt.setString(3, userVO.getPassword());
		stmt.setString(4, userVO.getSsn());
		stmt.setString(5, userVO.getPhone());
		stmt.setString(6, userVO.getAddr());
		stmt.setString(7, userVO.getEmail());
		stmt.executeUpdate();
		
		con.close();
	}

	public UserVO findUser(String userId) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from USERS where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userId);

		ResultSet rs = stmt.executeQuery();

		UserVO userVO = null;
		while (rs.next()) {
			userVO = new UserVO();
			userVO.setUserId(rs.getString("USER_ID"));
			userVO.setUserName(rs.getString("USER_NAME"));
			userVO.setPassword(rs.getString("PASSWORD"));
			userVO.setRole(rs.getString("ROLE"));
			userVO.setSsn(rs.getString("SSN"));
			userVO.setPhone(rs.getString("CELL_PHONE"));
			userVO.setAddr(rs.getString("ADDR"));
			userVO.setEmail(rs.getString("EMAIL"));
			userVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();

		return userVO;
	}

	public HashMap<String,Object> getUserList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
//		String sql = "select * from USERS ";
//		if (searchVO.getSearchCondition() != null) {
//			if (searchVO.getSearchCondition().equals("0")) {
//				sql += " where USER_ID like'" + searchVO.getSearchKeyword()
//						+ "%'";
//			} else if (searchVO.getSearchCondition().equals("1")) {
//				sql += " where USER_NAME like'" + searchVO.getSearchKeyword()
//						+ "%'";
//			}
//		}
//		
////		if (searchVO.getPage() != 1) {
////			
////			int finish = searchVO.getPage() * searchVO.getPageUnit();
////			int start = finish - 2;
////			System.out.println(finish);
////			sql += " WHERE ROWNUM >= " +  start + "  " + finish;
////		}
//		
//		sql += " order by USER_ID";
//
//		PreparedStatement stmt = 
//			con.prepareStatement(	sql,
//														ResultSet.TYPE_SCROLL_INSENSITIVE,
//														ResultSet.CONCUR_UPDATABLE);
//		ResultSet rs = stmt.executeQuery();
//
//		rs.last();
//		int total = rs.getRow();
//		System.out.println("로우의 수:" + total);
//
//		HashMap<String,Object> map = new HashMap<String,Object>();
//		map.put("count", new Integer(total));
//		
//		rs.first();
//		//rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
//		
//		System.out.println("searchVO.getPage():" + searchVO.getPage());
//		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
//
//		
//		ArrayList<UserVO> list = new ArrayList<UserVO>();
//		if (total > 0) {
//			for (int i = 0; i < searchVO.getPageUnit(); i++) {
//				UserVO vo = new UserVO();
//				vo.setUserId(rs.getString("USER_ID"));
//				vo.setUserName(rs.getString("USER_NAME"));
//				vo.setPassword(rs.getString("PASSWORD"));
//				vo.setRole(rs.getString("ROLE"));
//				vo.setSsn(rs.getString("SSN"));
//				vo.setPhone(rs.getString("CELL_PHONE"));
//				vo.setAddr(rs.getString("ADDR"));
//				vo.setEmail(rs.getString("EMAIL"));
//				vo.setRegDate(rs.getDate("REG_DATE"));
//
//				list.add(vo);
//				if (!rs.next())
//					break;
//			}
//		}
//		System.out.println("list.size() : "+ list.size());
//		map.put("list", list);
//		System.out.println("map().size() : "+ map.size());
//
//		con.close();
//			
//		return map;
		
		String sql = "";
		
		if (searchVO.getPage() == 1 && ("null".equals(searchVO.getSearchKeyword())|| searchVO.getSearchKeyword()==null)) {
			
			System.out.println("--------------1번 상황시 실행--------------------");
			
			sql =     "SELECT * "
					+ "FROM users "
					+ "ORDER BY user_id";
		}

		//String = "SELECT  ROWNUM, s.* FROM users s";
		
		else if (searchVO.getPage() == 1 && !"null".equals(searchVO.getSearchKeyword())) {
			
			sql =  "SELECT *"
				 + " FROM users"; 
			
			System.out.println("--------------2번 상황시 실행--------------------");
			
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE USER_ID LIKE(?)";
				
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " WHERE USER_NAME LIKE(?)";
			}
			sql += " ORDER BY user_id";
		}
		
		else if (searchVO.getPage() != 1 && searchVO.getSearchKeyword().equals("null")) {
			
			System.out.println("--------------3번 상황시 실행--------------------");
			
			sql = "SELECT *"+
					" FROM"+
					" (SELECT ROWNUM num, tv.* FROM (SELECT * FROM users ORDER BY user_id)tv)"+
					" WHERE num BETWEEN ? AND ?";
		}
		else {
			
			System.out.println("--------------4번 상황시 실행--------------------");
			
			sql = "SELECT *"+
					" FROM";
			
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " (SELECT ROWNUM num, tv.* "
						+ "FROM (SELECT * FROM users ORDER BY user_id)tv "
						+ "WHERE user_id LIKE(?))";
			}
			else if (searchVO.getSearchCondition().equals("1")) {
				sql += " (SELECT ROWNUM num, tv.* "
						+ "FROM (SELECT * FROM users ORDER BY user_id)tv "
						+ "WHERE user_name LIKE(?))";
			}
			
			sql += " WHERE num BETWEEN ? AND ?";
		}
		
		 

		PreparedStatement pstmt = con.prepareStatement(sql, 
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		
		if (searchVO.getPage()==1 && ("null".equals(searchVO.getSearchKeyword())|| searchVO.getSearchKeyword()==null)) {
			
			System.out.println("pstmt 1번 if문 입장");
			
		}
		else if (searchVO.getPage()==1 && !"null".equals(searchVO.getSearchKeyword())) {
			System.out.println("pstmt 2번 if문 입장");
			pstmt.setString(1, "%"+searchVO.getSearchKeyword()+"%");
			
		}
		else if (searchVO.getPage()!=1 && searchVO.getSearchKeyword().equals("null")) {
			System.out.println("pstmt 3번 if문 입장");
			int start = searchVO.getPage() * searchVO.getPageUnit() -(searchVO.getPageUnit() - 1);
			int finish = searchVO.getPage() * searchVO.getPageUnit();
			pstmt.setInt(1, start);
			pstmt.setInt(2, finish);
		}
		else {
			System.out.println("pstmt 4번 if문 입장");
			int start = searchVO.getPage() * searchVO.getPageUnit() -(searchVO.getPageUnit() - 1);
			int finish = searchVO.getPage() * searchVO.getPageUnit();
			pstmt.setString(1, "%"+searchVO.getSearchKeyword()+"%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, finish);
			
		}
		
			
		ResultSet rs = pstmt.executeQuery();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		
		if (searchVO.getPage() == 1) {
			rs.last();
			int total = rs.getRow();
			System.out.println("로우의 수:" + total);
			map.put("count", new Integer(total));
			rs.first();
			System.out.println("searchVO.getPage():" + searchVO.getPage());
			System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
			
		if (total > 0) {
				for (int i = 0; i < searchVO.getPageUnit(); i++) {
					UserVO vo = new UserVO();
					vo.setUserId(rs.getString("USER_ID"));
					vo.setUserName(rs.getString("USER_NAME"));
					vo.setPassword(rs.getString("PASSWORD"));
					vo.setRole(rs.getString("ROLE"));
					vo.setSsn(rs.getString("SSN"));
					vo.setPhone(rs.getString("CELL_PHONE"));
					vo.setAddr(rs.getString("ADDR"));
					vo.setEmail(rs.getString("EMAIL"));
					vo.setRegDate(rs.getDate("REG_DATE"));
					System.out.println(vo);
					
					list.add(vo);
					if (!rs.next())
						break; //이건 맨 마지막페이제서 3개 미만일때 2개까지 출력을 위해서
				}
			}
		}
		else {
			while(rs.next()) {
				UserVO vo = new UserVO();
				vo.setUserId(rs.getString("USER_ID"));
				vo.setUserName(rs.getString("USER_NAME"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setRole(rs.getString("ROLE"));
				vo.setSsn(rs.getString("SSN"));
				vo.setPhone(rs.getString("CELL_PHONE"));
				vo.setAddr(rs.getString("ADDR"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				System.out.println(vo);
				list.add(vo);
			}
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
		
	}

	public void updateUser(UserVO userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update USERS set USER_NAME=?,CELL_PHONE=?,ADDR=?,EMAIL=? where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserName());
		stmt.setString(2, userVO.getPhone());
		stmt.setString(3, userVO.getAddr());
		stmt.setString(4, userVO.getEmail());
		stmt.setString(5, userVO.getUserId());
		stmt.executeUpdate();
		
		con.close();
	}
}