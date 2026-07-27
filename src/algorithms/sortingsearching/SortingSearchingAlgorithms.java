package algorithms.sortingsearching;

import java.util.Objects;

/**
 * Classic sorting and searching algorithms implemented without using the
 * library sorting helpers. Sorting methods return a sorted copy and leave the
 * caller's input unchanged.
 */
public final class SortingSearchingAlgorithms {

    private SortingSearchingAlgorithms() {
        throw new AssertionError("Utility class must not be instantiated");
    }

    public static int[] bubbleSort(int[] values) {
        int[] result = copyOf(values);

        for (int end = result.length - 1; end > 0; end--) {
            boolean swapped = false;

            for (int i = 0; i < end; i++) {
                if (result[i] > result[i + 1]) {
                    swap(result, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return result;
    }

    public static int[] selectionSort(int[] values) {
        int[] result = copyOf(values);

        for (int i = 0; i < result.length - 1; i++) {
            int minimumIndex = i;

            for (int j = i + 1; j < result.length; j++) {
                if (result[j] < result[minimumIndex]) {
                    minimumIndex = j;
                }
            }

            swap(result, i, minimumIndex);
        }

        return result;
    }

    public static int[] insertionSort(int[] values) {
        int[] result = copyOf(values);

        for (int i = 1; i < result.length; i++) {
            int current = result[i];
            int j = i - 1;

            while (j >= 0 && result[j] > current) {
                result[j + 1] = result[j];
                j--;
            }

            result[j + 1] = current;
        }

        return result;
    }

    public static int[] mergeSort(int[] values) {
        int[] result = copyOf(values);
        int[] auxiliary = new int[result.length];
        mergeSort(result, auxiliary, 0, result.length);
        return result;
    }

    public static int[] quickSort(int[] values) {
        int[] result = copyOf(values);
        quickSort(result, 0, result.length - 1);
        return result;
    }

    public static int linearSearch(int[] values, int target) {
        requireArray(values);

        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searches an ascending array. When duplicates exist, returns the first
     * matching index.
     */
    public static int binarySearch(int[] sortedValues, int target) {
        requireArray(sortedValues);
        int low = 0;
        int high = sortedValues.length - 1;
        int firstMatch = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (sortedValues[middle] < target) {
                low = middle + 1;
            } else {
                if (sortedValues[middle] == target) {
                    firstMatch = middle;
                }
                high = middle - 1;
            }
        }

        return firstMatch;
    }

    /**
     * Recursive version of binary search. The input must be in ascending
     * order; duplicate targets resolve to their first index.
     */
    public static int recursiveBinarySearch(int[] sortedValues, int target) {
        requireArray(sortedValues);
        return recursiveBinarySearch(
                sortedValues,
                target,
                0,
                sortedValues.length - 1,
                -1
        );
    }

    private static void mergeSort(
            int[] values,
            int[] auxiliary,
            int start,
            int end
    ) {
        if (end - start < 2) {
            return;
        }

        int middle = start + (end - start) / 2;
        mergeSort(values, auxiliary, start, middle);
        mergeSort(values, auxiliary, middle, end);
        merge(values, auxiliary, start, middle, end);
    }

    private static void merge(
            int[] values,
            int[] auxiliary,
            int start,
            int middle,
            int end
    ) {
        int left = start;
        int right = middle;
        int destination = start;

        while (left < middle && right < end) {
            if (values[left] <= values[right]) {
                auxiliary[destination++] = values[left++];
            } else {
                auxiliary[destination++] = values[right++];
            }
        }

        while (left < middle) {
            auxiliary[destination++] = values[left++];
        }

        while (right < end) {
            auxiliary[destination++] = values[right++];
        }

        System.arraycopy(auxiliary, start, values, start, end - start);
    }

    private static void quickSort(int[] values, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(values, low, high);

            // Recurse into the smaller partition to keep stack usage bounded.
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(values, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(values, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] values, int low, int high) {
        int pivot = values[high];
        int smallerBoundary = low;

        for (int i = low; i < high; i++) {
            if (values[i] <= pivot) {
                swap(values, smallerBoundary, i);
                smallerBoundary++;
            }
        }

        swap(values, smallerBoundary, high);
        return smallerBoundary;
    }

    private static int recursiveBinarySearch(
            int[] values,
            int target,
            int low,
            int high,
            int firstMatch
    ) {
        if (low > high) {
            return firstMatch;
        }

        int middle = low + (high - low) / 2;

        if (values[middle] < target) {
            return recursiveBinarySearch(
                    values, target, middle + 1, high, firstMatch
            );
        }

        if (values[middle] == target) {
            firstMatch = middle;
        }

        return recursiveBinarySearch(
                values, target, low, middle - 1, firstMatch
        );
    }

    private static int[] copyOf(int[] values) {
        requireArray(values);
        return values.clone();
    }

    private static void requireArray(int[] values) {
        Objects.requireNonNull(values, "values must not be null");
    }

    private static void swap(int[] values, int first, int second) {
        int temporary = values[first];
        values[first] = values[second];
        values[second] = temporary;
    }
}