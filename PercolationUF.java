/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	public int[][] myGrid;
	public IUnionFind uniter;
	public int top;
	public int bot;


	//IUnionFind
	//

	/**
	 * Constructs a Percolation object for a nxn grid that uses unionThing to
	 * store sets representing the cells and the top/source and bottom/sink
	 * virtual cells
	 */
	public PercolationUF(int n, IUnionFind unionThing) {
		// TODO complete PercolationUF constructor
		//initialize grid, state with room for top and bottom
		myGrid = new int[n][n];
		uniter = unionThing;
		uniter.initialize(n*n + 2);
		top = n*n;
		bot = n*n+1;
		for(int i = 0; i< n; i++){ //sets unique indexes for the virtual top and bottom
			uniter.union(top, getIndex(0,i));
			uniter.union(bot, getIndex(n-1,i));
		}
}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 */
	public int getIndex(int row, int col) {
		if (row < 0 || row >= myGrid.length || col < 0 || col>=myGrid.length){
			return OUT_BOUNDS;
		}
		else{
			return row *myGrid[0].length + col;
		}
	}

	public void open(int i, int j) {
		//mark cells open, connect it to surrounding area
		//need to union 4 times but need to make sure its not off the grid etc
		if (i < 0 || i >= myGrid.length || j < 0 || j>=myGrid.length){
			throw new NullPointerException("Index out of bounds");
		}

		myGrid[i][j] = OPEN;
		connect(i,j);
	}

	public boolean isOpen(int i, int j) {
		if (i < 0 || i >= myGrid.length || j < 0 || j>=myGrid.length){
			throw new NullPointerException("Index out of bounds");}
		//return false;}
		return (myGrid[i][j] == OPEN);
	}

	public boolean isFull(int i, int j) {
		if (i < 0 || i >= myGrid.length || j < 0 || j>=myGrid.length){
			throw new NullPointerException("Index out of bounds");
			//return false;
		}
		if (myGrid[i][j] == BLOCKED){
			return false;
		}
		return (uniter.connected(top, getIndex(i,j)));
	}

	public boolean percolates() {
		//uniter.connected(top virtual site, bottom virtual site)
		return (uniter.connected(top, bot));
	}

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 */
	private void connect(int row, int col) {
		//union with 4 adjacent doing checks for out of bounds
		//returns;
		if (getIndex(row, col) == -1){
			throw new NullPointerException("Index out of bounds");
		}
		if (row == 0){
			uniter.union(top, getIndex(row,col));
		}

		if (row == myGrid.length-1){
			uniter.union(bot, getIndex(row,col));
		}
		
	
		//up
		if (getIndex(row-1, col) != -1 && myGrid[row-1][col]!= BLOCKED){
			uniter.union(getIndex(row,col), getIndex(row-1,col));
		}
		//down
		if (getIndex(row+1, col) != -1 && myGrid[row+1][col]!= BLOCKED){
			uniter.union(getIndex(row,col), getIndex(row+1,col));
		}
		//right}
		if (getIndex(row, col+1) != -1 && myGrid[row][col+1]!= BLOCKED){
			uniter.union(getIndex(row,col), getIndex(row,col+1));
		}
		//left
		if (getIndex(row, col-1) != -1 && myGrid[row][col-1]!= BLOCKED){
			uniter.union(getIndex(row,col), getIndex(row,col-1));}
	}
}










