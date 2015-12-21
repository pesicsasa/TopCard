package controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DbConnection;
import application.Session;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

/**
 * This class is responsible for controlling access to application by checking
 * if the user can enter.
 * 
 * @author Ben Lukas Dietrich
 */

public class LoginController {

	@FXML
	private Label label;
	@FXML
	private AnchorPane home_page;
	@FXML
	private TextField username_box;
	@FXML
	private Label invalid_label;
	@FXML
	private Button loginButton;

	/**
	 * Handles the login button action.
	 * 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	private void loginButtonAction(ActionEvent event) throws IOException {

		String username = username_box.getText();
		if (isValidCredentials(username)) {
			Parent startseite = FXMLLoader.load(getClass().getResource("/view/startpage.fxml"));
			Scene start_scene = new Scene(startseite);
			Stage start_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			start_stage.hide(); // optional
			start_stage.setScene(start_scene);
			start_stage.show();
		} else {
			username_box.clear();
			invalid_label.setText("invalid login");
		}
	}

	@FXML
	public void onEnter(ActionEvent event) throws IOException {
		initialize(event);
	}

	public void initialize(ActionEvent event) throws IOException {
		String username = username_box.getText();
		if (isValidCredentials(username)) {
			Parent startseite = FXMLLoader.load(getClass().getResource("/view/startpage.fxml"));
			Scene start_scene = new Scene(startseite);
			Stage start_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			start_stage.hide(); // optional
			start_stage.setScene(start_scene);
			start_stage.show();
		} else {
			username_box.clear();
			invalid_label.setText("invalid login");
		}

	}

	/**
	 * @return true if the user credential are correct, otherwise false.
	 * @author Nikola Draskovic Jelcic
	 */

	public boolean isValidCredentials(String username) {
		boolean let_in = false;
		System.out.println("SELECT * FROM topcard.User WHERE username= " + "'" + username_box.getText() + "'");

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnection.open().createStatement();
			rs = stmt.executeQuery("SELECT * FROM GroupB.User WHERE Username= " + "'" + username + "'");
			while (rs.next()) {
				if (rs.getString("Username") != null) {
					String uname = rs.getString("Username");
					System.out.println("Username = " + uname);
					let_in = true;
					User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4));
					System.out.println("userid = " + u.getId());
					Session.setUser(u);
					break;
				}
			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return let_in;

	}

}