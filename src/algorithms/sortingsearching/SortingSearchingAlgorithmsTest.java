package algorithms.sortingsearching;

import java.util.Arrays;


public final class SortingSearchingAlgorithmsTest {

    private static int testsPassed;

    private SortingSearchingAlgorithmsTest() {
    }

    public static void main(String[] args) {
        testBubbleSort();
        testSelectionSort();
        testInsertionSort();
        testMergeSort();
        testQuickSort();
        testLinearSearch();
        testBinarySearch();
        testRecursiveBinarySearch();
        testInputsRemainUnchanged();
        testNullRejection();

        System.out.println(
                "All " + testsPassed + " sorting and searching tests passed."
        );
    }

    private static void testBubbleSort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                SortingSearchingAlgorithms.bubbleSort(
                        new int[]{5, 1, 4, 2, 3}
                ), "bubble sort should order mixed values");
        assertArrayEquals(new int[]{1, 2, 3},
                SortingSearchingAlgorithms.bubbleSort(new int[]{1, 2, 3}),
                "bubble sort should preserve sorted order");
        assertArrayEquals(new int[]{-5, -1, 0, 4},
                SortingSearchingAlgorithms.bubbleSort(
                        new int[]{0, -5, 4, -1}
                ), "bubble sort should handle negatives");
        assertArrayEquals(new int[]{2, 2, 2},
                SortingSearchingAlgorithms.bubbleSort(new int[]{2, 2, 2}),
                "bubble sort should handle duplicates");
        assertArrayEquals(new int[0],
                SortingSearchingAlgorithms.bubbleSort(new int[0]),
                "bubble sort should handle an empty array");
    }

    private static void testSelectionSort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                SortingSearchingAlgorithms.selectionSort(
                        new int[]{3, 5, 1, 4, 2}
                ), "selection sort should order mixed values");
        assertArrayEquals(new int[]{1, 2, 3, 4},
                SortingSearchingAlgorithms.selectionSort(
                        new int[]{4, 3, 2, 1}
                ), "selection sort should handle reverse order");
        assertArrayEquals(new int[]{-2, -2, 0, 7},
                SortingSearchingAlgorithms.selectionSort(
                        new int[]{-2, 7, -2, 0}
                ), "selection sort should handle duplicates and negatives");
        assertArrayEquals(new int[]{9},
                SortingSearchingAlgorithms.selectionSort(new int[]{9}),
                "selection sort should handle one element");
    }

    private static void testInsertionSort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                SortingSearchingAlgorithms.insertionSort(
                        new int[]{2, 5, 3, 1, 4}
                ), "insertion sort should order mixed values");
        assertArrayEquals(new int[]{-8, -1, 3, 10},
                SortingSearchingAlgorithms.insertionSort(
                        new int[]{10, -1, -8, 3}
                ), "insertion sort should handle signed values");
        assertArrayEquals(new int[]{4, 4, 4, 4},
                SortingSearchingAlgorithms.insertionSort(
                        new int[]{4, 4, 4, 4}
                ), "insertion sort should handle equal values");
        assertArrayEquals(new int[0],
                SortingSearchingAlgorithms.insertionSort(new int[0]),
                "insertion sort should handle an empty array");
    }

    private static void testMergeSort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6},
                SortingSearchingAlgorithms.mergeSort(
                        new int[]{6, 1, 5, 2, 4, 3}
                ), "merge sort should order mixed values");
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                SortingSearchingAlgorithms.mergeSort(
                        new int[]{5, 4, 3, 2, 1}
                ), "merge sort should handle reverse order");
        assertArrayEquals(new int[]{-3, -1, 0, 0, 8},
                SortingSearchingAlgorithms.mergeSort(
                        new int[]{0, 8, -1, 0, -3}
                ), "merge sort should handle duplicates and negatives");
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
                SortingSearchingAlgorithms.mergeSort(new int[]{
                        Integer.MAX_VALUE, Integer.MIN_VALUE, 0
                }), "merge sort should handle integer extremes");
        assertArrayEquals(new int[]{7},
                SortingSearchingAlgorithms.mergeSort(new int[]{7}),
                "merge sort should handle one element");
        assertArrayEquals(new int[0],
                SortingSearchingAlgorithms.mergeSort(new int[0]),
                "merge sort should handle an empty array");
    }

    private static void testQuickSort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6},
                SortingSearchingAlgorithms.quickSort(
                        new int[]{4, 2, 6, 1, 5, 3}
                ), "quick sort should order mixed values");
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                SortingSearchingAlgorithms.quickSort(
                        new int[]{1, 2, 3, 4, 5}
                ), "quick sort should handle sorted input");
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                SortingSearchingAlgorithms.quickSort(
                        new int[]{5, 4, 3, 2, 1}
                ), "quick sort should handle reverse input");
        assertArrayEquals(new int[]{-7, -7, 2, 2, 9},
                SortingSearchingAlgorithms.quickSort(
                        new int[]{2, -7, 9, 2, -7}
                ), "quick sort should handle duplicates");
        assertArrayEquals(new int[]{11},
                SortingSearchingAlgorithms.quickSort(new int[]{11}),
                "quick sort should handle one element");
        assertArrayEquals(new int[0],
                SortingSearchingAlgorithms.quickSort(new int[0]),
                "quick sort should handle an empty array");
    }

    private static void testLinearSearch() {
        int[] values = {8, 3, 5, 3, 9};

        assertEquals(0, SortingSearchingAlgorithms.linearSearch(values, 8),
                "linear search should find the first element");
        assertEquals(4, SortingSearchingAlgorithms.linearSearch(values, 9),
                "linear search should find the last element");
        assertEquals(1, SortingSearchingAlgorithms.linearSearch(values, 3),
                "linear search should return the first duplicate");
        assertEquals(-1, SortingSearchingAlgorithms.linearSearch(values, 7),
                "linear search should report a missing target");
        assertEquals(-1,
                SortingSearchingAlgorithms.linearSearch(new int[0], 1),
                "linear search should handle an empty array");
        assertEquals(0,
                SortingSearchingAlgorithms.linearSearch(new int[]{-4}, -4),
                "linear search should handle one matching element");
        assertEquals(-1,
                SortingSearchingAlgorithms.linearSearch(new int[]{-4}, 4),
                "linear search should handle one nonmatching element");
    }

    private static void testBinarySearch() {
        int[] values = {-8, -2, 0, 3, 3, 3, 10, 25};

        assertEquals(0, SortingSearchingAlgorithms.binarySearch(values, -8),
                "binary search should find the first element");
        assertEquals(7, SortingSearchingAlgorithms.binarySearch(values, 25),
                "binary search should find the last element");
        assertEquals(3, SortingSearchingAlgorithms.binarySearch(values, 3),
                "binary search should return the first duplicate");
        assertEquals(2, SortingSearchingAlgorithms.binarySearch(values, 0),
                "binary search should find a middle value");
        assertEquals(-1, SortingSearchingAlgorithms.binarySearch(values, 4),
                "binary search should report an internal missing value");
        assertEquals(-1, SortingSearchingAlgorithms.binarySearch(values, -9),
                "binary search should report a target below the range");
        assertEquals(-1, SortingSearchingAlgorithms.binarySearch(values, 30),
                "binary search should report a target above the range");
        assertEquals(-1,
                SortingSearchingAlgorithms.binarySearch(new int[0], 1),
                "binary search should handle an empty array");
        assertEquals(0,
                SortingSearchingAlgorithms.binarySearch(new int[]{6}, 6),
                "binary search should handle one matching element");
        assertEquals(-1,
                SortingSearchingAlgorithms.binarySearch(new int[]{6}, 5),
                "binary search should handle one nonmatching element");
    }

    private static void testRecursiveBinarySearch() {
        int[] values = {-10, -1, 4, 4, 4, 12, 19};

        assertEquals(0,
                SortingSearchingAlgorithms.recursiveBinarySearch(values, -10),
                "recursive search should find the first element");
        assertEquals(6,
                SortingSearchingAlgorithms.recursiveBinarySearch(values, 19),
                "recursive search should find the last element");
        assertEquals(2,
                SortingSearchingAlgorithms.recursiveBinarySearch(values, 4),
                "recursive search should return the first duplicate");
        assertEquals(5,
                SortingSearchingAlgorithms.recursiveBinarySearch(values, 12),
                "recursive search should find an internal value");
        assertEquals(-1,
                SortingSearchingAlgorithms.recursiveBinarySearch(values, 5),
                "recursive search should report a missing target");
        assertEquals(-1,
                SortingSearchingAlgorithms.recursiveBinarySearch(
                        new int[0], 5
                ), "recursive search should handle an empty array");
        assertEquals(0,
                SortingSearchingAlgorithms.recursiveBinarySearch(
                        new int[]{2}, 2
                ), "recursive search should handle one matching element");
        assertEquals(-1,
                SortingSearchingAlgorithms.recursiveBinarySearch(
                        new int[]{2}, 3
                ), "recursive search should handle one nonmatching element");
    }

    private static void testInputsRemainUnchanged() {
        int[] original = {3, 1, 2};

        SortingSearchingAlgorithms.bubbleSort(original);
        assertArrayEquals(new int[]{3, 1, 2}, original,
                "bubble sort should not mutate its input");
        SortingSearchingAlgorithms.selectionSort(original);
        assertArrayEquals(new int[]{3, 1, 2}, original,
                "selection sort should not mutate its input");
        SortingSearchingAlgorithms.insertionSort(original);
        assertArrayEquals(new int[]{3, 1, 2}, original,
                "insertion sort should not mutate its input");
        SortingSearchingAlgorithms.mergeSort(original);
        assertArrayEquals(new int[]{3, 1, 2}, original,
                "merge sort should not mutate its input");
        SortingSearchingAlgorithms.quickSort(original);
        assertArrayEquals(new int[]{3, 1, 2}, original,
                "quick sort should not mutate its input");
    }

    private static void testNullRejection() {
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.bubbleSort(null),
                "bubble sort should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.selectionSort(null),
                "selection sort should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.insertionSort(null),
                "insertion sort should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.mergeSort(null),
                "merge sort should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.quickSort(null),
                "quick sort should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.linearSearch(null, 1),
                "linear search should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.binarySearch(null, 1),
                "binary search should reject null");
        assertThrows(NullPointerException.class,
                () -> SortingSearchingAlgorithms.recursiveBinarySearch(
                        null, 1
                ), "recursive binary search should reject null");
    }

    private static void assertArrayEquals(
            int[] expected,
            int[] actual,
            String message
    ) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(
                    message + " | Expected: " + Arrays.toString(expected)
                            + ", Actual: " + Arrays.toString(actual)
            );
        }

        testsPassed++;
    }

    private static void assertEquals(
            int expected,
            int actual,
            String message
    ) {
        if (expected != actual) {
            throw new AssertionError(
                    message + " | Expected: " + expected
                            + ", Actual: " + actual
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