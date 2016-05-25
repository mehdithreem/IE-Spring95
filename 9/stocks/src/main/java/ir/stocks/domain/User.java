package ir.stocks.domain;

public class User {
	private String username;
	private String password;
	
	private String name;
	private String lastName;
	private String email;

	private Integer credit;
	
	// protected Map<String, Share> shares;
	
	// private List<Order> ords;
	// private List<Order> acceptedOrds;
	// private List<Order> rejectedOrds;

	public User(String username, String password, String name, String lastName, String email) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.credit = 0;

		// shares = new HashMap<String, Share>();
		// ords = new ArrayList<Order>();
		// acceptedOrds = new ArrayList<Order>();
		// rejectedOrds = new ArrayList<Order>();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Integer getCredit() {
		return credit;
	}
	
	public void setCredit(Integer credit) {
		this.credit = credit;
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

//	public Boolean hasEnoughShare(Symbol sym, Integer count) {
//		Share shr = shares.get(sym.getID());
//
//		if (shr == null) return false;
//
//		if (shr.getQuantity() < count)
//			return false;
//
//		return true;
//	}

//	public void addShare(Symbol sym, Integer count) {
//		Share shr = shares.get(sym.getID());
//
//		if (shr == null) {
//			shr = new Share(this, sym, 0);
//			shares.put(sym.getID(), shr);
//		}
//
//		shr.IncQuantity(count);
//	}

//	public void decShare(Symbol sym, Integer count) {
//		Share shr = shares.get(sym.getID());
//
//		if (shr == null) {
//			return;
//		}
//
//		shr.DecQuantity(count);
//		if (shr.getQuantity() == 0) {
//			shares.remove(sym.getID());
//		}
//	}
}

//class Admin extends User {
//	private static Admin inst = new Admin(1, "admin", "admin");
//
//	private Admin(Integer _ID, String _name, String _lastName) {
//		super(_ID, _name, _lastName);
//	}
//
//	public static Admin getInstance(){
//		return inst;
//	}
//
//	public Boolean isAdmin() {
//		return true;
//	}
//
//	public Boolean hasEnoughShare(Symbol sym, Integer count) {
//		Share shr = shares.get(sym.getID());
//
//		if (shr == null) {
//			shr = new Share(this, sym, 0);
//			shares.put(sym.getID(), shr);
//		}
//		shr.IncQuantity(count);
//
//		return true;
//	}
//}
