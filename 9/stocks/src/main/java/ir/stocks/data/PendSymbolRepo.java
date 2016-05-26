package ir.stocks.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ir.stocks.domain.Symbol;

public class PendSymbolRepo {
	private static PendSymbolRepo repo = null;
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";

	static {
		try {
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
			} catch (ClassNotFoundException ex) {
				System.err.println("Unable to load HSQLDB JDBC driver");
		}
	}

	public static PendSymbolRepo getInstance() {
		if (repo == null) {
			repo = new PendSymbolRepo();
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

		st.executeUpdate("insert into user values ('" +
				target.getId() + ")" 
				);
//		st.executeUpdate("insert into symbol values (" +
//			String.valueOf(target.getID()) + ",'" +
//			target.getName() +"');"
//			);
		con.close();
		return true;
	}

}
