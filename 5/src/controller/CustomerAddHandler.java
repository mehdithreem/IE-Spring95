import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/add-user")
public class CustomerAddHandler extends CommandHandler {
	public void execute(PrintWriter out, HttpServletRequest request, HttpServletResponse response, Boolean hasError) throws IOException{
		StocksCore sc = StocksCore.getInstance();
		String idStr = request.getParameter("id");
		String nameStr = request.getParameter("name");
		String familyStr = request.getParameter("family");
		if (idStr == null || nameStr==null || familyStr==null) {
			// response.setStatus(404);
			out.println("Mismatched parameters");
			hasError = true;
			return;
		}
		if(! sc.addUser(Integer.parseInt(idStr),nameStr,familyStr) ) {
			out.println("Repeated id");
			hasError = true;
		}
		else
			out.println("کاربر جدید اضافه شد");
		// response.setStatus(200);
		return;
	}
}