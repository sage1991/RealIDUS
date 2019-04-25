package com.idus.myPage.dto;

public class ShoppingBagModifyRequest {

	private int spbNo; // 쇼핑백 번호
	private int amount; // 수량
	private int different; // 버튼 구분
	private int price;
	private int totalPrice;
	
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

	public int getDifferent() {
		return different;
	}

	public void setDifferent(int different) {
		this.different = different;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice() {
		this.totalPrice = this.amount * this.price;
	}

	
}
