package ir.stocks.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ir.stocks.data.UserRepo;
import ir.stocks.domain.Role;

@WebServlet("/app/admin/setroles")
public class SetRoles extends Controller {
	private static final long serialVersionUID = -7473982344945008542L;

	@Override
	protected void myDoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(	request.getParameter("target-user") == null) {
				response.setStatus(400);
				return;
		}
		
		List<Role> roles = new ArrayList<Role>();
		for(Role r : Role.values()) {
			if (request.getParameter(r.toString()) != null) {
				roles.add(r);
				System.out.println(r.toString());
			}
		}
		
		System.out.println("here2");
		
		try {
			UserRepo.getRepository().setUserRoles(request.getParameter("target-user"), roles);
			System.out.println("here3");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(406);
		}
		
		request.getRequestDispatcher("/app/usermanager").forward(request, response);
	}
}
