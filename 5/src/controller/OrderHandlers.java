import java.io.*;

abstract class OrderHandlers extends CommandHandler {
	protected User user;
	protected OrderType type;
	protected Symbol symb;
	protected Integer price;
	protected Integer quantity;

	protected void execute(PrintWriter out, HttpServletRequest request, HttpServletResponse response, Boolean hasError) throws IOException{
		StocksCore sc = StocksCore.getInstance();

		String idStr = request.getParameter("id");
		String instrStr = request.getParameter("instrument");
		String typeStr = request.getParameter("type");
		String priceStr = request.getParameter("price");
		String qntStr = request.getParameter("quantity");

		if (idStr == null || instrStr == null || priceStr == null || qntStr == null || typeStr == null) {
			response.setStatus(404);
			out.println("Mismatched parameters");
			hasError = true;
			return;
		}

		user = sc.findUser(Integer.parseInt(idStr));
		if (user == null) {
			response.setStatus(200);
			out.println("Unkown user id");
			hasError = true;
			return;
		}

		try {
			type = OrderType.valueOf(typeStr);
		} catch (IllegalArgumentException e) {
			response.setStatus(200);
			out.println("Invalid type");
			hasError = true;
			return;
		}

		symb = sc.findSymbol(instrStr);
		if (symb == null && !user.isAdmin()) {
			response.setStatus(200);
			out.println("Invalid symbol id");
			hasError = true;
			return;
		}

		price = Integer.parseInt(priceStr);
		quantity = Integer.parseInt(qntStr);

		response.setStatus(200);
		hasError = !exchange(out) || hasError;
	}

	public abstract Boolean exchange(PrintWriter out) throws IOException;
}

@WebServlet("/sell-controll")
class OrderSellHandler extends OrderHandlers {
	public Boolean exchange(PrintWriter out, Boolean hasError) throws IOException{
		if (symb == null && user.isAdmin()) {
			symb = StocksCore.getInstance().addSymbol(params.get("instrument"));
		}
		
		if (!user.hasEnoughShare(symb, quantity)) {
			out.println("Not enough share");
			return false;
		}

		Order curr = StocksCore.getInstance().getOrderNewInstance(type.toString());
		curr.init(user, symb, price, quantity, type, OrderCommand.SELL);
		
		user.addOrder(curr);
		curr.Exchange(out);

		retrun true;
	}
}

@WebServlet("/buy-controll")
class OrderBuyHandler extends OrderHandlers {
	public void exchange(PrintWriter out) throws IOException{
		if (symb == null && user.isAdmin()) {
			symb = StocksCore.getInstance().addSymbol(params.get("instrument"));
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
