import java.io.*;

enum OrderType {
	GTC,
	IOC,
	MPO
}

enum OrderStatus {
	QUEUED,
	ACCEPTED,
	REJECTED
}

enum OrderCommand {
	BUY,
	SELL
}

class Order {
	private User owner;
	private Symbol instrument;
	private Integer originalPrice;
	private Integer price;
	private Integer originalQuantity;
	private Integer quantity;
	private OrderType type;
	private OrderStatus status;
	private OrderCommand command;

	public Order(User _owner
			,Symbol _instrument
			,Integer _price
			,Integer _quantity
			,OrderType _type
			,OrderCommand _command) {
		owner = _owner;
		instrument = _instrument;
		originalPrice = _price;
		price = _price;
		originalQuantity = _quantity;
		quantity = _quantity;
		type = _type;
		command = _command;
		status = OrderStatus.QUEUED;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer newPrice) {
		price = newPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setStatus(OrderStatus newStat) {
		status = newStat;
	}

	public OrderCommand getCommand() {
		return command;
	}

	private void accept() {
		status = OrderStatus.ACCEPTED;
		owner.acceptOrder(this);

		if (command.equals(OrderCommand.BUY)) {
			owner.addShare(instrument, quantity);
		} else if (command.equals(OrderCommand.SELL)) {
			owner.deposit(quantity*price);
			owner.decShare(instrument, quantity);
		}
	}

	public Integer buy(Order forSell, PrintWriter out) {
		Integer retCode = -1;
		if (price < forSell.price) {
			return retCode;
		}
		if (quantity.equals(forSell.quantity)) {
			printSoldMessage(out, forSell.owner.getID(), quantity, instrument.getID(), price, owner.getID());

			forSell.price = price;
			accept();
			forSell.accept();
			retCode = 0;
		} else if (quantity > forSell.quantity) {
			printSoldMessage(out, forSell.owner.getID(), forSell.quantity, instrument.getID(), price, owner.getID());

			owner.addShare(instrument, forSell.quantity);
			quantity -= forSell.quantity;

			forSell.price = price;
			forSell.accept();
			retCode = 1;
		} else if (quantity < forSell.quantity) {
			printSoldMessage(out, forSell.owner.getID(), quantity, instrument.getID(), price, owner.getID());

			forSell.owner.deposit(quantity*price);
			forSell.owner.decShare(instrument, quantity);
			forSell.quantity -= quantity;

			accept();
			retCode = 2;
		}

		return retCode;
	}

	private void printSoldMessage(PrintWriter out, Integer sellerID, Integer quantity, String symb, Integer price, Integer buyerID) {
		out.println( String.valueOf(sellerID) 
			+ " sold " + String.valueOf(quantity) 
			+ " shares of " + symb 
			+ " @" + String.valueOf(price)
			+ " to " + String.valueOf(buyerID));
		System.out.println( String.valueOf(sellerID) 
			+ " sold " + String.valueOf(quantity) 
			+ " shares of " + symb 
			+ " @" + String.valueOf(price)
			+ " to " + String.valueOf(buyerID));
	}
}
