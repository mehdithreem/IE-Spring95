package ir.stocks.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import ir.stocks.domain.Order;
import ir.stocks.domain.OrderCommand;

public class OrderRepo {
	private static OrderRepo repo = null;

	public static OrderRepo getRepository() {
		if (repo == null) {
			repo = new OrderRepo();
		}
		return repo;
	}
	private static Integer idgen = (int) LocalDate.now().toEpochDay();
	
	private Integer generateID() {
		return idgen++;
	}
	
	public void create(Order target) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		Integer id = generateID();
		target.setId(id);
		
		st.executeUpdate("insert into order values (" +
				id + "','" +
				target.getOwner() + ",'" +
				target.getInstrument() + "'," +  
				target.getPrice() + "," +
				target.getQuantity() + ",'" +
				target.getCommand().toString() + "','" +
				target.getStatus().toString() +"');");
		
		con.close();
	}
	
	public void delete(Order target) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			st.executeUpdate("delete from order where orderid = " + String.valueOf(target.getId()) + ";");
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Queue<Order> getSymbolQueue(String sym, OrderCommand cmd) {
		Queue<Order> q = null;
		if (cmd.equals(OrderCommand.BUY)) {
			q = new PriorityQueue<Order>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o1.getPrice().equals(o2.getPrice()) ? 0 : o1.getPrice() < o2.getPrice() ? 1 : -1;
				}
			});
		} else if  (cmd.equals(OrderCommand.SELL)) {
			q = new PriorityQueue<Order>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o1.getPrice().equals(o2.getPrice()) ? 0 : o1.getPrice() < o2.getPrice() ? -1 : 1;
				}
			});
		} else {
			return q;
		}
		
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery("select * from order where symbolid = '" + sym + "' and command = '" + cmd.toString() + "';");
			
			con.close();
			
			while(rs.next()) {
				Order o = new Order(
						rs.getString("ownerid")
						,sym
						,rs.getInt("price")
						,rs.getInt("quantity")
						,OrderCommand.valueOf(rs.getString("command")));
				o.setId(rs.getInt("orderid"));
				q.offer(o);
			}
		} catch (SQLException e) {
			return null;
		}

		return q;
	}
}
