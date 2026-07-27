package datastructures.graph;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class UndirectedGraphTest {

    private static int testsPassed;

    public static void main(String[] args) {
        testVertices();
        testEdges();
        testTraversals();
        testPaths();
        testRemoval();
        testConnectivityAndClear();
        testInvalidOperations();

        System.out.println("All " + testsPassed + " graph tests passed.");
    }

    private static void testVertices() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();

        assertEquals(0, graph.vertexCount(), "New graph vertex count");
        assertEquals(0, graph.edgeCount(), "New graph edge count");
        assertTrue(graph.addVertex("A"), "First insertion should succeed");
        assertFalse(graph.addVertex("A"), "Duplicate insertion should fail");
        assertTrue(graph.containsVertex("A"), "Graph should contain A");
        assertFalse(graph.containsVertex("B"), "Graph should not contain B");
        assertEquals(Set.of("A"), graph.vertices(), "Vertex snapshot");
    }

    private static void testEdges() {
        UndirectedGraph<String> graph = sampleGraph();

        assertEquals(6, graph.vertexCount(), "Sample vertex count");
        assertEquals(6, graph.edgeCount(), "Sample edge count");
        assertTrue(graph.containsEdge("A", "B"), "A-B should exist");
        assertTrue(graph.containsEdge("B", "A"), "Edges should be undirected");
        assertFalse(graph.containsEdge("A", "F"), "A-F should not exist");
        assertFalse(graph.addEdge("A", "B"), "Duplicate edge should fail");
        assertEquals(6, graph.edgeCount(), "Duplicate must not change count");
        assertEquals(2, graph.degree("A"), "A degree");
        assertEquals(3, graph.degree("B"), "B degree");
        assertEquals(Set.of("B", "C"), graph.neighborsOf("A"), "A neighbors");
    }

    private static void testTraversals() {
        UndirectedGraph<String> graph = sampleGraph();

        assertEquals(List.of("A", "B", "C", "D", "E", "F"),
                graph.breadthFirstTraversal("A"), "BFS order");
        assertEquals(List.of("A", "B", "D", "E", "C", "F"),
                graph.depthFirstTraversal("A"), "DFS order");
        assertEquals(List.of("F", "E", "B", "C", "A", "D"),
                graph.breadthFirstTraversal("F"), "BFS from F");

        graph.addVertex("Z");
        assertEquals(List.of("Z"), graph.breadthFirstTraversal("Z"),
                "Isolated BFS");
        assertEquals(List.of("Z"), graph.depthFirstTraversal("Z"),
                "Isolated DFS");
    }

    private static void testPaths() {
        UndirectedGraph<String> graph = sampleGraph();

        assertTrue(graph.hasPath("A", "F"), "A should reach F");
        assertTrue(graph.hasPath("A", "A"), "Vertex reaches itself");
        assertEquals(List.of("A"), graph.shortestPath("A", "A"),
                "Self path");
        assertEquals(List.of("A", "B", "E", "F"),
                graph.shortestPath("A", "F"), "Shortest A-F path");

        graph.addVertex("Z");
        assertFalse(graph.hasPath("A", "Z"), "A should not reach Z");
        assertEquals(List.of(), graph.shortestPath("A", "Z"),
                "Disconnected path should be empty");
    }

    private static void testRemoval() {
        UndirectedGraph<String> graph = sampleGraph();

        assertTrue(graph.removeEdge("A", "B"), "Remove A-B");
        assertFalse(graph.containsEdge("A", "B"), "A-B removed");
        assertFalse(graph.containsEdge("B", "A"), "B-A removed");
        assertEquals(5, graph.edgeCount(), "Edge count after removal");
        assertFalse(graph.removeEdge("A", "B"), "Remove missing edge");

        assertTrue(graph.removeVertex("E"), "Remove E");
        assertFalse(graph.containsVertex("E"), "E removed");
        assertEquals(5, graph.vertexCount(), "Vertex count after removal");
        assertEquals(2, graph.edgeCount(), "Incident edges removed");
        assertEquals(Set.of("D"), graph.neighborsOf("B"),
                "B no longer references E");
        assertEquals(Set.of(), graph.neighborsOf("F"),
                "F no longer references E");
        assertFalse(graph.removeVertex("Z"), "Remove unknown vertex");
    }

    private static void testConnectivityAndClear() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();

        assertTrue(graph.isConnected(), "Empty graph is connected");
        graph.addVertex(1);
        assertTrue(graph.isConnected(), "One vertex is connected");
        graph.addVertex(2);
        assertFalse(graph.isConnected(), "Two isolated vertices");
        graph.addEdge(1, 2);
        assertTrue(graph.isConnected(), "Edge connects vertices");
        graph.clear();
        assertEquals(0, graph.vertexCount(), "Clear vertex count");
        assertEquals(0, graph.edgeCount(), "Clear edge count");
        assertTrue(graph.isConnected(), "Cleared graph is connected");
    }

    private static void testInvalidOperations() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        graph.addVertex("A");

        assertThrows(NullPointerException.class, () -> graph.addVertex(null),
                "Reject null vertex");
        assertThrows(NoSuchElementException.class, () -> graph.addEdge("A", "B"),
                "Reject unknown endpoint");
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "A"),
                "Reject self-loop");
        assertThrows(NoSuchElementException.class,
                () -> graph.breadthFirstTraversal("B"), "Reject unknown BFS start");
        assertThrows(NoSuchElementException.class,
                () -> graph.depthFirstTraversal("B"), "Reject unknown DFS start");
        assertThrows(NoSuchElementException.class,
                () -> graph.shortestPath("A", "B"),
                "Reject unknown path destination");
        assertThrows(UnsupportedOperationException.class,
                () -> graph.vertices().add("B"), "Vertices view is immutable");
        assertThrows(UnsupportedOperationException.class,
                () -> graph.neighborsOf("A").add("B"),
                "Neighbors view is immutable");
    }

    private static UndirectedGraph<String> sampleGraph() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        for (String vertex : List.of("A", "B", "C", "D", "E", "F")) {
            graph.addVertex(vertex);
        }
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "E");
        graph.addEdge("E", "F");
        return graph;
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
            Object expected, Object actual, String message
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
        } catch (Throwable actual) {
            if (expectedType.isInstance(actual)) {
                testsPassed++;
                return;
            }
            throw new AssertionError(
                    message + " | Expected: " + expectedType.getSimpleName()
                            + ", Actual: " + actual.getClass().getSimpleName(),
                    actual
            );
        }
        throw new AssertionError(
                message + " | Expected " + expectedType.getSimpleName()
                        + " but nothing was thrown"
        );
    }
}