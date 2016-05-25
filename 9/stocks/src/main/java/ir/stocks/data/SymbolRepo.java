package ir.stocks.data;

import ir.stocks.domain.Symbol;

import java.sql.*;

public class SymbolRepo {
	private static SymbolRepo repo = null;
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";

	static {
		try {
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
			} catch (ClassNotFoundException ex) {
				System.err.println("Unable to load HSQLDB JDBC driver");
		}
	}

	public static SymbolRepo getInstance() {
		if (repo == null) {
			repo = new SymbolRepo();
		}
		return repo;
	}

	public boolean create(Symbol target) throws SQLException {
		Connection con = DriverManager.getConnection(CONN_STR);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select id from symbol where id = " + String.valueOf(target.getID()));

		if (rs.next()) {
			con.close();
			return false;
		}

//		st.executeUpdate("insert into symbol values (" +
//			String.valueOf(target.getID()) + ",'" +
//			target.getName() +"');"
//			);
		con.close();
		return true;
	}

}