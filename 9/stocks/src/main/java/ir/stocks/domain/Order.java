package ir.stocks.domain;

import java.util.Queue;

import ir.stocks.data.OrderRepo;
import ir.stocks.data.ShareRepo;
import ir.stocks.data.TransactionRepo;
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
			
			TransactionRepo.getRepository().create(new Transaction(owner, forSell.owner, instrument, quantity, price));
		} else if (quantity > forSell.quantity) {

			ShareRepo.getRepository().addShare(owner, instrument, forSell.quantity);
			quantity -= forSell.quantity;
			OrderRepo.getRepository().updateQuantity(this);

			forSell.price = price;
			forSell.accept();
			retCode = 1;
			TransactionRepo.getRepository().create(new Transaction(owner, forSell.owner, instrument, forSell.quantity, price));

		} else if (quantity < forSell.quantity) {

			UserRepo.getRepository().depositUserCredit(forSell.owner, quantity*price);
			ShareRepo.getRepository().addShare(forSell.owner, instrument, -quantity);
			forSell.quantity -= quantity;
			OrderRepo.getRepository().updateQuantity(forSell);

			accept();
			retCode = 2;
			TransactionRepo.getRepository().create(new Transaction(owner, forSell.owner, instrument, quantity, price));
		}

		return retCode;
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
