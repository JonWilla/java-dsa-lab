import algorithms.sortingsearching.SortingSearchingAlgorithms;
import datastructures.graph.UndirectedGraph;
import datastructures.hashtable.SeparateChainingHashTable;
import datastructures.heap.MinHeapPriorityQueue;
import datastructures.list.SinglyLinkedList;
import datastructures.queue.ArrayQueue;
import datastructures.stack.ArrayStack;
import datastructures.tree.BinarySearchTree;

import java.util.Arrays;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        System.out.println("=== Java Data Structures & Algorithms Lab ===");
        demonstrateQueue();
        demonstrateStack();
        demonstrateLinkedList();
        demonstrateHashTable();
        demonstrateBinarySearchTree();
        demonstratePriorityQueue();
        demonstrateGraph();
        demonstrateAlgorithms();
        System.out.println("\nAll demonstrations completed successfully.");
    }

    private static void demonstrateQueue() {
        ArrayQueue<String> tasks = new ArrayQueue<>();
        tasks.enqueue("Validate telemetry");
        tasks.enqueue("Analyze sensor data");
        tasks.enqueue("Generate report");

        System.out.println("\nQueue (FIFO)");
        System.out.println("Next task: " + tasks.peek());
        System.out.println("Completed: " + tasks.dequeue());
        System.out.println("Tasks remaining: " + tasks.size());
    }

    private static void demonstrateStack() {
        ArrayStack<String> history = new ArrayStack<>();
        history.push("Dashboard");
        history.push("Telemetry");
        history.push("Analysis");

        System.out.println("\nStack (LIFO)");
        System.out.println("Current page: " + history.peek());
        System.out.println("Returning from: " + history.pop());
        System.out.println("Previous page: " + history.peek());
    }

    private static void demonstrateLinkedList() {
        SinglyLinkedList<String> pipeline = new SinglyLinkedList<>();
        pipeline.addLast("Ingest");
        pipeline.addLast("Validate");
        pipeline.addLast("Publish");

        System.out.println("\nSingly Linked List");
        System.out.println("First stage: " + pipeline.getFirst());
        System.out.println("Last stage: " + pipeline.getLast());
        System.out.println("Second stage: " + pipeline.get(1));
    }

    private static void demonstrateHashTable() {
        SeparateChainingHashTable<String, Integer> readings =
                new SeparateChainingHashTable<>();
        readings.put("temperature", 72);
        readings.put("pressure", 101);
        readings.put("battery", 94);

        System.out.println("\nSeparate-Chaining Hash Table");
        System.out.println("Battery: " + readings.get("battery") + "%");
        System.out.println("Stored readings: " + readings.size());
    }

    private static void demonstrateBinarySearchTree() {
        BinarySearchTree<Integer, String> events = new BinarySearchTree<>();
        events.put(30, "Report generated");
        events.put(10, "Telemetry received");
        events.put(20, "Telemetry validated");

        System.out.println("\nBinary Search Tree");
        System.out.println("Sorted event IDs: " + events.inOrderKeys());
        System.out.println("Event 20: " + events.get(20));
        System.out.println("Tree height: " + events.height());
    }

    private static void demonstratePriorityQueue() {
        MinHeapPriorityQueue<Integer> priorities = new MinHeapPriorityQueue<>();
        priorities.offer(30);
        priorities.offer(10);
        priorities.offer(20);

        System.out.println("\nMin-Heap Priority Queue");
        System.out.println("Highest priority (lowest value): " + priorities.peek());
        System.out.println("Processing order: "
                + priorities.poll() + ", " + priorities.poll() + ", " + priorities.poll());
    }

    private static void demonstrateGraph() {
        UndirectedGraph<String> network = new UndirectedGraph<>();
        network.addVertex("Ground");
        network.addVertex("Relay");
        network.addVertex("Satellite");
        network.addVertex("Archive");
        network.addEdge("Ground", "Relay");
        network.addEdge("Relay", "Satellite");
        network.addEdge("Satellite", "Archive");

        System.out.println("\nUndirected Graph");
        System.out.println("BFS: " + network.breadthFirstTraversal("Ground"));
        System.out.println("DFS: " + network.depthFirstTraversal("Ground"));
        System.out.println("Shortest path: "
                + network.shortestPath("Ground", "Archive"));
    }

    private static void demonstrateAlgorithms() {
        int[] values = {42, 7, 19, 3, 25};
        int[] sorted = SortingSearchingAlgorithms.mergeSort(values);

        System.out.println("\nSorting & Searching");
        System.out.println("Original: " + Arrays.toString(values));
        System.out.println("Merge sort: " + Arrays.toString(sorted));
        System.out.println("Binary-search index of 19: "
                + SortingSearchingAlgorithms.binarySearch(sorted, 19));
    }
}
