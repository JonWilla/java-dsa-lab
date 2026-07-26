# Java Data Structures and Algorithms Lab

A Java portfolio project focused on implementing data structures and algorithms from scratch, testing edge cases, analyzing performance, and practicing professional Git workflows.

## Project Goals

- Strengthen Java and object-oriented programming skills
- Implement common data structures without relying on Java’s built-in collection implementations
- Analyze time and space complexity
- Test normal behavior, boundary conditions, and error handling
- Practice feature branches, meaningful commits, and code reviews
- Build practical problem-solving skills through data structures, algorithms, testing, and complexity analysis

## Project Structure

```text
java-dsa-lab/
├── README.md
└── src/
    ├── Main.java
    └── datastructures/
        ├── queue/
        │   ├── ArrayQueue.java
        │   └── ArrayQueueTest.java
        └── stack/
            ├── ArrayStack.java
            └── ArrayStackTest.java
```

## Implemented Features

### Generic Array Queue

`ArrayQueue<T>` is a generic first-in, first-out data structure implemented with a dynamically resizing circular array.

#### Supported Operations

- `enqueue(T element)` adds an element to the back.
- `dequeue()` removes and returns the front element.
- `peek()` returns the front element without removing it.
- `size()` returns the number of stored elements.
- `isEmpty()` reports whether the queue is empty.
- The backing array automatically expands when it becomes full.

The queue rejects `null` values. Calling `dequeue()` or `peek()` on an empty queue throws `NoSuchElementException`.

#### Queue Complexity

| Operation | Typical Time | Worst-Case Time |
|---|---:|---:|
| `enqueue()` | O(1) | O(n) during resizing |
| `dequeue()` | O(1) | O(1) |
| `peek()` | O(1) | O(1) |
| `size()` | O(1) | O(1) |
| `isEmpty()` | O(1) | O(1) |

Although an individual resize requires O(n) time, resizing does not occur on every insertion. Therefore, `enqueue()` has amortized O(1) time complexity across many operations.

#### Queue Test Coverage

The queue test suite verifies:

- First-in, first-out ordering
- Non-destructive `peek()`
- Circular-array wraparound
- Automatic array resizing
- Empty-queue exceptions
- Rejection of `null` values

Expected test output:

```text
All 5 queue tests passed.
```

### Generic Array Stack

`ArrayStack<T>` is a generic last-in, first-out data structure backed by a dynamically resizing array.

#### Supported Operations

- `push(T element)` adds an element to the top.
- `pop()` removes and returns the top element.
- `peek()` returns the top element without removing it.
- `size()` returns the number of stored elements.
- `isEmpty()` reports whether the stack is empty.
- `clear()` removes all elements while keeping the stack reusable.

The stack rejects `null` values. Calling `pop()` or `peek()` on an empty stack throws `NoSuchElementException`.

#### Stack Complexity

| Operation | Time Complexity |
|---|---:|
| `push()` | Amortized O(1) |
| `pop()` | O(1) |
| `peek()` | O(1) |
| `size()` | O(1) |
| `isEmpty()` | O(1) |
| `clear()` | O(n) |

The backing array doubles when it becomes full, allowing `push()` to run in amortized constant time.

#### Stack Test Coverage

The stack test suite verifies:

- New-stack state
- Last-in, first-out ordering
- Non-destructive `peek()`
- Automatic array resizing
- Empty-stack exceptions
- Invalid-capacity rejection
- Rejection of `null` values
- Clearing and reusing the stack

Expected test output:

```text
All 7 stack tests passed.
```

## Demonstration

`Main.java` provides practical demonstrations of both data structures:

- The queue processes mission tasks in first-in, first-out order.
- The stack manages navigation history in last-in, first-out order.

Expected output:

```text
=== Queue Demonstration ===
Next task: Validate telemetry
Completed: Validate telemetry
Completed: Analyze sensor data
Completed: Generate mission report

=== Stack Demonstration ===
Current page: Navigation Analysis
Returning from: Navigation Analysis
Previous page: Telemetry Report
Pages remaining: 2
```

## Compile and Run

From the project root, compile the source files:

```powershell
javac -d out src\Main.java src\datastructures\queue\ArrayQueue.java src\datastructures\queue\ArrayQueueTest.java src\datastructures\stack\ArrayStack.java src\datastructures\stack\ArrayStackTest.java
```

Run the queue tests:

```powershell
java -cp out datastructures.queue.ArrayQueueTest
```

Run the stack tests:

```powershell
java -cp out datastructures.stack.ArrayStackTest
```

Run the combined demonstration:

```powershell
java -cp out Main
```

## Current Status

- Generic circular-array queue implemented and tested
- Generic dynamically resizing array stack implemented and tested
- Queue and stack demonstrations available through `Main`
- Operations and complexity documented
- Additional data structures and algorithms in progress