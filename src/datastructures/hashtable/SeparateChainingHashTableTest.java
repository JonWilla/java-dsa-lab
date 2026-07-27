package datastructures.hashtable;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class SeparateChainingHashTableTest {

    private static int testsPassed;

    public static void main(String[] args) {
        testNewTable();
        testPutAndGet();
        testUpdate();
        testCollisionHandling();
        testRemove();
        testResize();
        testKeys();
        testClear();
        testInvalidOperations();

        System.out.println("All " + testsPassed + " hash table tests passed.");
    }

    private static void testNewTable() {
        SeparateChainingHashTable<String, Integer> table =
                new SeparateChainingHashTable<>();

        assertTrue(table.isEmpty(), "A new table should be empty");
        assertEquals(0, table.size(), "A new table should have size 0");
        assertEquals(16, table.capacity(), "Default capacity should be 16");
        assertEquals(0.0, table.loadFactor(), "Initial load factor should be 0");
    }

    private static void testPutAndGet() {
        SeparateChainingHashTable<String, Integer> table =
                new SeparateChainingHashTable<>();

        assertEquals(null, table.put("Java", 10),
                "A new key should not have a previous value");
        assertEquals(null, table.put("C++", 20),
                "A second new key should not have a previous value");
        assertEquals(2, table.size(), "put should increase size");
        assertEquals(10, table.get("Java"), "get should find Java");
        assertEquals(20, table.get("C++"), "get should find C++");
        assertTrue(table.containsKey("Java"), "Java should be present");
        assertFalse(table.containsKey("Python"), "Python should be absent");
        assertEquals(99, table.getOrDefault("Python", 99),
                "Missing key should return the default");
    }

    private static void testUpdate() {
        SeparateChainingHashTable<String, Integer> table =
                new SeparateChainingHashTable<>();

        table.put("score", 80);
        assertEquals(80, table.put("score", 95),
                "Updating should return the previous value");
        assertEquals(95, table.get("score"),
                "Updating should store the new value");
        assertEquals(1, table.size(),
                "Updating an existing key should not increase size");
    }

    private static void testCollisionHandling() {
        SeparateChainingHashTable<CollisionKey, String> table =
                new SeparateChainingHashTable<>(4);
        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        CollisionKey third = new CollisionKey("third");

        table.put(first, "A");
        table.put(second, "B");
        table.put(third, "C");

        assertEquals(3, table.size(), "Colliding keys should all be stored");
        assertEquals("A", table.get(first), "First collision should remain");
        assertEquals("B", table.get(second), "Second collision should remain");
        assertEquals("C", table.get(third), "Third collision should remain");
    }

    private static void testRemove() {
        SeparateChainingHashTable<CollisionKey, String> table =
                new SeparateChainingHashTable<>(8);
        CollisionKey first = new CollisionKey("first");
        CollisionKey middle = new CollisionKey("middle");
        CollisionKey last = new CollisionKey("last");

        table.put(first, "A");
        table.put(middle, "B");
        table.put(last, "C");

        assertEquals("B", table.remove(middle),
                "remove should return the removed value");
        assertFalse(table.containsKey(middle),
                "Removed collision-chain key should be absent");
        assertEquals("A", table.get(first),
                "Removing one collision must preserve another");
        assertEquals("C", table.get(last),
                "Removing one collision must preserve the chain");
        assertEquals(2, table.size(), "remove should decrease size");
    }

    private static void testResize() {
        SeparateChainingHashTable<Integer, String> table =
                new SeparateChainingHashTable<>(4);

        table.put(1, "one");
        table.put(2, "two");
        table.put(3, "three");
        assertEquals(4, table.capacity(),
                "Capacity should remain before threshold is exceeded");

        table.put(4, "four");
        assertEquals(8, table.capacity(),
                "Capacity should double after threshold is exceeded");
        assertEquals(4, table.size(), "Resize should preserve size");
        assertEquals("one", table.get(1), "Resize should preserve key 1");
        assertEquals("two", table.get(2), "Resize should preserve key 2");
        assertEquals("three", table.get(3), "Resize should preserve key 3");
        assertEquals("four", table.get(4), "Resize should preserve key 4");
        assertTrue(table.loadFactor() <= 0.75,
                "Load factor should be controlled after resizing");
    }

    private static void testKeys() {
        SeparateChainingHashTable<String, Integer> table =
                new SeparateChainingHashTable<>();
        table.put("A", 1);
        table.put("B", 2);
        table.put("C", 3);

        Set<String> keys = new HashSet<>(table.keys());

        assertEquals(3, keys.size(), "keys should contain every key once");
        assertTrue(keys.contains("A"), "keys should contain A");
        assertTrue(keys.contains("B"), "keys should contain B");
        assertTrue(keys.contains("C"), "keys should contain C");
    }

    private static void testClear() {
        SeparateChainingHashTable<String, Integer> table =
                new SeparateChainingHashTable<>(4);
        table.put("A", 1);
        table.put("B", 2);

        table.clear();

        assertTrue(table.isEmpty(), "clear should empty the table");
        assertEquals(0, table.size(), "clear should reset size");
        assertEquals(16, table.capacity(),
                "clear should restore the default capacity");
        assertFalse(table.containsKey("A"),
                "clear should remove previously stored keys");
    }

    private static void testInvalidOperations() {
        assertThrows(IllegalArgumentException.class,
                () -> new SeparateChainingHashTable<>(0),
                "Zero capacity should be rejected");

        SeparateChainingHashTable<String, Integer> table =
                new SeparateChainingHashTable<>();

        assertThrows(NullPointerException.class, () -> table.put(null, 1),
                "put should reject a null key");
        assertThrows(NullPointerException.class, () -> table.get(null),
                "get should reject a null key");
        assertThrows(NullPointerException.class, () -> table.remove(null),
                "remove should reject a null key");
        assertThrows(NoSuchElementException.class, () -> table.get("missing"),
                "get should reject a missing key");
        assertThrows(NoSuchElementException.class, () -> table.remove("missing"),
                "remove should reject a missing key");

        table.put("nullable-value", null);
        assertTrue(table.containsKey("nullable-value"),
                "A key with a null value should still be present");
        assertEquals(null, table.get("nullable-value"),
                "Null values should be supported");
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

    private static final class CollisionKey {
        private final String value;

        private CollisionKey(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return 7;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CollisionKey)) {
                return false;
            }
            CollisionKey that = (CollisionKey) other;
            return value.equals(that.value);
        }
    }
}