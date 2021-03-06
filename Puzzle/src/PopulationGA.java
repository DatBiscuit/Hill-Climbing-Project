
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import java.util.Random;

import Evaluation.MinTurnNode;

public class PopulationGA {

		public static MinTurnNode[][] Pop(int size, int n, int it){
			MinTurnNode[][][] pop = new MinTurnNode [size][n][n];
			MinTurnNode[][]best= null;
			int[]parents;
			 Random rand = new Random();
             int rand1,s,f;
             
             
            
             s = Calendar.getInstance().get(Calendar.MILLISECOND);
            
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
			
			while(it>0) {// goes through iterations
				
				parents = selection(pop,size,n);
				pop = crossOver(parents,pop,size,n);
				mutation(pop,size);
				for(int i =0;i<size;i++) {
					EvaluationGrid.createGraph(pop[i]);
					EvaluationGrid.printTable(pop[i]);
					EvaluationGrid.createResult(pop[i]);	
				}
				
				it--;
				
			}
			
			best = best(pop,size,n);
			f = Calendar.getInstance().get(Calendar.MILLISECOND);
			
			//File file = new File("SAResults.txt");
			FileWriter fw=null;
			try {
				fw = new FileWriter("PBResults.txt",true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in creating fileWriter/n/n/n/n");
				e.printStackTrace();
			}
			 try {
				fw.write("Pop Size: "+size+"n: "+n+" Start: "+s+" End: "+f+" Total Time: "+(f-s)+"\n");
				fw.close();
			} catch (IOException e) {
				System.out.println("Error in writing/n/n/n/n");
				e.printStackTrace();
			}
			
			
			return best;
		}
		
		public static int[] selection(MinTurnNode[][][] pop,int size, int n) {//returns array of parent indexes for nextgen
			int[] parents = new int[size];
			Random rand = new Random();
			int one,two;
			double p1,p2;
			int totaleval=0;
			for(int i = 0; i<size; i++) {
				totaleval += pop[size-1][n-1][n-1].minpath;
			}
			
			
			for(int i = 0; i<size; i++) {
				one = rand.nextInt(size);
				two = rand.nextInt(size);
				while(one== two) {
					two = rand.nextInt(size);
				}
				p1 = ((double)pop[one][n-1][n-1].minpath)/totaleval;
				p2 = ((double)pop[two][n-1][n-1].minpath)/totaleval;
				
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
								}else {
									EvaluationGrid.setTable(j,k,pop[parent[i]][j][k].value,nextGen[parent[i]]);
									EvaluationGrid.setTable(j,k,pop[parent[i+1]][j][k].value,nextGen[parent[i+1]]);
								}
						}
					}
				}
			}
			
			
			return nextGen;
		}
		
		public static void mutation(MinTurnNode[][][] pop,int size) {// uses hillclimb to mutate
			for(int i=0;i<size;i++) {
				EvaluationGrid.BasicHillClimb(pop[i]);
			}
		}
		
		public static MinTurnNode[][] best(MinTurnNode[][][]pop, int size, int n){
			int heval=pop[0][n-1][n-1].minpath;
			int id = 0;
			for(int i = 1;i<size;i++) {
				if(pop[i][n-1][n-1].minpath>heval) {
					heval = pop[i][n-1][n-1].minpath;
					id = i;
				}
			}
			
			return pop[id];
		}
}
