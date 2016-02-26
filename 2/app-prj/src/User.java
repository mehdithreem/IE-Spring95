import java.io.*;
import java.util.*;
import com.sun.net.httpserver.*;

class User {
	protected Integer ID;
	protected String name;
	protected String lastName;

	private Integer credit;
	protected Map<String, Share> shares;
	
	private List<Order> ords;
	private List<Order> acceptedOrds;
	private List<Order> rejectedOrds;

	public User(Integer _ID, String _name, String _lastName) {
		ID = _ID;
		name = _name;
		lastName = _lastName;

		credit = 0;

		shares = new HashMap<String, Share>();
		ords = new ArrayList<Order>();
		acceptedOrds = new ArrayList<Order>();
		rejectedOrds = new ArrayList<Order>();
	}

	public Integer getID() {
		return ID;
	}

	public void deposit(Integer amount) {
		credit = credit + amount;
	}

	public Boolean withdraw(Integer amount) {
		if (credit < amount)
			return false;
		credit = credit - amount;
		return true;
	}

	public void addOrder(Order newOrd) {
		ords.add(newOrd);
	}

	public void acceptOrder(Order target) {
		ords.remove(target);
		acceptedOrds.add(target);
	}

	public void rejectOrder(Order target) {
		ords.remove(target);
		rejectedOrds.add(target);
	}

	public Boolean hasEnoughShare(Symbol sym, Integer count) {
		Share shr = shares.get(sym.getID());

		if (shr == null) return false;

		if (shr.getQuantity() < count)
			return false;

		return true;
	}

	public void addShare(Symbol sym, Integer count) {
		Share shr = shares.get(sym.getID());

		if (shr == null) {
			shr = new Share(this, sym, 0);
			shares.put(sym.getID(), shr);
		}

		shr.IncQuantity(count);
	}

	public void decShare(Symbol sym, Integer count) {
		Share shr = shares.get(sym.getID());

		if (shr == null) {
			return;
		}

		shr.DecQuantity(count);
		if (shr.getQuantity() == 0) {
			shares.remove(sym.getID());
		}
	}

	public Boolean isAdmin() {
		return false;
	}
}

class Admin extends User {
	private static Admin inst = new Admin(1, "admin", "admin");

	private Admin(Integer _ID, String _name, String _lastName) {
		super(_ID, _name, _lastName);
	}

	public static Admin getInstance(){
		return inst;
	}

	public Boolean isAdmin() {
		return true;
	}

	public Boolean hasEnoughShare(Symbol sym, Integer count) {
		Share shr = shares.get(sym.getID());

		if (shr == null) {
			shr = new Share(this, sym, 0);
			shares.put(sym.getID(), shr);
		}
		shr.IncQuantity(count);

		return true;
	}
}
