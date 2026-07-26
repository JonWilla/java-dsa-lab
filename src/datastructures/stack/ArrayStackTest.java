package datastructures.stack;

import java.util.NoSuchElementException;
import java.util.Objects;

public final class ArrayStackTest {

    private static int passed;

    private ArrayStackTest() {
    }

    public static void main(String[] args) {
        testLastInFirstOut();
        testPeekDoesNotRemove();
        testAutomaticResize();
        testEmptyStackErrors();
        testNullRejection();
        testInvalidCapacity();
        testClearAndReuse();

        System.out.println("All " + passed + " stack tests passed.");
    }

    private static void testLastInFirstOut() {
        ArrayStack<String> stack = new ArrayStack<>();

        stack.push("NASA");
        stack.push("Boeing");
        stack.push("Intel");

        assertEquals("Intel", stack.pop());
        assertEquals("Boeing", stack.pop());
        assertEquals("NASA", stack.pop());
        assertTrue(stack.isEmpty());

        pass();
    }

    private static void testPeekDoesNotRemove() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(42);

        assertEquals(42, stack.peek());
        assertEquals(1, stack.size());

        pass();
    }

    private static void testAutomaticResize() {
        ArrayStack<Integer> stack = new ArrayStack<>(2);

        for (int value = 1; value <= 100; value++) {
            stack.push(value);
        }

        assertEquals(100, stack.size());

        for (int expected = 100; expected >= 1; expected--) {
            assertEquals(expected, stack.pop());
        }

        assertTrue(stack.isEmpty());
        pass();
    }

    private static void testEmptyStackErrors() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        expectThrows(NoSuchElementException.class, stack::pop);
        expectThrows(NoSuchElementException.class, stack::peek);

        pass();
    }

    private static void testNullRejection() {
        ArrayStack<String> stack = new ArrayStack<>();

        expectThrows(
                NullPointerException.class,
                () -> stack.push(null)
        );

        pass();
    }

    private static void testInvalidCapacity() {
        expectThrows(
                IllegalArgumentException.class,
                () -> new ArrayStack<>(0)
        );
        expectThrows(
                IllegalArgumentException.class,
                () -> new ArrayStack<>(-1)
        );

        pass();
    }

    private static void testClearAndReuse() {
        ArrayStack<String> stack = new ArrayStack<>();

        stack.push("temporary");
        stack.push("data");
        stack.clear();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        stack.push("reused");
        assertEquals("reused", stack.pop());

        pass();
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(
                    "Expected " + expected + ", but received " + actual + "."
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
                            + exception.getClass().getSimpleName() + "."
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