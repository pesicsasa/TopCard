package view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/** @author Sasa Pesic
 * 
 * Launches the TopCard 
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TopCard");
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}