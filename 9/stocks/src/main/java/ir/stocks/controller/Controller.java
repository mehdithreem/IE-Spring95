package ir.stocks.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ir.stocks.data.UserRepo;
import ir.stocks.domain.User;

public abstract class Controller extends HttpServlet {
	private static final long serialVersionUID = 3969232472655651664L;

	private Boolean commonAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute("user") == null) {
			UserRepo urepo = UserRepo.getRepository();
			User user = null;
			try {
				user = urepo.findByUsername(request.getRemoteUser());
			} catch (Exception e) {
				e.printStackTrace();
				response.setContentType("text/html");
				response.getOutputStream().println("Internal system error!<p>Contact system administrator");
				return false;
			}
			if (user != null) {
				session.setAttribute("user", user);
			}
		}
		
		return true;
	}
	
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(commonAction(request, response))
			myDoPost(request, response);
	}
	
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(commonAction(request, response))
			myDoGet(request, response);
	}
	
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(404);
	}

	protected void myDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(404);
	}
}
