import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
@WebServlet("/user.json")
public class UserJSONController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/json");
		
		String idStr = request.getParameter("id");
		System.out.println("----------"+idStr);
		User user = StocksCore.getInstance().findUser(Integer.valueOf(idStr));

		if (user == null) {
			response.setContentType("text/plain");
			response.setStatus(203);
			response.getWriter().write("User not found.");
			return;
		}
		response.getWriter().write(" [ ");

		Collection<Share> shares = user.getShares().values();
		Integer remaining = shares.size();
		response.getWriter().write(" [ ");
		for(Share target : shares) {
			response.getWriter().write("{\"Symbol\":\"" + target.getSymbol().getId() 
										+ "\",\"Quantity\":"+target.getQuantity()+"}");
			if(remaining!=1)
				response.getWriter().write(" , ");
			remaining--;
		}
		response.getWriter().write("],");

		List<Order> orders = user.getOrders();
		remaining = orders.size();
		response.getWriter().write(" [ ");
		for(Order target : orders ) {
			response.getWriter().write("{\"Symbol\":\"" + target.getInstrument().getId() 
										+ "\",\"Quantity\":"+target.getQuantity()
										+ ",\"Price\":"+target.getPrice()
										+ ",\"BS\":\""+target.getCommand().toString()+"\"}");
			if(remaining!=1)
				response.getWriter().write(" , ");
			remaining--;
		}
		response.getWriter().write(" ], " + user.getCredit() +"]");
	}
}