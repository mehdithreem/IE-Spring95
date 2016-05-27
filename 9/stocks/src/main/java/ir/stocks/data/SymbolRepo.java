package ir.stocks.data;

import ir.stocks.domain.DepositRequest;
import ir.stocks.domain.Status;
import ir.stocks.domain.Symbol;
import ir.stocks.domain.User;

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

		st.executeUpdate("insert into symbol values (" +
			String.valueOf(target.getOwnerid()) + ",'" +
			target.getId() +"');"
			);
		
		con.close();
		return true;
	}

	public void rejectRequest(String symbolid) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate("update symbol set status = status + " + "REJECT" + " where id = '" + symbolid + "';");
	}
	
	public void acceptRequest(String symbolid) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate("update symbol set status = status + " + "ACCEPT" + " where id = '" + symbolid + "';");
	}

}