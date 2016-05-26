package ir.stocks.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class StyleFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println(path);

		if (path.startsWith("/style")) {
			System.out.println("filtered");
		    chain.doFilter(request, response); // Goes to default servlet.
		} else {
			System.out.println("dispathced to " + "/app" + path);
		    request.getRequestDispatcher("/app" + path).forward(request, response);
		}		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
