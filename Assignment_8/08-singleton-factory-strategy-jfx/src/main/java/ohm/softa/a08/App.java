package ohm.softa.a08;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entrypoint of application
 * Launches JavaFX app by loading main.fxml file
 */
public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		var root = FXMLLoader.<Parent>load(getClass().getResource("views/main.fxml"));

		stage.setTitle("Cafeteria");
		stage.setScene(new Scene(root, 800, 600));
		stage.show();

		stage.setOnCloseRequest(v -> Platform.exit());
	}
}
