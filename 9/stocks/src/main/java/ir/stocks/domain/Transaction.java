package ir.stocks.domain;

import java.time.LocalDateTime;

public class Transaction {
	private String buyer;
	private String seller;
	
	private String symbol;
	private Integer quantity;
	private Integer price;
	
	private String time;
	private Integer id;

	public Transaction(String buyer, String seller, String symbol, Integer quantity, Integer price) {
		this.buyer = buyer;
		this.seller = seller;
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
		time = LocalDateTime.now().toString();
	}

	public String getBuyer() {
		return buyer;
	}

	public String getSeller() {
		return seller;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public String getTime() {
		return time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
