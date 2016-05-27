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
		PreparedStatement pstmt = con.prepareStatement( "select id from symbol where id = ?" );
		pstmt.setString( 1, target.getID()); 
		ResultSet rs = pstmt.executeQuery( );

		if (rs.next()) {
			con.close();
			return false;
		}

		PreparedStatement st = null;
		st = con.prepareStatement("INSERT INTO order values (?, ?)");
		st.setString(1, String.valueOf(target.getOwnerid()));
		st.setString(2, target.getId());
		st.executeUpdate();
		
		con.close();
		return true;
	}

	public void rejectRequest(String symbolid) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement( "update symbol set status = status REJECT where id = ?; " );
		pstmt.setString(1, symbolid);
		ResultSet rs = pstmt.executeQuery( );
		con.close();
	}
	
	public void acceptRequest(String symbolid) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement( "update symbol set status = status ACCEPT where id = ?; " );
		pstmt.setString(1, symbolid);
		ResultSet rs = pstmt.executeQuery( );
		con.close();
	}

}