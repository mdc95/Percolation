import java.util.Arrays;

/**
 * Simulate percolation thresholds for a grid-base system using depth-first-search,
 * aka 'flood-fill' techniques for determining if the top of a grid is connected
 * to the bottom of a grid.
 * <P>
 * Modified from the COS 226 Princeton code for use at Duke. The modifications
 * consist of supporting the <code>IPercolate</code> interface, renaming methods
 * and fields to be more consistent with Java/Duke standards and rewriting code
 * to reflect the DFS/flood-fill techniques used in discussion at Duke.
 * <P>
 * @author Kevin Wayne, wayne@cs.princeton.edu
 * @author Owen Astrachan, ola@cs.duke.edu
 * @author Jeff Forbes, forbes@cs.duke.edu
 */


public class PercolationDFS implements IPercolate {
	// possible instance variable for storing grid state
	public int[][] myGrid;

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFS(int n) {
		myGrid = new int[n][n];
		for (int i = 0; i<n; i++){
			Arrays.fill(myGrid[i], BLOCKED);
		}		
	}

	public void open(int i, int j) {
		myGrid[i][j] = OPEN;
		
		for (int row = 0; row < myGrid[0].length; row++){
			for (int col = 0; col < myGrid[0].length; col++){
				if (isFull(row, col)){
				myGrid[row][col] = OPEN;
			}
		}
		}
		for (int x =  0; x <myGrid[0].length; x++){
				dfs(0,x);
		}
	}
		

	
		
		//clear all the full cells to be open
		//run dfs from source cells on top
		


	public boolean isOpen(int i, int j) {
		if (myGrid[i][j] == OPEN){
			return true;
		}
		return false;
	}

	public boolean isFull(int i, int j) {
		if(myGrid[i][j] == FULL){
			return true;
		}
		return false;
	}

	public boolean percolates() {
		for (int j =0; j<myGrid[0].length; j++){
			int size = myGrid[0].length;
			if(myGrid[size - 1][j] == FULL){
				return true;
			}
		}
		return false;
	}

	/**
	 * Private helper method to mark all cells that are open and reachable from
	 * (row,col).
	 * 
	 * @param row
	 *            is the row coordinate of the cell being checked/marked
	 * @param col
	 *            is the col coordinate of the cell being checked/marked
	 */
	private void dfs(int row, int col) {
		//out of bounds check
		if (row < 0 || row >= myGrid.length || col < 0 || col>=myGrid[0].length){
			return;
		}
		
		if (!isOpen(row,col) ||  isFull(row,col)){
			return;
		}
		myGrid[row][col] = FULL;
		dfs(row-1, col);
		dfs(row, col - 1);
		dfs(row + 1, col);
		dfs(row, col + 1);

	}

}
