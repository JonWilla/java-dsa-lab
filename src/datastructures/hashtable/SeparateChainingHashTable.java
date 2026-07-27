package datastructures.hashtable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SeparateChainingHashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;

    private static final class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] buckets;
    private int size;

    public SeparateChainingHashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SeparateChainingHashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Initial capacity must be greater than zero"
            );
        }

        buckets = (Entry<K, V>[]) new Entry[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return buckets.length;
    }

    public double loadFactor() {
        return (double) size / buckets.length;
    }

    public V put(K key, V value) {
        requireKey(key);

        int index = bucketIndex(key, buckets.length);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                V previousValue = current.value;
                current.value = value;
                return previousValue;
            }
            current = current.next;
        }

        if ((double) (size + 1) / buckets.length > MAX_LOAD_FACTOR) {
            resize(buckets.length * 2);
            index = bucketIndex(key, buckets.length);
        }

        buckets[index] = new Entry<>(key, value, buckets[index]);
        size++;
        return null;
    }

    public V get(K key) {
        Entry<K, V> entry = findEntry(key);

        if (entry == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }

        return entry.value;
    }

    public V getOrDefault(K key, V defaultValue) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? defaultValue : entry.value;
    }

    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    public V remove(K key) {
        requireKey(key);

        int index = bucketIndex(key, buckets.length);
        Entry<K, V> current = buckets[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    public List<K> keys() {
        List<K> keys = new ArrayList<>(size);

        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> current = bucket;

            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }

        return keys;
    }

    public void clear() {
        @SuppressWarnings("unchecked")
        Entry<K, V>[] emptyBuckets =
                (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];

        buckets = emptyBuckets;
        size = 0;
    }

    private Entry<K, V> findEntry(K key) {
        requireKey(key);

        int index = bucketIndex(key, buckets.length);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }
            current = current.next;
        }

        return null;
    }

    private int bucketIndex(K key, int capacity) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = (Entry<K, V>[]) new Entry[newCapacity];

        for (Entry<K, V> bucket : oldBuckets) {
            Entry<K, V> current = bucket;

            while (current != null) {
                Entry<K, V> next = current.next;
                int index = bucketIndex(current.key, newCapacity);
                current.next = buckets[index];
                buckets[index] = current;
                current = next;
            }
        }
    }

    private void requireKey(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
    }
}