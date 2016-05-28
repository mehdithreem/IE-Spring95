package ir.stocks.controller.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.controller.Controller;
import ir.stocks.data.OrderRepo;
import ir.stocks.domain.Order;
import ir.stocks.domain.Status;

@WebServlet("/app/finance/order/accept")
public class Accept extends Controller {
	private static final long serialVersionUID = 8724279355262770622L;

	@Override
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("orderid") == null) {
			response.setStatus(400);
			return;
		}
		
		Order target = OrderRepo.getRepository().getOrder(Integer.valueOf(request.getParameter("orderid")));
		if(target == null) {
			response.setStatus(406);
			return;
		}
		
		target.setStatus(Status.ACCEPTED);
		OrderRepo.getRepository().updateStatus(target);
		
		if(!target.Exchange())
			request.setAttribute("error", "problem");
		
		request.getRequestDispatcher("/app/ordermanager").forward(request, response);
	}
}
