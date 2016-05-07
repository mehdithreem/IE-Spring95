import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/send-sell")
public class OrderSellHandlerRAW extends OrderHandlersRAW {
	public Boolean exchange(PrintWriter out) throws IOException{
		if (symb == null && user.isAdmin()) {
			symb = StocksCore.getInstance().addSymbol(instrStr);
			System.out.println("symb "+ instrStr+ " added");
		}
		
		if (!user.hasEnoughShare(symb, quantity)) {
			out.println("Not enough share");
			return false;
		}

		Order curr = StocksCore.getInstance().getOrderNewInstance(type.toString());
		curr.init(user, symb, price, quantity, type, OrderCommand.SELL);
		
		user.addOrder(curr);
		curr.Exchange(out);

		return true;
	}
}
