import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Print statistics on Percolation: prompts the user for N and T, performs T
 * independent experiments on an N-by-N grid, prints out the 95% confidence
 * interval for the percolation threshold, and prints mean and std. deviation
 * of timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 */

public class PercolationStats {
	
	static double getMean(double[] data, int T){
		double sum = 0.0;
		for (double item: data){
			sum += item;
		}
		return sum/T;	
		}
	
	static double getVariance(double[] data, int T){
		if(T<=1){
		return Double.NaN;
		}
			else{
				double mean = getMean(data, T);
				double tmp = 0;
				for (double item: data){
					tmp += (item-mean)*(item-mean);
				}
				return tmp/(T-1);
			}
		}
	
	static double getStdDev(double[] data, int T){
		if(T<=1){
			return Double.NaN;
		}
		else{
			double var = getVariance(data, T);
			return Math.sqrt(var);
		}
	}
	static double totalTime(double[] data){
		double sum = 0.0;
		for (double item: data){
			sum += item;
		}
		return sum;
	}
	

	public static void main(String[] args) {
		int N, T;
		if (args.length == 2) { // use command-line arguments for
								// testing/grading
			N = Integer.parseInt(args[0]);
			T = Integer.parseInt(args[1]);
		} else {
			String input = JOptionPane.showInputDialog("Enter N and T", "20 100");
			String[] tmp = input.split(" "); //got help from TA on this part
			N = Integer.parseInt(tmp[0]);
			T = Integer.parseInt(tmp[1]);
		}
		
		
		// TODO: Perform T experiments for N-by-N grid
		double[] thresh = new double[T];
		double[] time = new double[T];
		ArrayList<Cell> myCells = new ArrayList<>();
		for (int x = 0; x<N; x++){
			for (int j = 0; j<N; j++){
				myCells.add(new Cell(x,j));
			}
		}
		for (int i = 0; i<T; i++){
			double start = System.currentTimeMillis();
			
			//Note: I would uncomment the IPercolate I wanted to run, then comment the others to gather the data
			
			//---------------------------------------------------------
			IPercolate perc = new PercolationDFS(N);
			//IPercolate perc = new PercolationUF(N, new QuickFind(N));
			//IPercolate perc = new PercolationUF(N, new QuickUWPC(N));
			//---------------------------------------------------------
			
			Collections.shuffle(myCells);
			int openedCells = 0;
			for (Cell cells : myCells){
				perc.open(cells.row, cells.col);
				openedCells++;
				if (perc.percolates()){
					thresh[i] = (openedCells*1.0/(N*N)); //add threshold to thresh
					break;
				}
			}
			double end = System.currentTimeMillis();
			time[i] = (end - start)/1000.0; //add time in (s) to time
		}
		
		// TODO:statistics and confidence interval
		double meanThresh = getMean(thresh, T);
		double devTime = getStdDev(time, T);
		double meanTime = getMean(time, T);
		double devThresh = getStdDev(thresh, T);
		double totalTime = totalTime(time);
		double confidenceIntpos = meanThresh + ((1.96*devThresh)/Math.sqrt(T));
		double confidenceIntneg = meanThresh - ((1.96*devThresh)/Math.sqrt(T));
		String confInt = "[" + Double.toString(confidenceIntneg) + ", " + Double.toString(confidenceIntpos) + "]";
		
		//print them
		System.out.println("Mean Percolation Threshold = " + meanThresh);
		System.out.println("StdDev Threshold = " + devThresh);	
		System.out.println("95% Confidence Interval = " + confInt);
		System.out.println("Total time = " + totalTime);
		System.out.println("Mean time per experiment = " + meanTime);
		System.out.println("StdDev Time = " + devTime);

	}
}
