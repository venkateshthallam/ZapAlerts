package zapAlerts;


import java.sql.*;
public class DBcon {

	public static Connection connect() throws ClassNotFoundException, SQLException{
	
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/zapalerts";
		String username= "root"; 
		String password= "root";
		Connection conn = null;
		conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
}