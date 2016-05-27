package ir.stocks.data;

import java.sql.Connection;
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
			Statement st = con.createStatement();
			
			Integer id = generateID();
			target.setId(id);
			
			st.executeUpdate("insert into order values (" +
					id + ",'" +
					target.getBuyer() + "','" +
					target.getSeller() + "','" +  
					target.getSymbol() + "'," +
					target.getPrice() + "," +
					target.getQuantity() + ",'" +
					target.getTime() +"');");
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
