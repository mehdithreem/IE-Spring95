package ir.stocks.domain;

import java.io.*;
import java.util.Queue;

import ir.stocks.data.OrderRepo;
import ir.stocks.data.ShareRepo;
import ir.stocks.data.UserRepo;

public class Order {
	private String owner;
	private String instrument;
	private Integer price;
	private Integer quantity;
	private OrderCommand command;
	private Status status;
	private Integer id;
	
	private static Integer max = 500;

	public Order(String _owner
			,String _instrument
			,Integer _price
			,Integer _quantity
			,OrderCommand _command) {
		owner = _owner;
		instrument = _instrument;
		price = _price;
		quantity = _quantity;
		command = _command;
		
		if (price > max)
			status = Status.PENDING;
		else
			status = Status.ACCEPTED;
	}
	
	public static Integer getMax() {
		return max;
	}

	public static void setMax(Integer max) {
		Order.max = max;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Integer getPrice() {
		return price;
	}

	public String getOwner() {
		return owner;
	}
	
	public void setPrice(Integer newPrice) {
		price = newPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public OrderCommand getCommand() {
		return command;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	private void accept() {
//		status = OrderStatus.ACCEPTED;
//		owner.acceptOrder(this);

		if (command.equals(OrderCommand.BUY)) {
			ShareRepo.getRepository().addShare(owner, instrument, quantity);
		} else if (command.equals(OrderCommand.SELL)) {
			UserRepo.getRepository().depositUserCredit(owner, quantity*price);
			ShareRepo.getRepository().addShare(owner, instrument, -quantity);
		}
	}

	public String getInstrument() {
		return instrument;
	}

	protected Integer buy(Order forSell) {
		Integer retCode = -1;
		if (price < forSell.price) {
			return retCode;
		}
		if (quantity.equals(forSell.quantity)) {

			forSell.price = price;
			accept();
			forSell.accept();
			retCode = 0;
			printSoldMessage(out, forSell.owner, quantity, instrument, price, owner);

		} else if (quantity > forSell.quantity) {

//			owner.addShare(instrument, forSell.quantity);
			quantity -= forSell.quantity;

			forSell.price = price;
			forSell.accept();
			retCode = 1;
			printSoldMessage(out, forSell.owner, forSell.quantity, instrument, price, owner);

		} else if (quantity < forSell.quantity) {

			forSell.owner.deposit(quantity*price);
//			forSell.owner.decShare(instrument, quantity);
			forSell.quantity -= quantity;

			accept();
			retCode = 2;
			printSoldMessage(out, forSell.owner, quantity, instrument, price, owner);
		}

		return retCode;
	}

	private void printSoldMessage(PrintWriter out, User seller, Integer quantity, String symb, Integer price, User buyer) {

	}

	public Boolean Exchange() {
		OrderRepo orepo = OrderRepo.getRepository();
		Queue<Order> sell = OrderRepo.getRepository().getSymbolQueue(instrument, OrderCommand.SELL);
		Queue<Order> buy = OrderRepo.getRepository().getSymbolQueue(instrument, OrderCommand.BUY);
		
		if (sell == null || buy == null)
			return false;
		
		while(sell.size() > 0 && buy.size() > 0) {
			Order currBuy = sell.peek();
			Order currSell = buy.peek();
			
			Integer result = currBuy.buy(currSell);
			
			if (result == -1) break;

			if(result == 0) {
				// exactly matched
				sell.remove(currSell);
				orepo.delete(currSell);
				buy.remove(currBuy);
				orepo.delete(currBuy);
			} else if (result == 1) {
				// buy stays, sell consumed
				sell.remove(currSell);
				orepo.delete(currSell);
			} else if (result == 2) {
				// buy consumed, sell stays
				buy.remove(currBuy);
				orepo.delete(currBuy);
			}
		}
		
		return true;
	}
	
}
