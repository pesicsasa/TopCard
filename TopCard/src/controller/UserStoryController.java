package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.DbConnection;
import model.Module;
import model.UserStory;

/**
 * Class implements methods for working with User_Story table from the database.
 * 
 * @author Stanislav Lokhtin
 *
 */

public class UserStoryController {

	/**
	 * 
	 * @return list of UserStory objects that are currently in the database
	 */

	public static List<UserStory> readUserStories() {
		List<UserStory> l = null;
		try {
			l = new ArrayList<UserStory>();
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Module";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				String desc = rs.getString(2);
				String name = rs.getString(3);
				UserStory m = new UserStory(name, desc, id);

			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return l;
	}

	public static String[] getUserStoryNames() {
		List<String> l = new ArrayList<String>();
		try {

			Connection conn = DbConnection.open();

			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery(" SELECT * FROM User_Story");

			int i = 0;
			while (r.next()) {
				i++;
				l.add(r.getString("Name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		DbConnection.close();
		String[] names = new String[l.size()];
		return l.toArray(names);
	}

	/**
	 * @param name
	 *            of the User Story
	 * @return id of the User Story that has the name {@code name}
	 */

	public static int getUserStoryForName(String name) {
		try {
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM GroupB.User_Story WHERE Name= " + "'" + name + "'");

			int id = -1;
			while (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			DbConnection.close();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;

	}

	public static String getUserStoryNameForId(int id) {
		try {
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User_Story WHERE User_Story_ID= " + "'" + id + "'");

			String name = "";
			while (rs.next()) {
				name = rs.getString("Name");
			}
			rs.close();
			stmt.close();
			DbConnection.close();
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int insertUserStory(String name, String description) {

		Connection conn = DbConnection.open();
		try {
			String insertModule = "INSERT INTO User_Story" + "(Description, Name) VALUES" + "(?,?)";
			PreparedStatement stmt = conn.prepareStatement(insertModule, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, description);
			stmt.setString(2, name);
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int us_ID = keys.getInt(1);
			conn.commit();
			stmt.close();
			DbConnection.close();
			return us_ID;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return -1;
	}

}