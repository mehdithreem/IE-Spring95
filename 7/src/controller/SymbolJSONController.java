import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/symbols.json")
public class SymbolJSONController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/json");

		Collection<Symbol> symbs = StocksCore.getInstance().getAllSymbols();
		response.getWriter().write(" [ ");

		Integer remaining = symbs.size();
		for(Symbol target : symbs) {
			response.getWriter().write("{\"NAME\":\"" + target.getID() + "\", ");

			response.getWriter().write("\"SELL\" : [");
			Integer count = 0;
			for(Order ord : target.getSells()) {
				response.getWriter().write("["
					+String.valueOf(ord.getOwner().getID())+","
					+String.valueOf(ord.getQuantity())+","
					+String.valueOf(ord.getPrice())+"]");
				if ((count == target.getSells().size()-1) || count== 4) break;
				else response.getWriter().write(",");
				count++;
			}

			response.getWriter().write("], \"BUY\" : [");
			count = 0;
			for(Order ord : target.getBuys()) {
				response.getWriter().write("["
					+String.valueOf(ord.getOwner().getID())+","
					+String.valueOf(ord.getQuantity())+","
					+String.valueOf(ord.getPrice())+"]");
				if ((count == target.getSells().size()-1) || count== 4) break;
				else response.getWriter().write(",");
				count++;
			}
			response.getWriter().write("]");

			response.getWriter().write("}");
			if (remaining != 1) response.getWriter().write(",");
			remaining--;
		}

		response.getWriter().write(" ]");
	}
}
