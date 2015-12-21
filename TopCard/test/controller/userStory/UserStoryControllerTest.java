package controller.userStory;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import controller.UserStoryController;
import model.UserStory;


/**
 * @author Maximilian Kostial
 *
 */

public class UserStoryControllerTest {
	
	@Test
	public void testGetAllUserStories() {
		
		List<UserStory> list = UserStoryController.readUserStories();
		boolean success = list!= null;
		assertTrue(success);
		
	}
	
	@Test
	
	public void testGetUserStoryName() {
		
		String userStoryName = "User story 1";
		int id = UserStoryController.getUserStoryForName(userStoryName);
		boolean success = id!= -1;
		assertTrue(success);
		
		
	}

} 
