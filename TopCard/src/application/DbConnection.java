package application;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**This class provides the database connection for the TopCard MySQL database which is part of the TopCard System.
* 
* @author Stanislav Lokhtin
*
*/

public class DbConnection {
	
	static Connection conn = null;

	
	/**
	 * This method opens a new database connection (conn) which allows executing queries
	 * @return conn
	 */

  public static Connection open() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {

			conn = DriverManager.getConnection("jdbc:mysql://localhost/groupb","root", "root");
			conn.setAutoCommit(false);

			//System.out.println("Connection opened!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
     /**
	 * This method closes the database connection
	 * @author Stanislav Lokhtin
	 */
	 
	public static void close()
	{
		try {
			conn.close();
			//System.out.println("Connection closed!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
