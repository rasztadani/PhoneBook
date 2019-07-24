package phonebook;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class PhoneBook extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");		
		primaryStage.setTitle("Telefonkönyv");
		primaryStage.setWidth(800);
		primaryStage.setHeight(695);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
