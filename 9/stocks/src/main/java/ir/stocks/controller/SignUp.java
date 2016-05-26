package ir.stocks.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.data.UserRepo;
import ir.stocks.domain.User;

@WebServlet("/signUp")
public class SignUp extends Controller {
	private static final long serialVersionUID = 1464486337788353668L;

	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(	request.getParameter("username") == null || request.getParameter("name") == null ||
			request.getParameter("lastName") == null || request.getParameter("password") == null ||
			request.getParameter("email") == null) {
			System.out.println("bad sign up.");
			response.setStatus(400);
			return;
		}
		
		try {
			UserRepo.getRepository().create(
					new User((String) request.getParameter("username"), 
							(String) request.getParameter("password"), 
							(String) request.getParameter("name"), 
							(String) request.getParameter("lastName"), 
							(String) request.getParameter("email"))
			);
		}  catch (SQLException e) {
			request.setAttribute("duplicated", "true");
			request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
			System.out.println("duplicated username");
			return;
		}
		
		request.setAttribute("new_account", "true");
		request.getRequestDispatcher("/Index.jsp").forward(request, response);
		System.out.println("user created.");
	}
}
