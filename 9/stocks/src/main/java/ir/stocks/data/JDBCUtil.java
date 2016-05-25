package ir.stocks.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost/stocks";

	static {
		try {
				Class.forName("org.hsqldb.jdbcDriver");
			} catch (ClassNotFoundException ex) {
				System.err.println("Unable to load HSQL JDBC driver");
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONN_STR);
	}
}
