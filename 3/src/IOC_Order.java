import java.io.*;
import java.util.*;

public class IOC_Order extends Order {
	public void Exchange(PrintWriter out) {
		if (getCommand().equals(OrderCommand.BUY))
			buy(out);
		else
			sell(out);
	}
	
	private void buy(PrintWriter out) {
		setStatus(OrderStatus.QUEUED);

		Integer sellCapasity = 0;
		Integer maxPrice = 0;
		for(Order tmpOrder : getInstrument().getSells()) {
			sellCapasity += tmpOrder.getQuantity();
			if (sellCapasity >= getQuantity()) {
				maxPrice = tmpOrder.getPrice();
				break;
			}
		}

		if (sellCapasity < getQuantity() || maxPrice > getPrice()) {
			out.println("Order is declined");
			setStatus(OrderStatus.REJECTED);
			return;
		}

		Boolean orderDone = false;
		while(!orderDone) {
			Order currSell = getInstrument().sellPeek();
			
			Integer result = buy(currSell, out);

			if (result == -1) break;


			if(result == 0) {
				// exactly matched
				getInstrument().sellRemove(currSell);
				orderDone = true;
			} else if (result == 1) {
				// buy stays, sell consumed
				getInstrument().sellRemove(currSell);
			} else if (result == 2) {
				// buy consumed, sell stays
				orderDone = true;
			}
		}
	}

	private void sell(PrintWriter out) {
		setStatus(OrderStatus.QUEUED);

		Integer buyCapasity = 0;
		Integer minPrice = getPrice();
		for(Order tmpOrder : getInstrument().getBuys()) {
			buyCapasity += tmpOrder.getQuantity();
			if (buyCapasity >= getQuantity()) {
				minPrice = tmpOrder.getPrice();
				break;
			}
		}

		if (buyCapasity < getQuantity() || minPrice < getPrice()) {
			out.println("Order is declined");
			setStatus(OrderStatus.REJECTED);
			return;
		}

		Boolean orderDone = false;
		while(!orderDone) {
			Order currBuy = getInstrument().buyPeek();
			
			Integer result = currBuy.buy(this, out);

			if (result == -1) break;

			if(result == 0) {
				// exactly matched
				getInstrument().buyRemove(currBuy);
				orderDone = true;
			} else if (result == 1) {
				// buy stays, sell consumed
				orderDone = true;
			} else if (result == 2) {
				// buy consumed, sell stays
				getInstrument().buyRemove(currBuy);
			}
		}
	}
}