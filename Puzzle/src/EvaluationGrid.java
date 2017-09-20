
import java.util.ArrayList;

import Evaluation.MinTurnNode;
import javafx.scene.layout.GridPane;


public class EvaluationGrid {
	
	
	public static MinTurnNode[][] eval;
	public static MinTurnNode[][] result;
	
	public static void evalGrid(int size) {
		eval = new MinTurnNode[size][size];
		result = new MinTurnNode[size][size];
	}
	
	public static void setTable(int r, int c, int val) {
		eval[r][c] = new MinTurnNode(r,c,val);
		//System.out.println(val+" "+ eval[x][y].value);
		return;
	}
	
	public static void printTable() {
		for(int i = 0; i < eval.length; i++) {
			for(int j = 0; j < eval.length; j++) {
				System.out.print(" " + eval[i][j].value + " ");
			}
			System.out.println();
		}
	}
	public static void createGraph(){
		int dis;
		for(int i = 0; i< eval.length;i++){
			for(int j = 0; j<eval.length;j++){
				dis = eval[i][j].value;
				if(i+dis>eval.length-1){
					eval[i][j].south = eval[i+dis][j];
				}
				if(i-dis<0){
					eval[i][j].north = eval[i-dis][j];
				}
				if(j+dis>eval.length-1){
					eval[i][j].east = eval[i][j+dis];
				}
				if(j-dis<0){
					eval[i][j].west = eval[i][j-dis];
				}
			}
		}
	}
	public static void createResult() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
/*	
	public static int[][] eval;
	public static int[][] result;
	
	public static void evalGrid(int size) {
		eval = new int[size][size];
		result = new int[size][size];
	}
	
	public static void setTable(int x, int y, int val) {
		eval[x][y] = val;
		return;
	}
	
	public static void printTable() {
		for(int i = 0; i < eval.length; i++) {
			for(int j = 0; j< eval.length; j++) {
				System.out.print(" " + eval[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void createResult() {
		
	}
*/
}