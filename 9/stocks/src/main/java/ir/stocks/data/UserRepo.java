package ir.stocks.data;

import ir.stocks.domain.Role;
import ir.stocks.domain.User;

import java.sql.*;

public class UserRepo {
	private static UserRepo repo = null;

	public static UserRepo getRepository() {
		if (repo == null) {
			repo = new UserRepo();
		}
		return repo;
	}

	public void create(User target) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		String exeStr = "insert into user values ('" +
				target.getUsername() + "','" +
				target.getPassword() + "','" +
				target.getName() + "','" +
				target.getLastName() + "','" +
				target.getEmail() + "'," +
				String.valueOf(target.getCredit()) + ");";
		System.out.println(exeStr);	
		st.executeUpdate(exeStr);
		
		exeStr = "insert into user_roles values ('" +
				target.getUsername() + "','" +
				Role.MEMBER.toString().toLowerCase() + "');";
		System.out.println(exeStr);	
		st.executeUpdate(exeStr);
		
		con.close();
	}
	
	public User findByUsername(String username) throws SQLException {
		User retval = null;
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from user where username='" + username + "'");
		if (rs.next()) {
			User fin = new User(username,
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("lastName"),
					rs.getString("email"));
			fin.setCredit(rs.getInt("credit"));
			retval = fin;
		}
		
		con.close();
		return retval;
	}
	
	public Boolean updateUserCredit(String username, Integer newAmount) throws SQLException {
		Boolean retval = true;
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		if (st.executeUpdate("update user set credit = credit + " + String.valueOf(newAmount) +
				" where username = '" + username + "';") == 0);
			retval = false;

		con.close();
		return retval;
	}

}