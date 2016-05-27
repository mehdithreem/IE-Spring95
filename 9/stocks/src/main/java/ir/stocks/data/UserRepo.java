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
		PreparedStatement st = null;
		st = con.prepareStatement("INSERT INTO user values (?, ? ,? ,? ,? ,? )");
		st.setString(1, target.getUsername());
		st.setString(2, target.getPassword());
		st.setString(3, target.getName());
		st.setString(4, target.getLastName());
		st.setString(5, target.getEmail());
		st.setString(6, String.valueOf(target.getCredit()));
		st.executeUpdate();
		
		
		st = con.prepareStatement("INSERT INTO user_roles values (?, ? )");
		st.setString(1, target.getUsername());
		st.setString(2, Role.MEMBER.toString().toLowerCase());
		st.executeUpdate();
		
		con.close();
	}
	
	public User findByUsername(String username) throws SQLException {
		User retval = null;
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement( "select * from user where username= ? ;" );
		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery( );
		
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
	
	public Boolean depositUserCredit(String username, Integer newAmount) {
		try {
			Boolean retval = true;
			Connection con = JDBCUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement( "update user set credit = credit + ? where username = ? ;" );
			pstmt.setString(1, String.valueOf(newAmount));
			pstmt.setString(2, username );
			
			if (pstmt.executeUpdate() == 0);
				retval = false;
	
			con.close();
			return retval;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
		PreparedStatement pstmt = con.prepareStatement("select role from user_roles where username= ? ;");
		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery( );
		while (rs.next()) {
			ret.add(Role.valueOf(rs.getString(1).toUpperCase()));
		}
		
		con.close();
		return ret;
	}
	
	public void setUserRoles(String username, List<Role> roles) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("delete from user_roles where username= ? ;");
		pstmt.setString(1, username);
		pstmt.executeQuery( );
		
		for(Role r : roles) {
			pstmt = con.prepareStatement("insert into user_roles values(?, ?);");
			pstmt.setString(1, username);
			pstmt.setString(2, r.toString().toLowerCase());
			pstmt.executeUpdate();
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