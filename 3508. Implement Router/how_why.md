# How\_Why.md

## Problem

You need to design a **Router** with limited memory that processes incoming packets.
Each packet is represented as `(source, destination, timestamp)`.

The Router must support:

1. **`addPacket(source, destination, timestamp)`**

   * Adds a packet if it is unique (same `(source, destination, timestamp)` not already stored).
   * If memory is full, automatically forwards the oldest packet.
   * Returns `true` if added, `false` otherwise.

2. **`forwardPacket()`**

   * Forwards (removes and returns) the oldest packet in memory.
   * If no packets exist, return an empty array.

3. **`getCount(destination, startTime, endTime)`**

   * Returns the number of packets for the given `destination` whose timestamps fall in `[startTime, endTime]`.

Constraints:

* Must efficiently handle **duplicates**, **limited memory**, and **range queries**.
* Multiple operations will be called, so performance matters.

---

## How (Step-by-step Solution)

### Approach 1: Encoded Key + HashMaps (compact)

1. **Encode packets** into a `long` key:

   ```java
   key = (source << 40) | (destination << 20) | timestamp
   ```

   Ensures uniqueness.

2. **Store packets** in:

   * `packets` → map of `key → {source, destination, timestamp}`
   * `counts` → map of `destination → sorted list of timestamps`
   * `queue` → FIFO order for eviction

3. **Operations**:

   * `addPacket`:

     * Reject if key exists.
     * If memory full → `forwardPacket`.
     * Insert into maps + queue.
   * `forwardPacket`:

     * Remove earliest packet from queue + maps.
   * `getCount`:

     * Use **binary search** (`lowerBound`, `upperBound`) on timestamps list to count efficiently.

✅ **Efficient for time range queries.**
⚠️ Assumes timestamps are **inserted in non-decreasing order** (otherwise `counts` list may not be sorted).

---

### Approach 2: Queue + Per-destination List (robust)

1. **Use a queue** (`Deque<int[]>`) to maintain global order of packets for eviction.
2. **Per-destination storage**:

   * `map[destination] → list of {source, timestamp}`
   * Each list is naturally ordered by arrival.
3. **Operations**:

   * `addPacket`:

     * Binary search (`small()`) to check for duplicates in that destination list.
     * Insert new packet into both queue + list.
     * If memory exceeded → forward oldest.
   * `forwardPacket`:

     * Poll from queue.
     * Remove first occurrence in destination list.
   * `getCount`:

     * Binary search (`small()`, `big()`) to count timestamps in `[start, end]`.

✅ **Works correctly regardless of timestamp order.**
⚠️ Slightly heavier overhead because of duplicate checking and extra list operations.

---

## Why (Reasoning)

* We need to balance **fast packet eviction** and **efficient range queries**.
* Both approaches use:

  * **Queue** → maintain eviction order.
  * **Map per destination** → allow efficient timestamp counting.
* **Binary search** ensures `O(log n)` lookup for time ranges instead of `O(n)`.
* Uniqueness check ensures no duplicate `(source, destination, timestamp)` packets.

---

## Complexity Analysis

| Operation       | Approach 1 (Encoded Key) | Approach 2 (Queue + Lists)   |
| --------------- | ------------------------ | ---------------------------- |
| `addPacket`     | O(log n) (binary search) | O(log n) (binary search)     |
| `forwardPacket` | O(1)                     | O(1) + remove from list O(1) |
| `getCount`      | O(log n)                 | O(log n)                     |
| Space           | O(n)                     | O(n)                         |

---

## Best Method

* If **timestamps are guaranteed sorted by arrival** →
  **Approach 1** is best (simpler & more efficient).
* If **timestamps may arrive in arbitrary order** →
  **Approach 2** is safer (robust against disorder).

---

## Example Walkthrough

### Input

```java
Router router = new Router(2);
router.addPacket(1, 10, 5);   // true
router.addPacket(2, 10, 6);   // true
router.addPacket(1, 10, 5);   // false (duplicate)
router.addPacket(3, 20, 7);   // evicts oldest
router.getCount(10, 5, 6);    // 1
router.forwardPacket();       // [2, 10, 6]
```

### Output

```java
true
true
false
true
1
[2,10,6]
```

---
