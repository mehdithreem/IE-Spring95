package ir.stocks.controller.symbol;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.SymbolRequestRepo;
import ir.stocks.domain.SymbolRequest;
import ir.stocks.domain.User;

@WebServlet("/app/company/symbol/create")
public class Create extends Controller {
	private static final long serialVersionUID = -4766798000640793628L;

	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( request.getParameter("symbolid") == null ||  
			request.getParameter("quantity") == null ||
			request.getParameter("price") == null ){
			response.setStatus(400);
			return;
		}
		
		Integer price = null;
		Integer quantity = null;
		try {
			price = Integer.parseInt(request.getParameter("price"));
			quantity = Integer.parseInt(request.getParameter("quantity"));
		} catch (NumberFormatException e) {
			response.setStatus(400);
			return;
		}
		
		User user = (User) request.getAttribute("user");
		
		if (!SymbolRequestRepo.getRepository().create(
				new SymbolRequest(user.getUsername(), 
					request.getParameter("symbolid"),
					price,
					quantity))) {
			request.setAttribute("error", "duplicate-name");
		};
		
		request.getRequestDispatcher("/app/symbolmanager").forward(request, response);
	}	
}
