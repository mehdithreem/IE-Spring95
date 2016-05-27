package ir.stocks.data;

import ir.stocks.domain.Role;
import ir.stocks.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Map<Role,Boolean> getUserRolesMap(String username) throws SQLException {
		Map<Role, Boolean> ret = new HashMap<Role, Boolean>();
		for(Role r : Role.values()) {
			ret.put(r, false);
		}
		
		for(Role r : getUserRoles(username)) {
			ret.replace(r, true);
		}
		
		return ret;
	}
	
	public List<Role> getUserRoles(String username) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		List<Role> ret = new ArrayList<Role>();		
		ResultSet rs = st.executeQuery("select role from user_roles where username='" + username + "'");
		while (rs.next()) {
			ret.add(Role.valueOf(rs.getString(1).toUpperCase()));
		}
		
		con.close();
		return ret;
	}
	
	public void setUserRoles(String username, List<Role> roles) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		
		st.executeUpdate("delete from user_roles where username='" + username + "'");
		
		for(Role r : roles) {
			st.executeUpdate("insert into user_roles values('" + username + "','" + r.toString().toLowerCase() + "');");
		}
		
		con.close();
	}
	
	public List<User> getAll()  throws SQLException {
		List<User> ret = new ArrayList<User>();
		
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from user");
		while (rs.next()) {
			User newUser = new User(
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("lastName"),
					rs.getString("email"));
			newUser.setCredit(rs.getInt("credit"));
			ret.add(newUser);
		}
			
		return ret;
	}

}