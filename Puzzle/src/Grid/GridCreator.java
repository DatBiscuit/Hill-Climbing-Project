package Grid;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class GridCreator {
	
	public static int gridSize = 3;
	private static Tile[][] grid;
	private static Queue<Tile> tileQueue = new LinkedList<Tile>();

	enum neighborType {
		LEFT,RIGHT,ABOVE,BELOW;
	}
	
//	public static void main(String[] args) {
//		makeGrid();
//	}
	
	public static Tile[][] getGrid() {
		return grid;
	}
	
	public static void makeGrid() {
		generateGrid();
		System.out.println("%%% Printing Grid %%%");
		printGrid();
		evaluateGrid();
		System.out.println("%%% Printing Evaluation Grid %%%");
		printEvaluationGrid();
	}
	
	private static void generateGrid() {
		grid = new Tile[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				int currentNumber = generateRandomNumber();
				grid[i][j] = new Tile(currentNumber, i , j);
			}
		}
		grid[gridSize - 1][gridSize - 1] = new Tile(0, gridSize - 1, gridSize - 1);
	}
	
	private static int generateRandomNumber() {
	   	Random rand = new Random();
	   	int randomNumber = rand.nextInt(gridSize);
	   	while(randomNumber == 0) {
	   		 randomNumber = rand.nextInt(gridSize);
	   	}
   		return randomNumber;
	}
	
	public static void printGrid() {
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				System.out.print(" " + grid[i][j].number + " ");
			}
			System.out.println();
		}
	}
	
	public static void printEvaluationGrid() {
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				System.out.print(" " + grid[i][j].evaluationValue + " ");
			}
			System.out.println();
		}
	}
	
	private static void evaluateGrid() {
		grid[0][0].evaluationValue = "0";
		tileQueue.add(grid[0][0]);
		int tileEvaluationNumber = 0;

		while(!tileQueue.isEmpty()) {
			lookAt(tileEvaluationNumber);
			tileEvaluationNumber++;
		}
	}
	
	private static void lookAt(int evaluationNumber) {
		if(tileQueue.size() == 1) {
			Tile currentTile = tileQueue.remove();
			viewNeighbors(currentTile);
		} else {
			while(!tileQueue.isEmpty() && Integer.parseInt(tileQueue.peek().evaluationValue) == evaluationNumber) {
				Tile currentTile = tileQueue.remove();
				viewNeighbors(currentTile);
			}
		}
			
		System.out.println(tileQueue.size());

	}
	

	//TODO Enum directions
	private static void viewNeighbors(Tile currentTile) {
		checkNeighbor(currentTile, "left");
		checkNeighbor(currentTile, "right");
		checkNeighbor(currentTile, "above");
		checkNeighbor(currentTile, "below");
	}
	
	private static void checkNeighbor(Tile currentTile, String neighborType) {
		int[] coords = coordinates(currentTile, neighborType);
		int x = coords[0];
		int y = coords[1];
		
		try {
			Tile neighbor = grid[x][y];
			if(tileQueue.contains(neighbor) || !neighbor.evaluationValue.equals("X")) {
				System.out.println("Tile already in Queue or Visited Already: " + neighbor + " " + neighbor.evaluationValue);
				return;
			} else {
				int evaluatedValue = Integer.parseInt(currentTile.evaluationValue);
				grid[x][y].evaluationValue = Integer.toString(evaluatedValue + 1);
				tileQueue.add(grid[x][y]);
			}
		} catch (Exception e) {
			System.out.println(neighborType + " " +"Neighbor Error: " + e);
		}
	}
	
	private static int[] coordinates(Tile currentTile, String neighborType) {
		int[] coords = {0,0};
		if(neighborType.equals("left")) {
			coords[0] = currentTile.x - currentTile.number;
			coords[1] = currentTile.y;
			return coords;
		} else if(neighborType.equals("right")) {
			coords[0] = currentTile.x + currentTile.number;
			coords[1] = currentTile.y;
			return coords;
		} else if(neighborType.equals("above")) {
			coords[0] = currentTile.x;
			coords[1] = currentTile.y - currentTile.number;
			return coords;
		} else if(neighborType.equals("below")) {
			coords[0] = currentTile.x;
			coords[1] = currentTile.y + currentTile.number;
			return coords;
		} else {
			System.out.println("Error, Incorrect type entered");
			return coords;
		}
	}
	
}
