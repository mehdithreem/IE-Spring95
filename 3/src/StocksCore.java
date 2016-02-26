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
	
	private static StocksCore stocksCore = new StocksCore();

	private StocksCore(){
		users = new HashMap<Integer,User>();

		users.put(1, Admin.getInstance());

		symbols = new HashMap<String,Symbol>();
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

		server.createContext("/" , unknownHandler);
		server.start();

  	}
}
