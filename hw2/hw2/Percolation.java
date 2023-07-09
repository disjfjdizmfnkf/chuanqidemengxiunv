package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int[] grid;           //使用整数数组做instance variable
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private int opened_counter = 0;
    /** create N-by-N grid, with all sites initially blocked*/
    public Percolation(int N){
        this.N = N;
        if (N <= 0){
            throw new RuntimeException("java.lang.IndexOutOfBoundsException");
        }
        grid = new int[N*N];
        for(int i = 0; i < N * N; i++){
            grid[i] = 0;  // 0 means blocked
        }
        uf = new WeightedQuickUnionUF(N * N + 2);  //空出位置 0 和 N*N+1
        ufWithoutBottom = new WeightedQuickUnionUF(N * N +1); //空出位置 0
    }

    private int uf_index(int row, int col){return row*N + col + 1;}
    private int[][] directions = {{1, 0},{-1, 0},{0, 1},{0, -1}};
    public void  unionAround(int row, int col){
        for (int[] dir : directions){
            int x = row + dir[0];
            int y = col + dir[1];
            if (x >= 0 && x < N && y >= 0 && y < N && isOpen(x, y)){   //控制在范围内
                uf.union(uf_index(x, y), uf_index(row, col));
                ufWithoutBottom.union(uf_index(x, y), uf_index(row, col));
            }
        }
        if (row == 0){
            uf.union(0, uf_index(row, col));   //开通的并在0集合里面
            ufWithoutBottom.union(0, uf_index(row, col));
        }
        if (row == N - 1){
            uf.union(uf_index(row, col), N*N + 1);
        }
    }

    /** open the site (row, col) if it is not open already*/
    public void open(int row, int col){
        validate(row* N + col);
        grid[row* N + col] = 1;
        opened_counter += 1;
        unionAround(row, col);
    }

    /** is the site (row, col) open?*/
    public boolean isOpen(int row, int col){
        validate(row* N + col);
        return grid[row * N + col] == 1;
    }

    /** is the site (row, col) full?*/
    public boolean isFull(int row, int col) {
        validate(row* N + col);
        return ufWithoutBottom.connected(0, row* N + col);
    }

    /** 所有open的个数 */
    public int numberOfOpenSites(){
        return  opened_counter;
    }

    /** does the system percolate?*/
    public boolean percolates(){
        return uf.connected(0, N*N +1);
    }

    /** use for unit testing (not required, but keep this here for the autograder)*/
    public static void main(String[] args){
    }

    /** Throws an exception if v1 is not a valid index.*/
    private void validate(int N) {
        if(N < 0 || N > this.N * this.N){
            throw new IllegalArgumentException("java.lang.IndexOutOfBoundsException");
        }
    }

}