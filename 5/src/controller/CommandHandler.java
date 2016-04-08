import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


public abstract class CommandHandler extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		StringWriter sw = new StringWriter();
		Boolean hasError = false;

		execute(new PrintWriter(sw, true), request, response, hasError);

		if (hasError) {
			request.setAtrribute("errorMessage", sw.toString());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else {
			request.setAtrribute("message", sw.toString());
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
	}

	protected abstract void execute(PrintWriter out, HttpServletRequest request,
	 HttpServletResponse response, Boolean hasError) throws IOException;
}
