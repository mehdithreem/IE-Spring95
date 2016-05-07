import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


public abstract class CommandHandlerRAW extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		StringWriter sw = new StringWriter();
		Boolean hasError = false;

		System.out.println("sdfadfdsafa");

		execute(new PrintWriter(sw, true), request, response, hasError);

		System.out.println("23131");

		if (hasError) {
			response.setStatus(203);
		}
		response.setContentType("text/plain");
		response.getWriter().write(sw.toString());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
	}

	protected abstract void execute(PrintWriter out, HttpServletRequest request,
	 HttpServletResponse response, Boolean hasError) throws IOException;
}
