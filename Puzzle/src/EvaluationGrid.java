
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import Evaluation.MinTurnNode;


public class EvaluationGrid {
	
	
	//public static MinTurnNode[][] eval;
	
	
	public static MinTurnNode[][] evalGrid(int size) {
		MinTurnNode[][] eval = new MinTurnNode[size][size];
		return eval;
	}
	
	public static void setTable(int r, int c, int val, MinTurnNode[][] eval) {
		eval[r][c] = new MinTurnNode(r,c,val);
		//System.out.println(val+" "+ eval[x][y].value);
		return;
	}
	
	public static void printTable(MinTurnNode[][] eval) {
		for(int i = 0; i < eval.length; i++) {
			for(int j = 0; j < eval.length; j++) {
				System.out.print(" " + eval[i][j].value + " ");
			}
			System.out.println();
		}
	}
	
	/*
	 * creates the edges between the nodes
	 */
	public static void createGraph(MinTurnNode[][] eval){
		int dis;
		for(int i = 0; i< eval.length;i++){
			for(int j = 0; j<eval.length;j++){
				dis = eval[i][j].value;
				eval[i][j].minpath = -1;	
				resetEdge(i,j,eval);
				if(i+dis<eval.length){
					eval[i][j].south = eval[i+dis][j];
				}
				if(i-dis>=0){
					eval[i][j].north = eval[i-dis][j];
				}
				if(j+dis<eval.length){
					eval[i][j].east = eval[i][j+dis];
				}
				if(j-dis>=0){
					eval[i][j].west = eval[i][j-dis];
				}
			}
		}
	}
	
	/*
	 * creates the evaluation grid 
	 */
	public static void createResult(MinTurnNode[][] eval) {
		int[][] visited = new int[eval.length][eval.length];
		Queue<MinTurnNode> queue = new LinkedList<MinTurnNode>();
		MinTurnNode Temp;
		queue.add(eval[0][0]);
		eval[0][0].minpath = 0;
		
		while(!queue.isEmpty()){
			Temp = queue.poll();
			if(visited[Temp.r][Temp.c]==0){
				
				visited[Temp.r][Temp.c]=1;
				
				if(Temp.north!=null){
					queue.add(Temp.north);
					if(Temp.north.minpath==-1){
						Temp.north.minpath = Temp.minpath+1;
					}
				}
				if(Temp.south!=null){
					queue.add(Temp.south);
					if(Temp.south.minpath==-1){
						Temp.south.minpath = Temp.minpath+1;
					}
					
				}
				if(Temp.east!=null){
					queue.add(Temp.east);
					if(Temp.east.minpath==-1){
						Temp.east.minpath = Temp.minpath+1;
					}
					
				}
				if(Temp.west!=null){
					queue.add(Temp.west);
					if(Temp.west.minpath==-1){
						Temp.west.minpath = Temp.minpath+1;
					}
					
				}
				
			}
			
			
		}
		
	}
	
	public static GridPane rePrintTable(MinTurnNode[][] eval ){
		GridPane root = new GridPane();
		
		for(int i = 0; i < eval.length; i++) {
			for(int j = 0; j < eval.length; j++) {
				//setting the text for the GUI
				TextField tf = new TextField();
				tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                if(eval[i][j].minpath == -1) {
                	tf.setText(" X ");
                } else {
                	tf.setText(" " + eval[i][j].value + " ");
                }
                root.setRowIndex(tf,i);
                root.setColumnIndex(tf,j);    
                root.getChildren().add(tf);
			}
		}
		return root;
	}
	
	public static GridPane printResultTable(MinTurnNode[][] eval) {
		GridPane root = new GridPane();
		int evalFunction = 0;
		
		for(int i = 0; i < eval.length; i++) {
			for(int j = 0; j < eval.length; j++) {
				//setting the text for the GUI
				TextField tf = new TextField();
				tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                if(eval[i][j].minpath == -1) {
                	tf.setText(" X ");
                	evalFunction--;
                } else {
                	tf.setText(" " + eval[i][j].minpath + " ");
                }
                root.setRowIndex(tf,i);
                root.setColumnIndex(tf,j);    
                root.getChildren().add(tf);
				System.out.print(" " + eval[i][j].minpath + " ");
			}
			System.out.println();
		}
		if(eval[eval.length-1][eval.length-1].minpath==-1){
		System.out.println(evalFunction);
		root.addRow(eval.length + 1, new Label("Evaluation value: " + evalFunction));
		}else{
			System.out.println(eval[eval.length-1][eval.length-1].minpath);
			root.addRow(eval.length + 1, new Label("Evaluation value: " + eval[eval.length-1][eval.length-1].minpath));

		}
		return root;
		
	}
	public static void resetEdge(int i, int j,MinTurnNode[][] eval) {
		eval[i][j].north=null;
		eval[i][j].south=null;
		eval[i][j].east=null;
		eval[i][j].west=null;
		
	}
	
	public static int[][] fillArr(int[][]temp,MinTurnNode[][] eval){
		for(int i = 0; i<eval.length;i++) {
			for(int j=0;j<eval.length;j++) {
				temp[i][j]=eval[i][j].value;
			}
		}
		
		return temp;
		
	}

	public static void fillEval(int[][]temp,MinTurnNode[][] eval){
		for(int i = 0; i<eval.length;i++) {
			for(int j=0;j<eval.length;j++) {
				eval[i][j].value=temp[i][j];
			}
		}
	
		
	}
	
	public static int[] BasicHillClimb(MinTurnNode[][] eval){
		int[] pre = new int[3];
		Random rand = new Random();
        int val = rand.nextInt(eval.length);
        while(val == 0) {
        	val = rand.nextInt(eval.length);
        }
        int r = rand.nextInt(eval.length);
        int c = rand.nextInt(eval.length);
        while(r!=eval.length-1 && c!=eval.length-1){
        	r = rand.nextInt(eval.length);
        	c = rand.nextInt(eval.length);
        }
        pre[0]=eval[r][c].value;
        pre[1] = r;
        pre[2] = c;
        
        eval[r][c].value=val;
        
        return pre;
        
	}

}
