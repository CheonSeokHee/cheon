package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public PurchaseDAO() {
		
	}

	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		System.out.println("DAO 진입");
		
		Connection con = DBUtil.getConnection();
		
		ProductVO provo = new ProductVO();
		ProductDao prodao = new ProductDao();
		
		System.out.println("======구매 등록 쿼리문 시작======");
		
//		String sql = "insert into TRANSACTION values(seq_transaction_tran_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, replace(?,'-',''))";
//
//		//String sql = "insert into transaction(tran_no,PROD_NO,payment_option,BUYER_ID,receiver_name,receiver_phone,DEMAILADDR,dlvy_request,TRAN_STATUS_CODE,ORDER_DATA,dlvy_date)"
//				    // + "values(seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,?,SYSDATE,replace(?,'-',''))";
//		
//		PreparedStatement stmt = con.prepareStatement(sql);
//		
//		stmt.setInt(1, provo.getProdNo());
//		stmt.setString(2, purchaseVO.getBuyer().getUserId());
//		stmt.setString(3, purchaseVO.getPaymentOption());
//		stmt.setString(4, purchaseVO.getReceiverName());
//		stmt.setString(5, purchaseVO.getReceiverPhone());
//		stmt.setString(6, purchaseVO.getDivyAddr());
//		stmt.setString(7, purchaseVO.getTranCode());
//		stmt.setString(8, purchaseVO.getDivyRequest());
//		stmt.setString(9, purchaseVO.getDivyDate());
//		
//		System.out.println("잘 넣음");
//		
//		stmt.executeUpdate();
//		
//		con.close();
		
		String sql = "INSERT INTO TRANSACTION VALUES(seq_transaction_tran_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE,replace(?,'-',''))";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		System.out.println("==================purchase 구매 쿼리 시작========================");
		
		pstmt.setInt(1,purchaseVO.getPurchaseProd().getProdNo());
		
		System.out.println(purchaseVO.getPurchaseProd().getProdNo());
		
		pstmt.setString(2, purchaseVO.getBuyer().getUserId());
		pstmt.setString(3, purchaseVO.getPaymentOption());
		pstmt.setString(4, purchaseVO.getReceiverName());
		pstmt.setString(5, purchaseVO.getReceiverPhone());
		pstmt.setString(6, purchaseVO.getDivyAddr());
		pstmt.setString(7, purchaseVO.getDivyRequest());
		pstmt.setInt(8, 1);
		pstmt.setString(9, purchaseVO.getDivyDate());
		
		pstmt.executeUpdate();
		
		con.close();
		System.out.println("==================purchase 구매 쿼리 종료========================");
	}

	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		PurchaseVO purchaseVO = new PurchaseVO();

		String sql = "select * from transaction where tran_NO=?";
				
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		ResultSet rs = stmt.executeQuery();

		while(rs.next()) {
			
			purchaseVO.setPurchaseProd(new ProductServiceImpl().getProduct(rs.getInt("prod_no")));
			purchaseVO.setTranNo(rs.getInt("tran_NO"));
			purchaseVO.setBuyer(new UserServiceImpl().getUser(rs.getString("buyer_id")));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
		}
		
		return purchaseVO;
		
	
	}

	public PurchaseVO findPurchase2(int ProdNo) throws Exception {

		Connection con = DBUtil.getConnection();

		PurchaseVO purchaseVO = new PurchaseVO();

		String sql = "select * from transaction where Prod_No=?";
				
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, ProdNo);
		
		ResultSet rs = stmt.executeQuery();
		
		
		
		while(rs.next()) {
			purchaseVO.setPurchaseProd(new ProductServiceImpl().getProduct(rs.getInt("prod_no")));
			purchaseVO.setBuyer(new UserServiceImpl().getUser(rs.getString("buyer_id")));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			
		}
		
		return purchaseVO;
	}

	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		
		System.out.println("=========purchaseList Dao시작=========");

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE buyer_id = ?";

		PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setString(1, buyerId);
		
		ResultSet rs = pstmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		
		System.out.println("로우의 개수" + total);

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		if (total > 0) {
			
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO purchaseVO = new PurchaseVO();
				
				purchaseVO.setTranNo(rs.getInt("tran_no"));
				purchaseVO.setPurchaseProd(new ProductServiceImpl().getProduct(rs.getInt("prod_no")));
				purchaseVO.setBuyer(new UserServiceImpl().getUser(rs.getString("buyer_id")));
				purchaseVO.setPaymentOption(rs.getString("payment_option"));
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setDivyAddr(rs.getString("demailaddr"));
				purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
				purchaseVO.setTranCode(rs.getString("tran_status_code"));
				purchaseVO.setOrderDate(rs.getDate("order_data"));
				purchaseVO.setDivyDate(rs.getString("dlvy_date"));
				System.out.println(purchaseVO);
				list.add(purchaseVO);
				if (!rs.next()) {
					break;
				}	
			}		
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		
		System.out.println("map().size() : "+ map.size());
		con.close();
		
		System.out.println("=======purchaseList Dao끝=========");
		
		return map;
		
	}

	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception {
	
		return null;
	}

	
	public void updatePurcahse(PurchaseVO purchaseVO) throws Exception 
	{
		Connection con = DBUtil.getConnection();
		
		System.out.println("=======updatePurchase 쿼리 시작=========");
		
		String sql = "update transaction set PAYMENT_OPTION=?, RECEIVER_NAME=?,RECEIVER_PHONE=?,DEMAILADDR=?,DLVY_REQUEST=?,ORDER_DATA=?,DLVY_DATE=? where TRAN_NO=?";
		
		
		PreparedStatement stmt = con.prepareStatement(sql);
			
		
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setDate(7, purchaseVO.getOrderDate());
		stmt.setInt(8, purchaseVO.getTranNo());
		
		stmt.executeUpdate();
		
	
		
		System.out.println("=======updatePurchase 쿼리 끝=========");
		
		con.close();
	}

	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		System.out.println("=======updateTranCode Dao 시작=========");
		
		String sql = "UPDATE transaction "
				   + "SET tran_status_code = ? "			
				   + "WHERE tran_no = ? or prod_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, 1);
		pstmt.setInt(2, purchaseVO.getPurchaseProd().getProdNo());
		pstmt.setInt(3, purchaseVO.getTranNo());
		
		
		System.out.println("updateTranCode tranNo!!!!!!!!!!!!!!!!!");
		
		System.out.println(purchaseVO.getTranNo());
		
		
		int tran = pstmt.executeUpdate();
		
		if (tran == 1) {
			
			System.out.println("tranCode 정상적 업데이트 완료");		
		}
		else {
			System.out.println("tranCode 업데이터 실패");
		}
		
		con.close();
	}
	
		
		
	}


