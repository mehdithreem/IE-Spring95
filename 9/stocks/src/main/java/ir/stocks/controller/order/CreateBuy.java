package ir.stocks.controller.order;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.OrderRepo;
import ir.stocks.domain.Order;
import ir.stocks.domain.OrderCommand;
import ir.stocks.domain.Status;
import ir.stocks.domain.User;

@WebServlet("/app/user/order/buy")
public class CreateBuy extends Controller {
	private static final long serialVersionUID = 1153512583903604196L;

	@Override
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(request.getParameter("instrument") == null || request.getParameter("price") == null
				|| request.getParameter("quantity") == null) {
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
		
		if (user.getCredit() < price * quantity) {
			request.setAttribute("error", "not-enough-money");
		} else {
			try {
				Order ord = new Order(user.getUsername()
						,request.getParameter("instrument")
						,price
						,quantity
						,OrderCommand.BUY);
				OrderRepo.getRepository().create(ord);
				
				if (ord.getStatus().equals(Status.PENDING)) {
					request.setAttribute("message", "needs-approve");
				} else if(!ord.Exchange()) {
					request.setAttribute("error", "problem");
				}
			} catch (SQLException e) {
				request.setAttribute("error", "instr-not-exist");
			}
		}
		
		request.getRequestDispatcher("/app/ordermanager").forward(request, response);
	}
}
