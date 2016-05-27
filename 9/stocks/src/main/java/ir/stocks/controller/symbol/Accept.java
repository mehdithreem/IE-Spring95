package ir.stocks.controller.symbol;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.DepositRequestRepo;
import ir.stocks.data.SymbolRepo;

@WebServlet("/app/company/accept-symbol")
public class Accept extends Controller {
	private static final long serialVersionUID = -2914031413560813321L;
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( request.getParameter("symbolid") == null){
			response.setStatus(400);
			return;
		}
			
		try {
			SymbolRepo.getInstance().acceptRequest((String)request.getParameter("symbolid"));
		}  catch (SQLException e) {
			response.setStatus(406);
		}
	}
}
