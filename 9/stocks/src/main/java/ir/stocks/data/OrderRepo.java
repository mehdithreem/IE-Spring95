package ir.stocks.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ir.stocks.domain.Order;
import ir.stocks.domain.Status;

public class OrderRepo {
	private static OrderRepo repo = null;

	public static OrderRepo getRepository() {
		if (repo == null) {
			repo = new OrderRepo();
		}
		return repo;
	}
	private static Integer idgen = 0;
	
	private Integer generateID() {
		return idgen++;
	}
	
	public boolean create(Order target) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		st.executeUpdate("insert into order values ('" +
				generateID() + "','" +
				target.getOwner().getUsername() + "','" +
				target.getInstrument().getId() + "','" +  
				target.getPrice() + "','" +
				target.getQuantity() + "','" +
				"GTC" + "','" + 
				Status.PENDING + "','" +
				'1' + ");"
				);
		
		con.close();
		return true;
	}
}
