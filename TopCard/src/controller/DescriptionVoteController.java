package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.Date;

import application.DbConnection;
import application.Session;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Content;
import model.DescriptionVote;
import model.DescriptionVoteStatistics;
import model.RationaleVote;
import model.RequirementCard;
import model.User;

public class DescriptionVoteController extends VoteController implements Initializable {

	@FXML
	private Slider precise_slider;
	@FXML
	private Slider understandable_slider;
	@FXML
	private ChoiceBox<String> correct_choicebox = new ChoiceBox<String>();
	@FXML
	private Button vote_buttons;

	@FXML
	private TextArea description_textfield;

	@FXML
	public void saveVote(ActionEvent event) throws IOException {
		RequirementCard selected = StartpageController.getSelectedRc();
		Content ct = ContentController.getLatestContentForRc(selected);

		PreparedStatement stmt = null;
		Connection conn = DbConnection.open();
		try {
			String insertVote = "INSERT INTO Description_Votes (Date_Cast, Precise, Understandable, Correct, User_ID, Content_ID) VALUES (?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertVote);
			stmt.setDate(1, new Date(new java.util.Date().getTime()));
			stmt.setInt(2, (int) precise_slider.getValue());
			stmt.setInt(3, (int) understandable_slider.getValue());
			stmt.setString(4, correct_choicebox.getValue());
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

	public static List<DescriptionVote> getAllVotesForContent(Content c) {
		List<DescriptionVote> l = null;
		try

		{
			l = new ArrayList<DescriptionVote>();
			Connection conn = DbConnection.open();
			String query = "SELECT * FROM Description_Votes WHERE Content_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, c.getId());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				Date dateCast = rs.getDate(2);
				int precise = rs.getInt(3);
				int understandable = rs.getInt(4);
				String correct = rs.getString(5);
				int userId = rs.getInt(6);
				int contentId = rs.getInt(7);
				DescriptionVote vote = new DescriptionVote(id, precise, understandable, correct, dateCast, userId,
						contentId);
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

	public static DescriptionVoteStatistics calculateAverageVotingScores(Content c) {
		List<DescriptionVote> l = getAllVotesForContent(c);
		double sum_precise = 0;
		double sum_understandable = 0;
		int avr_corect;
		int correct_y = 0;
		int correct_n = 0;
		int correct_na = 0;
		double [] precise = new double[l.size()];
		double [] understandable = new double [l.size()];
		for (int i=0; i<l.size(); i++) {
			DescriptionVote d = l.get(i);
			sum_precise += d.getPrecise();
			sum_understandable += d.getUnderstandable();
			if (d.getCorrect().equalsIgnoreCase("y"))
				correct_y += 1;
			else if (d.getCorrect().equalsIgnoreCase("n"))
				correct_n += 1;
			else
				correct_na += 1;

			precise[i] = d.getPrecise();
			understandable[i] = d.getUnderstandable();
		}
		System.out.println("precise: " + sum_precise + ", under: " + sum_understandable + " dividing by " + l.size());

		DecimalFormat df = new DecimalFormat("#.##");      
		
		double avr_precise = sum_precise / l.size();
		double avr_understandable = sum_understandable / l.size();
		double var_prec = StatisticsController.getVariance(precise);
		var_prec = Double.valueOf(df.format(var_prec));
		double sd_prec = StatisticsController.getStdDev(precise);
		sd_prec = Double.valueOf(df.format(sd_prec));
		double var_und = StatisticsController.getVariance(understandable);
		var_und = Double.valueOf(df.format(var_und));
		double sd_und = StatisticsController.getStdDev(understandable);
		sd_und = Double.valueOf(df.format(sd_und));

		double mean_prec = StatisticsController.getMean(precise);
		double median_prec = StatisticsController.median(precise);
		String correct = correct_y+"/"+l.size()+"Y" + " "  +correct_n+ "/" + l.size() +"N "+correct_na+ "/" + l.size() + "?" ; 
		System.out.println("AVR PRECISE: " + avr_precise + ", AVR UND: " + avr_understandable + ", AVR CORRECT: "
				+ "" + "yes: " +correct_y+ "/" + l.size() + ", no: "+correct_n+ "/" + l.size() + ", na: "+correct_na+ "/" + l.size() );
	

		
		DescriptionVoteStatistics dvs = new DescriptionVoteStatistics();
		dvs.setCorrect(correct);
		dvs.setPrecise(avr_precise);
		dvs.setUnderstandable(avr_understandable);
		dvs.setStandardDeviation_precise(sd_prec);
		dvs.setVariance_precise(var_prec);
		dvs.setStandardDeviation_understandable(sd_und);
		dvs.setVariance_understandable(var_und);
		return dvs;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		correct_choicebox.setItems(FXCollections.observableArrayList("Y", "N", "?"));
		correct_choicebox.getSelectionModel().select(0);
		RequirementCard selected = StartpageController.getSelectedRc();
		Content ct = ContentController.getLatestContentForRc(selected);
		description_textfield.setText(ct.getDescription());
		description_textfield.setDisable(true);

	}

}
