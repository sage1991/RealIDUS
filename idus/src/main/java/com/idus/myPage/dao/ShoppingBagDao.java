package com.idus.myPage.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.idus.myPage.dto.OrderRequest;
import com.idus.myPage.dto.ShoppingBag;

@Repository
public class ShoppingBagDao {

	@Autowired
	private SqlSession sqlSession;

	// 내 쇼핑백 현황 검색
	public List<ShoppingBag> selectAllShoppingBag(int customerNo) {
		return sqlSession.selectList("cart.selectAllShoppingBag", customerNo);
	}
	
	// 소핑백 작품 수량 수정 
	public int updateShoppingBag(ShoppingBag shoppingBag) {
		return sqlSession.update("cart.updateShoppingBag",shoppingBag);
	}
	
	// 쇼핑백 작품 제거
	public int deletShoppingBag(int customerNo) {
		return sqlSession.delete("cart.deletShoppingBag", customerNo);
	}
	
	// 작품 주문
	public int insertOrder(OrderRequest orderRequest) {
		return sqlSession.insert("cart.insertOrder", orderRequest);
	}
	
}
