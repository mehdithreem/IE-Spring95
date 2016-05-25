import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/admin/requests")
public class AdminDepositRequests extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("requests", ToAdminRequestRepository.getInstance().getAll());
		request.getRequestDispatcher("/deposit-requests.jsp").forward(request, response);
	}
}