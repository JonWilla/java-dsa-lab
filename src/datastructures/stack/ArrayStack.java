package datastructures.stack;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A generic last-in, first-out stack backed by a dynamically
 * resizing array.
 *
 * @param <T> the type of elements stored in the stack
 */
public class ArrayStack<T> {

    private static final int DEFAULT_CAPACITY = 8;

    private T[] elements;
    private int size;

    /**
     * Creates an empty stack with the default initial capacity.
     */
    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty stack with the specified initial capacity.
     *
     * @param initialCapacity the initial size of the backing array
     * @throws IllegalArgumentException if initialCapacity is less than 1
     */
    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException(
                    "Initial capacity must be at least 1."
            );
        }

        elements = (T[]) new Object[initialCapacity];
    }

    /**
     * Adds an element to the top of the stack.
     *
     * @param element the element to add
     * @throws NullPointerException if element is null
     */
    public void push(T element) {
        Objects.requireNonNull(element, "Stack does not accept null elements.");

        if (size == elements.length) {
            resize(elements.length * 2);
        }

        elements[size] = element;
        size++;
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the removed top element
     * @throws NoSuchElementException if the stack is empty
     */
    public T pop() {
        ensureNotEmpty();

        size--;
        T removedElement = elements[size];
        elements[size] = null;

        return removedElement;
    }

    /**
     * Returns the element at the top without removing it.
     *
     * @return the current top element
     * @throws NoSuchElementException if the stack is empty
     */
    public T peek() {
        ensureNotEmpty();
        return elements[size - 1];
    }

    /**
     * Returns the number of elements stored in the stack.
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Determines whether the stack contains no elements.
     *
     * @return true when the stack is empty; otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes every element from the stack.
     */
    public void clear() {
        Arrays.fill(elements, 0, size, null);
        size = 0;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }
    }

    private void resize(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }
}