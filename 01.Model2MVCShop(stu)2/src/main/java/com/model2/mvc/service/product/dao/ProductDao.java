package com.model2.mvc.service.product.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDao {

	public ProductDao() {
	
	}

	public void insertProduct(ProductVO productVO) throws Exception {
		System.out.println("DAO 시작");
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into product(prod_no,prod_name,PROD_DETAIL,MANUFACTURE_DAY,PRICE,IMAGE_FILE,REG_DATE)"
				+ "values(seq_product_prod_no.NEXTVAL,?,?,replace(?,'-',''),?,?,SYSDATE)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
	
		
		stmt.executeUpdate();
		System.out.println("DAO 종료");
		
		con.close();
	}

	
	public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();

		ProductVO prodVO = null;
		
		while(rs.next()) {
			prodVO = new ProductVO();
			prodVO.setProdNo(rs.getInt("PROD_NO"));
			prodVO.setProdName(rs.getString("PROD_NAME"));
			prodVO.setProdDetail(rs.getString("PROD_DETAIL"));
			prodVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			prodVO.setPrice(rs.getInt("PRICE"));
			prodVO.setFileName(rs.getString("IMAGE_FILE"));
			prodVO.setRegDate(rs.getDate("REG_DATE"));
			
		}
		
		con.close();
		return prodVO;
	}

	

	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		
		
		Connection con = DBUtil.getConnection();
		
		
		
		System.out.println("============getProductList 쿼리 시작================");
		
		
		String sql = "select p.*,NVL(t.tran_status_code, 0) tran_status_code from product p left join transaction t on p.prod_no = t.prod_no(+)";
		
		if(searchVO.getSearchCondition() != null) {
			
			if(searchVO.getSearchCondition().equals("0")) {
				sql += "where p.PROD_NO like '%" + searchVO.getSearchKeyword()
				          + "%'";
				
			}else if (searchVO.getSearchCondition().equals("1")) {
				sql += "where p.PROD_NAME like '%" + searchVO.getSearchKeyword()
		          + "%'";
			}else if (searchVO.getSearchCondition().equals("2")) {
				sql += "where p.price like '%" + searchVO.getSearchKeyword()
				+"%'";
			}
		}
		
		sql += " order by p.PROD_NO";	
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,

						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs = stmt.executeQuery();
					
		rs.last();
		
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				
				ProductVO prodVO = new ProductVO();
				
				prodVO.setProdNo(rs.getInt("PROD_NO"));
				System.out.println(rs.getInt("PROD_NO"));
				
				prodVO.setProdName(rs.getString("PROD_NAME"));
				prodVO.setProdDetail(rs.getString("PROD_DETAIL"));
				prodVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
				prodVO.setPrice(rs.getInt("PRICE"));
				prodVO.setFileName(rs.getString("IMAGE_FILE"));
				prodVO.setRegDate(rs.getDate("REG_DATE"));
				
				prodVO.setProTranCode(rs.getString("tran_status_code").trim());
				
				System.out.println("tran_status_code 이다!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(rs.getString("tran_status_code"));
				System.out.println("tran_status_code 다 받음!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				
				
				list.add(prodVO);
				if (!rs.next())
					break;
			}		
	}
		System.out.println("list.size() : "+ list.size());
		
		map.put("list", list);
		
		System.out.println(list);
		
		System.out.println("map().size() : "+ map.size());

		System.out.println("============getProductList 쿼리 끝================");
		con.close();
		
		
		return map;
	}
	
	
	public void updateProduct(ProductVO productVO) throws Exception {
	
		Connection con = DBUtil.getConnection();
		
		String sql = "update product set prod_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
		
	}

}
