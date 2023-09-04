package bearmaps;

import java.util.List;

public class KDTree implements PointSet{
    private Node root;

    private static class Node{
        Point point;
        Node leftChild;
        Node rightChild;

        Node(Point p){
            point = p;
        }
    }

    public KDTree(List<Point> points){
        for (Point p : points){
            root = add(p, root, 'x');
        }
    }

    @Override
    /*返回距离输入坐标最近的点*/
    public Point nearest(double x, double y){
        return  nearest(root, new Point(x, y), root, 'x' ).point;
    }

    private Node nearest(Node curNode, Point goal, Node best, char axis){
        if (curNode == null){
            return best;
        }

        if (Point.distance(curNode.point, goal) < Point.distance(best.point, goal)){
            best = curNode;
        }

        double axisDistence = axisDiff(goal, curNode.point, axis);

        Node goodSide;
        Node badSide;

        if (axisDistence <= 0){
            goodSide = curNode.leftChild;
            badSide = curNode.rightChild;
        }else {
            goodSide = curNode.rightChild;
            badSide = curNode.leftChild;
        }

        best = nearest(goodSide, goal, best, switchAxis(axis));

        /* goodSide 找完之后，看看badSide有没有更进的点
        * 为什么使用距离的平方？
        * 因为我们只在乎最近点，而对于那些可能的最近点，距离目标的距离和某一个方向上的平方近似
        * */
        if (axisDistence * axisDistence < Point.distance(goal, best.point)) {
            best = nearest(badSide, goal, best, switchAxis(axis));
        }

        return best;
    }


    /*各个点已经划分出来不同的范围，将这些点加入到树当中(无论二叉树还是什么的)，计算机才可以根据这些点寻找到任何地方最近的点
    * 1.选择一个root开始
    * 2.先根据x划分（点和空间）两部分（这里根据距离）
    * 3.递归的给子节点（换一个方向的轴xyz...）重复上面操作
    * */
    private Node add(Point p, Node node, char axis) {
        if (node == null) {
            return new Node(p);
        }
        if (axisDiff(p, node.point, axis) <= 0) {
            node.leftChild = add(p, node.leftChild, switchAxis(axis));
        } else {
            node.rightChild = add(p, node.rightChild, switchAxis(axis));
        }
        return node;
    }


    private double axisDiff(Point p1, Point p2, char axis) {
        if (axis == 'x') {
            return p1.getX() - p2.getX();
        } else {
            return p1.getY() - p2.getY();
        }
    }

    /*切换维度(轴)，划分范围*/
    private char switchAxis(char axis) {
        if (axis == 'x') {
            return 'y';
        } else {
            return 'x';
        }
    }

}
