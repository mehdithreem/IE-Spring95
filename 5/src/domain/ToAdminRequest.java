import java.util.*;
import java.io.*;

public class ToAdminRequest {
	Integer id;
	Integer amount;
	Integer reqID;

	ToAdminRequest(Interger _reqID, Integer _id, Integer _amount) {
		id = _id;
		amount = _amount;
		reqID = _reqID;
	}

	void accept() {
		StocksCore sc = StocksCore.getInstance();
		User user = sc.findUser(id);
		user.deposit(amount);
	}
}