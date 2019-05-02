package com.idus.myPage.dto;

public class ShoppingBagModifyRequest {

	private int spbNo; // 쇼핑백 번호
	private int amount; // 수량
	private int different; // 버튼 구분
	private int price;	// 갸격
	private int totalPrice;	// 총가격
	private int customerNo;
	
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

	public int getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	

}
