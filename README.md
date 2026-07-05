# Data Structures & Algorithmic Applications in Java

Implementations of fundamental data structures, sorting algorithms, priority queues, and binary search trees with practical applications, developed for the **Data Structures** course at Athens University of Economics and Business (AUEB).

---

## Projects Overview

### 1. Linear Data Structures & Applications (`01-linear-structures-and-applications`)
- **Core Structures:** Linked List (`ListImpl`), LIFO Stack (`StackImpl`), and FIFO Queue (`QueueImpl`).
- **Applications:**
  - `NetProfit`: Financial share-trading profit calculator operating on FIFO lot matching.
  - `TagChecking`: Structural HTML syntax validator verifying matching open/close tags using an explicit stack.

### 2. Priority Queues, Sorting & Greedy Heuristics (`02-heaps-sorting-and-greedy`)
- **Core Structures:** Array-based Maximum Priority Queue / Binary Max-Heap (`MaxPQ`).
- **Algorithms:** Recursive and in-place sorting routines (`Sort`).
- **Bin Packing Application (`DiskManager`):**
  - Solving the offline/online Bin Packing problem to minimize storage disks.
  - Evaluation of **Greedy** vs. **Greedy-Decreasing** heuristics across benchmark input sizes ($N \in [100, 10000]$).

### 3. Binary Search Trees & Frequency Analysis (`03-binary-search-tree-wordcounter`)
- **Core Structures:** Dynamic Binary Search Tree (`BST`) supporting insertion, search, and in-order traversals.
- **Application (`WordCounter`):**
  - Parsing unstructured text corpora (e.g., academic essays).
  - Filtering stopwords and computing token frequencies with distinct/total word statistics.

---

## Build & Test
Each subproject includes complete JUnit tests and sample input files:
```bash
# Compile and run tests using standard javac/java
javac -d bin $(find . -name "*.java")