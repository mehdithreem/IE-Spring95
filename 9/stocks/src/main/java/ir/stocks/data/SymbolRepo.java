package ir.stocks.data;

import ir.stocks.domain.Symbol;

import java.sql.*;

public class SymbolRepo {
	private static SymbolRepo repo = null;

	public static SymbolRepo getInstance() {
		if (repo == null) {
			repo = new SymbolRepo();
		}
		return repo;
	}

	public boolean create(Symbol target) throws SQLException {
		Connection con = JDBCUtil.getConnection();
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