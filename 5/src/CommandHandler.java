import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class CommandHandler extends HttpServlet{
	private Integer responseCode;
	
	private void extractParams(String queryString) {
		params = new LinkedHashMap<String, String>();
		if (queryString == null || queryString.equals(""))
			return;
		String[] paramPairs = queryString.split("\\&");
		for (String pp : paramPairs)
			params.put(pp.substring(0, pp.indexOf('=')), pp.substring(pp.indexOf('=') + 1));
	}

	public void setResCode(Integer res){
		responseCode = res;
	}
	public void handle(HttpExchange t) throws IOException {
		extractParams(t.getRequestURI().getQuery());

		StringWriter sw = new StringWriter();
		execute(new PrintWriter(sw, true));
		byte[] result = sw.toString().getBytes();
		t.sendResponseHeaders(responseCode, result.length);
		Headers headers = t.getResponseHeaders();
		headers.add("Date", Calendar.getInstance().getTime().toString());
		headers.add("Content-Type", "text/plain");
		OutputStream os = t.getResponseBody();
		os.write(result);
		os.close();
	}
}
