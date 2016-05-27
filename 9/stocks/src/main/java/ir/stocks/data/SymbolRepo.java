package ir.stocks.data;

import ir.stocks.domain.Status;
import ir.stocks.domain.Symbol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

		PreparedStatement st = null;
		st = con.prepareStatement("INSERT INTO symbol values (?, ?)");
		st.setString(1, String.valueOf(target.getOwnerid()));
		st.setString(2, target.getId());
		st.executeUpdate();
		
		con.close();
		return true;
	}

	public void rejectRequest(String symbolid) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement( "update symbol set status = '" + Status.REJECTED + "' where id = ?; " );
		pstmt.setString(1, symbolid);
		pstmt.executeUpdate( );
		con.close();
	}
	
	public void acceptRequest(String symbolid) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement( "update symbol set status = '" + Status.ACCEPTED + "' where id = ?; ");
		pstmt.setString(1, symbolid);
		pstmt.executeUpdate( );
		con.close();
	}
	
	public List<String> getNames() throws SQLException {
		List<String> ret = new ArrayList<String>();
		
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select id from symbol;");
		while (rs.next()) {
			ret.add(rs.getString(1));
		}
			
		return ret;
	}

}