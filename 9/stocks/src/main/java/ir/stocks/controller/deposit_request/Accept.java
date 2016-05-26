package ir.stocks.controller.deposit_request;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.DepositRequestRepo;

@WebServlet("/app/finance/deposit/accept")
public class Accept extends Controller {
	private static final long serialVersionUID = 7360908586501934710L;
	
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("reqid") == null) {
			response.setStatus(400);
			return;
		}
		Integer reqid = null;
		try {
			Integer.valueOf((String) request.getParameter("reqid"));
		} catch (NumberFormatException e) {
			response.setStatus(400);
			return;
		}
			
		try {
			DepositRequestRepo.getRepository().acceptRequest(reqid);
		}  catch (SQLException e) {
			response.setStatus(406);
		}
	}	
}