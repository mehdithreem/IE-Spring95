import ir.ramtung.coolserver.*;
import java.io.*;

class CustomerAddHandler extends CommandHandler {
	public void execute(PrintWriter out) throws IOException{
		StocksCore sc = StocksCore.getInstance();
		String idStr = params.get("id");
		String nameStr = params.get("name");
		String familyStr = params.get("family");
		if (idStr == null || nameStr==null || familyStr==null) {
			setResCode(404);
			out.println("Mismatched parameters");
			return;
		}
		if(! sc.addUser(Integer.parseInt(idStr),nameStr,familyStr) )
			out.println("Repeated id");
		else
			out.println("New user is added");
		setResCode(200);
		return;
	}
}

class CustomerDepositHandler extends CommandHandler {
	public void execute(PrintWriter out) throws IOException{
		StocksCore sc = StocksCore.getInstance();
		String idStr = params.get("id");
		String amountStr = params.get("amount");
		if(idStr==null || amountStr==null) {
			setResCode(404);
			out.println("Mismatched parameters");
			return;
		}
		setResCode(200);
		Integer id = Integer.parseInt(idStr);
		Integer amount = Integer.parseInt(amountStr);
		User user = sc.findUser(id);
		if(user == null)
			out.println("Unknown user id");
		else{
			user.deposit(amount);
			out.println("Successful");
		}
		return;
	}
}

class CustomerWithdrawHandler extends CommandHandler {
	public void execute(PrintWriter out) throws IOException{
		StocksCore sc = StocksCore.getInstance();
		String idStr = params.get("id");
		String amountStr = params.get("amount");
		if(idStr==null || amountStr==null) {
			setResCode(404);
			out.println("Mismatched parameters");
			return;
		}
		setResCode(200);
		Integer id = Integer.parseInt(idStr);
		Integer amount = Integer.parseInt(amountStr);
		User user = sc.findUser(id);
		if(user == null)
			out.println("Unknown user id");
		else if(!user.withdraw(amount))
			out.println("Not enough money");
		else
			out.println("Successful");
		return;
	}
}


