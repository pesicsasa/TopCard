package controller.login;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.User;

/**
 * @author Maximilian Kostial
 *
 */


public class LoginControllerTest {

	
	@Test
	public void testRightUsername() {
		
		LoginController lc = new LoginController();
		String username = "test";
		
		assertTrue(lc.isValidCredentials(username));  
	}
	
	@Test
	public void testWrongUsername() {
		LoginController lc = new LoginController();
		String username = "doesnotexist"; 
		assertFalse(lc.isValidCredentials(username));
	}
	
	@Test
	public void initializeStartpage()
	{
	}

}
