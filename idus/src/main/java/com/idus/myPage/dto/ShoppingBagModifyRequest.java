package com.idus.myPage.dto;

import com.idus.myPage.dto.ShoppingBag;

public class ShoppingBagModifyRequest {

	private int spbNo; // 쇼핑백 번호
	private int amount; // 수량

	public int getSpbNo() {
		return spbNo;
	}

	public void setSpbNo(int spbNo) {
		this.spbNo = spbNo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	// 변경할 쇼핑백 정보 불러오기
	public ShoppingBagModifyRequest(ShoppingBag shoppingBag) {
		this.amount = shoppingBag.getAmount();
	}

}
