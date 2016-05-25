package ir.stocks.domain;

import java.util.*;

public class OrderType {
	private static Set<String> types= new HashSet<String>();
	private String myType;

	private OrderType(String type) {
		myType = type;
	}

	public boolean equals(Object o) {
		if (o == null || (!(o instanceof OrderType) && !(o instanceof CharSequence))){
			return false;
		}

		if (o instanceof CharSequence) {
			String str = (String)o;
			if (myType.equals(str))
				return true;
			else
				return false;
		}
		else {
			OrderType c = (OrderType)o;
			if (myType.equals(c.myType))
				return true;
			else
				return false;
		}
	}

	public String toString() {
		return myType;
	}

	public static OrderType valueOf(String input) throws IllegalArgumentException {
		if (types.contains(input))
			return new OrderType(input);
		else
			throw new IllegalArgumentException();
	}

	public static Boolean addType(String newType) {
		if (types.contains(newType))
			return false;
		else
			types.add(newType);

		return true;
	}
}
