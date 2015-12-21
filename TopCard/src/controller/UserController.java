package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.DbConnection;
import model.User;

/**
 * Class is responsible for operations on User table from the database
 * 
 * @author Nikola Draskovic Jelcic
 *
 */

public class UserController {

	/**
	 * returns the List of all Users
	 * 
	 * @return List<User>
	 */

	public static List<User> getAllUsers() {

		List<User> l = null;
		try

		{
			l = new ArrayList<User>();
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM User";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				String firsttName = rs.getString(2);
				String lastName = rs.getString(3);
				String username = rs.getString(4);
				String email = rs.getString(5);
				
				User m = new User(id,firsttName,lastName,username,email);
			}

			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return l;
	}

	public static String[] getAllUserNames() {
		List<String> l = new ArrayList<String>();
		try {

			Connection conn = DbConnection.open();

			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM User");

			while (r.next()) {
				String str = r.getString("First_Name") + " " + r.getString("Last_Name"); 
				System.out.println(str);
				l.add(str);
				
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
	 *            of the User
	 * @return id of the User that has the name {@code name}
	 */

	public static int getUserForName(String firstname, String lastname) {
		try {
			Connection conn = DbConnection.open();
			String query = "SELECT * FROM User WHERE First_Name = ? AND Last_Name = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ResultSet rs = ps.executeQuery();
			
			int id = -1;
			while (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			ps.close();
			DbConnection.close();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;

	}

	public static String getUserNameForId(int id) {
		try {
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE User_ID= " + "'" + id + "'");

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

}
