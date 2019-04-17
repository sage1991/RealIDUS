package com.idus.myPage.dto;

import com.idus.myPage.dto.ShoppingBag;

public class ShopppingBagModifiyRequest {

	private int spbNo;		// 쇼핑백 번호
	private int customerNo;	// 구매자 번호
	private int artistNo;	// 판매자 번호
	private int pieceNo;	// 작품 번호
	private int optionNo;	// 옵션 번호
	private int amount;		// 수량
	private int price;		// 가격
	public int getSpbNo() {
		return spbNo;
	}
	public void setSpbNo(int spbNo) {
		this.spbNo = spbNo;
	}
	public int getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}
	public int getArtistNo() {
		return artistNo;
	}
	public void setArtistNo(int artistNo) {
		this.artistNo = artistNo;
	}
	public int getPieceNo() {
		return pieceNo;
	}
	public void setPieceNo(int pieceNo) {
		this.pieceNo = pieceNo;
	}
	public int getOptionNo() {
		return optionNo;
	}
	public void setOptionNo(int optionNo) {
		this.optionNo = optionNo;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	// 변경할 쇼핑백 정보 불러오기
	public ShopppingBagModifiyRequest(ShoppingBag shoppingBag) {
		this.spbNo = shoppingBag.getSpbNo();
		this.customerNo = shoppingBag.getCustomerNo();
		this.artistNo = shoppingBag.getArtistNo();
		this.pieceNo = shoppingBag.getPieceNo();
		this.optionNo = shoppingBag.getOptionNo();
		this.amount = shoppingBag.getAmount();
		this.price = shoppingBag.getPrice();
	}
	
}
