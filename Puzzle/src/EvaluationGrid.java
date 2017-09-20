
import java.util.LinkedList;
import java.util.Queue;

import Evaluation.MinTurnNode;


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
				if(i+dis<eval.length){
					eval[i][j].south = eval[i+dis][j];
				}
				if(i-dis>0){
					eval[i][j].north = eval[i-dis][j];
				}
				if(j+dis<eval.length){
					eval[i][j].east = eval[i][j+dis];
				}
				if(j-dis>0){
					eval[i][j].west = eval[i][j-dis];
				}
			}
		}
	}
	public static void createResult() {
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
	
	public static void printResultTable() {
		for(int i = 0; i < eval.length; i++) {
			for(int j = 0; j < eval.length; j++) {
				System.out.print(" " + eval[i][j].minpath + " ");
			}
			System.out.println();
		}
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
