package datastructures.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BinarySearchTree<K extends Comparable<? super K>, V> {

    private Node<K, V> root;
    private int size;

    private static final class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        requireKey(key);

        if (root == null) {
            root = new Node<>(key, value);
            size = 1;
            return;
        }

        Node<K, V> current = root;

        while (true) {
            int comparison = key.compareTo(current.key);

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node<>(key, value);
                    size++;
                    return;
                }
                current = current.left;
            } else if (comparison > 0) {
                if (current.right == null) {
                    current.right = new Node<>(key, value);
                    size++;
                    return;
                }
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }
    }

    public V get(K key) {
        Node<K, V> node = findNode(key);

        if (node == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }

        return node.value;
    }

    public V getOrDefault(K key, V defaultValue) {
        Node<K, V> node = findNode(key);
        return node == null ? defaultValue : node.value;
    }

    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    public K minKey() {
        ensureNotEmpty();
        return minimum(root).key;
    }

    public K maxKey() {
        ensureNotEmpty();

        Node<K, V> current = root;
        while (current.right != null) {
            current = current.right;
        }

        return current.key;
    }

    public int height() {
        return height(root);
    }

    public List<K> inOrderKeys() {
        List<K> keys = new ArrayList<>();
        collectInOrder(root, keys);
        return keys;
    }

    public V remove(K key) {
        requireKey(key);
        Node<K, V> target = findNode(key);

        if (target == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }

        V removedValue = target.value;
        root = remove(root, key);
        size--;
        return removedValue;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    private Node<K, V> findNode(K key) {
        requireKey(key);
        Node<K, V> current = root;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return current;
            }
        }

        return null;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            node.left = remove(node.left, key);
        } else if (comparison > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            Node<K, V> successor = minimum(node.right);
            Node<K, V> replacement = new Node<>(successor.key, successor.value);
            replacement.left = node.left;
            replacement.right = removeMinimum(node.right);
            return replacement;
        }

        return node;
    }

    private Node<K, V> removeMinimum(Node<K, V> node) {
        if (node.left == null) {
            return node.right;
        }

        node.left = removeMinimum(node.left);
        return node;
    }

    private Node<K, V> minimum(Node<K, V> node) {
        Node<K, V> current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    private int height(Node<K, V> node) {
        if (node == null) {
            return -1;
        }

        return 1 + Math.max(height(node.left), height(node.right));
    }

    private void collectInOrder(Node<K, V> node, List<K> keys) {
        if (node == null) {
            return;
        }

        collectInOrder(node.left, keys);
        keys.add(node.key);
        collectInOrder(node.right, keys);
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
    }

    private void requireKey(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
    }
}