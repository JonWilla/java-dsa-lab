# Java Data Structures and Algorithms Lab

A Java portfolio project focused on implementing data structures and algorithms from scratch, testing edge cases, analyzing performance, and practicing professional Git workflows.

## Project Goals

- Strengthen Java and object-oriented programming skills
- Implement common data structures without relying on Java’s built-in collection implementations
- Analyze time and space complexity
- Test normal behavior, boundary conditions, and error handling
- Practice feature branches, meaningful commits, and code reviews
- Prepare for software engineering internship interviews

## Implemented Features

### Generic Array Queue

The project includes a generic first-in, first-out queue implemented with a dynamically resizing circular array.

Supported operations:

- `enqueue` — adds an element to the back
- `dequeue` — removes and returns the front element
- `peek` — returns the front element without removing it
- `size` — returns the number of stored elements
- `isEmpty` — determines whether the queue is empty
- Automatic capacity expansion when the backing array becomes full

The queue rejects `null` values and throws an exception when `dequeue` or `peek` is called on an empty queue.

### Queue Complexity

| Operation | Typical Time | Worst-Case Time |
|---|---:|---:|
| `enqueue` | O(1) | O(n) during resizing |
| `dequeue` | O(1) | O(1) |
| `peek` | O(1) | O(1) |
| `size` | O(1) | O(1) |
| `isEmpty` | O(1) | O(1) |

Although an individual resize requires O(n) time, resizing does not occur on every insertion. Therefore, `enqueue` has amortized O(1) time complexity across many operations.

## Queue Tests

The queue test runner checks:

1. First-in, first-out ordering
2. `peek` without removal
3. Circular-array wraparound and resizing
4. Empty-queue exceptions
5. Rejection of `null` values

Expected test output:

```text
All 5 queue tests passed.