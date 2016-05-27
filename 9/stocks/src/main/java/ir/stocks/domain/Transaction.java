package ir.stocks.domain;

public class Transaction {
	private User buyer;
	private User seller;
	
	private Symbol symbol;
	private Integer quantity;
	private Integer price;
	
	private Status status;
}
