package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public int[] grid;           //使用整数数组做instance variable
    public int d = Math.abs(grid.length);    //边长N
    public WeightedQuickUnionUF uf;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
        if (N <= 0){
            throw new RuntimeException("java.lang.IndexOutOfBoundsException");
        }
        grid = new int[N*N];
        for(int i = 0; i < d*d; i++){
            grid[i] = 0;  // 0 means blocked
        }
        uf = new WeightedQuickUnionUF(d*d + 1);
    }

    static int[][] directions = new int[][]{{1, 0},{-1, 0},{0, 1},{0, -1}};
    public void  unionAround(int row, int col){
        for (int[] dir : directions){
            int n_row = row + dir[0];
            int n_col = col + dir[1];
            if (n_row >= 0 && n_row <= d && n_col >= 0 && n_col <= d && grid[n_row*d +col] == 1){
                uf.union(n_row*d +col, n_row*d + n_col);
            }

        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col){
        validate(row*d + col);
        grid[row*d + col] = 1; // 1 means open
        uf.union(d*d+1, row*d + col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validate(row*d + col);
        return grid[row * d + col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row*d + col);
        return uf.find(row*d + col) < 0;
    }

    // number of open sites
    public int numberOfOpenSites(){
        return  1;
    }

    // does the system percolate?
    public boolean percolates(){
        return true;
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args){
    }

    // Throws an exception if v1 is not a valid index.
    private void validate(int N) {
        if(N < 0 || N > d*d){
            throw new IllegalArgumentException("java.lang.IndexOutOfBoundsException");
        }
    }
}