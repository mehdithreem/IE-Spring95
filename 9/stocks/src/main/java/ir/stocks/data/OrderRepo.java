package ir.stocks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ir.stocks.domain.Order;
import ir.stocks.domain.OrderCommand;
import ir.stocks.domain.Status;

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
		
		Integer id = generateID();
		target.setId(id);
		
		
		PreparedStatement st = null;
		st = con.prepareStatement("INSERT INTO orders values (?, ? ,? ,? ,? ,? ,?)");
		st.setLong(1, id);
		st.setString(2, target.getOwner());
		st.setString(3, target.getInstrument());
		st.setLong(4, target.getPrice());
		st.setLong(5, target.getQuantity());
		st.setString(6, target.getCommand().toString());
		st.setString(7, target.getStatus().toString());
		st.executeUpdate();
		
		con.close();
	}
	
	public void delete(Order target) {
		String orderId = String.valueOf(target.getId());
		String query = "delete from orders where orderid = ? ;";
		try {
			Connection con = JDBCUtil.getConnection();			
			PreparedStatement pstmt = con.prepareStatement( query );
			pstmt.setString( 1, orderId); 
			pstmt.executeQuery( );
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateQuantity(Order target) {
		String orderId = String.valueOf(target.getId());
		String query = "update orders set quantity = ? where orderid = ? ;";
		try {
			Connection con = JDBCUtil.getConnection();			
			PreparedStatement pstmt = con.prepareStatement( query );
			pstmt.setInt(1, target.getQuantity());
			pstmt.setString(2, orderId); 
			pstmt.executeQuery();
			
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
			String query = "select * from orders where symbolid = ? and command = ? ;";
			Connection con = JDBCUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement( query );
			pstmt.setString( 1, sym); 
			pstmt.setString( 2, cmd.toString()); 
			ResultSet rs = pstmt.executeQuery( );
			con.close();
			
			while(rs.next()) {
				Order o = new Order(
						rs.getString("ownerid")
						,sym
						,rs.getInt("price")
						,rs.getInt("quantity")
						,OrderCommand.valueOf(rs.getString("command")));
				o.setId(rs.getInt("orderid"));
				o.setStatus(Status.valueOf(rs.getString("status")));
				q.offer(o);
			}
		} catch (SQLException e) {
			return null;
		}

		return q;
	}
	
	public List<Order> getPendings() throws SQLException {
		List<Order> ret = new ArrayList<Order>();
		
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from orders where status = '" + Status.PENDING.toString() + "';");
		while (rs.next()) {
			Order newOrder = new Order(rs.getString("ownerid"),
					rs.getString("symbolid"),
					rs.getInt("price"),
					rs.getInt("quantity"),
					OrderCommand.valueOf(rs.getString("command")));
			
			newOrder.setStatus(Status.valueOf(rs.getString("status")));
			newOrder.setId(rs.getInt("orderid"));
			ret.add(newOrder);
		}
			
		return ret;
	}
}
