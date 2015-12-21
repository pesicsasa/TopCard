package controller;

import java.io.IOException; 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DbConnection;
import application.Session;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.*;

/**
 * Class is responsible for operations on Requirement_Card table from the
 * database
 * 
 * @author Sasa Pesic
 *
 */

public class RequirementCardController{

	@FXML
	private TextField requirement_name;
	@FXML
	private TextField requirement_number;
	@FXML
	private ChoiceBox requirement_module;
	@FXML
	private Button add_module;

	@FXML
	private TextArea requirement_description;
	@FXML
	private TextArea requirement_rationale;
	@FXML
	private TextField requirement_source;
	@FXML
	private ChoiceBox requirement_userStory;
	@FXML
	private Button userStory;
	@FXML
	private TextField requirement_sup;

	/**
	 * Inserts the requirement card into the database.
	 * 
	 * @param source:
	 *            field from the Create Requirement Card form
	 * @param moduleId:
	 *            field from the Create Requirement Card form
	 * @param uStoryId:
	 *            field from the Create Requirement Card form
	 * @param userId:
	 *            field from the Create Requirement Card form
	 * @param desc:
	 *            field from the Create Requirement Card form
	 * @param rat:
	 *            field from the Create Requirement Card form
	 * @return rc_ID: id of the requirement card currently inserted
	 */

	public static int insertRcToDB(RequirementCard r, Content c)

	{
		System.out.println("called insert rc");
		PreparedStatement stmt = null;
		Connection conn = DbConnection.open();
		System.out.println(r);

		try {
			String insertRC = "INSERT INTO Requirement_Card"
					+ "(Source, Module_ID, Status_ID, User_Story_ID, User_ID, Supporting_materials, Name) VALUES"
					+ "(?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertRC, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, r.getSource());
			stmt.setInt(2, Integer.parseInt(r.getModule()));
			stmt.setInt(3, 5);
			if (Integer.parseInt(r.getUserStory()) == -1) {
				stmt.setNull(4, java.sql.Types.NULL);
			} else {
				stmt.setInt(4, Integer.parseInt(r.getUserStory()));
			}
			stmt.setInt(5, Integer.parseInt(r.getUser()));
			stmt.setString(6, r.getSupportingMaterials());
			stmt.setString(7, r.getName());
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int rc_ID = keys.getInt(1);
			c.setRc(rc_ID);
			conn.commit();
			stmt.close();
			DbConnection.close();
			ContentController.insertRcContentToDB(c);
			return rc_ID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.rollback();
		} catch (SQLException e1) { // TODO Auto-generated
			e1.printStackTrace();
		}

		return -1;
	}
	
	public static int updateRC(int id, RequirementCard r)
	{
		System.out.println("called insert rc");
		PreparedStatement stmt = null;
		Connection conn = DbConnection.open();
		System.out.println(r);

		try {
			String insertRC = "UPDATE Requirement_Card SET "
					+ "Source=?, Module_ID=?, Status_ID=?, User_Story_ID=?, User_ID=?, Supporting_materials=?, Name=? "
					+ "WHERE Requirement_Card_ID = ?";
			stmt = conn.prepareStatement(insertRC, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, r.getSource());
			stmt.setInt(2, Integer.parseInt(r.getModule()));
			stmt.setInt(3, Integer.parseInt(r.getStatus()));
			if (Integer.parseInt(r.getUserStory()) == -1) {
				stmt.setNull(4, java.sql.Types.NULL);
			} else {
				stmt.setInt(4, Integer.parseInt(r.getUserStory()));
			}
			stmt.setInt(5, Integer.parseInt(r.getUser()));
			stmt.setString(6, r.getSupportingMaterials());
			stmt.setString(7, r.getName());
			stmt.setInt(8, id);
			int success = stmt.executeUpdate();
			conn.commit();
			stmt.close();
			DbConnection.close();
			return success;

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.rollback();
		} catch (SQLException e1) { // TODO Auto-generated
			e1.printStackTrace();
		}

		return -1;
		
	}

	/**
	 * Insert the requirement cards` content into the database
	 * 
	 * @param d
	 * @param desc:
	 *            field from the Create Requirement Card form
	 * @param rat:
	 *            field from the Create Requirement Card form
	 * @param major:
	 *            automatically filled: 0
	 * @param minor:
	 *            automatically filled: 1
	 * @param rcId:
	 *            passed from public int insertRcToDB(...) "return ct_ID: id of
	 *            the requirement card content currently inserted
	 */

	/**
	 * Returns the Create new element followed by all the RC of the current user
	 */

	public static ObservableList<RequirementCard> getRcDataByCurrentUser() {
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();
		try {
			Connection conn = DbConnection.open();
			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery(
					" SELECT * FROM GroupB.Requirement_Card WHERE User_ID=" + "'" + Session.getUser().getId() + "'");

			data.add(new RequirementCard("", "", "", "", "", "", "Create new"));
			while (r.next()) {
				RequirementCard rc = new RequirementCard(r.getString("Module_ID"), r.getString("Source"),
						r.getString("Status_ID"), r.getString("User_ID"), r.getString("Supporting_materials"),
						r.getString("User_Story_ID"), r.getString("Name"));
				rc.setId(r.getInt("Requirement_Card_ID"));
				data.add(rc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.close();
		return data;

	}

	/**
	 * Returns all the RC that have put up to vote status
	 */

	public static ObservableList<RequirementCard> getVotableRcData() {
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();
		try {
			Connection conn = DbConnection.open();
			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery(" SELECT * FROM GroupB.Requirement_Card WHERE Status_ID= '4'");

			while (r.next()) {

				RequirementCard rc = new RequirementCard(r.getString("Module_ID"), r.getString("Source"),
						r.getString("Status_ID"), r.getString("User_ID"), r.getString("Supporting_materials"),
						r.getString("User_Story_ID"), r.getString("Name"));
				rc.setId(r.getInt("Requirement_Card_ID"));
				data.add(rc);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.close();
		return data;

	}
	
	public static ObservableList<RequirementCard> getAllRcData() {
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();
		try {
			Connection conn = DbConnection.open();
			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery(" SELECT * FROM GroupB.Requirement_Card");

			while (r.next()) {

				RequirementCard rc = new RequirementCard(r.getString("Module_ID"), r.getString("Source"),
						r.getString("Status_ID"), r.getString("User_ID"), r.getString("Supporting_materials"),
						r.getString("User_Story_ID"), r.getString("Name"));
				rc.setId(r.getInt("Requirement_Card_ID"));
				data.add(rc);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.close();
		return data;

	}

	public static ObservableList<RequirementCard> getAllVotableCardsForModuleId(int id) {
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();
		try {
			Connection conn = DbConnection.open();
			String query = "SELECT * FROM Requirement_Card WHERE Module_ID= ? AND Status_ID =?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, 4);
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				RequirementCard rc = new RequirementCard(r.getString("Module_ID"), r.getString("Source"),
						r.getString("Status_ID"), r.getString("User_ID"), r.getString("Supporting_materials"),
						r.getString("User_Story_ID"), r.getString("Name"));
				rc.setId(r.getInt("Requirement_Card_ID"));
				data.add(rc);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.close();
		return data;
	}

	public static ObservableList<RequirementCard> getAllVotableCardsForUserId(int id) {
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();
		try {
			Connection conn = DbConnection.open();
			String query = "SELECT * FROM Requirement_Card WHERE User_ID= ? AND Status_ID=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, 4);
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				RequirementCard rc = new RequirementCard(r.getString("Module_ID"), r.getString("Source"),
						r.getString("Status_ID"), r.getString("User_ID"), r.getString("Supporting_materials"),
						r.getString("User_Story_ID"), r.getString("Name"));
				rc.setId(r.getInt("Requirement_Card_ID"));
				data.add(rc);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.close();
		return data;
	}

	public static ObservableList<RequirementCard> getAllVotableCardsForUserAndModuleId(int userId, int moduleId) {
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();
		try {
			Connection conn = DbConnection.open();
			String query = "SELECT * FROM Requirement_Card WHERE User_ID= ? AND Module_ID = ? AND Status_ID = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setInt(2, moduleId);
			ps.setInt(3, 4);
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				RequirementCard rc = new RequirementCard(r.getString("Module_ID"), r.getString("Source"),
						r.getString("Status_ID"), r.getString("User_ID"), r.getString("Supporting_materials"),
						r.getString("User_Story_ID"), r.getString("Name"));
				rc.setId(r.getInt("Requirement_Card_ID"));
				data.add(rc);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.close();
		return data;
	}

	public static int putUpToVote(RequirementCard r) {
		int success = -1;
		Connection conn = DbConnection.open();
		try {
			String query = "update Requirement_Card set Status_ID = ? where Requirement_Card_ID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, 4);
			preparedStmt.setInt(2, r.getId());
			success = preparedStmt.executeUpdate();
			conn.commit();
			conn.close();
			preparedStmt.close();
			DbConnection.close();
			Content workingCopy = ContentController.getWorkingCopy(r);
			Content latestContent = ContentController.getLatestContentForRc(r);
			latestContent.setMinor(latestContent.getMinor()+1);
			latestContent.setDate(new Date(new java.util.Date().getTime()));
			latestContent.setDescription(workingCopy.getDescription());
			latestContent.setRationale(workingCopy.getRationale());
			ContentController.insertRcContentToDB(latestContent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return success;

	}

	public static int freeze(RequirementCard r) {
		int success = -1;
		Connection conn = DbConnection.open();
		try {
			Statement s = conn.createStatement();
			String query = "update Requirement_Card set Status_ID = ? where Requirement_Card_ID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, 1);
			preparedStmt.setInt(2, r.getId());
			success = preparedStmt.executeUpdate();
			conn.commit();
			DbConnection.close();
			
			Content latestContent = ContentController.getLatestContentForRc(r);
			latestContent.setDate(new Date(new java.util.Date().getTime()));
			ContentController.insertRcContentToDB(latestContent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return success;

	}
	
	public static int createNextMajorVersion(RequirementCard r) {
		int success = -1;
		Connection conn = DbConnection.open();
		try {
			Statement s = conn.createStatement();
			String query = "update Requirement_Card set Status_ID = ? where Requirement_Card_ID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, 5);
			preparedStmt.setInt(2, r.getId());
			success = preparedStmt.executeUpdate();
			conn.commit();
			DbConnection.close();
			
			Content latestContent = ContentController.getLatestContentForRc(r);
			latestContent.setMajor(latestContent.getMajor()+1);
			latestContent.setMinor(0);
			latestContent.setDate(new Date(new java.util.Date().getTime()));
			ContentController.insertRcContentToDB(latestContent);
			Content workingCopy = ContentController.getWorkingCopy(r);
			workingCopy.setDescription(latestContent.getDescription());
			workingCopy.setRationale(latestContent.getRationale());
			workingCopy.setDate(latestContent.getDate());
			workingCopy.setMajor(0);
			workingCopy.setMinor(0);
			ContentController.updateContent(workingCopy, r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return success;

	}
	

}