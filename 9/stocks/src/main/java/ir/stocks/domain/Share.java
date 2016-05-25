package ir.stocks.domain;

public class Share {
	private Symbol symbol;
	private User owner;

	private Integer quantity;

	public Share(User _owner, Symbol _symbol, Integer _quantity){
		symbol = _symbol;
		owner = _owner;
		quantity = _quantity;
	}

	public Symbol getSymbol(){
		return symbol;
	}

	public void IncQuantity(Integer value) {
		quantity += value;
	}

	public void DecQuantity(Integer value) {
		quantity -= value;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
