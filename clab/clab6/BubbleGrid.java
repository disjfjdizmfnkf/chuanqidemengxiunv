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

        int []result; //return the num of
        result = new int [darts.length];

        /*                 标记射中的泡泡                        */
        for(int[] dart : darts){         //增强for循环
            if (grid[dart[0]][dart[1]] == 1){
                grid[dart[0]][dart[1]] = 0;
            }
        }

        /*                                                */

        return result;
    }
}
