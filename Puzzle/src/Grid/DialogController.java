package Grid;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogController {

	public void newGridDialog() throws Exception {
		Stage dialog = new Stage();
		AnchorPane root = FXMLLoader.load(getClass().getResource("DialogBox.fxml"));
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.setTitle("New Grid");
		dialog.show();
	}
}
