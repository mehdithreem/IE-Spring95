import java.net.*;
import java.io.*;
import java.util.*;

public class HelperServerConnection {
	private String hserverName;
	private Integer hserveerPort;

	HelperServerConnection(String _hserverName, Integer _hserverPort) {
		hserverName = _hserverName;
		hserveerPort = _hserverPort;
	}

	public ArrayList<SharesInfo> GETALL() throws IOException {
		ArrayList<SharesInfo> retVal = new ArrayList<SharesInfo>();
		Socket s = connect();
		if ( s == null ) return retVal;

		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter socketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

			socketOut.println("GET ALL\n");
			String info = socketIn.readLine();

			StringTokenizer mainTokenizer = new StringTokenizer(info, ";");
			while (mainTokenizer.hasMoreTokens()) {
				StringTokenizer secTokenizer = new StringTokenizer(mainTokenizer.nextToken(), "()$");

				retVal.add(new SharesInfo(secTokenizer.nextToken(), secTokenizer.nextToken(), Integer.valueOf(secTokenizer.nextToken())));
			}
		} finally {
			s.close();
		}

		return retVal;
	}

	public Integer GETPrice(String symbolName) throws IOException {
		// returns -1 if symbol name not found
		// returns 0 if connection failed

		Integer retVal = 0;
		Socket s = connect();
		if ( s == null ) return retVal;

		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter socketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

			socketOut.println("GET " + symbolName + "\n");
			String info = socketIn.readLine();

			if (info.equals("Unknown symbol \""+symbolName+"\"")) {
				retVal = -1;
			} else {
				StringTokenizer tokenzier = new StringTokenizer(info, "$");
				tokenzier.nextToken();
				retVal = Integer.valueOf(tokenzier.nextToken());
			}
		} finally {
			s.close();
		}

		return retVal;
	}

	private Socket connect() throws IOException {
		Socket s = null;

		try {
			s = new Socket(hserverName, hserveerPort);
		} catch (Exception e) {
			System.out.println("Error connection to helper server.");
			throw e;
		}

		return s;
	}
}

