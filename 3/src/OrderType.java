import java.io.*;
import java.util.*;

class OrderType {
	private static Set<String> types= new HashSet<String>();
	private String myType;

	public OrderType(String type) {
		myType = type;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof OrderType) || !(o instanceof String))
			return false;

		if (o instanceof String) {
			String str = (String)o;
			if (myType.equals(str))
				return true;
			else
				return false;
		}
		else {
			OrderType c = (OrderType)o;
			if (myType.equals(myType))
				return true;
			else
				return false;
		}
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
