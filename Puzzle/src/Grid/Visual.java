package Grid;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Visual extends Application {
	
	GridPane gridGenerated= new GridPane();
	GridPane evaluatedGrid = new GridPane();
	static GridCreator gridMaker = new GridCreator();
	static GridCreator evaluatedGridMaker = new GridCreator();

	public static void main(String[] args) {
		gridMaker.makeGrid();
		//evaluatedGridMaker.
		launch(args);
	}
	
	public void start(Stage stage) {
		try {
			setGridPane();
			gridGenerated.setAlignment(Pos.CENTER);
			Scene scene = new Scene(gridGenerated,400,400);		
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setGridPane() {
		Tile[][] grid = gridMaker.getGrid();
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				Label value = new Label(Integer.toString(grid[i][j].number));
				gridToShow.add(value, j, i, 1, 1);
			}
		}
	}

}
