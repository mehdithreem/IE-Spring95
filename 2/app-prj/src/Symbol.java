import java.util.*;
import java.io.*;

class Symbol {
	private String ID;

	private Queue<Order> sellQueue;
	private Queue<Order> buyQueue;

	public String getID() {
		return ID;
	}

	public Symbol(String _id) {
		ID = _id;

		sellQueue = new PriorityQueue<Order>(new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o1.getPrice().equals(o2.getPrice()) ? 0 : o1.getPrice() < o2.getPrice() ? -1 : 1;
			}
		});

		buyQueue = new PriorityQueue<Order>(new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o1.getPrice().equals(o2.getPrice()) ? 0 : o1.getPrice() < o2.getPrice() ? 1 : -1;
			}
		});
	}

	public void GTC_Exchange(Order neword, PrintWriter out) {
		neword.setStatus(OrderStatus.QUEUED);
		if (neword.getCommand().equals(OrderCommand.BUY)) {
			buyQueue.offer(neword);
		}
		else {
			sellQueue.offer(neword);
		}

		Boolean exchangeHappened = false;
		while(sellQueue.size() > 0 && buyQueue.size() > 0) {
			Order currBuy = buyQueue.peek();
			Order currSell = sellQueue.peek();
			
			Integer result = currBuy.buy(currSell, out);
			
			if (result == -1) break;

			exchangeHappened = true;
			if(result == 0) {
				// exactly matched
				buyQueue.remove(currBuy);
				sellQueue.remove(currSell);
			} else if (result == 1) {
				// buy stays, sell consumed
				sellQueue.remove(currSell);
			} else if (result == 2) {
				// buy consumed, sell stays
				buyQueue.remove(currBuy);
			}
		}

		if (!exchangeHappened)
			out.println("Order is queued");
	}

	public void IOC_Exchange(Order neword, PrintWriter out) {
		if (neword.getCommand().equals(OrderCommand.BUY))
			QueueLessBuy(false, neword, out);
		else
			QueueLessSell(false, neword, out);
	}

	public void MPO_Exchange(Order neword, PrintWriter out) {
		if (neword.getCommand().equals(OrderCommand.BUY))
			QueueLessBuy(true, neword, out);
		else
			QueueLessSell(true, neword, out);
	}

	private void QueueLessBuy(Boolean noPrice, Order neword, PrintWriter out) {
		neword.setStatus(OrderStatus.QUEUED);

		Integer sellCapasity = 0;
		Integer maxPrice = 0;
		for(Order tmpOrder : sellQueue) {
			sellCapasity += tmpOrder.getQuantity();
			if (sellCapasity >= neword.getQuantity()) {
				maxPrice = tmpOrder.getPrice();
				break;
			}
		}

		if (sellCapasity < neword.getQuantity() || (maxPrice > neword.getPrice() && !noPrice)) {
			out.println("Order is declined");
			neword.setStatus(OrderStatus.REJECTED);
			return;
		}

		Boolean orderDone = false;
		while(!orderDone) {
			Order currSell = sellQueue.peek();
			
			if (noPrice) neword.setPrice(currSell.getPrice());
			Integer result = neword.buy(currSell, out);

			if (result == -1) break;


			if(result == 0) {
				// exactly matched
				sellQueue.remove(currSell);
				orderDone = true;
			} else if (result == 1) {
				// buy stays, sell consumed
				sellQueue.remove(currSell);
			} else if (result == 2) {
				// buy consumed, sell stays
				orderDone = true;
			}
		}
	}

	private void QueueLessSell(Boolean noPrice, Order neword, PrintWriter out) {
		neword.setStatus(OrderStatus.QUEUED);

		Integer buyCapasity = 0;
		Integer minPrice = neword.getPrice();
		for(Order tmpOrder : buyQueue) {
			buyCapasity += tmpOrder.getQuantity();
			if (buyCapasity >= neword.getQuantity()) {
				minPrice = tmpOrder.getPrice();
				break;
			}
		}

		if (buyCapasity < neword.getQuantity() || (minPrice < neword.getPrice() && !noPrice)) {
			out.println("Order is declined");
			neword.setStatus(OrderStatus.REJECTED);
			return;
		}

		Boolean orderDone = false;
		while(!orderDone) {
			Order currBuy = buyQueue.peek();
			
			if (noPrice) neword.setPrice(currBuy.getPrice());
			Integer result = currBuy.buy(neword, out);

			if (result == -1) break;

			if(result == 0) {
				// exactly matched
				buyQueue.remove(currBuy);
				orderDone = true;
			} else if (result == 1) {
				// buy stays, sell consumed
				orderDone = true;
			} else if (result == 2) {
				// buy consumed, sell stays
				buyQueue.remove(currBuy);
			}
		}
	}
}
