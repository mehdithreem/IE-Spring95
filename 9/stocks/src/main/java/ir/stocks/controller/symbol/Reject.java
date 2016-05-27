package ir.stocks.controller.symbol;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.SymbolRequestRepo;

@WebServlet("/app/company/symbol/reject")
public class Reject extends Controller {
	private static final long serialVersionUID = -3852860741808017598L;

	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( request.getParameter("symbolid") == null){
			response.setStatus(400);
			return;
		}
			
		SymbolRequestRepo.getRepository().rejectRequest(request.getParameter("symbolid"));
		
		request.getRequestDispatcher("/app/symbolmanager").forward(request, response);
	}
}
