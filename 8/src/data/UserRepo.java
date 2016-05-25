import java.util.*;
import java.sql.*;

public class UserRepo {
	private static UserRepo repo = null;
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";

	static {
		try {
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
			} catch (ClassNotFoundException ex) {
				System.err.println("Unable to load HSQLDB JDBC driver");
		}
	}

	public static UserRepo getInstance() {
		if (repo == null) {
			repo = new UserRepo();
		}
		return stocksCore;
	}

	public boolean create(User target) throws SQLException {
		Connection con = DriverManager.getConnection(CONN_STR);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select id from user where id = " + String.valueOf(target.getID()));

		if (resultSet.next()) {
			con.close();
			return false;
		})

		st.executeUpdate("insert into user values (" +
			String.valueOf(target.getID()) + ",'" +
			target.getName() + "','" target.getLastName() +"');"
			);
		con.close();
		return true;
	}

}