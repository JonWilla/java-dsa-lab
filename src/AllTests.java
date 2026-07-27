import algorithms.sortingsearching.SortingSearchingAlgorithmsTest;
import datastructures.graph.UndirectedGraphTest;
import datastructures.hashtable.SeparateChainingHashTableTest;
import datastructures.heap.MinHeapPriorityQueueTest;
import datastructures.list.SinglyLinkedListTest;
import datastructures.queue.ArrayQueueTest;
import datastructures.stack.ArrayStackTest;
import datastructures.tree.BinarySearchTreeTest;

public final class AllTests {

    private AllTests() {
    }

    public static void main(String[] args) {
        run("Array queue", () -> ArrayQueueTest.main(new String[0]));
        run("Array stack", () -> ArrayStackTest.main(new String[0]));
        run("Singly linked list", () -> SinglyLinkedListTest.main(new String[0]));
        run("Separate-chaining hash table",
                () -> SeparateChainingHashTableTest.main(new String[0]));
        run("Binary search tree", () -> BinarySearchTreeTest.main(new String[0]));
        run("Min-heap priority queue",
                () -> MinHeapPriorityQueueTest.main(new String[0]));
        run("Undirected graph", () -> UndirectedGraphTest.main(new String[0]));
        run("Sorting and searching",
                () -> SortingSearchingAlgorithmsTest.main(new String[0]));

        System.out.println("\n========================================");
        System.out.println("ALL 8 TEST SUITES PASSED");
        System.out.println("========================================");
    }

    private static void run(String name, Runnable suite) {
        System.out.println("\n--- " + name + " ---");
        suite.run();
    }
}
