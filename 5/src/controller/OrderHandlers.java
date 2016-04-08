import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

abstract class OrderHandlers extends CommandHandler {
	protected User user;
	protected OrderType type;
	protected Symbol symb;
	protected String instrStr;
	protected Integer price;
	protected Integer quantity;

	public void execute(PrintWriter out, HttpServletRequest request, HttpServletResponse response, Boolean hasError) throws IOException{
		StocksCore sc = StocksCore.getInstance();

		String idStr = request.getParameter("id");
		String instrStr = request.getParameter("instrument");
		String typeStr = request.getParameter("type");
		String priceStr = request.getParameter("price");
		String qntStr = request.getParameter("quantity");

		if (idStr == null || instrStr == null || priceStr == null || qntStr == null || typeStr == null) {
			// response.setStatus(404);
			out.println("Mismatched parameters");
			hasError = true;
			return;
		}

		user = sc.findUser(Integer.parseInt(idStr));
		if (user == null) {
			// response.setStatus(200);
			out.println("Unkown user id");
			hasError = true;
			return;
		}

		try {
			type = OrderType.valueOf(typeStr);
		} catch (IllegalArgumentException e) {
			// response.setStatus(200);
			out.println("Invalid type");
			hasError = true;
			return;
		}

		symb = sc.findSymbol(instrStr);
		if (symb == null && !user.isAdmin()) {
			// response.setStatus(200);
			out.println("Invalid symbol id");
			hasError = true;
			return;
		}

		price = Integer.parseInt(priceStr);
		quantity = Integer.parseInt(qntStr);

		// response.setStatus(200);
		hasError = !exchange(out) || hasError;
	}

	public abstract Boolean exchange(PrintWriter out) throws IOException;
}