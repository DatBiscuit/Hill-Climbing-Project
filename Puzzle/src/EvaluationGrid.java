
import javafx.scene.layout.GridPane;


public class EvaluationGrid {
	
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

}
