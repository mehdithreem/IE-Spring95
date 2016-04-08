import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/")
public class SymbolController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		req.setAttribute("symbols", StocksCore.getInstance().getAllSymbols());
		req.getRequestDispatcher("view-symbols.jsp").forward(req, resp);
	}
}
