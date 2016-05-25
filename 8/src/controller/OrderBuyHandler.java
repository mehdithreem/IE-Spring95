import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/buy-controll")
public class OrderBuyHandler extends OrderHandlers {
	public Boolean exchange(PrintWriter out) throws IOException{
		if (symb == null && user.isAdmin()) {
			symb = StocksCore.getInstance().addSymbol(instrStr);
		}
		
		if (!user.withdraw(price)) {
			out.println("Not enough money");
			return false;
		}

		Order curr = StocksCore.getInstance().getOrderNewInstance(type.toString());
		curr.init(user, symb, price, quantity, type, OrderCommand.BUY);

		user.addOrder(curr);
		curr.Exchange(out);

		return true;
	}
}
