package ir.stocks.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ir.stocks.domain.Share;

public class ShareRepo {
	private static ShareRepo repo = null;

	public static ShareRepo getRepository() {
		if (repo == null) {
			repo = new ShareRepo();
		}
		return repo;
	}
	
	public void create(Share target) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();

		st.executeUpdate("insert into share values ('" +
				target.getOwner() + "','" +
				target.getSymbol() + "'," +
				target.getQuantity() + ");"
				);
		
		con.close();
	}
	
	public Share getShare(String username, String sym) throws SQLException {
		Share ret = null;
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select * from share where symbolid = '" + sym + 
				"' and userid = '" + username + "'");

		con.close();
		if (rs.next()) {
			ret = new Share(
					username,
					rs.getString("symbolid"),
					rs.getInt("quantity")
					);
		}
		
		return ret;
	}
	
	public void addShare(String username, String sym, Integer count) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			if (st.executeUpdate("update share set quantity = quantity + " +
					String.valueOf(count) + " where symbolid = '" + sym + 
					"' and userid = '" + username + "';") < 1) {
				
				st.executeUpdate("insert into share values ('" +
						username + "','" +
						sym + "'," +
						String.valueOf(count) + ");"
						);
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
