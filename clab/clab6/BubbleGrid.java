public class BubbleGrid {
    private int[][] grid;
    private int row = grid.length;
    private int col = grid[0].length;
    private int size = row * col; //the number of digits grid have
    private UnionFind uf;

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
        uf = new UnionFind(size + 1);

        int[] result; //return the num of
        result = new int[darts.length];

        /*-------------------------------------标记射中的泡泡----------------------------------------------*/
        for (int[] dart : darts) {         //增强for循环
            if (grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 2;
            }
        }
        /*-------------------------------------射击后连接泡泡----------------------------------------------*/


        return result;
    }

    private int stretch(int x, int y){return x * row +y;}

    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    //把（x，y）和周围等于1的并起来
    private void unionAround(int x, int y){
        for (int[] dir : directions){
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < row && ny >= 0 && ny < col && grid[nx][ny] == 1){    //使用if能限制范围
                uf.union(stretch(x,y), stretch(nx, ny));
            }
            //如果第一行的1周围都为0，上面if不会执行，但是这个泡泡也不会掉下来，所以有
            if (x == 0) {
                uf.union(stretch(x,y), 0);
            }
        }
    }
}
