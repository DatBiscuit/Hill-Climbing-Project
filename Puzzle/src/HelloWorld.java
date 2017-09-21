import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HelloWorld extends Application {
	
	private static Stage stage;
	public static Scene scene;
	public static Scene scene2;
	public static int mSize;
	public static GridPane root;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		/*
		TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < 5; i++) {
            Tab tab = new Tab();
            tab.setText("Tab" + i);
            HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        */
		
		stage.setTitle("AI Project 1");
		
		TabPane tabs = new TabPane();
		Tab gridtab = new Tab();
		Tab evaltab = new Tab();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label inputTitle = new Label("Size of Matrix:");
		grid.add(inputTitle, 0, 1);

		TextField size = new TextField();
		grid.add(size, 1, 1);
		Button btn = new Button("Enter");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		//code used to help with testing the textfield
		final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		        //where the stage will change to the matrix
		    	if ((size.getText() != null && !size.getText().isEmpty())) {
		    		
		    		/*Tries to see if the proper input was put in
		    		 * proper input includes only whole number digits
		    		 */
		    		try {
		    		if(Integer.parseInt(size.getText())<=1){
		    			throw new Exception();
		    		}	
		    		mSize = Integer.parseInt(size.getText());
		    		
		    		} catch(Exception ex) {
		    			actiontarget.setFill(Color.FIREBRICK);
		    			actiontarget.setText("You did not provide a valid input");
		    			return;
		    		}
		    		
		    		EvaluationGrid.evalGrid(mSize);
		    		root = new GridPane();
		    		for(int y = 0; y < mSize; y++){
		                for(int x = 0; x < mSize; x++){

		                    Random rand = new Random();
		                    int rand1 = rand.nextInt(mSize);
		                    while(rand1 == 0) {
		                    	rand1 = rand.nextInt(mSize);
		                    }

		                    // Create a new TextField in each Iteration
		                    TextField tf = new TextField();
		                    tf.setPrefHeight(50);
		                    tf.setPrefWidth(50);
		                    tf.setAlignment(Pos.CENTER);
		                    tf.setEditable(false);
		                    if(x == mSize-1 && y == mSize-1) {
		                    	tf.setText(" 0 ");
		                    	EvaluationGrid.setTable(y, x, 0);
		                    } else {
		                    	tf.setText(" " + rand1 + " ");
		                    	EvaluationGrid.setTable(y, x, rand1);
		                    }

		                    // Iterate the Index using the loops
		                    root.setRowIndex(tf,y);
		                    root.setColumnIndex(tf,x);    
		                    root.getChildren().add(tf);
		                }
		            }
		    		
		    		//setting up first tab for the original grid
		    		gridtab.setContent(root);
		    		gridtab.setText("Original Grid");
		    		tabs.getTabs().add(gridtab);
		    		
		    		//where the evaluation takes place
		    		EvaluationGrid.createGraph();
		    		EvaluationGrid.printTable();
		    		EvaluationGrid.createResult();
		    		System.out.println();
		    		
		    		//setting up second tab
		    		evaltab.setContent(EvaluationGrid.printResultTable());
		    		evaltab.setText("Evaluation Grid");
		    		tabs.getTabs().add(evaltab);
		    		
		    		//set the scene to show
		    		scene2 = new Scene(tabs);
		    		stage.setScene(scene2);
		    		stage.show();
		    		
		    		
		    		
		        } else {
		        	actiontarget.setFill(Color.FIREBRICK);
		            actiontarget.setText("You have not left a comment.");
		        }
		    	
		    }
		});
		
		scene = new Scene(grid);

		stage.setScene(scene);
		stage.show();
		
	}
	
	
	 public static void changeScene(Scene scene, Stage stage) {
	    	stage.setScene(scene);
	    	stage.show();
	    }
	

}
