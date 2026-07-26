package datastructures.list;

import java.util.NoSuchElementException;

public class SinglyLinkedListTest {

    private static int testsPassed;

    public static void main(String[] args) {
        testEmptyList();
        testAddFirst();
        testAddLast();
        testIndexedAdd();
        testGetMethods();
        testRemoveFirst();
        testRemoveLast();
        testIndexedRemove();
        testInvalidOperations();

        System.out.println("All " + testsPassed
                + " singly linked list tests passed.");
    }

    private static void testEmptyList() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        assertTrue(list.isEmpty(), "A new list should be empty");
        assertEquals(0, list.size(), "A new list should have size 0");
    }

    private static void testAddFirst() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.addFirst("C");
        list.addFirst("B");
        list.addFirst("A");

        assertEquals(3, list.size(), "addFirst should increase size");
        assertEquals("A", list.getFirst(), "addFirst should update head");
        assertEquals("C", list.getLast(), "addFirst should preserve tail");
    }

    private static void testAddLast() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        assertEquals(3, list.size(), "addLast should increase size");
        assertEquals(10, list.getFirst(), "addLast should preserve head");
        assertEquals(30, list.getLast(), "addLast should update tail");
    }

    private static void testIndexedAdd() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.add(0, "B");
        list.add(0, "A");
        list.add(2, "D");
        list.add(2, "C");

        assertEquals(4, list.size(), "Indexed add should increase size");
        assertEquals("A", list.get(0), "Index 0 should contain A");
        assertEquals("B", list.get(1), "Index 1 should contain B");
        assertEquals("C", list.get(2), "Index 2 should contain C");
        assertEquals("D", list.get(3), "Index 3 should contain D");
    }

    private static void testGetMethods() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.addLast("Alpha");
        list.addLast("Beta");
        list.addLast("Gamma");

        assertEquals("Alpha", list.getFirst(), "getFirst should return head data");
        assertEquals("Gamma", list.getLast(), "getLast should return tail data");
        assertEquals("Beta", list.get(1), "get should return indexed data");
    }

    private static void testRemoveFirst() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        assertEquals("A", list.removeFirst(), "removeFirst should return A");
        assertEquals("B", list.getFirst(), "B should become the new head");
        assertEquals(2, list.size(), "removeFirst should decrease size");

        list.removeFirst();
        list.removeFirst();

        assertTrue(list.isEmpty(), "Removing every element should empty the list");
    }

    private static void testRemoveLast() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        assertEquals("C", list.removeLast(), "removeLast should return C");
        assertEquals("B", list.getLast(), "B should become the new tail");
        assertEquals(2, list.size(), "removeLast should decrease size");

        list.removeLast();
        assertEquals("A", list.removeLast(),
                "Removing the only element should work");
        assertTrue(list.isEmpty(), "List should be empty after all removals");
    }

    private static void testIndexedRemove() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");

        assertEquals("B", list.remove(1),
                "Indexed removal should return the removed data");
        assertEquals("C", list.get(1),
                "The successor should shift into the removed position");

        assertEquals("A", list.remove(0),
                "remove(0) should delegate to removeFirst");
        assertEquals("D", list.remove(list.size() - 1),
                "Removing the final index should delegate to removeLast");

        assertEquals(1, list.size(), "One element should remain");
        assertEquals("C", list.getFirst(), "C should be the remaining element");
        assertEquals("C", list.getLast(),
                "Head and tail should reference the remaining element");
    }

    private static void testInvalidOperations() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        assertThrows(NoSuchElementException.class, list::getFirst,
                "getFirst should reject an empty list");
        assertThrows(NoSuchElementException.class, list::getLast,
                "getLast should reject an empty list");
        assertThrows(NoSuchElementException.class, list::removeFirst,
                "removeFirst should reject an empty list");
        assertThrows(NoSuchElementException.class, list::removeLast,
                "removeLast should reject an empty list");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0),
                "get should reject index 0 on an empty list");
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "A"),
                "add should reject a negative index");
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "A"),
                "add should reject an index greater than size");

        list.addLast("A");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1),
                "get should reject a negative index");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1),
                "get should reject index equal to size");
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1),
                "remove should reject index equal to size");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }

        testsPassed++;
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
                message + " | Expected "
                        + expectedType.getSimpleName()
                        + " but nothing was thrown"
        );
    }
}