
import java.util.Random;

import Evaluation.MinTurnNode;

public class PopulationGA {

		public static MinTurnNode[][] Pop(int size, int n, int it){
			MinTurnNode[][][] pop = new MinTurnNode [size][n][n];
			 Random rand = new Random();
             int rand1;
            
			for(int i = 0;i<size; i++) {// Creates initial population
				
				EvaluationGrid.setTable(n,n,0,pop[i]);
				for(int j = 0;j<n-1;j++) {
					for(int k = 0;k<n-1;k++) {
						rand1 = rand.nextInt(size);
			            while(rand1 == 0) {
			             	rand1 = rand.nextInt(size);
			            }
			            EvaluationGrid.setTable(j,k,rand1,pop[i]);
					}
				}
				
				EvaluationGrid.createGraph(pop[i]);
				EvaluationGrid.printTable(pop[i]);
				EvaluationGrid.createResult(pop[i]);	
			}
			
			
			
			
			
			
			return best;
		}
		
		public static int[] selection(MinTurnNode[][][] pop,int size) {
			int[] parents = new int[size];
			Random rand = new Random();
			int one,two;
			
			for(int i = 0; i<size; i++) {
				one = rand.nextInt(size);
				two = rand.nextInt(size);
				while(one== two) {
					two = rand.nextInt(size);
				}
			//turn this into probablities based on best total probablity solution being  ie minpath/ (minpath1+ minpath2+...+minpathn)
				if(pop[one][size][size].minpath>pop[two][size][size].minpath) {
					parents[i]=one;
				}else {
					parents[i]=two;
				}
			}
			
			
			return parents;
		}
		
		public static MinTurnNode[][][] crossOver(int[] parent, MinTurnNode[][][] pop, int size){
			
			
			
			
			return pop;
		}
		
}
