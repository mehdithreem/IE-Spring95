import java.util.*;
import java.io.*;

public class ToAdminRequest {
	Integer id;
	Integer amount;
	Integer reqID;

	ToAdminRequest(Integer _reqID, Integer _id, Integer _amount) {
		id = _id;
		amount = _amount;
		reqID = _reqID;
	}

	public Integer getId() {
		return id;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getReqID() {
		return reqID;
	}

	public void accept() {
		StocksCore sc = StocksCore.getInstance();
		User user = sc.findUser(id);
		user.deposit(amount);
	}
}