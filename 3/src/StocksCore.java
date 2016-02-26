import java.util.*;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import ir.ramtung.coolserver.*;

class UnknownCommandHandler extends CommandHandler {
	public void execute(PrintWriter out) throws IOException{
		setResCode(404);
		out.println("Unknown Command");
		return;
	}
}


public class StocksCore {
	private Map<Integer, User> users;
	private Map<String, Symbol> symbols;
	private Map<String, Class<? extends Order>> exchangeHandlers;
	
	private static StocksCore stocksCore = new StocksCore();

	private StocksCore(){
		users = new HashMap<Integer,User>();
		symbols = new HashMap<String,Symbol>();
		exchangeHandlers = new HashMap<String, Class<? extends Order>>();

		users.put(1, Admin.getInstance());
	}

	public static StocksCore getInstance(){
		return stocksCore;
	}

	public Boolean addUser(Integer id, String name, String lastName) {
		if (users.putIfAbsent(id,new User(id, name, lastName)) == null)
			return true;
		return false;
	}

	public Symbol addSymbol(String id) {
		Symbol newSym = new Symbol(id);
		symbols.put(id, newSym);
		return newSym;
	}

	public User findUser(Integer id) {
		return users.get(id);
	}

	public Symbol findSymbol(String id) {
		return symbols.get(id);
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

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(9091) , 0);
		HttpHandler unknownHandler = new UnknownCommandHandler();

		server.createContext("/customer/add" , new CustomerAddHandler());
		server.createContext("/customer/deposit" , new CustomerDepositHandler());
		server.createContext("/customer/withdraw" , new CustomerWithdrawHandler());
		server.createContext("/customer" , unknownHandler);

		server.createContext("/order/sell", new OrderSellHandler());
		server.createContext("/order/buy", new OrderBuyHandler());
		server.createContext("/order" , unknownHandler);

		server.createContext("/config/uploadzip", new ConfigHandler());

		server.createContext("/" , unknownHandler);
		server.start();

  	}
}
