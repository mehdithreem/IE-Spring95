package ir.stocks.domain;

public class Symbol {
	private String id;

	// private Queue<Order> sellQueue;
	// private Queue<Order> buyQueue;

	public String getId() {
		return id;
	}

	public String getID() {
		return id;
	}

	public Symbol(String _id) {
		id = _id;

//		sellQueue = new PriorityQueue<Order>(new Comparator<Order>() {
//			@Override
//			public int compare(Order o1, Order o2) {
//				return o1.getPrice().equals(o2.getPrice()) ? 0 : o1.getPrice() < o2.getPrice() ? -1 : 1;
//			}
//		});
//
//		buyQueue = new PriorityQueue<Order>(new Comparator<Order>() {
//			@Override
//			public int compare(Order o1, Order o2) {
//				return o1.getPrice().equals(o2.getPrice()) ? 0 : o1.getPrice() < o2.getPrice() ? 1 : -1;
//			}
//		});
	}

//	public Queue<Order> getSells() {
//		return sellQueue;
//	}
//
//	public Queue<Order> getBuys() {
//		return buyQueue;
//	}
//
//	public void buyOffer(Order neword) {
//		buyQueue.offer(neword);
//	}
//
//	public Integer buyQueueSize() {
//		return buyQueue.size();
//	}
//
//	public Order buyPeek() {
//		return buyQueue.peek();
//	}
//
//	public void buyRemove(Order target) {
//		buyQueue.remove(target);
//	}
//
//	public void sellOffer(Order neword) {
//		sellQueue.offer(neword);
//	}
//
//	public Integer sellQueueSize() {
//		return sellQueue.size();
//	}
//
//	public Order sellPeek() {
//		return sellQueue.peek();
//	}
//
//	public void sellRemove(Order target) {
//		sellQueue.remove(target);
//	}
}
