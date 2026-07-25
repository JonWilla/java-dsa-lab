package datastructures.queue;

import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayQueueTest {

    private static int passed;

    public static void main(String[] args) {
        testFirstInFirstOut();
        testPeekDoesNotRemove();
        testWraparoundAndResize();
        testEmptyQueueErrors();
        testNullRejection();

        System.out.println("All " + passed + " queue tests passed.");
    }

    private static void testFirstInFirstOut() {
        ArrayQueue<String> queue = new ArrayQueue<>();

        queue.enqueue("NASA");
        queue.enqueue("Boeing");
        queue.enqueue("Intel");

        assertEquals("NASA", queue.dequeue());
        assertEquals("Boeing", queue.dequeue());
        assertEquals("Intel", queue.dequeue());
        assertTrue(queue.isEmpty());

        pass();
    }

    private static void testPeekDoesNotRemove() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        queue.enqueue(42);

        assertEquals(42, queue.peek());
        assertEquals(1, queue.size());

        pass();
    }

    private static void testWraparoundAndResize() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(3);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(10, queue.dequeue());

        queue.enqueue(40);
        queue.enqueue(50);

        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
        assertEquals(40, queue.dequeue());
        assertEquals(50, queue.dequeue());

        pass();
    }

    private static void testEmptyQueueErrors() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        expectThrows(NoSuchElementException.class, queue::dequeue);
        expectThrows(NoSuchElementException.class, queue::peek);

        pass();
    }

    private static void testNullRejection() {
        ArrayQueue<String> queue = new ArrayQueue<>();

        expectThrows(
                NullPointerException.class,
                () -> queue.enqueue(null)
        );

        pass();
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(
                    "Expected " + expected + ", but received " + actual
            );
        }
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected condition to be true.");
        }
    }

    private static void expectThrows(
            Class<? extends Throwable> expectedType,
            Runnable operation
    ) {
        try {
            operation.run();
        } catch (Throwable exception) {
            if (expectedType.isInstance(exception)) {
                return;
            }

            throw new AssertionError(
                    "Expected " + expectedType.getSimpleName()
                            + ", but received "
                            + exception.getClass().getSimpleName()
            );
        }

        throw new AssertionError(
                "Expected " + expectedType.getSimpleName()
                        + ", but no exception was thrown."
        );
    }

    private static void pass() {
        passed++;
    }
}