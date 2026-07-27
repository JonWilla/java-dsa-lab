package datastructures.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class UndirectedGraph<T> {

    private final Map<T, LinkedHashSet<T>> adjacency = new LinkedHashMap<>();
    private int edgeCount;

    public boolean addVertex(T vertex) {
        requireVertex(vertex);
        if (adjacency.containsKey(vertex)) {
            return false;
        }
        adjacency.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean containsVertex(T vertex) {
        requireVertex(vertex);
        return adjacency.containsKey(vertex);
    }

    public boolean addEdge(T first, T second) {
        requireExistingVertex(first);
        requireExistingVertex(second);
        if (Objects.equals(first, second)) {
            throw new IllegalArgumentException("Self-loops are not supported");
        }

        if (!adjacency.get(first).add(second)) {
            return false;
        }
        adjacency.get(second).add(first);
        edgeCount++;
        return true;
    }

    public boolean containsEdge(T first, T second) {
        requireExistingVertex(first);
        requireExistingVertex(second);
        return adjacency.get(first).contains(second);
    }

    public boolean removeEdge(T first, T second) {
        requireExistingVertex(first);
        requireExistingVertex(second);
        if (!adjacency.get(first).remove(second)) {
            return false;
        }
        adjacency.get(second).remove(first);
        edgeCount--;
        return true;
    }

    public boolean removeVertex(T vertex) {
        requireVertex(vertex);
        LinkedHashSet<T> neighbors = adjacency.get(vertex);
        if (neighbors == null) {
            return false;
        }

        for (T neighbor : new ArrayList<>(neighbors)) {
            adjacency.get(neighbor).remove(vertex);
            edgeCount--;
        }
        adjacency.remove(vertex);
        return true;
    }

    public int vertexCount() {
        return adjacency.size();
    }

    public int edgeCount() {
        return edgeCount;
    }

    public int degree(T vertex) {
        requireExistingVertex(vertex);
        return adjacency.get(vertex).size();
    }

    public Set<T> vertices() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(adjacency.keySet()));
    }

    public Set<T> neighborsOf(T vertex) {
        requireExistingVertex(vertex);
        return Collections.unmodifiableSet(
                new LinkedHashSet<>(adjacency.get(vertex))
        );
    }

    public List<T> breadthFirstTraversal(T start) {
        requireExistingVertex(start);

        List<T> result = new ArrayList<>();
        Set<T> visited = new LinkedHashSet<>();
        Queue<T> frontier = new ArrayDeque<>();
        visited.add(start);
        frontier.add(start);

        while (!frontier.isEmpty()) {
            T current = frontier.remove();
            result.add(current);

            for (T neighbor : adjacency.get(current)) {
                if (visited.add(neighbor)) {
                    frontier.add(neighbor);
                }
            }
        }
        return result;
    }

    public List<T> depthFirstTraversal(T start) {
        requireExistingVertex(start);

        List<T> result = new ArrayList<>();
        Set<T> visited = new LinkedHashSet<>();
        Deque<T> frontier = new ArrayDeque<>();
        frontier.push(start);

        while (!frontier.isEmpty()) {
            T current = frontier.pop();
            if (!visited.add(current)) {
                continue;
            }
            result.add(current);

            List<T> neighbors = new ArrayList<>(adjacency.get(current));
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                T neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    frontier.push(neighbor);
                }
            }
        }
        return result;
    }

    public boolean hasPath(T start, T destination) {
        requireExistingVertex(start);
        requireExistingVertex(destination);
        return breadthFirstTraversal(start).contains(destination);
    }

    public List<T> shortestPath(T start, T destination) {
        requireExistingVertex(start);
        requireExistingVertex(destination);

        Queue<T> frontier = new ArrayDeque<>();
        Set<T> visited = new LinkedHashSet<>();
        Map<T, T> predecessor = new LinkedHashMap<>();
        frontier.add(start);
        visited.add(start);

        while (!frontier.isEmpty()) {
            T current = frontier.remove();
            if (Objects.equals(current, destination)) {
                return buildPath(predecessor, destination);
            }

            for (T neighbor : adjacency.get(current)) {
                if (visited.add(neighbor)) {
                    predecessor.put(neighbor, current);
                    frontier.add(neighbor);
                }
            }
        }
        return List.of();
    }

    public boolean isConnected() {
        if (adjacency.isEmpty()) {
            return true;
        }
        T start = adjacency.keySet().iterator().next();
        return breadthFirstTraversal(start).size() == adjacency.size();
    }

    public void clear() {
        adjacency.clear();
        edgeCount = 0;
    }

    private List<T> buildPath(Map<T, T> predecessor, T destination) {
        List<T> path = new ArrayList<>();
        T current = destination;
        while (current != null) {
            path.add(current);
            current = predecessor.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private void requireExistingVertex(T vertex) {
        requireVertex(vertex);
        if (!adjacency.containsKey(vertex)) {
            throw new NoSuchElementException("Unknown vertex: " + vertex);
        }
    }

    private void requireVertex(T vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null");
    }
}