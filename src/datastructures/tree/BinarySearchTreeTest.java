package datastructures.tree;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class BinarySearchTreeTest {

    private static int testsPassed;

    public static void main(String[] args) {
        testEmptyTree();
        testInsertAndRetrieve();
        testUpdateExistingKey();
        testContainsAndDefault();
        testMinimumAndMaximum();
        testInOrderTraversal();
        testHeight();
        testRemoveLeaf();
        testRemoveOneChild();
        testRemoveTwoChildren();
        testRemoveRoot();
        testClear();
        testInvalidOperations();

        System.out.println("All " + testsPassed
                + " binary search tree tests passed.");
    }

    private static void testEmptyTree() {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();

        assertTrue(tree.isEmpty(), "A new tree should be empty");
        assertEquals(0, tree.size(), "A new tree should have size 0");
        assertEquals(-1, tree.height(), "An empty tree should have height -1");
        assertEquals(Arrays.asList(), tree.inOrderKeys(),
                "An empty traversal should return an empty list");
    }

    private static void testInsertAndRetrieve() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals(7, tree.size(), "Seven unique keys should be stored");
        assertFalse(tree.isEmpty(), "A populated tree should not be empty");
        assertEquals("fifty", tree.get(50), "Root value should be retrievable");
        assertEquals("twenty", tree.get(20), "Left-side value should be retrievable");
        assertEquals("eighty", tree.get(80), "Right-side value should be retrievable");
    }

    private static void testUpdateExistingKey() {
        BinarySearchTree<Integer, String> tree = createTree();

        tree.put(30, "updated");

        assertEquals(7, tree.size(), "Updating a key should not change size");
        assertEquals("updated", tree.get(30),
                "Updating a key should replace its value");

        tree.put(30, null);
        assertEquals(null, tree.get(30), "Null values should be supported");
        assertTrue(tree.containsKey(30),
                "A key with a null value should still be present");
    }

    private static void testContainsAndDefault() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertTrue(tree.containsKey(40), "containsKey should find a stored key");
        assertFalse(tree.containsKey(99), "containsKey should reject a missing key");
        assertEquals("sixty", tree.getOrDefault(60, "missing"),
                "getOrDefault should return a stored value");
        assertEquals("missing", tree.getOrDefault(99, "missing"),
                "getOrDefault should return the fallback for a missing key");
    }

    private static void testMinimumAndMaximum() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals(20, tree.minKey(), "Minimum key should be 20");
        assertEquals(80, tree.maxKey(), "Maximum key should be 80");
    }

    private static void testInOrderTraversal() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals(Arrays.asList(20, 30, 40, 50, 60, 70, 80),
                tree.inOrderKeys(),
                "In-order traversal should return sorted keys");
    }

    private static void testHeight() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals(2, tree.height(), "Balanced seven-node tree should have height 2");

        tree.put(90, "ninety");
        tree.put(100, "one hundred");

        assertEquals(4, tree.height(), "A deeper right path should increase height");
    }

    private static void testRemoveLeaf() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals("twenty", tree.remove(20),
                "Removing a leaf should return its value");
        assertEquals(6, tree.size(), "Leaf removal should decrease size");
        assertFalse(tree.containsKey(20), "Removed leaf should be absent");
        assertEquals(Arrays.asList(30, 40, 50, 60, 70, 80),
                tree.inOrderKeys(), "Remaining keys should stay ordered");
    }

    private static void testRemoveOneChild() {
        BinarySearchTree<Integer, String> tree = createTree();
        tree.put(65, "sixty-five");

        assertEquals("sixty", tree.remove(60),
                "Removing a one-child node should return its value");
        assertEquals(7, tree.size(), "One-child removal should decrease size");
        assertTrue(tree.containsKey(65), "The child should remain connected");
        assertEquals(Arrays.asList(20, 30, 40, 50, 65, 70, 80),
                tree.inOrderKeys(), "One-child removal should preserve ordering");
    }

    private static void testRemoveTwoChildren() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals("thirty", tree.remove(30),
                "Removing a two-child node should return its value");
        assertEquals(6, tree.size(), "Two-child removal should decrease size");
        assertFalse(tree.containsKey(30), "Removed key should be absent");
        assertEquals(Arrays.asList(20, 40, 50, 60, 70, 80),
                tree.inOrderKeys(), "Successor replacement should preserve ordering");
        assertEquals("forty", tree.get(40),
                "Successor value should remain associated with its key");
    }

    private static void testRemoveRoot() {
        BinarySearchTree<Integer, String> tree = createTree();

        assertEquals("fifty", tree.remove(50),
                "Removing the root should return its value");
        assertEquals(6, tree.size(), "Root removal should decrease size");
        assertFalse(tree.containsKey(50), "Old root key should be absent");
        assertEquals(Arrays.asList(20, 30, 40, 60, 70, 80),
                tree.inOrderKeys(), "Root removal should preserve ordering");
        assertEquals("sixty", tree.get(60),
                "Root successor should retain the correct value");
    }

    private static void testClear() {
        BinarySearchTree<Integer, String> tree = createTree();

        tree.clear();

        assertTrue(tree.isEmpty(), "clear should empty the tree");
        assertEquals(0, tree.size(), "clear should reset size");
        assertEquals(-1, tree.height(), "Cleared tree should have height -1");
        assertEquals(Arrays.asList(), tree.inOrderKeys(),
                "Cleared tree traversal should be empty");

        tree.put(1, "one");
        assertEquals("one", tree.get(1),
                "Tree should remain reusable after clear");
    }

    private static void testInvalidOperations() {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();

        assertThrows(NoSuchElementException.class, tree::minKey,
                "minKey should reject an empty tree");
        assertThrows(NoSuchElementException.class, tree::maxKey,
                "maxKey should reject an empty tree");
        assertThrows(NoSuchElementException.class, () -> tree.get(10),
                "get should reject a missing key");
        assertThrows(NoSuchElementException.class, () -> tree.remove(10),
                "remove should reject a missing key");
        assertThrows(NullPointerException.class, () -> tree.put(null, "value"),
                "put should reject a null key");
        assertThrows(NullPointerException.class, () -> tree.get(null),
                "get should reject a null key");
        assertThrows(NullPointerException.class, () -> tree.containsKey(null),
                "containsKey should reject a null key");
        assertThrows(NullPointerException.class, () -> tree.remove(null),
                "remove should reject a null key");
    }

    private static BinarySearchTree<Integer, String> createTree() {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
        tree.put(50, "fifty");
        tree.put(30, "thirty");
        tree.put(70, "seventy");
        tree.put(20, "twenty");
        tree.put(40, "forty");
        tree.put(60, "sixty");
        tree.put(80, "eighty");
        return tree;
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