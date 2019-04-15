package com.idus.myPage.dto;

public class OrderRequest {

	private int artistNo;
	private int customerNo;
	private int pieceNo;
	private int optionNo;
	private int statusNo;
	private int amount;
	private int price;

	public int getArtistNo() {
		return artistNo;
	}

	public void setArtistNo(int artistNo) {
		this.artistNo = artistNo;
	}

	public int getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
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

	public int getStatusNo() {
		return statusNo;
	}

	public void setStatusNo(int statusNo) {
		this.statusNo = statusNo;
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

	public OrderRequest(ShoppingBag shoppingBag) {
		this.artistNo = shoppingBag.getArtistNo();
		this.customerNo = shoppingBag.getCustomerNo();
		this.pieceNo = shoppingBag.getPieceNo();
		this.optionNo = shoppingBag.getOptionNo();
		this.statusNo = 1;
		this.amount = shoppingBag.getAmount();
		this.price = shoppingBag.getPrice();
	}

	
}
