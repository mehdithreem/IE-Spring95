import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/deposit-user")
public class CustomerDepositHandler extends CommandHandler {
	public void execute(PrintWriter out, HttpServletRequest request, HttpServletResponse response, Boolean hasError) throws IOException{
		StocksCore sc = StocksCore.getInstance();
		String idStr = request.getParameter("id");
		String amountStr = request.getParameter("amount");
		if(idStr==null || amountStr==null) {
			response.setStatus(404);
			out.println("Mismatched parameters");
			hasError = true;
			return;
		}
		response.setStatus(200);
		Integer id = Integer.parseInt(idStr);
		Integer amount = Integer.parseInt(amountStr);
		User user = sc.findUser(id);
		if(user == null) {
			out.println("Unknown user id");
			hasError = true;
		}
		else{
			ToAdminRequestRepository.getInstance().addNew(id, amount);
			out.println("Request sent to admin");
		}
		return;
	}
}

// class CustomerWithdrawHandler extends CommandHandler {
// 	public void execute(PrintWriter out) throws IOException{
// 		StocksCore sc = StocksCore.getInstance();
// 		String idStr = request.getParameter("id");
// 		String amountStr = request.getParameter("amount");
// 		if(idStr==null || amountStr==null) {
// 			response.setStatus(404);
// 			out.println("Mismatched parameters");
// 			hasError = true;
// 			return;
// 		}
// 		response.setStatus(200);
// 		Integer id = Integer.parseInt(idStr);
// 		Integer amount = Integer.parseInt(amountStr);
// 		User user = sc.findUser(id);
// 		if(user == null) {
// 			out.println("Unknown user id");
// 			hasError = true;
// 		}
// 		else if(!user.withdraw(amount)) {
// 			out.println("Not enough money");
// 			hasError = true;
// 		}
// 		else
// 			out.println("Successful");
// 		return;
// 	}
// }


