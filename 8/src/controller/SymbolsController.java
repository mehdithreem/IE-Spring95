import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/symbols")
public class SymbolsController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("symbols", StocksCore.getInstance().getAllSymbols());
		request.getRequestDispatcher("view-symbols.jsp").forward(request, response);
	}
}
