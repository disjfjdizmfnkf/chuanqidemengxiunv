public class BubbleGrid {
    private int[][] grid;
    private int rows;
    private int cols;
    private UnionFind uf;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        // TODO
        uf = new UnionFind(rows * cols + 1);

        /*-------------------------------------标记射中的泡泡----------------------------------------------*/
        for (int[] dart : darts) {         //增强for循环
            if (grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 2;
            }
        }

        /*-------------------------------------射击后连接泡泡----------------------------------------------*/
        /* Union all adjacent bubbles. */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    unionAround(i, j);
                }
            }
        }

        /*-------------------------------------回到初始情况返回差值----------------------------------------------*/
        int[] result = new int[darts.length];

        for (int i = darts.length - 1; i >= 0; i--){ //ps这是一个逆向的过程，切记循环方式也应逆向
            int[] dart = darts[i];
            int stuckNum = uf.sizeOf(0);  //after darts

            if (grid[dart[0]][dart[1]] == 2){
                grid[dart[0]][dart[1]] = 1;
                unionAround(dart[0],dart[1]);
                int newStuckNum = uf.sizeOf(0);
                if (newStuckNum - stuckNum > 1) {  // popped bubble doesn't count as fall
                    result[i] = newStuckNum - stuckNum - 1;
                }
            } else {  // dart hits an empty position
                result[i] = 0;
            }
        }
        return result;
    }

    private int stretch(int x, int y){return x * cols + y + 1;}

    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    //把（x，y）和周围等于1的并起来
    private void unionAround(int x, int y){
        for (int[] dir : directions){
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && grid[nx][ny] == 1){    //使用if能限制范围
                uf.union(stretch(x,y), stretch(nx, ny));
            }
            //如果第一行的1周围都为0，上面if不会执行，但是这个泡泡也不会掉下来，所以有
            if (x == 0) {
                uf.union(stretch(x,y), 0);
            }
        }
    }
}