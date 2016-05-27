package ir.stocks.domain;

public class Symbol {
	private String id;
	private String ownerid; 
	
	public String getId() {
		return id;
	}

	public String getID() {
		return id;
	}

	public Symbol(String _id , String _ownerid) {
		id = _id;
		ownerid = _ownerid;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
}
