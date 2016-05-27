package ir.stocks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		
		PreparedStatement st = null;
		st = con.prepareStatement("INSERT INTO share values (?, ? ,? )");
		st.setString(1, target.getOwner());
		st.setString(2, target.getSymbol());
		st.setLong(3, target.getQuantity());
		st.executeUpdate();
		
		con.close();
	}
	
	public Share getShare(String username, String sym) throws SQLException {
		Share ret = null;
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement( "select * from share where symbolid = ? and userid = ? ;");
		pstmt.setString(1, sym);
		pstmt.setString(2, username);
		ResultSet rs = pstmt.executeQuery( );
		
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
			PreparedStatement pstmt = con.prepareStatement( "update share set quantity = quantity + ? where symbolid = ? and userid = ? ;");
			pstmt.setString(1, String.valueOf(count));
			pstmt.setString(2, sym);
			pstmt.setString(3, username);
			
			
			if (pstmt.executeUpdate() < 1) {
				
				PreparedStatement pstmt2 = con.prepareStatement( "insert into share values ( ?, ?, ? );");
				pstmt2.setString(1, username);
				pstmt2.setString(2, sym);
				pstmt2.setString(3, String.valueOf(count));
				pstmt2.executeUpdate();
			
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
