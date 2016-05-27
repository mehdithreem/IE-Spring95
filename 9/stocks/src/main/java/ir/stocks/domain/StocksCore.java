package ir.stocks.domain;

import java.util.*;
import java.io.*;

public class StocksCore {
	// private Map<Integer, User> users;
	private Map<String, Symbol> symbols;
	private Map<String, Class<? extends Order>> exchangeHandlers;
	private StringWriter csvWriter;
	
	private static StocksCore stocksCore = null;

	private StocksCore() {
		// users = new HashMap<Integer,User>();
		symbols = new HashMap<String,Symbol>();
		exchangeHandlers = new HashMap<String, Class<? extends Order>>();
		csvWriter = new StringWriter();
		csvWriter.write("Buyer, Seller, instrument, type of trade, quantity, Buyer Remained Money, Seller Current Money\n");

		// users.put(1, Admin.getInstance());
	}

	public static StocksCore getInstance() {
		if (stocksCore == null) {
			stocksCore = new StocksCore();
		}
		return stocksCore;
	}

	public void appendToWriter(String newStr) {
		csvWriter.write(newStr);
	}

	public String getCSV() {
		return csvWriter.toString();
	}

	public Symbol addSymbol(String id , String usrid) {
		Symbol newSym = new Symbol(id , usrid);
		symbols.put(id, newSym);
		return newSym;
	}

	public Symbol findSymbol(String id) {
		return symbols.get(id);
	}

	public Collection<Symbol> getAllSymbols() {
		return symbols.values();
	}

	public Set<String> getExchangeTypes() {
		return exchangeHandlers.keySet();
	}

	public void loadExchanges(String serviceRootFolder) {
		File root = new File(serviceRootFolder);
		String[] classes = root.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("_Order.class");
			}
		});
		for (String className : classes) {
			String exchangeType = className.substring(0, className.indexOf("_Order.class"));
			String exchangeClass = className.substring(0, className.indexOf(".class"));

			if (exchangeHandlers.containsKey(exchangeType)) {
				System.out.println("Class already available: " + exchangeClass);
				continue;
			}
				
			try {
				Class<? extends Order> clazz = Class.forName(exchangeClass).asSubclass(Order.class);
				exchangeHandlers.put(exchangeType, clazz);
				OrderType.addType(exchangeType);
				System.out.println("Class loaded: " + exchangeClass);
			} catch (Exception ex) {
				System.err.println("Unable to load class: " + exchangeClass);
				ex.printStackTrace();
			}
		}
	}

	public Order getOrderNewInstance(String type) {
		Order retOrder = null;
		try {
			retOrder = (Order) exchangeHandlers.get(type).newInstance();
		} catch (Exception e) {
			System.out.println("ERORR: cannot instantiate " + type + " class.");
			return null;
		}
		return retOrder;
	}
}
