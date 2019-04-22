package com.idus.myPage.dto;

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

}
