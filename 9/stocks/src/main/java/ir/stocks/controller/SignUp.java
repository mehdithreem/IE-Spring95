package ir.stocks.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.data.UserRepo;
import ir.stocks.domain.User;

@WebServlet("/app/signUp")
public class SignUp extends Controller {
	private static final long serialVersionUID = 1464486337788353668L;

	protected void myDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(	request.getAttribute("username") == null || request.getAttribute("name") == null ||
			request.getAttribute("lastName") == null || request.getAttribute("password") == null ||
			request.getAttribute("email") == null) {
			response.setStatus(400);
			return;
		}
		
		try {
			UserRepo.getRepository().create(
					new User((String) request.getAttribute("username"), 
							(String) request.getAttribute("password"), 
							(String) request.getAttribute("name"), 
							(String) request.getAttribute("lastName"), 
							(String) request.getAttribute("email"))
			);
		}  catch (SQLException e) {
			response.setStatus(406);
		}
	}
}
