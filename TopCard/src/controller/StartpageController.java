package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import application.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import model.Content;
import model.DescriptionVote;
import model.RequirementCard;

/**
 * The StartpageController coordinates all actions on the startpage.fxml scene,
 * this also means initialization of the StartpageController
 * 
 * @author: Ben Lukas Dietrich
 *
 */

public class StartpageController implements Initializable {

	// Viewer
	@FXML
	private ChoiceBox<String> choicebox_module = new ChoiceBox<String>();
	@FXML
	private ChoiceBox<String> choicebox_owner = new ChoiceBox<String>();
	@FXML
	private TableView<RequirementCard> requirement_table_view;
	@FXML
	private TableColumn<RequirementCard, String> reqCardNameColumn_view;
	@FXML
	private TableColumn<RequirementCard, String> reqCardSourceColumn_view;
	// end Viewer

	// Creator//
	@FXML
	private Button create_button;
	@FXML
	private Button update_button;
	@FXML
	private Button putUpToVote_button;
	@FXML
	private Button freeze_button;
	@FXML
	private TextField requirement_name;
	@FXML
	private TextField requirement_number;
	@FXML
	private ChoiceBox<String> requirement_module = new ChoiceBox<String>();
	@FXML
	private Button add_module;
	@FXML
	private TextArea requirement_description;
	@FXML
	private TextArea requirement_rationale;
	@FXML
	private TextField requirement_source;
	@FXML
	private ChoiceBox<String> requirement_userStory = new ChoiceBox<String>();
	@FXML
	private Button userStory;
	@FXML
	private TextField requirement_sup;
	@FXML
	private TableView<RequirementCard> requirement_table;
	@FXML
	private TableColumn<RequirementCard, String> reqCardNameColumn;
	@FXML
	private TableColumn<RequirementCard, String> reqCardSourceColumn;
	// end Creator

	// versions
	@FXML
	ChoiceBox<String> choicebox_versions = new ChoiceBox<String>();

	// voting buttons
	@FXML
	private Button vote_description_button;
	@FXML
	private Button vote_rationale_button;

	// Card viewer
	@FXML
	ChoiceBox<String> type_choicebox = new ChoiceBox<String>();
	@FXML
	private ChoiceBox<String> choicebox_module_viewer = new ChoiceBox<String>();
	@FXML
	private ChoiceBox<String> choicebox_owner_viewer = new ChoiceBox<String>();

	@FXML
	CheckBox checkbox_understandable_desc;
	@FXML
	CheckBox checkbox_precise_desc;
	@FXML
	CheckBox checkbox_correct_desc;
	@FXML
	CheckBox checkbox_understandable_rat;
	@FXML
	CheckBox checkbox_precise_rat;
	@FXML
	CheckBox checkbox_traceable_rat;
	@FXML
	ChoiceBox<String> choicebox_und_desc = new ChoiceBox<String>();
	@FXML
	ChoiceBox<String> choicebox_prec_desc = new ChoiceBox<String>();
	@FXML
	ChoiceBox<String> choicebox_correct_desc = new ChoiceBox<String>();
	@FXML
	ChoiceBox<String> choicebox_und_rat = new ChoiceBox<String>();
	@FXML
	ChoiceBox<String> choicebox_prec_rat = new ChoiceBox<String>();
	@FXML
	ChoiceBox<String> choicebox_trac_rat = new ChoiceBox<String>();

	@FXML
	private TableView<RequirementCard> table_viewer;
	@FXML
	private TableColumn<RequirementCard, String> table_viewer_name;
	@FXML
	private TableColumn<RequirementCard, String> table_viewer_source;
	@FXML
	private TableColumn<RequirementCard, String> table_viewer_current_version;
	@FXML
	private TableColumn<RequirementCard, String> table_viewer_description;
	@FXML
	private TableColumn<RequirementCard, String> table_viewer_rationale;
	@FXML
	private Button button_showIndividualStat;

	// other fields we need
	static RequirementCard selectedRC;

	@FXML
	private void showIndividualStats (ActionEvent event)
	{
		System.out.println("pressed");
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/statistics.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Individual statistics");
		stage.show();

		(((Node) event.getSource()).getScene()).getWindow().hide();
	}
	@FXML
	private void searchButton(ActionEvent event) throws IOException {

		System.out.println("search");
		String moduleChoice = choicebox_module.getValue();
		String userChoice = choicebox_owner.getValue();
		ObservableList<RequirementCard> data = FXCollections.observableArrayList();

		if (!moduleChoice.equalsIgnoreCase("All modules") && !userChoice.equalsIgnoreCase("All owners")) {
			int moduleId = ModuleController.getModuleForName(choicebox_module.getValue());
			String str = choicebox_owner.getValue();
			String[] user = str.split(" ");
			int userId = UserController.getUserForName(user[0], user[1]);
			data = RequirementCardController.getAllVotableCardsForUserAndModuleId(userId, moduleId);

		} else if ((!moduleChoice.equalsIgnoreCase("All modules") && userChoice.equalsIgnoreCase("All owners"))) {
			int moduleId = ModuleController.getModuleForName(choicebox_module.getValue());
			data = RequirementCardController.getAllVotableCardsForModuleId(moduleId);
		} else {
			String str = choicebox_owner.getValue();
			String[] user = str.split(" ");
			int userId = UserController.getUserForName(user[0], user[1]);
			data = RequirementCardController.getAllVotableCardsForUserId(userId);
		}

		requirement_table_view.setEditable(true);
		reqCardNameColumn_view.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		reqCardSourceColumn_view.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
		requirement_table_view.setItems(data);

		if (moduleChoice.equalsIgnoreCase("All modules") && userChoice.equalsIgnoreCase("All owners")) {
			fillTheTableInTheCardVoter();
		}

	}

	public void fillTheTableInTheCardViewer() {
		table_viewer.setEditable(true);
		table_viewer_name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		table_viewer_source.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
		table_viewer.setItems(RequirementCardController.getAllRcData());
	}

	public void fillTheTableInTheCardVoter() {
		requirement_table_view.setEditable(true);
		reqCardNameColumn_view.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		reqCardSourceColumn_view.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
		requirement_table_view.setItems(RequirementCardController.getVotableRcData());
	}

	@FXML
	public void disableVoting(ActionEvent event) {

	}

	@FXML
	public void updateRC(ActionEvent event) {

		RequirementCard selectedRC = requirement_table.getSelectionModel().getSelectedItem();
		int itemIndexInTheTable = requirement_table.getSelectionModel().getFocusedIndex();

		String moduleId = String.valueOf(ModuleController.getModuleForName(requirement_module.getValue()));
		String source = requirement_source.getText();
		String userStory = String.valueOf(UserStoryController.getUserStoryForName(requirement_userStory.getValue()));
		String userId = String.valueOf(Session.getUser().getId());
		String desc = requirement_description.getText();
		String rat = requirement_rationale.getText();
		String suppMats = requirement_sup.getText();
		String name = requirement_name.getText();

		RequirementCard editedRC = new RequirementCard(moduleId, source, selectedRC.getStatus(), userId, suppMats,
				userStory, name);

		Content workingCopy = ContentController.getWorkingCopy(selectedRC);
		System.out.println("working copy: " + workingCopy);
		if ((workingCopy.getDescription().equalsIgnoreCase(desc))
				&& (workingCopy.getRationale().equalsIgnoreCase(rat))) {
			RequirementCardController.updateRC(selectedRC.getId(), editedRC);

		} else {
			workingCopy.setDescription(desc);
			workingCopy.setRationale(rat);
			RequirementCardController.updateRC(selectedRC.getId(), editedRC);
			ContentController.updateContent(workingCopy, selectedRC);
		}

		// showAlertWindow("You have successfully updated a Requirement Card's
		// working copy!");

		fillTheTableInTheCardCreator();
		fillTheTableInTheCardVoter();

		requirement_table.getSelectionModel().select(itemIndexInTheTable);
		requirement_table.getFocusModel().focus(itemIndexInTheTable);
	}

	/**
	 * Handles the action of pressing the Save button.
	 * 
	 * @author Nikola Draskovic
	 * @param event
	 */
	@FXML
	public void createRC(ActionEvent event) {

		String module = requirement_module.getValue();
		int modId = ModuleController.getModuleForName(module);
		String source = requirement_source.getText();
		String userStory = requirement_userStory.getValue();
		int uStId = UserStoryController.getUserStoryForName(userStory);
		String desc = requirement_description.getText();
		String rat = requirement_rationale.getText();
		String suppMats = requirement_sup.getText();
		String name = requirement_name.getText();
		String errorMessage = "The";
		RequirementCard r = new RequirementCard(String.valueOf(modId), source, "1",
				String.valueOf(Session.getUser().getId()), suppMats, String.valueOf(uStId), name);
		Content c = new Content();
		c.setDate(new Date(new java.util.Date().getTime()));
		c.setDescription(desc);
		c.setRationale(rat);
		c.setMajor(0);
		c.setMinor(0);
		int errorCount = 0;

		if (name.isEmpty()) {
			errorMessage += " name";
			errorCount++;
		}
		if (module == null) {
			if (errorCount > 0) {
				errorMessage += ",";
			}
			errorMessage += " module";
			errorCount++;
		}
		if (desc.isEmpty()) {
			if (errorCount > 0) {
				errorMessage += ",";
			}
			errorMessage += " description";
			errorCount++;
		}
		if (rat.isEmpty()) {
			if (errorCount > 0) {
				errorMessage += ",";
			}
			errorMessage += " rationale";
			errorCount++;
		}
		if (source.isEmpty()) {
			if (errorCount > 0) {
				errorMessage += ",";
			}
			errorMessage += " source";
			errorCount++;
		}
		if (errorCount < 1) {
			RequirementCardController.insertRcToDB(r, c);
			fillTheTableInTheCardCreator();
			requirement_table.getSelectionModel().selectLast();
			showAlertWindow("You have successfully created a new Requirement Card!");

		} else {
			if (errorCount == 1) {
				errorMessage += " field is empty!";
			} else {
				errorMessage += " fields are empty!";
			}
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Required fields are missing!");
			alert.setContentText(errorMessage);
			if (errorCount > 2) {
				alert.getDialogPane().setPrefSize(400, 200);
			}
			alert.show();
		}

	}

	@FXML
	public void freezeUnfreezeRC(ActionEvent event) {
		RequirementCard item = requirement_table.getSelectionModel().getSelectedItem();
		if (freeze_button.getText().equalsIgnoreCase("Freeze")) { // freeze the
																	// RC
			RequirementCardController.freeze(item);
			int itemIndexInTheTable = requirement_table.getSelectionModel().getFocusedIndex();

			showAlertWindow("You have successfully frozen the selected Requirement Card!");

			fillTheTableInTheCardCreator();
			fillTheTableInTheCardVoter();
			freeze_button.setText("New major");
			// freeze_button.setStyle("-fx-background-color: turquoise;
			// -fx-text-fill: blue;");
			fieldsEnabled(false);
			freeze_button.setDisable(false);
			update_button.setDisable(true);
			putUpToVote_button.setDisable(true);
			create_button.setDisable(true);
			requirement_table.getSelectionModel().select(itemIndexInTheTable);
			requirement_table.getFocusModel().focus(itemIndexInTheTable);
		} else { // unfreeze the RC
			RequirementCardController.createNextMajorVersion(item);
			int itemIndexInTheTable = requirement_table.getSelectionModel().getFocusedIndex();

			showAlertWindow("You unfroze the selected Requirement Card!");

			fillTheTableInTheCardCreator();
			fillTheTableInTheCardVoter();
			freeze_button.setText("Freeze");
			fieldsEnabled(true);
			// freeze_button.setStyle("");
			freeze_button.setDisable(false);
			update_button.setDisable(false);
			putUpToVote_button.setDisable(false);
			create_button.setDisable(true);
			requirement_table.getSelectionModel().select(itemIndexInTheTable);
			requirement_table.getFocusModel().focus(itemIndexInTheTable);
		}
		initVersions(choicebox_versions, item);
	}

	private void fieldsEnabled(boolean enabledOrNot) {
		requirement_name.setDisable(!enabledOrNot);
		requirement_description.setDisable(!enabledOrNot);
		requirement_rationale.setDisable(!enabledOrNot);
		requirement_source.setDisable(!enabledOrNot);
		requirement_sup.setDisable(!enabledOrNot);
		requirement_module.setDisable(!enabledOrNot);
		requirement_userStory.setDisable(!enabledOrNot);
	}

	private void showAlertWindow(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

	@FXML
	public void putToVoteRC(ActionEvent event) {

		RequirementCard item = requirement_table.getSelectionModel().getSelectedItem();
		int itemIndexInTheTable = requirement_table.getSelectionModel().getFocusedIndex();
		showAlertWindow("You have successfully put up to vote the selected Requirement Card!");
		updateRC(event);
		RequirementCardController.putUpToVote(item);
		fillTheTableInTheCardCreator();
		fillTheTableInTheCardVoter();
		requirement_table.getSelectionModel().select(itemIndexInTheTable);
		requirement_table.getFocusModel().focus(itemIndexInTheTable);
		initVersions(choicebox_versions, item);
	}

	public void fillTheTableInTheCardCreator() {
		requirement_table.setEditable(true);
		reqCardNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		reqCardSourceColumn.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
		requirement_table.setItems(RequirementCardController.getRcDataByCurrentUser());
	}

	/**
	 * @author Maximilian Kostial When the Vote Rationale Button is pressed it
	 *         shows a new Window with the voting options.
	 */
	public void voteOnRationale(ActionEvent event) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/view/RationaleVote.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Rationale Vote");
		stage.show();

		(((Node) event.getSource()).getScene()).getWindow().hide();

	}

	/**
	 * @author Ben Dietrich Description Vote Scene opens
	 */
	public void voteOnDescription(ActionEvent event) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/view/DescriptionVote.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Vote on Description");

		stage.show();

		(((Node) event.getSource()).getScene()).getWindow().hide();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 */

	public void initVersions(ChoiceBox<String> versions, RequirementCard rc) {
		List<Content> listOfContents = ContentController.getAllContents(rc);

		ObservableList<String> items = FXCollections.observableArrayList();
		for (int i = 0; i < listOfContents.size(); i++) {
			String version = listOfContents.get(i).getMajor() + "." + listOfContents.get(i).getMinor();
			items.add(version);
		}
		versions.setItems(items);
		versions.getSelectionModel().selectLast();

	}

	public void initModules(ChoiceBox<String> modulesBox) {
		String[] name_modules = ModuleController.getAllModuleNames();
		int i = name_modules.length;
		ObservableList<String> items2 = FXCollections.observableArrayList();
		modulesBox.setItems(items2);
		modulesBox.getSelectionModel();
		while (i > 0) {
			i = i - 1;
			items2.add(name_modules[i]);
		}
		modulesBox.getSelectionModel().selectLast();
	}

	public void initUserStories(ChoiceBox<String> userStoriesBox) {
		String[] name_userstory = UserStoryController.getUserStoryNames();
		int i = name_userstory.length;
		ObservableList<String> items2 = FXCollections.observableArrayList();
		userStoriesBox.setItems(items2);
		userStoriesBox.getSelectionModel();
		while (i > 0) {
			i = i - 1;
			items2.add(name_userstory[i]);
		}
		userStoriesBox.getSelectionModel().selectLast();

	}

	public void initUsers(ChoiceBox<String> usersBox) {
		String[] ownernamesViewer = UserController.getAllUserNames();
		ObservableList<String> ownerViewer = FXCollections.observableArrayList();
		usersBox.setItems(ownerViewer);
		int x = ownernamesViewer.length;
		ownerViewer.add("All owners");

		while (x > 0) {
			x = x - 1;
			ownerViewer.add(ownernamesViewer[x]);
		}
		usersBox.getSelectionModel().selectLast();
		;
	}

	public void fillInRcDataCreator(RequirementCard selectedRC, Content c) {
		System.out.println(selectedRC);
		requirement_name.setText(selectedRC.getName());
		String module_name = ModuleController.getModuleNameForId(Integer.parseInt(selectedRC.getModule()));
		requirement_module.getSelectionModel().select(module_name);
		requirement_description.setText(c.getDescription());
		requirement_rationale.setText(c.getRationale());
		requirement_source.setText(selectedRC.getSource());
		String us_name = UserStoryController.getUserStoryNameForId(Integer.parseInt(selectedRC.getModule()));
		requirement_userStory.setValue(us_name);
		requirement_sup.setText(selectedRC.getSupportingMaterials());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		freeze_button.setDisable(true);
		update_button.setDisable(true);
		putUpToVote_button.setDisable(true);
		initModules(requirement_module);
		initModules(choicebox_module);
		initModules(choicebox_module_viewer);
		initUsers(choicebox_owner);
		initUsers(choicebox_owner_viewer);
		initUserStories(requirement_userStory);
		fillTheTableInTheCardCreator();
		fillTheTableInTheCardVoter();
		fillTheTableInTheCardViewer();

		// SELECTING THE VERSION IN THE CHOICEBOX
		choicebox_versions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
				if (oldValue != null && newValue != null) {
					freeze_button.setDisable(false);
					update_button.setDisable(false);
					putUpToVote_button.setDisable(false);
					create_button.setDisable(true);
					fieldsEnabled(true);
					System.out.println(newValue);
					String[] temp = newValue.split("\\.");
					Content c = ContentController.getContentForVersion(selectedRC, Integer.parseInt(temp[0]),
							Integer.parseInt(temp[1]));
					fillInRcDataCreator(selectedRC, c);
					if (!newValue.equals(ContentController.getLatestContentForRc(selectedRC).getMajor() + "."
							+ ContentController.getLatestContentForRc(selectedRC).getMinor())) {
						fieldsEnabled(false);
						freeze_button.setDisable(true);
						update_button.setDisable(true);
						putUpToVote_button.setDisable(true);
						create_button.setDisable(true);
					} else if (selectedRC.getStatus().equals("1")) {
						fieldsEnabled(false);
						update_button.setDisable(true);
						putUpToVote_button.setDisable(true);
						create_button.setDisable(true);
					}
				}

			}
		});

		requirement_table_view.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				System.out.println("pressed");
				selectedRC = requirement_table_view.getSelectionModel().getSelectedItem();
				System.out.println("current rc: " + selectedRC);

			}
		});

		table_viewer.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					selectedRC = table_viewer.getSelectionModel().getSelectedItem();
					Content cc = ContentController.getLatestContentForRc(selectedRC);
					DescriptionVoteController.calculateAverageVotingScores(cc);
				}
			}
		});

		// SELECTING THE RC IN THE TABLEVIEW
		requirement_table.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					selectedRC = requirement_table.getSelectionModel().getSelectedItem();
					//Content cc = ContentController.getLatestContentForRc(selectedRC);
					//DescriptionVoteController.calculateAverageVotingScores(cc);
					freeze_button.setText("Freeze");
					freeze_button.setDisable(false);
					update_button.setDisable(false);
					putUpToVote_button.setDisable(false);
					create_button.setDisable(true);
					fieldsEnabled(true);
					if (!selectedRC.getName().equalsIgnoreCase("Create new")) {
						if (selectedRC.getStatus().equals("1")) {
							freeze_button.setText("New major");
							freeze_button.setDisable(false);
							fieldsEnabled(false);
							update_button.setDisable(true);
							putUpToVote_button.setDisable(true);
							create_button.setDisable(true);
						} else if (selectedRC.getStatus().equals("2")) {

						} else if (selectedRC.getStatus().equals("3")) {

						} else if (selectedRC.getStatus().equals("4")) {

						} else {

						}
						Content c = ContentController.getWorkingCopy(selectedRC);
						fillInRcDataCreator(selectedRC, c);
						initVersions(choicebox_versions, selectedRC);
					} else {
						freeze_button.setText("Freeze");
						freeze_button.setDisable(true);
						update_button.setDisable(true);
						putUpToVote_button.setDisable(true);
						create_button.setDisable(false);
						requirement_name.setText("");
						requirement_description.setText("");
						requirement_rationale.setText("");
						requirement_source.setText("");
						requirement_sup.setText("");
						ObservableList<String> emptyList = FXCollections.observableArrayList();
						choicebox_versions.setItems(emptyList);
						requirement_module.getSelectionModel().selectLast();
						requirement_userStory.getSelectionModel().selectLast();
					}
				}
			}
		});

	}

	public static RequirementCard getSelectedRc() {
		return selectedRC;
	}

	// start logout
	@FXML
	private Button logout_button;

	public void buttonClickHandlerLogout(ActionEvent event) throws Exception {
		try {
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
