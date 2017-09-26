
import java.util.Random;

import Evaluation.MinTurnNode;

public class PopulationGA {

		public static MinTurnNode[][] Pop(int size, int n, int it){
			MinTurnNode[][][] pop = new MinTurnNode [size][n][n];
			MinTurnNode[][]best= null;
			 Random rand = new Random();
             int rand1;
            
			for(int i = 0;i<size; i++) {// Creates initial population
				
				EvaluationGrid.setTable(n-1,n-1,0,pop[i]);
				for(int j = 0;j<n;j++) {
					for(int k = 0;k<n;k++) {
						if(!(j==n-1&&k==n-1)) {
							rand1 = rand.nextInt(size);
							while(rand1 == 0) {
								rand1 = rand.nextInt(size);
							}
							EvaluationGrid.setTable(j,k,rand1,pop[i]);
						}
					}
				}
				
				EvaluationGrid.createGraph(pop[i]);
				EvaluationGrid.printTable(pop[i]);
				EvaluationGrid.createResult(pop[i]);	
			}
			
			
			
			
			
			
			return best;
		}
		
		public static int[] selection(MinTurnNode[][][] pop,int size, int n) {
			int[] parents = new int[size];
			Random rand = new Random();
			int one,two;
			double p1,p2;
			int totaleval=0;
			for(int i = 0; i<size; i++) {
				totaleval += pop[size][n][n].minpath;
			}
			
			
			for(int i = 0; i<size; i++) {
				one = rand.nextInt(size);
				two = rand.nextInt(size);
				while(one== two) {
					two = rand.nextInt(size);
				}
				p1 = ((double)pop[one][n][n].minpath)/totaleval;
				p2 = ((double)pop[two][n][n].minpath)/totaleval;
				
				if(p1>p2) {
					parents[i]=one;
				}else {
					parents[i]=two;
				}
			}
			
			
			return parents;
		}
		
		public static MinTurnNode[][][] crossOver(int[] parent, MinTurnNode[][][] pop, int size, int n){
			MinTurnNode[][][] nextGen = new MinTurnNode[size][n][n];
			Random rand = new Random();
			int rand1 = rand.nextInt(n);
			int temp;
			
			while(rand1==0 || rand1 == n-1) {
				rand1 = rand.nextInt(n);
			}
			if(size%2==0) {
				for(int i = 0;i<size;i+=2) {
					for(int j=0;j<n;j++) {
						for(int k=0;k<rand1;k++) {
								if(k<rand1) {
									EvaluationGrid.setTable(j,k,pop[parent[i+1]][j][k].value,nextGen[parent[i]]);
									EvaluationGrid.setTable(j,k,pop[parent[i]][j][k].value,nextGen[parent[i+1]]);
									
									
									temp=pop[parent[i]][j][k].value;
									pop[parent[i]][j][k].value=pop[parent[i+1]][j][k].value;
									pop[parent[i+1]][j][k].value=temp;
								}
						}
					}
				}
			}
			
			
			return pop;
		}
		
}
