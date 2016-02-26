import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	private int portNum;
	private String helperServerIp;
	private int helperServerPort;

	Server(int _portNum ,String _helperServerIp ,int _helperServerPort){
		portNum = _portNum;
		helperServerIp = _helperServerIp;
		helperServerPort = _helperServerPort;
	}
	
	public void start() throws IOException{
		HelperServerConnection hsc = new HelperServerConnection(helperServerIp , helperServerPort );
		ServerSocket s = new ServerSocket(portNum);
		System.out.println("Server started: " + s);
		try{
			while(true){
				System.out.println("Waiting for client ...");
				Socket socket = s.accept();
				try{
					System.out.println("Server is conneected to: " + socket);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

					 while (!socket.isClosed()) {
						Order order = new Order(in.readLine());
						if(order.getType().equals("GET"))
							order.edit(hsc.GETALL());
						else if(order.getType().equals("SELL") || order.getType().equals("BUY") )
							order.checkValidatePrice(hsc.GETPrice(order.getShareName()));
						else
							out.println("Invalid request!");

						System.out.println("Response sent.");
						out.println(order.getResponse());
						out.flush();
					}
				} catch (Exception e) {
					System.out.println("Execption caught.\n" + e.getMessage());
				} finally {
					System.out.println("Disconnecting ...");
					socket.close();
				}
			}
		}finally{
			s.close();
		}
	}
}
