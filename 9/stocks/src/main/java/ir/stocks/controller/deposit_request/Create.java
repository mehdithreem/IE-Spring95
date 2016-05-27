package ir.stocks.controller.deposit_request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.DepositRequestRepo;
import ir.stocks.domain.DepositRequest;
import ir.stocks.domain.User;

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

		User target = (User) request.getAttribute("user");
		System.out.println("deposit req: " + target.getUsername());
		if (!DepositRequestRepo.getRepository().create(
				new DepositRequest(
						target.getUsername()
						, Integer.valueOf((String) request.getParameter("amount"))
						)
			)) {
			response.setStatus(406);
		}
		
		request.setAttribute("message", "success");
		request.getRequestDispatcher("/app/credit").forward(request, response);
	}	
}
