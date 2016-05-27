package ir.stocks.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.stocks.domain.Status;
import ir.stocks.domain.SymbolRequest;

public class SymbolRequestRepo {
	private static SymbolRequestRepo repo = null;

	public static SymbolRequestRepo getRepository() {
		if (repo == null) {
			repo = new SymbolRequestRepo();
		}
		return repo;
	}
	
	private static Map<String, SymbolRequest> repomap = new HashMap<String, SymbolRequest>();
	
	public Boolean create(SymbolRequest target) {
		if(repomap.put(target.getSymbol(), target) == null)
			return true;
		else
			return false;
	}
	
	public void acceptRequest(String reqId) {
		System.out.println("Accept request " + String.valueOf(reqId));
		SymbolRequest r = repomap.get(reqId);
		if (r == null)
			return;
		
		System.out.println("Accepted");
		r.setStatus(Status.ACCEPTED);
	}
	
	public SymbolRequest get(String symbol) {
		return repomap.get(symbol);
	}
	
	public void rejectRequest(String reqId) {
		SymbolRequest r = repomap.get(reqId);
		if (r == null)
			return;
		
		r.setStatus(Status.REJECTED);
	}
	
	public List<SymbolRequest> getAll() {
		List<SymbolRequest> ret = new ArrayList<SymbolRequest>();
		ret.addAll(repomap.values());
		return ret;
	}
	
	public List<SymbolRequest> getAll(String username) {
		List<SymbolRequest> lst = new ArrayList<SymbolRequest>();
		for(SymbolRequest r : repomap.values())
			if(r.getUser().equals(username))
				lst.add(r);
		return lst;
	}
}
