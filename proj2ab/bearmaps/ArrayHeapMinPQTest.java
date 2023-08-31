package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    public void testAdd_Size(){
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1,1);
        test.add(2,2);
        test.add(3,4);
        test.add(4,3);
        test.add(5,6);
        test.add(6,8);
        assertEquals(6, test.size());
        test.removeSmallest();
        test.removeSmallest();
        test.removeSmallest();
        assertEquals(3,test.size());
    }

    @Test
    public void testContains(){
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 2);
        test.add(6, 3);
        assertTrue(test.contains(3));
        assertFalse(test.contains(9));
    }

    @Test
    public void testGetSmallest(){
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 2);
        test.add(6, 3);
        assertEquals(1, (int)test.getSmallest());  //getSmallest()返回泛型T
        test.add(7,0);
        assertEquals(7, (int)test.getSmallest());
    }

    @Test
    public void testRemoveSmallest(){
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 7);
        test.add(5, 6);
        test.add(6, 4);
        test.add(7, 5);
        test.add(8, 8);
        assertEquals(1, (int) test.removeSmallest());
        assertEquals(2, (int) test.removeSmallest());
        assertEquals(3, (int) test.removeSmallest());
        assertEquals(6, (int) test.removeSmallest());
        assertEquals(7, (int) test.removeSmallest());
        assertEquals(5, (int) test.removeSmallest());
        assertEquals(4, (int) test.removeSmallest());
        assertEquals(8, (int) test.removeSmallest());
    }

    @Test
    public void testChangePriority(){
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 0);
        test.add(6, 3);
        test.changePriority(5, 1.5);
        test.changePriority(6, 0);
        assertEquals(6, (int) test.getSmallest());
        assertEquals(6, (int) test.removeSmallest());
        assertEquals(1, (int) test.removeSmallest());
        assertEquals(5, (int) test.getSmallest());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExtrinsicMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeap.add(i, 100000 - i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < 10000; j += 1) {
            minHeap.changePriority(StdRandom.uniform(0, minHeap.size()), j);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }
}
