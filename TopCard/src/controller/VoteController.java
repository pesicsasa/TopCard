package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * 
 * @author Maximilian Kostial
 *
 */
public abstract class VoteController {

	/**
	 * Method handles the vote button so that the vote window is opened and the
	 * startpage window is closed
	 * 
	 * @param event
	 */
	public void voteButtonHandler(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Voting successfull");
		alert.setHeaderText("Your Voting was successfull!");
		alert.setContentText(null);

		alert.showAndWait();

		(((Node) event.getSource()).getScene()).getWindow().hide();

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/view/startpage.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Vote");
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Method handles the cancel button so that the vote window is closed and
	 * the startpage window is opened after pressing the button
	 * 
	 * @param event
	 */
	public void cancelButtonHandler(ActionEvent event) {

		(((Node) event.getSource()).getScene()).getWindow().hide();

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/startpage.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Vote");
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
