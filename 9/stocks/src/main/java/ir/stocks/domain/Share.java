package ir.stocks.domain;

public class Share {
	private String symbol;
	private String owner;

	private Integer quantity;

	public Share(String _owner, String _symbol, Integer _quantity){
		symbol = _symbol;
		owner = _owner;
		quantity = _quantity;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getOwner() {
		return owner;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
