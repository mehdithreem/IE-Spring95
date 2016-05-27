package ir.stocks.domain;

public class SymbolRequest {
	private String user;
	private String symbol;
	private Integer price;
	private Integer quantity;
	private Status status;
	
	public SymbolRequest(String user, String symbol, Integer price, Integer quantity) {
		status = Status.PENDING;
		this.user = user;
		this.symbol = symbol;
		this.price = price;
		this.quantity = quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
}
