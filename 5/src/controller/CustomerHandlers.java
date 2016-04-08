import ir.ramtung.coolserver.*;
import java.io.*;

@WebServlet("/add-user")
class CustomerAddHandler extends CommandHandler {
	public void execute(PrintWriter out, HttpServletRequest request, HttpServletResponse response, Boolean hasError) throws IOException{
		StocksCore sc = StocksCore.getInstance();
		String idStr = request.getParameter("id");
		String nameStr = request.getParameter("name");
		String familyStr = request.getParameter("family");
		if (idStr == null || nameStr==null || familyStr==null) {
			response.setStatus(404);
			out.println("Mismatched parameters");
			hasError = true;
			return;
		}
		if(! sc.addUser(Integer.parseInt(idStr),nameStr,familyStr) ) {
			out.println("Repeated id");
			hasError = true;
		}
		else
			out.println("New user is added");
		response.setStatus(200);
		return;
	}
}

@WebServlet("/deposit-user")
class CustomerDepositHandler extends CommandHandler {
	public void execute(PrintWriter out) throws IOException{
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
			ToAdminRequestReposeitory.getInstance().addNew(id, amount);
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


