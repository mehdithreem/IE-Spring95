class SharesInfo {
	private String fullName;
	private String name;
	private Integer price;

	SharesInfo(String _fullName, String _name, Integer _price) {
		fullName = _fullName;
		name = _name;
		price = _price;
	}

	public void print() {
		System.out.print(fullName+"("+name+")$"+String.valueOf(price));
	}

	public void println() {
		print();
		System.out.print("\n");
	}

	public String getFullName() {
		return fullName;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}
}

