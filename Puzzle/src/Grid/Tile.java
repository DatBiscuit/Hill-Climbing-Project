package Grid;

public class Tile {
	
	int x,y;
	int number;
	String evaluationValue;
	
	
	public Tile(int number, int x, int y) {
		this.number = number;
		this.evaluationValue = "X";
		this.x = x;
		this.y = y;
	}
	
	public void setEvaluationValue(String evaluationValue) {
		this.evaluationValue = evaluationValue;
	}
	
}
