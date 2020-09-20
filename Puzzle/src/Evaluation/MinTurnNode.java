package Evaluation;

public class MinTurnNode {
	
	//TODO Anyway to condense this?
	public int value;
	public MinTurnNode north;
	public MinTurnNode south;
	public MinTurnNode west;
	public MinTurnNode east;
	public int r,c;
	public int minpath = -1;
	
	//TODO Why is there a default constructor? Try to do away with htis
	public MinTurnNode(){
		
	}
	
	public MinTurnNode( int r, int c,int num) {
		this.value = num;
		this.r = r;
		this.c = c;
	}

}
