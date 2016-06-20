package ir.stocks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import ir.stocks.domain.Transaction;

public class TransactionRepo {
	private static TransactionRepo repo = null;

	public static TransactionRepo getRepository() {
		if (repo == null) {
			repo = new TransactionRepo();
		}
		return repo;
	}
	
	private Integer idgen = (int) LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
	
	private Integer generateID() {
		return idgen++;
	}
	
	public void create(Transaction target){
		try {
			Connection con = JDBCUtil.getConnection();
			PreparedStatement st = null;
			Integer id = generateID();
			target.setId(id);
			
			st = con.prepareStatement("insert into order values (?,?,?,?,?,?,?);");
			st.setInt(1, id);
			st.setString(2, target.getBuyer());
			st.setString(3, target.getSeller());
			st.setString(4, target.getSymbol());
			st.setInt(5, target.getPrice());
			st.setInt(6, target.getQuantity());
			st.setString(7, target.getTime());
			st.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getMostFolan() {
		String retStr = "";
		try {
			Connection con = JDBCUtil.getConnection();
			PreparedStatement st = null;
			
			st = con.prepareStatement("SELECT symbolid FROM TRANSACTION where price = (select MAX(price) from TRANSACTION)");
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				retStr = rs.getString("symbolid");
			}
			
			System.out.println(retStr);
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return retStr;
	}
}
