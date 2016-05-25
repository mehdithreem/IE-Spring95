import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/admin/requests/decline")
public class AdminDepositRequestsDecline extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ToAdminRequestRepository.getInstance().decline(Integer.valueOf(request.getParameter("id")));
		request.getRequestDispatcher("/admin/requests").forward(request, response);
	}
}