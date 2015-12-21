package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DbConnection;

public class SearchRcController {
	
	public static void findAllForModule(String moduleName)
	{
		int id = ModuleController.getModuleForName(moduleName);
		try
		{
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
		    String query = "SELECT * FROM GroupB.Requirement_Card WHERE Module_ID= " + "'" + id + "'";

		    ResultSet rs = stmt.executeQuery(query);

		    rs.close();
		    stmt.close();
		    DbConnection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void findAllForUserStory(String userStoryName)
	{
		int id = UserStoryController.getUserStoryForName(userStoryName);
		try
		{
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
		    String query = "SELECT * FROM GroupB.Requirement_Card WHERE User_Story_ID= " + "'" + id + "'";

		    ResultSet rs = stmt.executeQuery(query);

		    rs.close();
		    stmt.close();
		    DbConnection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
