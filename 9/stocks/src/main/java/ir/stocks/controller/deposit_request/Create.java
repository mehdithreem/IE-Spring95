package ir.stocks.controller.deposit_request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.DepositRequestRepo;
import ir.stocks.domain.DepositRequest;

@WebServlet("/app/user/deposit")
public class Create extends Controller {
	private static final long serialVersionUID = 198263704817170780L;
	
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("amount") == null) {
			response.setStatus(400);
			return;
		}
		try {
			Integer.valueOf((String) request.getParameter("amount"));
		} catch (NumberFormatException e) {
			response.setStatus(400);
			return;
		}

		if (!DepositRequestRepo.getRepository().create(
				new DepositRequest(
						(String) request.getSession().getAttribute("user")
						, Integer.valueOf((String) request.getAttribute("amount"))
						)
			)) {
			response.setStatus(406);
		}
	}	
}
