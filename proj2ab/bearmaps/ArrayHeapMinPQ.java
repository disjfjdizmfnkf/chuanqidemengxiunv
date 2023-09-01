package bearmaps;

import edu.princeton.cs.algs4.Heap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
    private ArrayList<heapNode<T>> heap;
    private HashMap<T, Integer> indexMap;

    private static class heapNode<T>{     //ADT
        private T item;
        private double priority;       //作为插入位置的指标，为元素大小排序的依据

        heapNode(T i, double pr){  //构造函数
            item = i;
            priority = pr;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double p) {
            priority = p;
        }

    }

    public ArrayHeapMinPQ(){  //构造函数
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)){
            throw new IllegalArgumentException("item is already present");
        }
        heap.add(new heapNode<>(item, priority));
        indexMap.put(item, size() - 1);
        swimUp(size() - 1);         //每次添加在整个heap最右下角
    }

    @Override
    public boolean contains(T item) {return indexMap.containsKey(item);}

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size() == 0){
            throw new NoSuchElementException("PQ is empty");
        }
        return heap.get(0).getItem();
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size() == 0){
            throw new NoSuchElementException("PQ is empty");
        }
        T whichToRemove = heap.get(0).getItem();
        heap.set(0, heap.get(size() - 1));
        indexMap.put(heap.get(0).getItem(), 0);
        heap.remove(size() - 1);
        indexMap.remove(whichToRemove);
        if (size() != 0){
            swimDown(0);
        }
        return whichToRemove;
    }

    @Override
    public int size() {
        return heap.size();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)){
            throw new NoSuchElementException("The item doesn't exist");
        }
        int index = indexMap.get(item);
        if (priority < heap.get(index).getPriority()){
            heap.get(index).setPriority(priority);
            swimUp(index);
        }else {
            heap.get(index).setPriority(priority);
            swimDown(index);
        }
    }

    //下沉的时候，父节点要和最小子节点交换,父节点priority大
    private void swimDown(int index){
        int smallestChild = smallestChild(index);
        while (heap.get(smallestChild).getPriority() < heap.get(index).getPriority()){
            swap(index, smallestChild);
            index = smallestChild;
            smallestChild = smallestChild(index);
        }
    }

    /*         递归溢出
    private void swimDown(int index){
        int smallestChild = smallestChild(index);
        if (heap.get(smallestChild).getPriority() < heap.get(index).getPriority()){
            swap(smallestChild, index);
            swimUp(smallestChild);
        }
    }
    */

    private void swimUp(int index) {
        if (index > 0) {
            int parent = parent(index);
            if (heap.get(parent).getPriority() > heap.get(index).getPriority()) {
                swap(index, parent);
                swimUp(parent);
            }
        }
    }
    /*private int smallestChild(int index){
        int leftChild = 2*index + 1;
        int rightChild = 2*index + 2;
        leftChild = leftChild < size() ? leftChild : index;
        rightChild = rightChild < size() ? rightChild : leftChild;
        return heap.get(leftChild).getPriority() <= heap.get(rightChild).getPriority() ? leftChild : rightChild;
    }*/

    private int smallestChild(int index) {
        int leftChild = index * 2 + 1;
        leftChild = leftChild < size() ? leftChild : index;
        int rightChild = index * 2 + 2;
        rightChild = rightChild < size() ? rightChild : leftChild;
        return heap.get(leftChild).getPriority() < heap.get(rightChild).getPriority()
                ? leftChild : rightChild;
    }

    private int parent(int index){
        if (index == 0){
            return  0;
        }
        return (index - 1)/2;
    }

    private void  swap(int index1, int index2){
        heapNode<T> node1 = heap.get(index1);
        heapNode<T> node2 = heap.get(index2);
        indexMap.put(node1.getItem(), index2);  //同名直接修改
        indexMap.put(node2.getItem(), index1);
        heap.set(index1, node2);
        heap.set(index2, node1);
    }

}
