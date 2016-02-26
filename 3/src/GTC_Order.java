import java.io.*;
import java.util.*;

public class GTC_Order extends Order {
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
}