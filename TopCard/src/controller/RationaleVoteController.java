package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.DbConnection;
import application.Session;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Content;
import model.Module;
import model.RationaleVote;
import model.RequirementCard;
import model.User;

public class RationaleVoteController extends VoteController implements Initializable {

	@FXML
	private Slider preciseSlider;
	@FXML
	private Slider sliderUnderstandble;
	@FXML
	private ChoiceBox<String> traceable_choicebox = new ChoiceBox<String>();
	
	@FXML
	private TextArea rationale_textfield;

	@FXML
	public void saveVote(ActionEvent event) throws IOException {
		RequirementCard selected = StartpageController.getSelectedRc();
		Content ct = ContentController.getLatestContentForRc(selected);
		rationale_textfield.setText(ct.getDescription());
		rationale_textfield.setDisable(true);
		PreparedStatement stmt = null;
		Connection conn = DbConnection.open();
		try {
			String  insertVote = "INSERT INTO Rationale_Votes (Date_Cast, Precise, Understandable, Traceable, User_ID, Content_ID) VALUES (?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertVote);
			stmt.setDate(1, new Date(new java.util.Date().getTime()));
			stmt.setInt(2, (int) preciseSlider.getValue());
			stmt.setInt(3, (int) sliderUnderstandble.getValue());
			stmt.setString(4, traceable_choicebox.getValue());
			stmt.setInt(5, ((User) Session.getUser()).getId());
			stmt.setInt(6, ct.getId()); 
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			DbConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) { // TODO Auto-generated
				e1.printStackTrace();
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Voting successfull");
		alert.setHeaderText("Your Voting was successfull!");
		alert.setContentText(null);

		alert.showAndWait();

	}
	
	public List<RationaleVote>  getAllVotesForContent(Content c)
	{
		List<RationaleVote> l = null;
		try

		{
			l = new ArrayList<RationaleVote>();
			Connection conn = DbConnection.open();
			String query = "SELECT * FROM Rationale_Votes where Content_ID =?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, c.getId());
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				Date dateCast = rs.getDate(2);
				int precise = rs.getInt(3);
				int understandable = rs.getInt(4);
				String traceable = rs.getString(5);
				int userId = rs.getInt(6);
				int contentId = rs.getInt(7);
				RationaleVote vote = new RationaleVote(id, precise, understandable, traceable, dateCast, userId, contentId);
				l.add(vote);
				
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
	
	public void calculateAverageForCriteria(Content c)
	{
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		traceable_choicebox.setItems(FXCollections.observableArrayList("Y", "N" ,"?"));
		traceable_choicebox.getSelectionModel().select(0);
		RequirementCard selected = StartpageController.getSelectedRc();
		Content ct = ContentController.getLatestContentForRc(selected);
		rationale_textfield.setText(ct.getRationale());
		rationale_textfield.setDisable(true);

	}
	

}
