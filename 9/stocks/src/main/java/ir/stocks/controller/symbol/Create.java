package ir.stocks.controller.symbol;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.OrderRepo;
import ir.stocks.data.ShareRepo;
import ir.stocks.data.UserRepo;
import ir.stocks.domain.Order;
import ir.stocks.domain.OrderType;
import ir.stocks.domain.Share;
import ir.stocks.domain.Symbol;
import ir.stocks.domain.User;

@WebServlet("/app/company/create-symbol")
public class Create extends Controller {
	private static final long serialVersionUID = -4766798000640793628L;

	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( request.getParameter("symbolid") == null ||  
			request.getParameter("quantity") == null ||
			request.getParameter("price") == null ){
			response.setStatus(400);
			return;
		}
		
		try {
			ShareRepo.getRepository().create(
					new Share(	
							(User) request.getAttribute("user"), 
							new Symbol((String)request.getAttribute("symbolid"),((User)request.getAttribute("user")).getUsername()),
							Integer.valueOf(request.getParameter("quantity"))
							)
			);
		}  catch (SQLException e) {
			System.out.println("duplicated share");
			return;
		}
		
		try {
			OrderRepo.getRepository().create(
					new Order(	
							(User) request.getAttribute("user"),
							new Symbol((String)request.getAttribute("symbolid"), ((User) request.getAttribute("user")).getUsername() ),
							Integer.valueOf(request.getParameter("price")),
							Integer.valueOf(request.getParameter("quantity")),
							"BUY"
							)
			);
		}  catch (SQLException e) {
			System.out.println("duplicated order");
			return;
		}
	}	
}
