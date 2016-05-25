package ir.stocks.data;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ir.stocks.domain.DepositRequest;
import ir.stocks.domain.Status;

public class DepositRequestRepo {
	private static DepositRequestRepo repo = null;

	public static DepositRequestRepo getRepository() {
		if (repo == null) {
			repo = new DepositRequestRepo();
		}
		return repo;
	}
	
	private static Map<Integer, DepositRequest> repomap = new HashMap<Integer, DepositRequest>();
	private static Integer idgen = 0;
	
	private Integer generateID() {
		return idgen++;
	}
	
	public Boolean create(DepositRequest target) {
		Integer id = generateID();
		target.setReqID(id);
		if(repomap.put(id, target) == null)
			return true;
		else
			return false;
	}
	
	public void acceptRequest(Integer reqId) throws SQLException {
		DepositRequest r = repomap.get(reqId);
		if (r == null)
			return;
		
		r.setStatus(Status.ACCEPTED);
		UserRepo.getRepository().updateUserCredit(r.getId(), r.getAmount());
	}
	
	public void rejectRequest(Integer reqId) throws SQLException {
		DepositRequest r = repomap.get(reqId);
		if (r == null)
			return;
		
		r.setStatus(Status.REJECTED);
	}
}
