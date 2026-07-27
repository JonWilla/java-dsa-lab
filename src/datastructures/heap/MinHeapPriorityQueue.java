package datastructures.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MinHeapPriorityQueue<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;

    public MinHeapPriorityQueue() {
        this(DEFAULT_CAPACITY, null);
    }

    public MinHeapPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    public MinHeapPriorityQueue(Comparator<? super T> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    public MinHeapPriorityQueue(
            int initialCapacity,
            Comparator<? super T> comparator
    ) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException(
                    "Initial capacity must be at least 1"
            );
        }

        heap = new Object[initialCapacity];
        this.comparator = comparator;
    }

    public void offer(T element) {
        Objects.requireNonNull(element, "Element cannot be null");
        ensureCapacity();

        heap[size] = element;
        siftUp(size);
        size++;
    }

    public T peek() {
        ensureNotEmpty();
        return elementAt(0);
    }

    public T poll() {
        ensureNotEmpty();
        return removeAt(0);
    }

    public boolean remove(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        for (int i = 0; i < size; i++) {
            if (element.equals(heap[i])) {
                removeAt(i);
                return true;
            }
        }

        return false;
    }

    public boolean contains(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        for (int i = 0; i < size; i++) {
            if (element.equals(heap[i])) {
                return true;
            }
        }

        return false;
    }

    public boolean replace(T oldElement, T newElement) {
        Objects.requireNonNull(oldElement, "Old element cannot be null");
        Objects.requireNonNull(newElement, "New element cannot be null");

        for (int i = 0; i < size; i++) {
            if (oldElement.equals(heap[i])) {
                heap[i] = newElement;

                if (i > 0 && compare(elementAt(i), elementAt(parent(i))) < 0) {
                    siftUp(i);
                } else {
                    siftDown(i);
                }

                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }

        size = 0;
    }

    public List<T> toLevelOrderList() {
        List<T> elements = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            elements.add(elementAt(i));
        }

        return elements;
    }

    private T removeAt(int index) {
        T removedElement = elementAt(index);
        int lastIndex = size - 1;
        T lastElement = elementAt(lastIndex);

        heap[lastIndex] = null;
        size--;

        if (index < size) {
            heap[index] = lastElement;

            if (index > 0
                    && compare(elementAt(index), elementAt(parent(index))) < 0) {
                siftUp(index);
            } else {
                siftDown(index);
            }
        }

        return removedElement;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = parent(index);

            if (compare(elementAt(index), elementAt(parentIndex)) >= 0) {
                return;
            }

            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void siftDown(int index) {
        while (leftChild(index) < size) {
            int smallerChild = leftChild(index);
            int rightChild = rightChild(index);

            if (rightChild < size
                    && compare(
                    elementAt(rightChild),
                    elementAt(smallerChild)
            ) < 0) {
                smallerChild = rightChild;
            }

            if (compare(elementAt(index), elementAt(smallerChild)) <= 0) {
                return;
            }

            swap(index, smallerChild);
            index = smallerChild;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }

        if (!(first instanceof Comparable<?>)) {
            throw new IllegalStateException(
                    "Elements must be Comparable when no Comparator is provided"
            );
        }

        return ((Comparable<? super T>) first).compareTo(second);
    }

    private void ensureCapacity() {
        if (size < heap.length) {
            return;
        }

        Object[] expandedHeap = new Object[heap.length * 2];
        System.arraycopy(heap, 0, expandedHeap, 0, size);
        heap = expandedHeap;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }

    private void swap(int firstIndex, int secondIndex) {
        Object temporary = heap[firstIndex];
        heap[firstIndex] = heap[secondIndex];
        heap[secondIndex] = temporary;
    }
}