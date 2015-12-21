package database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;


public class DatabaseConnectionTest {

	
	@Test
	public void OpenConnectionTest() {
		Connection conn = application.DbConnection.open();
		boolean success = conn!=null;
		assertTrue(success);
	}
	
	@Test
	public void CloseConnectionTest() {
		Connection conn = application.DbConnection.open();
		application.DbConnection.close();
		boolean success = false;
		try {
			success = conn.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(success);
	}

}
