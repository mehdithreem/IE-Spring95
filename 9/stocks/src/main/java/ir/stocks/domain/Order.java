package ir.stocks.domain;

import java.io.*;

public class Order {
	private User owner;
	private Symbol instrument;
	private Integer price;
	private Integer quantity;
	private OrderType type;
	private String command;

	public Order(User _owner
			,Symbol _instrument
			,Integer _price
			,Integer _quantity
			,String _command) {
		owner = _owner;
		instrument = _instrument;
		price = _price;
		quantity = _quantity;
		setType(new OrderType("GTC"));
		command = _command;
	}

	public Integer getPrice() {
		return price;
	}

	public User getOwner() {
		return owner;
	}
	
	public void setPrice(Integer newPrice) {
		price = newPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getCommand() {
		return command;
	}

	private void accept() {
//		status = OrderStatus.ACCEPTED;
//		owner.acceptOrder(this);
//
//		if (command.equals(String.BUY)) {
//			owner.addShare(instrument, quantity);
//		} else if (command.equals(String.SELL)) {
//			owner.deposit(quantity*price);
//			owner.decShare(instrument, quantity);
//		}
	}

	public Symbol getInstrument() {
		return instrument;
	}

	protected Integer buy(Order forSell, PrintWriter out) {
		Integer retCode = -1;
		if (price < forSell.price) {
			return retCode;
		}
		if (quantity.equals(forSell.quantity)) {

			forSell.price = price;
			accept();
			forSell.accept();
			retCode = 0;
			printSoldMessage(out, forSell.owner, quantity, instrument.getID(), price, owner);

		} else if (quantity > forSell.quantity) {

//			owner.addShare(instrument, forSell.quantity);
			quantity -= forSell.quantity;

			forSell.price = price;
			forSell.accept();
			retCode = 1;
			printSoldMessage(out, forSell.owner, forSell.quantity, instrument.getID(), price, owner);

		} else if (quantity < forSell.quantity) {

			forSell.owner.deposit(quantity*price);
//			forSell.owner.decShare(instrument, quantity);
			forSell.quantity -= quantity;

			accept();
			retCode = 2;
			printSoldMessage(out, forSell.owner, quantity, instrument.getID(), price, owner);
		}

		return retCode;
	}

	private void printSoldMessage(PrintWriter out, User seller, Integer quantity, String symb, Integer price, User buyer) {
//		Integer sellerID = seller.getID();
//		Integer buyerID = buyer.getID();
//		out.println( String.valueOf(sellerID) 
//			+ " sold " + String.valueOf(quantity) 
//			+ " shares of " + symb 
//			+ " @" + String.valueOf(price)
//			+ " to " + String.valueOf(buyerID));
//		System.out.println( String.valueOf(sellerID) 
//			+ " sold " + String.valueOf(quantity) 
//			+ " shares of " + symb 
//			+ " @" + String.valueOf(price)
//			+ " to " + String.valueOf(buyerID));
//		 StocksCore.getInstance().appendToWriter(String.valueOf(buyerID));
//		 StocksCore.getInstance().appendToWriter(",");
//		 StocksCore.getInstance().appendToWriter(String.valueOf(sellerID));
//		 StocksCore.getInstance().appendToWriter(",");
//		 StocksCore.getInstance().appendToWriter(symb);
//		 StocksCore.getInstance().appendToWriter(",");
//		 StocksCore.getInstance().appendToWriter(type.toString());
//		 StocksCore.getInstance().appendToWriter(",");
//		 StocksCore.getInstance().appendToWriter(String.valueOf(quantity));
//		 StocksCore.getInstance().appendToWriter(",");
//		 StocksCore.getInstance().appendToWriter(String.valueOf(buyer.getCredit()));
//		 StocksCore.getInstance().appendToWriter(",");
//		 StocksCore.getInstance().appendToWriter(String.valueOf(seller.getCredit()));
//		 StocksCore.getInstance().appendToWriter("\n");
	}

	public void Exchange(PrintWriter out) {
		setStatus(OrderStatus.QUEUED);
		Symbol symb = getInstrument();
		if (getCommand().equals(OrderCommand.BUY)) {
			symb.buyOffer(this);
		}
		else {
			symb.sellOffer(this);
		}

		Boolean exchangeHappened = false;
		while(symb.sellQueueSize() > 0 && symb.buyQueueSize() > 0) {
			Order currBuy = symb.buyPeek();
			Order currSell = symb.sellPeek();
			
			Integer result = currBuy.buy(currSell, out);
			
			if (result == -1) break;

			exchangeHappened = true;
			if(result == 0) {
				// exactly matched
				symb.buyRemove(currBuy);
				symb.sellRemove(currSell);
			} else if (result == 1) {
				// buy stays, sell consumed
				symb.sellRemove(currSell);
			} else if (result == 2) {
				// buy consumed, sell stays
				symb.buyRemove(currBuy);
			}
		}

		if (!exchangeHappened)
			out.println("Order is queued");
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}
	
	
}
