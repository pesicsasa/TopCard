package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Content;
import model.DescriptionVoteStatistics;
import model.RequirementCard;

public class IndividualStatisticsController implements Initializable{

	@FXML
	Label label_precise_desc;
	@FXML
	Label label_understandable_desc;
	@FXML
	Label label_correct_desc;
	@FXML
	Label label_stdev_desc_prec;
	@FXML
	Label label_stdev_desc_und;
	@FXML
	Label label_var_desc_prec;
	@FXML
	Label label_var_desc_und;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RequirementCard rc = StartpageController.getSelectedRc();
		Content c = ContentController.getLatestContentForRc(rc);
		DescriptionVoteStatistics dvs = DescriptionVoteController.calculateAverageVotingScores(c);
		
		label_precise_desc.setText(String.valueOf(dvs.getPrecise()));
		label_understandable_desc.setText(String.valueOf(dvs.getUnderstandable()));
		label_correct_desc.setText(dvs.getCorrect());
		label_stdev_desc_prec.setText(String.valueOf(dvs.getStandardDeviation_precise()));
		label_stdev_desc_und.setText(String.valueOf(dvs.getStandardDeviation_understandable()));
		label_var_desc_prec.setText(String.valueOf(dvs.getVariance_precise()));
		label_var_desc_und.setText(String.valueOf(dvs.getVariance_understandable()));
		
	}
}
