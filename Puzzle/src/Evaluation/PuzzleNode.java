package Evaluation;

/*	Contains value of a single grid box and points to the grid boxes reachable in each direction*/

public class PuzzleNode {
	
	int val = -1;
	int spath = -1;
	PuzzleNode up = null;
	PuzzleNode down = null;
	PuzzleNode left = null;
	PuzzleNode right = null;
	
	public PuzzleNode(int val){
		this.val = val;

	}
	
	//TODO Another default constructor why?
	public PuzzleNode() {
		
	}
}
