import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
	private static final String url="jdbc:mysql://localhost:3306/Desktop_Banking";
	private static final String user="root";
	private static final String pass="Your Password";
	
	public static Connection make_connection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, user, pass);
	}
}