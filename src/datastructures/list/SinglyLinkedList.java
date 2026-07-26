package datastructures.list;

public class SinglyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private final T data;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }
    public T getFirst() {
        ensureNotEmpty();
        return head.data;
    }

    public T getLast() {
        ensureNotEmpty();
        return tail.data;
    }
    public T get(int index) {
        checkElementIndex(index);

        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }
    public void add(int index, T data) {
        checkPositionIndex(index);

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == size) {
            addLast(data);
            return;
        }

        Node<T> previous = head;

        for (int i = 0; i < index - 1; i++) {
            previous = previous.next;
        }

        Node<T> newNode = new Node<>(data);
        newNode.next = previous.next;
        previous.next = newNode;
        size++;
    }
    public T removeFirst() {
        ensureNotEmpty();

        T removedData = head.data;
        head = head.next;
        size--;

        if (size == 0) {
            tail = null;
        }

        return removedData;
    }

    public T removeLast() {
        ensureNotEmpty();

        if (size == 1) {
            return removeFirst();
        }

        Node<T> current = head;

        while (current.next != tail) {
            current = current.next;
        }

        T removedData = tail.data;
        current.next = null;
        tail = current;
        size--;

        return removedData;
    }
    public T remove(int index) {
        checkElementIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<T> previous = head;

        for (int i = 0; i < index - 1; i++) {
            previous = previous.next;
        }

        Node<T> removedNode = previous.next;
        previous.next = removedNode.next;
        size--;

        return removedNode.data;
    }
    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }
    }
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }
    }
    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("List is empty");
        }
    }
}