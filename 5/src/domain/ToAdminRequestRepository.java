import java.util.*;
import java.io.*;

public class ToAdminRequestRepository {
	private Map<Integer,ToAdminRequest> requests;
	private Integer counter = 0;

	private static ToAdminRequestRepository rep = new ToAdminRequestRepository();

	private ToAdminRequestRepository() {
		requests = new HashMap<Integer, ToAdminRequest>();
	}

	public static ToAdminRequestRepository getInstance(){
		return repo;
	}

	public void addNew(Integer id, Integer amount) {
		requests.put(couter, new ToAdminRequest(counter, id, amount));
		couter++;
	}

	public void accept(Integer reqID) {
		requests.get(reqID).accept();
		requests.remove(reqID);
	}

	public void decline(Integer reqID) {
		requests.remove(reqID);
	}

	public List<ToAdminRequest> getAll() {
		return requests.values();
	}
	
}