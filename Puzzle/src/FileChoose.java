import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

public class FileChoose extends Application {

	private static Stage stage;
	public static Scene scene;
	public static Scene scene2;
	public static int mSize;
	public static GridPane root;
	public static Tab gridTab;
	public static Tab evalTab;
	static MinTurnNode[][] eval;
	private Desktop desktop = Desktop.getDesktop();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View files");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home"))
				);
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
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		//where number is being input
		/*
		Label inputTitle = new Label("Size of Matrix:");
		grid.add(inputTitle, 0, 1);
		TextField size = new TextField();
		grid.add(size, 1, 1);
		*/
		final FileChooser fileChooser = new FileChooser();
		final Button openButton = new Button("Open a File");
		openButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						configureFileChooser(fileChooser);
						File file = fileChooser.showOpenDialog(stage);
						if(file != null) {
							gridtab.setContent(openFile(file));
							gridtab.setText("Original Grid");
				    		tabs.getTabs().add(gridtab);
				    		
				    		//where the evaluation takes place
				    		EvaluationGrid.createGraph(eval);
				    		EvaluationGrid.printTable(eval);
				    		EvaluationGrid.createResult(eval);
				    		System.out.println();
				    		EvaluationGrid.printResultTable(eval);
				    		
				    		//BHC(EvaluationGrid.eval ,200);
				    		//HCR(EvaluationGrid.eval,200,5);
				    		
				    		
				    		//setting up second tab
				    		evaltab.setContent(EvaluationGrid.printResultTable(eval));
				    		evaltab.setText("Evaluation Grid");
				    		tabs.getTabs().add(evaltab);
				    		
				    		//set the scene to show
				    		scene2 = new Scene(tabs);
				    		stage.setScene(scene2);
				    		stage.show();
						}
					}
				});
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(openButton);
		grid.add(hbBtn, 1, 4);
		
		/*
		Button btn = new Button("Enter");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			 
			//where parsing will occur
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
		    		
		    		 eval =EvaluationGrid.evalGrid(mSize);
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
		                    	EvaluationGrid.setTable(y, x, 0,eval);
		                    } else {
		                    	tf.setText(" " + rand1 + " ");
		                    	EvaluationGrid.setTable(y, x, rand1, eval);
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
		    		
		    		//BHC(EvaluationGrid.eval ,200);
		    		//HCR(EvaluationGrid.eval,200,5);
		    		
		    		
		    		//setting up second tab
		    		evaltab.setContent(EvaluationGrid.printResultTable(eval));
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
		
		*/
		
		scene = new Scene(grid);

		stage.setScene(scene);
		stage.show();
		
	}
	
	private GridPane openFile(File file) {
		root = new GridPane();
		int row = 0;
		FileReader fr = null;
		BufferedReader br = null;
		try{
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		boolean justread = true;
		String current;
		while((current = br.readLine()) != null) {
			//read first line as the size
			if(justread) {
				mSize = Integer.parseInt(current);
				eval = EvaluationGrid.evalGrid(mSize);
				justread = false;
			} else {
				//line by line fills the row
				for(int i = 0; i < 2*mSize-1; i++) {
					if(current.charAt(i) == ' ') {
						i++;
					}
					TextField tf = new TextField();
					 tf.setPrefHeight(50);
		             tf.setPrefWidth(50);
		             tf.setAlignment(Pos.CENTER);
		             tf.setEditable(false);
		             if(row == mSize - 1 && i == 2*mSize) {
		            	 tf.setText(" 0 ");
		            	 EvaluationGrid.setTable(row, i/2, 0, eval);
		             } else {
		            	 tf.setText(" " + current.charAt(i) + " ");
		            	 EvaluationGrid.setTable(row, i/2,Integer.parseInt(String.valueOf(current.charAt(i))) , eval);
		             }
		             root.setRowIndex(tf, row);
		             root.setColumnIndex(tf, i/2);
		             root.getChildren().add(tf);
		             
				}
				row++;
				/*
				// Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                if(x == mSize-1 && y == mSize-1) {
                	tf.setText(" 0 ");
                	EvaluationGrid.setTable(y, x, 0,eval);
                } else {
                	tf.setText(" " + rand1 + " ");
                	EvaluationGrid.setTable(y, x, rand1, eval);
                }

                // Iterate the Index using the loops
                root.setRowIndex(tf,y);
                root.setColumnIndex(tf,x);    
                root.getChildren().add(tf);
                */
			}
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(br != null) {
					br.close();
				}
				if(fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return root;
	}
	
	public static void setTabs(Tab g, Tab e) {
		gridTab = g;
		evalTab = e;
		
	}
	
	public static Tab getTabs(char c) {
		if(c == 'g')return gridTab;
		
		if(c == 'e') return evalTab;
		
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
		//Formatter file = null;
		int n = start.length-1;
		int s,f;
		int[] pre;
		int sminpath;
		
		
		/*
		try {
			file = new Formatter("BHCResults.txt");
			
		}catch(FileNotFoundException e){
			System.out.println("Error/n/n/n/n");
		}	
		*/
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(it!=0){
			sminpath = start[n][n].minpath;
			
			pre = EvaluationGrid.BasicHillClimb(eval);
		
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
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
				
			}/*else {
				//change to new grid
				sminpath=start[n][n].minpath;
			}
			*/
			it--;
			
			
			
			/*
			if(file!=null) {
				System.out.println("Its writing");
				file.format("%s %d %s %d %s %d %s %d %s","Start: ",s,"End: ",f,"Total Time: ",(f-s),"Evaluation Val: ",start[n][n].minpath,"\r\n");
			}	
			*/
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
		
		/*
		try {
			file = new Formatter("HCRResults.txt");
			
		}catch(FileNotFoundException e){
			System.out.println("Error/n/n/n/n");
		}	
		*/
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		init = EvaluationGrid.fillArr(init,eval);
		
		
		while(it!=0){
			
			
			if(it%r==0) {
				System.out.println("HCR RESET: "+sminpath+" "+start[n][n].minpath);
				EvaluationGrid.printTable(eval);
				EvaluationGrid.fillEval(init,eval);
				EvaluationGrid.printTable(eval);
				System.out.println("HCR RESET: "+sminpath+" "+start[n][n].minpath);
				//file.format("%s \r\n","RESET");
				
			}

			
			pre = EvaluationGrid.BasicHillClimb(eval);
		
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
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
			
			/*
			System.out.println("Start: "+s+"\nFinish: "+f+"\nTime Taken: "+(f-s));
			if(file!=null) {
				System.out.println("Its writing");
				file.format("%s %d %s %d %s %d %s %d %s","Start: ",s,"End: ",f,"Total Time: ",(f-s),"Evaluation Val: ",start[n][n].minpath,"\r\n");
			}*/	
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
		
		
		
		
		
		
		
		//file.format("\r\n%s %d\r\n","Best Eval: ",sminpath);
		
		//file.close();
		
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
		
		/*
		try {
			file = new Formatter("HCWResults.txt");
			
		}catch(FileNotFoundException e){
			System.out.println("Error/n/n/n/n");
		}*/	
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(it!=0){
			
			sminpath = start[n][n].minpath;
			
			pre = EvaluationGrid.BasicHillClimb(eval);
		
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
			System.out.println();
			EvaluationGrid.printResultTable(eval);
	
			val = rand.nextDouble();
			//revert back to original grid
			if(val>p){
				//file.format("%s %f\r\n","UpStep",p);
				if(start[n][n].minpath < sminpath){
					System.out.println(sminpath+" "+start[n][n].minpath);
					start[pre[1]][pre[2]].value = pre[0];
					System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
					EvaluationGrid.createGraph(eval);
					EvaluationGrid.printTable(eval);
					EvaluationGrid.createResult(eval);
				
				}
			}else{
				//file.format("%s %f\r\n","DownStep",p);
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
			
			/*
			System.out.println("Start: "+s+"\nFinish: "+f+"\nTime Taken: "+(f-s));
			if(file!=null) {
				System.out.println("Its writing");
				file.format("%s %d %s %d %s %d %s %d %s","Start: ",s,"End: ",f,"Total Time: ",(f-s),"Evaluation Val: ",start[n][n].minpath,"\r\n");
			}*/	
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
		
		
		
		
		//file.close();
		
		return start;
		
	}

	
	
	public static MinTurnNode[][] SA(MinTurnNode[][] start, int it, double t, double d){
		System.out.println("SA: \n");
		//Formatter file = null;
		int n = start.length-1;
		int s,f;
		int[] pre;
		int sminpath;
		Random rand = new Random();
        double val,p;
		
		/*
		try {
			file = new Formatter("SAResults.txt");
			
		}catch(FileNotFoundException e){
			System.out.println("Error/n/n/n/n");
		}*/	
		s = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(it!=0){
			
			sminpath = start[n][n].minpath;
			
			pre = EvaluationGrid.BasicHillClimb(eval);
		
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
			EvaluationGrid.createGraph(eval);
			EvaluationGrid.printTable(eval);
			EvaluationGrid.createResult(eval);
			
			//System.out.println(sminpath+" "+start[n][n].minpath);
			
			System.out.println();
			EvaluationGrid.printResultTable(eval);
	
			p = Math.exp((start[n][n].minpath-sminpath)/t); //Probability p = e^(V(j')-V(j))/t
			
			val = rand.nextDouble();
			//revert back to original grid
			if(val>p){
				//file.format("%s %f\r\n","UpStep",p);
				if(start[n][n].minpath < sminpath){
					System.out.println(sminpath+" "+start[n][n].minpath);
					start[pre[1]][pre[2]].value = pre[0];
					System.out.println("r: "+pre[1]+" c: "+pre[2]+" pre val: "+pre[0]);
					EvaluationGrid.createGraph(eval);
					EvaluationGrid.printTable(eval);
					EvaluationGrid.createResult(eval);
				
				}
			}else{
				//file.format("%s %f\r\n","DownStep",p);
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
			
			/*
			System.out.println("Start: "+s+"\nFinish: "+f+"\nTime Taken: "+(f-s));
			if(file!=null) {
				System.out.println("Its writing");
				file.format("%s %d %s %d %s %d %s %d %s","Start: ",s,"End: ",f,"Total Time: ",(f-s),"Evaluation Val: ",start[n][n].minpath,"\r\n");
			}*/	
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
		
		
		
		
		
		
		//file.close();
		
		return start;
		
	}
}
