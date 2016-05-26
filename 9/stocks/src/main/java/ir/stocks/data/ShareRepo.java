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
	
	public boolean create(Share target) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select id from share where symbolid = " + target.getSymbol().getId() + 
																" and userid = " + target.getOwner().getUsername());

		if (rs.next()) {
			con.close();
			return false;
		}

		st.executeUpdate("insert into share values ('" +
				target.getOwner().getUsername() + "','" +
				target.getSymbol().getId() + "','" +
				target.getQuantity() + ");"
				);
		
		con.close();
		return true;
	}
}
