import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     *
     * @param q1  A Queue of items
     * @param q2  A Queue of items
     * @return    A Queue containing the items of 
     *            q1 followed by the items of q2.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /**
     * Returns a random item from the given queue.
     *
     * @param items  A Queue of items
     * @return       A random item from items
     */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item n : unsorted){
            int cmpResult = n.compareTo(pivot);
            if (cmpResult  < 0){
                less.enqueue(n);
            } else if (cmpResult == 0) {
                equal.enqueue(n);
            }else {
                greater.enqueue(n);
            }
        }
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     *
     * @param items  A Queue of possibly unsorted items
     * @return       A Queue of sorted items
     */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        if (items == null){
            throw new NullPointerException();
        }

        //base case
        if (items.size() <= 1){
            return items;
        }

        //2.子问题
        Item pivot = getRandomItem(items);
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();

        partition(items, pivot, less, equal, greater);

        //3.调用自身
        less = quickSort(less);
        greater = quickSort(greater);
        /*错误写法
        * quickSort(less);
        * quickSort(greater);
        * 没有把调用结果赋值给less greater
        * */

        //2.子问题
        return catenate(catenate(less, equal), greater);
    }
}
