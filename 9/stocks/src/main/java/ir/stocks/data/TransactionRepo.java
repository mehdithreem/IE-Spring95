package ir.stocks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import ir.stocks.domain.Transaction;

public class TransactionRepo {
	private static TransactionRepo repo = null;

	public static TransactionRepo getRepository() {
		if (repo == null) {
			repo = new TransactionRepo();
		}
		return repo;
	}
	
	private Integer idgen = (int) LocalDate.now().toEpochDay();
	
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
			st.setLong(1, id);
			st.setString(2, target.getBuyer());
			st.setString(3, target.getSeller());
			st.setString(4, target.getSymbol());
			st.setLong(5, target.getPrice());
			st.setLong(6, target.getQuantity());
			st.setString(7, target.getTime());
			st.executeQuery();
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
