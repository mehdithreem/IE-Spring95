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
import ir.stocks.data.SymbolRepo;
import ir.stocks.data.SymbolRequestRepo;
import ir.stocks.domain.Order;
import ir.stocks.domain.OrderCommand;
import ir.stocks.domain.Status;
import ir.stocks.domain.Symbol;
import ir.stocks.domain.SymbolRequest;

@WebServlet("/app/company/symbol/accept")
public class Accept extends Controller {
	private static final long serialVersionUID = -2914031413560813321L;
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( request.getParameter("symbolid") == null){
			response.setStatus(400);
			return;
		}
		
		SymbolRequest r = SymbolRequestRepo.getRepository().get(request.getParameter("symbolid"));
		SymbolRequestRepo.getRepository().acceptRequest(r.getSymbol());
			
		try {
			SymbolRepo.getInstance().create(new Symbol(r.getSymbol(), r.getUser()));
			ShareRepo.getRepository().addShare(r.getUser(), r.getSymbol(), r.getQuantity());
			Order ord = new Order(r.getUser()
					,r.getSymbol()
					,r.getPrice()
					,r.getQuantity()
					,OrderCommand.SELL);
			OrderRepo.getRepository().create(ord);
			
			if (ord.getStatus().equals(Status.PENDING)) {
				request.setAttribute("message", "needs-approve");
			} else if(!ord.Exchange()) {
				request.setAttribute("error", "problem");
			}
		}  catch (SQLException e) {
			response.setStatus(406);
			e.printStackTrace();
			return;
		}
		
		request.getRequestDispatcher("/app/symbolmanager").forward(request, response);
	}
}
