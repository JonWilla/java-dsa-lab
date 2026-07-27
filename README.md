# Java Data Structures & Algorithms Lab

A dependency-free Java laboratory that implements foundational data structures
and algorithms from scratch. The project emphasizes correctness, generics,
edge-case handling, asymptotic analysis, automated testing, and a professional
feature-branch Git workflow.

## Highlights

- Generic, array-backed queue and stack with dynamic resizing
- Generic singly linked list with indexed and iterator operations
- Separate-chaining hash table with collision handling and rehashing
- Binary search tree with ordered traversal and complete deletion logic
- Array-backed min-heap priority queue with comparator support
- Undirected graph with BFS, DFS, connectivity, and shortest paths
- Five sorting algorithms and three search implementations
- Eight executable regression suites with no external test dependency
- Integrated demonstration program
- GitHub Actions continuous integration

## Project structure

```text
src/
├── Main.java
├── AllTests.java
├── algorithms/
│   └── sortingsearching/
│       ├── SortingSearchingAlgorithms.java
│       └── SortingSearchingAlgorithmsTest.java
└── datastructures/
    ├── graph/
    ├── hashtable/
    ├── heap/
    ├── list/
    ├── queue/
    ├── stack/
    └── tree/
```

Each package contains its implementation and a self-contained test runner.

## Requirements

- JDK 17 or newer
- PowerShell for the convenience script (optional)

No build framework or third-party library is required.

## Build and run

### PowerShell

Run the complete workflow from the repository root:

```powershell
.\run-tests.ps1
```

### Manual PowerShell commands

```powershell
$sources = Get-ChildItem -Path src -Recurse -Filter *.java |
    Select-Object -ExpandProperty FullName
javac -d out $sources
java -cp out AllTests
java -cp out Main
```

Successful regression output ends with:

```text
ALL 8 TEST SUITES PASSED
```

## Implemented operations

| Component | Core operations |
|---|---|
| Array queue | enqueue, dequeue, peek, resize, circular wraparound |
| Array stack | push, pop, peek, clear, resize |
| Singly linked list | add, get, set, remove, search, iterate |
| Hash table | put, get, update, remove, rehash, key collection |
| Binary search tree | put, get, min/max, height, traversal, remove |
| Min heap | offer, peek, poll, remove, replace, comparator ordering |
| Undirected graph | vertices, edges, BFS, DFS, shortest path, connectivity |
| Algorithms | bubble, selection, insertion, merge, quick, linear and binary search |

## Complexity reference

| Operation | Average | Worst case |
|---|---:|---:|
| Queue enqueue/dequeue | O(1) amortized | O(n) during resize |
| Stack push/pop | O(1) amortized | O(n) during resize |
| Linked-list add/remove first | O(1) | O(1) |
| Linked-list indexed access | O(n) | O(n) |
| Hash-table put/get/remove | O(1) | O(n) |
| BST put/get/remove | O(log n) | O(n) |
| Heap offer/poll | O(log n) | O(log n) |
| Graph BFS/DFS | O(V + E) | O(V + E) |
| Merge sort | O(n log n) | O(n log n) |
| Quick sort | O(n log n) | O(n²) |
| Binary search | O(log n) | O(log n) |

The BST is intentionally unbalanced to expose how insertion order can change
tree height and worst-case performance.

## Design decisions

- **Generics:** Collections accept reusable element, key, and value types.
- **Fail-fast validation:** Invalid nulls, indexes, and empty removals produce
  clear exceptions.
- **Encapsulation:** Internal nodes, buckets, and backing arrays are not
  exposed to callers.
- **Deterministic traversal:** Linked insertion order makes graph test results
  reproducible.
- **Input immutability:** Sorting functions return sorted copies instead of
  changing the caller's array.
- **Dependency-free tests:** Every suite can run with only the JDK, making the
  implementation and assertions easy to inspect.

## Interview discussion points

1. Explain why queue and stack resizing is O(1) amortized even though one
   individual resize is O(n).
2. Compare separate chaining with open addressing for collision resolution.
3. Describe the three BST deletion cases and how an in-order successor is used.
4. Explain why a heap provides fast access to the minimum but does not keep
   every element globally sorted.
5. Compare BFS and DFS, and explain why BFS finds an unweighted shortest path.
6. Explain why an unbalanced BST can degrade from O(log n) to O(n).
7. Compare stable and unstable sorting algorithms and their space tradeoffs.

## Git workflow

The repository was developed through focused feature branches:

```text
feature/queue
feature/stack
feature/singly-linked-list
feature/hash-table
feature/binary-search-tree
feature/heap-priority-queue
feature/graph-traversal
feature/sorting-searching
feature/final-integration
```

Each milestone was compiled and tested before being committed, pushed, and
merged into `main`.

## Résumé-ready description

**Java Data Structures & Algorithms Lab** — Implemented eight reusable Java
data-structure and algorithm modules using generics, dynamic resizing,
collision resolution, tree and heap invariants, BFS/DFS, shortest-path
traversal, and sorting/searching algorithms; built comprehensive dependency-free
regression suites and automated compilation and testing with GitHub Actions.

## Future enhancements

- Add JUnit 5 and Maven or Gradle
- Benchmark operations with JMH
- Add balanced AVL or red-black trees
- Add directed and weighted graph algorithms
- Build an interactive visualizer as a separate project
