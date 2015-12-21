package application;

import model.User;

/**This class is used to store the user profile after logging into the TopCard System.
 * @author Nikola Draskovic Jelcic
 *
 */

public class Session {

	 
	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Session.user = user;
	}
	 

}