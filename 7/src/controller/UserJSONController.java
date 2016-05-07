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
		User user = StocksCore.getInstance().findUser(Integer.valuOf(idStr));
		response.getWriter().write(" [ ");

		Collection<Share> shares = user.getShares().values();
		Integer remaining = shares.size();
		response.getWriter().write(" [ ");
		for(Share target : shares) {
			remaining--;
			response.getWriter().write("{\"Symbol\":" + target.getSymbol().getId() 
										+ ",\"Quantity\":"+target.getQuantity()+"}");
			if(remaining!=1)
				response.getWriter().write(" , ");
		}
		response.getWriter().write("]");

		List<Order> orders = user.getOreders();
		remaining = orders.size();
		response.getWriter().write(" [ ");
		for(Order target : orders ) {
			remaining--;
			response.getWriter().write("{\"Symbol\":" + target.getInstrument().getId() 
										+ ",\"Quantity\":"+target.getQuantity()+
										+ ",\"Price\":"+target.getPrice()+
										+ ",\"Buy,Sell\":"+target.getCommand().toString()+"}");
			if(remaining!=1)
				response.getWriter().write(" , ");
		}
		response.getWriter().write(" ], " + user.getCredit() +"]");
	}
}