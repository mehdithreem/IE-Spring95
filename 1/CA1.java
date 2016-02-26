import java.io.*;
import java.util.*;

public class CA1 {
	public static void main(String[] args) throws IOException {
		int portNum = Integer.parseInt(args[0]);
		String helperServerIp = args[1];
		int helperServerPort = Integer.parseInt(args[2]);
		Server s = new Server(portNum , helperServerIp , helperServerPort);
		s.start();
	}
}

