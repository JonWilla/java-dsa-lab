package datastructures.heap;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class MinHeapPriorityQueueTest {

    private static int testsPassed;

    public static void main(String[] args) {
        testEmptyQueue();
        testOfferAndPeek();
        testPollOrdering();
        testDuplicateValues();
        testAutomaticResizing();
        testRemove();
        testReplace();
        testComparator();
        testLevelOrder();
        testClear();
        testInvalidOperations();
        testNonComparableElements();

        System.out.println("All " + testsPassed
                + " heap priority queue tests passed.");
    }

    private static void testEmptyQueue() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        assertTrue(queue.isEmpty(), "A new queue should be empty");
        assertEquals(0, queue.size(), "A new queue should have size 0");
    }

    private static void testOfferAndPeek() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        queue.offer(30);
        queue.offer(10);
        queue.offer(20);

        assertEquals(10, queue.peek(), "peek should return the minimum");
        assertEquals(3, queue.size(), "offer should increase size");
        assertFalse(queue.isEmpty(), "Queue with elements should not be empty");
        assertEquals(10, queue.peek(), "peek should not remove the minimum");
        assertEquals(3, queue.size(), "peek should not change size");
    }

    private static void testPollOrdering() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();
        int[] values = {7, 2, 9, 1, 5, 8, 3};

        for (int value : values) {
            queue.offer(value);
        }

        for (int expected = 1; expected <= 3; expected++) {
            assertEquals(expected, queue.poll(),
                    "poll should return ascending values");
        }

        assertEquals(5, queue.poll(), "Next value should be 5");
        assertEquals(7, queue.poll(), "Next value should be 7");
        assertEquals(8, queue.poll(), "Next value should be 8");
        assertEquals(9, queue.poll(), "Last value should be 9");
        assertTrue(queue.isEmpty(), "Polling all values should empty the queue");
    }

    private static void testDuplicateValues() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        queue.offer(4);
        queue.offer(2);
        queue.offer(2);
        queue.offer(4);

        assertEquals(2, queue.poll(), "First duplicate minimum should be returned");
        assertEquals(2, queue.poll(), "Second duplicate minimum should be returned");
        assertEquals(4, queue.poll(), "First duplicate maximum should be returned");
        assertEquals(4, queue.poll(), "Second duplicate maximum should be returned");
    }

    private static void testAutomaticResizing() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>(1);

        for (int value = 25; value >= 1; value--) {
            queue.offer(value);
        }

        assertEquals(25, queue.size(), "Queue should grow beyond initial capacity");
        assertEquals(1, queue.peek(), "Minimum should survive resizing");

        for (int expected = 1; expected <= 25; expected++) {
            assertEquals(expected, queue.poll(),
                    "Resized queue should preserve heap ordering");
        }
    }

    private static void testRemove() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        for (int value : new int[]{10, 4, 15, 2, 8, 12, 20}) {
            queue.offer(value);
        }

        assertTrue(queue.remove(8), "remove should find a middle element");
        assertFalse(queue.contains(8), "Removed element should be absent");
        assertEquals(6, queue.size(), "remove should decrease size");
        assertTrue(queue.remove(2), "remove should handle the root");
        assertEquals(4, queue.peek(), "Heap should repair after root removal");
        assertFalse(queue.remove(99), "remove should report a missing element");
        assertEquals(5, queue.size(), "Failed remove should not change size");
    }

    private static void testReplace() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        queue.offer(5);
        queue.offer(10);
        queue.offer(15);
        queue.offer(20);

        assertTrue(queue.replace(15, 1), "replace should find an existing element");
        assertEquals(1, queue.peek(), "Smaller replacement should sift upward");
        assertTrue(queue.replace(1, 30), "replace should update the root");
        assertEquals(5, queue.peek(), "Larger replacement should sift downward");
        assertFalse(queue.replace(99, 3), "replace should report a missing element");
        assertEquals(4, queue.size(), "replace should not change size");
    }

    private static void testComparator() {
        MinHeapPriorityQueue<String> queue =
                new MinHeapPriorityQueue<>(Comparator.reverseOrder());

        queue.offer("B");
        queue.offer("A");
        queue.offer("C");

        assertEquals("C", queue.poll(),
                "Reverse comparator should give C highest priority");
        assertEquals("B", queue.poll(),
                "Reverse comparator should give B next priority");
        assertEquals("A", queue.poll(),
                "Reverse comparator should give A last priority");
    }

    private static void testLevelOrder() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        queue.offer(1);
        queue.offer(3);
        queue.offer(2);
        queue.offer(7);
        queue.offer(6);

        assertEquals(List.of(1, 3, 2, 7, 6), queue.toLevelOrderList(),
                "Level-order list should expose the heap layout");
        assertEquals(5, queue.size(),
                "Creating a level-order list should not change size");
    }

    private static void testClear() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        queue.offer(3);
        queue.offer(1);
        queue.offer(2);
        queue.clear();

        assertTrue(queue.isEmpty(), "clear should empty the queue");
        assertEquals(0, queue.size(), "clear should reset size");

        queue.offer(9);
        assertEquals(9, queue.peek(), "Queue should be reusable after clear");
    }

    private static void testInvalidOperations() {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>();

        assertThrows(NoSuchElementException.class, queue::peek,
                "peek should reject an empty queue");
        assertThrows(NoSuchElementException.class, queue::poll,
                "poll should reject an empty queue");
        assertThrows(NullPointerException.class, () -> queue.offer(null),
                "offer should reject null");
        assertThrows(NullPointerException.class, () -> queue.contains(null),
                "contains should reject null");
        assertThrows(NullPointerException.class, () -> queue.remove(null),
                "remove should reject null");
        assertThrows(NullPointerException.class, () -> queue.replace(null, 1),
                "replace should reject a null old element");
        assertThrows(NullPointerException.class, () -> queue.replace(1, null),
                "replace should reject a null new element");
        assertThrows(IllegalArgumentException.class,
                () -> new MinHeapPriorityQueue<Integer>(0),
                "Capacity 0 should be rejected");
    }

    private static void testNonComparableElements() {
        MinHeapPriorityQueue<Task> queue = new MinHeapPriorityQueue<>();

        queue.offer(new Task("first"));

        assertThrows(IllegalStateException.class,
                () -> queue.offer(new Task("second")),
                "Non-comparable elements require a comparator");

        MinHeapPriorityQueue<Task> orderedQueue =
                new MinHeapPriorityQueue<>(Comparator.comparing(Task::name));
        orderedQueue.offer(new Task("Beta"));
        orderedQueue.offer(new Task("Alpha"));

        assertEquals("Alpha", orderedQueue.poll().name(),
                "Comparator should support non-comparable elements");
        assertEquals("Beta", orderedQueue.poll().name(),
                "Comparator ordering should remain correct");
    }

    private record Task(String name) {
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }

        testsPassed++;
    }

    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    private static void assertEquals(
            Object expected,
            Object actual,
            String message
    ) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(
                    message + " | Expected: " + expected + ", Actual: " + actual
            );
        }

        testsPassed++;
    }

    private static void assertThrows(
            Class<? extends Throwable> expectedType,
            Runnable operation,
            String message
    ) {
        try {
            operation.run();
        } catch (Throwable actualException) {
            if (expectedType.isInstance(actualException)) {
                testsPassed++;
                return;
            }

            throw new AssertionError(
                    message + " | Expected: " + expectedType.getSimpleName()
                            + ", Actual: "
                            + actualException.getClass().getSimpleName(),
                    actualException
            );
        }

        throw new AssertionError(
                message + " | Expected " + expectedType.getSimpleName()
                        + " but nothing was thrown"
        );
    }
}