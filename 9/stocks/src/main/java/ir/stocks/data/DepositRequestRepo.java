package ir.stocks.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private Integer idgen = 0;
	
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
		System.out.println("Accept request " + String.valueOf(reqId));
		DepositRequest r = repomap.get(reqId);
		if (r == null)
			return;
		
		System.out.println("Accepted");
		r.setStatus(Status.ACCEPTED);
		UserRepo.getRepository().updateUserCredit(r.getId(), r.getAmount());
	}
	
	public void rejectRequest(Integer reqId) throws SQLException {
		DepositRequest r = repomap.get(reqId);
		if (r == null)
			return;
		
		r.setStatus(Status.REJECTED);
	}
	
	public List<DepositRequest> getAll() {
		List<DepositRequest> ret = new ArrayList<DepositRequest>();
		ret.addAll(repomap.values());
		return ret;
	}
	
	public List<DepositRequest> getAll(String username) {
		List<DepositRequest> lst = new ArrayList<DepositRequest>();
		for(DepositRequest r : repomap.values())
			if(r.getId().equals(username))
				lst.add(r);
		return lst;
	}
}
