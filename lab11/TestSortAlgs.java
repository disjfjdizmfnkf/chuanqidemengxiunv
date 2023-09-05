import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestSortAlgs {

    private Queue<String> buildTest(){
        Queue<String> testQueue = new Queue<>();
        testQueue.enqueue("What");
        testQueue.enqueue("Josh");
        testQueue.enqueue("Tom");
        testQueue.enqueue("Dicke");
        testQueue.enqueue("July");
        testQueue.enqueue("Maya");
        testQueue.enqueue("Daina");
        return testQueue;
    }

    @Test
    public void testQuickSort() {
        Queue<String> test = buildTest();

        Queue<String> result = QuickSort.quickSort(test);

        assertEquals(test.size(), result.size());
        assertTrue(isSorted(result));
    }

    @Test
    public void testMergeSort() {
        Queue<String> test = buildTest();

        Queue<String> result = MergeSort.mergeSort(test);

        assertEquals(test.size(), result.size());
        assertTrue(isSorted(result));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
