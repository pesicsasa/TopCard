package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Module;

/**
 * Class is responsible for operations on Module table from the database
 * 
 * @author Nikola Draskovic Jelcic
 *
 */

public class ModuleController {
	
	/**
	 * returns the List of all Modules
	 * 
	 * @return List<Module>
	 */

	public static List<Module> readModules() {

		List<Module> l = null;
		try

		{
			l = new ArrayList<Module>();
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Module";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				String desc = rs.getString(2);
				String name = rs.getString(3);
				Module m = new Module(name, desc, id);
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

	public static String[] getAllModuleNames() {
		List<String> l = new ArrayList<String>();
		try {

			Connection conn = DbConnection.open();

			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery(" SELECT * FROM Module");

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
	 *            of the Module
	 * @return id of the Module that has the name {@code name}
	 */

	public static int getModuleForName(String name) {
		try {
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM GroupB.Module WHERE Name= " + "'" + name + "'");

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

	public static String getModuleNameForId(int id) {
		try {
			Connection conn = DbConnection.open();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Module WHERE Module_ID= " + "'" + id + "'");

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
	

	public static int insertModule(String name, String description) {

		Connection conn = DbConnection.open();
		try {
			String insertModule = "INSERT INTO Module" + "(Description, Name) VALUES" + "(?,?)";
			PreparedStatement stmt = conn.prepareStatement(insertModule, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, description);
			stmt.setString(2, name);
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int module_ID = keys.getInt(1);
			conn.commit();
			stmt.close();
			DbConnection.close();
			return module_ID;
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
