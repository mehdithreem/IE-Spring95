import java.io.*;

abstract class OrderHandlers extends CommandHandler {
	protected User user;
	protected OrderType type;
	protected Symbol symb;
	protected Integer price;
	protected Integer quantity;

	public void execute(PrintWriter out) throws IOException{
		StocksCore sc = StocksCore.getInstance();

		String idStr = params.get("id");
		String instrStr = params.get("instrument");
		String typeStr = params.get("type");
		String priceStr = params.get("price");
		String qntStr = params.get("quantity");

		if (idStr == null || instrStr == null || priceStr == null || qntStr == null || typeStr == null) {
			setResCode(404);
			out.println("Mismatched parameters");
			return;
		}

		user = sc.findUser(Integer.parseInt(idStr));
		if (user == null) {
			setResCode(200);
			out.println("Unkown user id");
			return;
		}

		try {
			type = OrderType.valueOf(typeStr);
		} catch (IllegalArgumentException e) {
			setResCode(200);
			out.println("Invalid type");
			return;
		}

		symb = sc.findSymbol(instrStr);
		if (symb == null && !user.isAdmin()) {
			setResCode(200);
			out.println("Invalid symbol id");
			return;
		}

		price = Integer.parseInt(priceStr);
		quantity = Integer.parseInt(qntStr);

		setResCode(200);
		exchange(out);
	}

	public abstract void exchange(PrintWriter out) throws IOException;
}

class OrderSellHandler extends OrderHandlers {
	public void exchange(PrintWriter out) throws IOException{
		if (symb == null && user.isAdmin()) {
			symb = StocksCore.getInstance().addSymbol(params.get("instrument"));
		}
		
		if (!user.hasEnoughShare(symb, quantity)) {
			out.println("Not enough share");
			return;
		}

		Order curr = StocksCore.getInstance().getOrderNewInstance(type.toString());
		curr.init(user, symb, price, quantity, type, OrderCommand.SELL);
		
		user.addOrder(curr);
		curr.Exchange(out);
	}
}


class OrderBuyHandler extends OrderHandlers {
	public void exchange(PrintWriter out) throws IOException{
		if (symb == null && user.isAdmin()) {
			symb = StocksCore.getInstance().addSymbol(params.get("instrument"));
		}
		
		if (!user.withdraw(price)) {
			out.println("Not enough money");
			return;
		}

		Order curr = StocksCore.getInstance().getOrderNewInstance(type.toString());
		curr.init(user, symb, price, quantity, type, OrderCommand.BUY);

		user.addOrder(curr);
		curr.Exchange(out);
	}
}
