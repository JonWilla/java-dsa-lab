package datastructures.queue;

import java.util.NoSuchElementException;
import java.util.Objects;

public final class ArrayQueue<T> {

    private Object[] elements;
    private int head;
    private int tail;
    private int size;

    public ArrayQueue() {
        this(4);
    }

    public ArrayQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Initial capacity must be greater than zero."
            );
        }

        elements = new Object[initialCapacity];
    }

    public void enqueue(T value) {
        Objects.requireNonNull(value, "Queue does not accept null values.");

        if (size == elements.length) {
            resize();
        }

        elements[tail] = value;
        tail = (tail + 1) % elements.length;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        ensureNotEmpty();

        T value = (T) elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;

        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        ensureNotEmpty();
        return (T) elements[head];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Object[] largerArray = new Object[elements.length * 2];

        for (int i = 0; i < size; i++) {
            largerArray[i] =
                    elements[(head + i) % elements.length];
        }

        elements = largerArray;
        head = 0;
        tail = size;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
    }
}