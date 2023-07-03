public class BubbleGrid {
    private int[][] grid;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        // TODO
        int n = grid.length;
        int m = grid[0].length;
        int size = m*n; //the number of digits grid have
        UnionFind uf = new UnionFind(size+1);

        int []result;
        result = new int [darts.length];

        for (int i = 0; i < darts.length; i++){
            if(grid[darts[i][0]][darts[i][1]] == 0){
                result[i] = 0;
            }
            else {
                int befor = uf.sizeOf(size+1);
                grid[darts[i][0]][darts[i][1]] = 0;
                for (int j = 0;j < m; j++){
                    if (grid[0][j] == 1){continue;}
                    if (grid[1][j-1] == 1 || grid[1][j+1] == 1){continue;}
                    else {
                     uf.union(n+j, size+1);
                     grid[1][j] = 0;
                    }
                }
                result[i] = befor - uf.sizeOf(size+1);
            }
        }

        return result;
    }
}
