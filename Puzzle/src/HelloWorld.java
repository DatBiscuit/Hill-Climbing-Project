//import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
//import java.util.Formatter;
import java.util.Optional;
import java.util.Random;

import Evaluation.MinTurnNode;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;


public class HelloWorld extends Application {
	
	private static Stage stage;
	public static Scene scene;
	public static Scene scene2;
	public static int mSize;
	public static GridPane root;
	public static Tab gridTab;
	public static Tab evalTab;
	static MinTurnNode[][] eval;
	static GridPane sizeDialog;
	
	public static void main(String[] args) {
		launch(args);
	}

	private void setUpPromptSizeDialogWindow() {
		sizeDialog = new GridPane();
		sizeDialog.setAlignment(Pos.CENTER);
		sizeDialog.setHgap(10);
		sizeDialog.setVgap(10);
		sizeDialog.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		sizeDialog.add(scenetitle, 0, 0, 2, 1);

		Label inputTitle = new Label("Size of Matrix:");
		sizeDialog.add(inputTitle, 0, 1);
		TextField size = new TextField();
		sizeDialog.add(size, 1, 1);
		Button btn = new Button("Enter");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		sizeDialog.add(hbBtn, 1, 4);
		
		final Text actiontarget = new Text();
        sizeDialog.add(actiontarget, 1, 6);
	} 
	
	
	private int generateRandomNumber() {
	   	Random rand = new Random();
	   	int randomNumber = rand.nextInt(mSize);
	   	while(randomNumber == 0) {
	   		 randomNumber = rand.nextInt(mSize);
	   	}
   		return randomNumber;
	}
	
	private TextField createNewTile() {
        TextField tf = new TextField();
        tf.setPrefHeight(50);
        tf.setPrefWidth(50);
        tf.setAlignment(Pos.CENTER);
        tf.setEditable(false);
        return tf;
     }
	
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Hill Climbing");
		
		
		//setting up the tabs
		TabPane tabs = new TabPane();
		Tab buttons = new Tab();
		buttons.setContent(createButtons());
		buttons.setText("Options");
		tabs.getTabs().add(buttons);
		Tab gridtab = new Tab();
		Tab evaltab = new Tab();
		
		//setting tabs
		setTabs(gridtab, evaltab);
		
		//setUpPromptSizeDialogWindow();
		
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
		    		
		    		//Tries to see if the proper input was put in
		    		//proper input includes only whole number digits
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
		    		
		    		eval = EvaluationGrid.evalGrid(mSize);
		    		root = new GridPane();
		    		for(int y = 0; y < mSize; y++){
		                for(int x = 0; x < mSize; x++){
		                    
		                    int currentNumber = generateRandomNumber();
		                    
		                    TextField tf = createNewTile();
		                    
		                    if(x == mSize-1 && y == mSize-1) {
		                    	tf.setText(" 0 ");
		                    	EvaluationGrid.setTable(y, x, 0,eval);
		                    } else {
		                    	tf.setText(" " + currentNumber + " ");
		                    	EvaluationGrid.setTable(y, x, currentNumber, eval);
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
		    		EvaluationGrid.createGraph(eval);
		    		EvaluationGrid.printTable(eval);
		    		EvaluationGrid.createResult(eval);
		    		System.out.println();
		    		EvaluationGrid.printResultTable(eval);
		    		
		    		//setting up second tab
		    		evaltab.setContent(EvaluationGrid.printResultTable(eval));
		    		evaltab.setText("Evaluation Grid");
		    		tabs.getTabs().add(evaltab);
		    		
		    		//set the scene to show
		    		scene2 = new Scene(tabs);
		    		changeScene(scene2, stage);
		    		
		        } else {
		        	actiontarget.setFill(Color.FIREBRICK);
		            actiontarget.setText("You have not left a comment.");
		        }
		    	
		    }
		});
		
		scene = new Scene(grid);
		changeScene(scene, stage);
	}
	
	public static void setTabs(Tab grid, Tab evaluation) {
		gridTab = grid;
		evalTab = evaluation;
		
	}
	
	public static Tab getTabs(char currentTab) {
		if(currentTab == 'g')return gridTab;
		
		if(currentTab == 'e') return evalTab;
		
		return null;
	}

	/*
	 * Function to add the grid tab with all the options
	 */
	  public static GridPane createButtons() {
	  
	    Button bHC = new Button("Basic Hill Climbing");
	    Button hCRR = new Button("Hill Climbing with Random Restarts");
	    Button hCRW = new Button("Hill Climbing with Random Walk");
	    Button sA = new Button("Simulated Anealing");
	    Button pB = new Button("Population-Based");
	    
	    
	    //put in respect action for each button
	    bHC.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	int iter = 0;
		    	TextInputDialog dialog;
		    	dialog = new TextInputDialog();
                dialog.initOwner(stage);
                dialog.setTitle("Basic Hill Climb");
                dialog.setContentText("Enter Number of Iterations: ");
                Optional<String> result = dialog.showAndWait();
                if(result.isPresent()) {
                	try {
                	if(Integer.parseInt(result.get())<=0){
		    			throw new Exception();
		    		}	
		    		iter = Integer.parseInt(result.get());
                		
                	} catch(Exception ex) {
                		errDialog("Not proper input. Try numbers that are 1 or greater");
                	}
                	
                	//DO BASIC HILL CLIMB
                	BHC(eval , iter);
                	evalTab.setContent(EvaluationGrid.printResultTable(eval));
                	gridTab.setContent(EvaluationGrid.rePrintTable(eval));
                	
                }
		    }
		    });

	    hCRR.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	int iters = 0;
		    	int rand = 0;
		    	Dialog<Pair<String, String>> dialog = new Dialog<>();
		        dialog.setTitle("Hill Climbing with Random Restarts");

		        // Set the button types.
		        ButtonType enter = new ButtonType("OK", ButtonData.OK_DONE);
		        
		        dialog.getDialogPane().getButtonTypes().addAll(enter, ButtonType.CANCEL);
		        

		        GridPane gridPane = new GridPane();
		        gridPane.setHgap(10);
		        gridPane.setVgap(10);
		        gridPane.setPadding(new Insets(20, 150, 10, 10));

		        TextField iter = new TextField();
		        TextField rest = new TextField();

		        gridPane.add(new Label("Iterations:"),0,0);
		        gridPane.add(iter, 1, 0);
		        gridPane.add(new Label("Random Restart:"), 2, 0);
		        gridPane.add(rest, 3, 0);

		        dialog.getDialogPane().setContent(gridPane);
		        
		        //lambda function to see if the okay button was hit
		        dialog.setResultConverter(dialogButton -> {
		            if (dialogButton == enter) {
		            	return new Pair<>(iter.getText(), rest.getText());
		            }
		            return null;
		        });
		        
		        Optional<Pair<String, String>> result = dialog.showAndWait();
		
		        if(result.equals(Optional.empty())) {
		        	return;
		        }
		        
		        //collect data from user
		        try {
	        		if(Integer.parseInt(result.get().getKey()) < 1 || Integer.parseInt(result.get().getValue()) < 1) {
	        			throw new Exception();
	        		}
	        		iters = Integer.parseInt(iter.getText());
	        		rand = Integer.parseInt(rest.getText());
	        	} catch(Exception ex) {
	        		errDialog("Not proper input. Try numbers that are 1 or greater");
	        		
	        	}
		        //Do hill climb with random restarts
		        HCR(eval,iters,rand);
		        evalTab.setContent(EvaluationGrid.printResultTable(eval));
            	gridTab.setContent(EvaluationGrid.rePrintTable(eval));
		    }
		    });
	    
	    hCRW.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	int iters = 0;
		    	double prob = 0;
		    	Dialog<Pair<String, String>> dialog = new Dialog<>();
		        dialog.setTitle("Hill Climbing with Random Restarts");

		        // Set the button types.
		        ButtonType enter = new ButtonType("OK", ButtonData.OK_DONE);
		        
		        dialog.getDialogPane().getButtonTypes().addAll(enter, ButtonType.CANCEL);
		        

		        GridPane gridPane = new GridPane();
		        gridPane.setHgap(10);
		        gridPane.setVgap(10);
		        gridPane.setPadding(new Insets(20, 150, 10, 10));

		        TextField iter = new TextField();
		        TextField p = new TextField();

		        gridPane.add(new Label("Iterations:"),0,0);
		        gridPane.add(iter, 1, 0);
		        gridPane.add(new Label("Probability:"), 2, 0);
		        gridPane.add(p, 3, 0);

		        dialog.getDialogPane().setContent(gridPane);
		        
		        //lambda function to see if the okay button was hit
		        dialog.setResultConverter(dialogButton -> {
		            if (dialogButton == enter) {
		            	return new Pair<>(iter.getText(), p.getText());
		            }
		            return null;
		        });
		        
		        Optional<Pair<String, String>> result = dialog.showAndWait();
		
		        if(result.equals(Optional.empty())) {
		        	return;
		        }
		        
		        //collect data from user
		        try {
	        		if(Integer.parseInt(result.get().getKey()) < 1 || Double.parseDouble(result.get().getValue()) > 1 || Double.parseDouble(result.get().getValue()) < 0) {
	        			throw new Exception();
	        		}
	        		iters = Integer.parseInt(iter.getText());
	        		prob = Double.parseDouble(p.getText());
	        	} catch(Exception ex) {
	        		errDialog("Not proper input. Try numbers that are 1 or greater");
	        		
	        	}
		        //Do hill climb with random walk
		        HCW(eval,iters,prob);
		        evalTab.setContent(EvaluationGrid.printResultTable(eval));
            	gridTab.setContent(EvaluationGrid.rePrintTable(eval));

		    }
		    });
	    
	    sA.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	int iter = 0;
		    	double temp = 0;
		    	double decay = 0;
		    	Dialog<Results> dialog = new Dialog<>();
		    	dialog.setTitle("Simulating Anealing");
		    	
		    	// Set the button types.
		        ButtonType enter = new ButtonType("OK", ButtonData.OK_DONE);
		        
		        dialog.getDialogPane().getButtonTypes().addAll(enter, ButtonType.CANCEL);
		        

		        GridPane gridPane = new GridPane();
		        gridPane.setHgap(10);
		        gridPane.setVgap(10);
		        gridPane.setPadding(new Insets(20, 150, 10, 10));

		        TextField i = new TextField();
		        TextField t = new TextField();
		        TextField d = new TextField();

		        gridPane.add(new Label("Iterations:"),0,0);
		        gridPane.add(i, 1, 0);
		        gridPane.add(new Label("Temperature:"), 2, 0);
		        gridPane.add(t, 3, 0);
		        gridPane.add(new Label("Decay:"), 4, 0);
		        gridPane.add(d, 5, 0);

		        dialog.getDialogPane().setContent(gridPane);
		        
		      //lambda function to see if the okay button was hit
		        dialog.setResultConverter(dialogButton -> {
		            if (dialogButton == enter) {
		            	return new Results(i.getText(), t.getText(), d.getText());
		            }
		            return null;
		        });
		        
		        Optional<Results> result = dialog.showAndWait();
				
		        if(result.equals(Optional.empty())) {
		        	return;
		        }
		        
		        //collect data from user
		        try {
	        		if(Integer.parseInt(result.get().iter) < 1 
	        				|| Double.parseDouble(result.get().temp) <= 1 
	        				
	        				|| Double.parseDouble(result.get().decay) > 1 
	        				|| Double.parseDouble(result.get().decay) < 0)  {
	        			throw new Exception();
	        		}
	        		iter = Integer.parseInt(i.getText());
	        		temp = Double.parseDouble(t.getText());
	        		decay = Double.parseDouble(d.getText());
	        	} catch(Exception ex) {
	        		errDialog("Not proper input. Try numbers that are 1 or greater");
	        		
	        	}
		        //Do anealing
		        SA(eval,iter,temp,decay);
		        evalTab.setContent(EvaluationGrid.printResultTable(eval));
            	gridTab.setContent(EvaluationGrid.rePrintTable(eval));
		        
		    }
		    });
	    
	    pB.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	int iters = 0;
		    	int size = 0;
		    	Dialog<Pair<String, String>> dialog = new Dialog<>();
		        dialog.setTitle("Population Based Algorithm");

		        // Set the button types.
		        ButtonType enter = new ButtonType("OK", ButtonData.OK_DONE);
		        
		        dialog.getDialogPane().getButtonTypes().addAll(enter, ButtonType.CANCEL);
		        

		        GridPane gridPane = new GridPane();
		        gridPane.setHgap(10);
		        gridPane.setVgap(10);
		        gridPane.setPadding(new Insets(20, 150, 10, 10));

		        TextField iter = new TextField();
		        TextField p = new TextField();

		        gridPane.add(new Label("Iterations:"),0,0);
		        gridPane.add(iter, 1, 0);
		        gridPane.add(new Label("Population Size:"), 2, 0);
		        gridPane.add(p, 3, 0);

		        dialog.getDialogPane().setContent(gridPane);
		        
		        //lambda function to see if the okay button was hit
		        dialog.setResultConverter(dialogButton -> {
		            if (dialogButton == enter) {
		            	return new Pair<>(iter.getText(), p.getText());
		            }
		            return null;
		        });
		        
		        Optional<Pair<String, String>> result = dialog.showAndWait();
		
		        if(result.equals(Optional.empty())) {
		        	return;
		        }
		        
		        //collect data from user
		        try {
	        		if(Integer.parseInt(result.get().getKey()) < 1 || Integer.parseInt(result.get().getValue()) < 1) {
	        			throw new Exception();
	        		}
	        		iters = Integer.parseInt(iter.getText());
	        		size = Integer.parseInt(p.getText());
	        	} catch(Exception ex) {
	        		errDialog("Not proper input. Try numbers that are 1 or greater");
	        		
	        	}
		        //Do Population based algorithm
		        MinTurnNode[][] fgrid = PopulationGA.Pop(size, mSize, iters);
		        evalTab.setContent(EvaluationGrid.printResultTable(fgrid));
            	gridTab.setContent(EvaluationGrid.rePrintTable(fgrid));
		    }
		    });
	    
	    
	    GridPane gridPane = new GridPane();

	    //adds the buttons to the grid
	    gridPane.add(bHC, 0, 4, 2, 2);
	    gridPane.add(hCRR, 0, 10, 3, 3);
	    gridPane.add(hCRW, 0, 15, 3, 3);
	    gridPane.add(sA, 0, 22, 2, 2);
	    gridPane.add(pB, 0, 24, 2, 2);

	    return gridPane;
	  }
	
	
	 public static void changeScene(Scene scene, Stage stage) {
	    	stage.setScene(scene);
	    	stage.show();
	 }
	 
	 public static void errDialog(String emessage) {
		   Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("ALERT ERROR");
			alert.setHeaderText("ERROR");
			alert.setContentText(emessage);
			alert.showAndWait();
	 }
	 
	 private static class Results {

	        String iter;
	        String temp;
	        String decay;

	        public Results(String a, String b, String c) {
	        	this.iter = a;
	        	this.temp = b;
	        	this.decay = c;
	        }
	    }
	 
	 
	public static MinTurnNode[][] BHC(MinTurnNode[][] start, int it){
		System.out.println("BHC: \n");
		int n = start.length-1;
		int s,f;
		int[] pre;
		int sminpath;
		
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(it!=0){
			sminpath = start[n][n].minpath;
			
			pre = EvaluationGrid.BasicHillClimb(eval);
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			System.out.println();
			EvaluationGrid.printResultTable(eval);
	
			//revert back to original grid
			if(start[n][n].minpath < sminpath){
				System.out.println(sminpath+" "+start[n][n].minpath);
				start[pre[1]][pre[2]].value = pre[0];
				System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
				EvaluationGrid.createGraph(eval);
				EvaluationGrid.printTable(eval);
				EvaluationGrid.createResult(eval);
				
			}
			it--;
			
			
		}
		f = Calendar.getInstance().get(Calendar.MILLISECOND);
		System.out.println("Start: "+s+"\nFinish: "+f+"\nTime Taken: "+(f-s));
		
		
		FileWriter fw=null;
		try {
			fw = new FileWriter("BHCResults.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in creating fileWriter/n/n/n/n");
			e.printStackTrace();
		}
		 try {
			fw.write("Size n: "+start.length+" Start: "+s+" End: "+f+" Total Time: "+(f-s)+" Evaluation Val: "+start[n][n].minpath+"\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("Error in writing/n/n/n/n");
			e.printStackTrace();
		}
		
		
		
		return start;
		
	}

	
	
	public static MinTurnNode[][] HCR(MinTurnNode[][] start, int it, int r){
		System.out.println("HCR: \n");
		//Formatter file = null;
		int n = start.length-1;
		int s,f;
		int[] pre;
		int sminpath=start[n][n].minpath;
		int[][] init = new int[n+1][n+1];
		int[][] best = new int[n+1][n+1];
		
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		init = EvaluationGrid.fillArr(init,eval);
		
		
		while(it!=0){
			
			
			if(it%r==0) {
				System.out.println("HCR RESET: "+sminpath+" "+start[n][n].minpath);
				EvaluationGrid.printTable(eval);
				EvaluationGrid.fillEval(init,eval);
				EvaluationGrid.printTable(eval);
				System.out.println("HCR RESET: "+sminpath+" "+start[n][n].minpath);
				
			}

			
			pre = EvaluationGrid.BasicHillClimb(eval);
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			
			System.out.println();
			EvaluationGrid.printResultTable(eval);
	
			//revert back to original grid
			if(start[n][n].minpath < sminpath){
				System.out.println(sminpath+" "+start[n][n].minpath);
				start[pre[1]][pre[2]].value = pre[0];
				System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
				EvaluationGrid.createGraph(eval);
				EvaluationGrid.printTable(eval);
				EvaluationGrid.createResult(eval);
				
			}else {
				//BEST
				sminpath=start[n][n].minpath;
				best = EvaluationGrid.fillArr(best,eval);
			}
			
			it--;

		}
		EvaluationGrid.fillEval(best,eval);
		f = Calendar.getInstance().get(Calendar.MILLISECOND);
		
		FileWriter fw=null;
		try {
			fw = new FileWriter("HCRResults.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in creating fileWriter/n/n/n/n");
			e.printStackTrace();
		}
		 try {
			fw.write("Size n: "+start.length+" r: "+r+" Start: "+s+" End: "+f+" Total Time: "+(f-s)+" Evaluation Val: "+start[n][n].minpath+"\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("Error in writing/n/n/n/n");
			e.printStackTrace();
		}
		
		
		return start;
		
	}
	
	public static MinTurnNode[][] HCW(MinTurnNode[][] start, int it, double p){
		System.out.println("HCW: \n");
		//Formatter file = null;
		int n = start.length-1;
		int s,f;
		int[] pre;
		int sminpath;
		Random rand = new Random();
        double val ;
		
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(it!=0){
			
			sminpath = start[n][n].minpath;
			
			pre = EvaluationGrid.BasicHillClimb(eval);
		

			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			
			System.out.println();
			EvaluationGrid.printResultTable(eval);
	
			val = rand.nextDouble();
			//revert back to original grid
			if(val>p){
				if(start[n][n].minpath < sminpath){
					System.out.println(sminpath+" "+start[n][n].minpath);
					start[pre[1]][pre[2]].value = pre[0];
					System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
					EvaluationGrid.createGraph(eval);
					EvaluationGrid.printTable(eval);
					EvaluationGrid.createResult(eval);
				
				}
			}else{

				if(start[n][n].minpath >= sminpath){
					System.out.println(sminpath+" "+start[n][n].minpath);
					start[pre[1]][pre[2]].value = pre[0];
					System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
					EvaluationGrid.createGraph(eval);
					EvaluationGrid.printTable(eval);
					EvaluationGrid.createResult(eval);
				
					}
			}
			it--;
			
		}
		f = Calendar.getInstance().get(Calendar.MILLISECOND);
		
		FileWriter fw=null;
		try {
			fw = new FileWriter("HCWResults.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in creating fileWriter/n/n/n/n");
			e.printStackTrace();
		}
		 try {
			fw.write("Size n: "+start.length+" Prob: "+p+" Start: "+s+" End: "+f+" Total Time: "+(f-s)+" Evaluation Val: "+start[n][n].minpath+"\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("Error in writing/n/n/n/n");
			e.printStackTrace();
		}
		
		
		return start;
		
	}

	
	
	public static MinTurnNode[][] SA(MinTurnNode[][] start, int it, double t, double d){
		System.out.println("SA: \n");
		int n = start.length-1;
		int s,f;
		int[] pre;
		int sminpath;
		Random rand = new Random();
        double val,p;
		
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(it!=0){
			
			sminpath = start[n][n].minpath;
			
			pre = EvaluationGrid.BasicHillClimb(eval);
		
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			
			System.out.println();
			EvaluationGrid.printResultTable(eval);
	
			p = Math.exp((start[n][n].minpath-sminpath)/t); //Probability p = e^(V(j')-V(j))/t
			
			val = rand.nextDouble();
			//revert back to original grid
			if(val>p){

				if(start[n][n].minpath < sminpath){
					System.out.println(sminpath+" "+start[n][n].minpath);
					start[pre[1]][pre[2]].value = pre[0];
					System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
					EvaluationGrid.createGraph(eval);
					EvaluationGrid.printTable(eval);
					EvaluationGrid.createResult(eval);
				
				}
			}else{

				if(start[n][n].minpath >= sminpath){
					System.out.println(sminpath+" "+start[n][n].minpath);
					start[pre[1]][pre[2]].value = pre[0];
					System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
					EvaluationGrid.createGraph(eval);
					EvaluationGrid.printTable(eval);
					EvaluationGrid.createResult(eval);
				
					}
			}
			it--;
			
			t= t*d; // temperature decays
		}
		f = Calendar.getInstance().get(Calendar.MILLISECOND);
		
		FileWriter fw=null;
		try {
			fw = new FileWriter("SAResults.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in creating fileWriter/n/n/n/n");
			e.printStackTrace();
		}
		 try {
			fw.write("Size n: "+start.length+" Temp: "+t+" Decay: "+d+" Start: "+s+" End: "+f+" Total Time: "+(f-s)+" Evaluation Val: "+start[n][n].minpath+"\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("Error in writing/n/n/n/n");
			e.printStackTrace();
		}
		
		
		return start;
		
	}
	
	
	}
