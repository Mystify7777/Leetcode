# How_Why.md: Queue Reconstruction by Height

## Problem

You are given an array of people, `people`, which are the attributes of some people in a queue (not necessarily in order). Each `people[i] = [hi, ki]` represents the ith person of height `hi` with exactly `ki` other people in front who have a height greater than or equal to `hi`.

**Reconstruct and return the queue** that is represented by the input array `people`. The returned queue should be formatted as an array `queue`, where `queue[j] = [hj, kj]` is the attributes of the jth person in the queue.

**Example:**

```java
Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
Explanation:
Person 0 has height 5 with nobody taller or equal in front.
Person 1 has height 7 with nobody taller or equal in front.
Person 2 has height 5 with two people taller or equal in front (persons 0 and 1).
Person 3 has height 6 with one person taller or equal in front (person 1).
Person 4 has height 4 with four people taller or equal in front (persons 0, 1, 2, and 3).
Person 5 has height 7 with one person taller or equal in front (person 1).
```

---

## How (Step-by-step Solution)

### Approach: Sort by Height + Insert by Position

The key insight is to process people in a specific order to make the reconstruction simpler.

1. **Sort the People Array**
   - **Primary sort**: By height in **descending order** (tallest first)
   - **Secondary sort**: By k-value in **ascending order** (smaller k first when heights are equal)
   - This ensures we process taller people before shorter people.

2. **Why This Sorting Works?**
   - When we insert taller people first, shorter people won't affect their k-values.
   - For a person with `[h, k]`, when we insert them, all taller people are already placed.
   - We can safely insert them at index `k` because:
     - All people already in the list have height ≥ h
     - We need exactly k people with height ≥ h in front
     - So insert at position k!

3. **Insert Each Person at Index k**
   - Create an empty result list.
   - For each person `[h, k]` in sorted order:
     - Insert them at index `k` in the result list.
   - LinkedList allows efficient insertion at any position.

4. **Convert to Array**
   - Convert the final list to a 2D array and return.

---

## Why (Reasoning)

### Why Sort by Height Descending?

- If we process tallest people first, shorter people won't affect their k-values.
- When inserting a person `[h, k]`, all people already in the queue have height ≥ h.
- This means the k-value directly corresponds to the insertion index!

### Why Secondary Sort by k Ascending?

- When two people have the same height, the one with smaller k should come first.
- This maintains the relative order and ensures correct placement.

### Why Insert at Index k?

- At insertion time, all people in the list are ≥ current height.
- We need exactly k people ≥ current height in front.
- Therefore, inserting at position k gives us exactly k people in front!

### Visual Example

```java
Input: [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]

Step 1: Sort
[[7,0],[7,1],[6,1],[5,0],[5,2],[4,4]]
 (taller first, smaller k first when tied)

Step 2: Insert one by one
[7,0] → insert at index 0 → [[7,0]]
[7,1] → insert at index 1 → [[7,0],[7,1]]
[6,1] → insert at index 1 → [[7,0],[6,1],[7,1]]
[5,0] → insert at index 0 → [[5,0],[7,0],[6,1],[7,1]]
[5,2] → insert at index 2 → [[5,0],[7,0],[5,2],[6,1],[7,1]]
[4,4] → insert at index 4 → [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]

Result: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
```

---

## Implementation Comparison

### Solution 1: Arrays.sort() + LinkedList (Optimal)

```java
public int[][] reconstructQueue(int[][] people) {
    Arrays.sort(people, (a,b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
    
    List<int[]> ordered = new LinkedList<>();
    for (int[] p: people) 
        ordered.add(p[1], p);
    
    return ordered.toArray(new int[people.length][2]);
}
```

**Advantages:**

- Simple and clean code
- LinkedList provides O(1) insertion when you have the position
- Arrays.sort() is in-place and efficient

### Solution 2: PriorityQueue + ArrayList

```java
public int[][] reconstructQueue(int[][] people) {
    PriorityQueue<int[]> pq = new PriorityQueue<>(
        (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : b[0] - a[0]
    );
    
    for(int i = 0; i < people.length; i++) 
        pq.offer(people[i]);
    
    List<int[]> list = new ArrayList<>(people.length);
    while(!pq.isEmpty()) {
        int[] current = pq.poll();
        list.add(current[1], current);
    }
    return list.toArray(people);
}
```

---

## Why is Solution 1 (Arrays.sort) Faster?

### Answer

The **Arrays.sort + LinkedList** implementation is faster for several reasons:

1. **Sorting Algorithm Efficiency**
   - **Arrays.sort()**: Uses Dual-Pivot Quicksort for primitives, Timsort for objects. Highly optimized in-place sorting with O(n log n) average case.
   - **PriorityQueue**: Building the heap requires O(n log n) time with offer operations, but has higher constant factors due to heap maintenance overhead.

2. **Memory Allocation**
   - **Arrays.sort()**: In-place sorting, no extra memory for sorting.
   - **PriorityQueue**: Requires additional heap structure allocation and maintenance.

3. **Insertion Operations**
   - **LinkedList**: Although insertion is O(n) in worst case (must traverse to position), the node linking is straightforward.
   - **ArrayList**: `add(index, element)` requires shifting all subsequent elements, which is O(n) per insertion.
   - However, the sorting approach is the same, so the real difference is in the sorting phase.

4. **Heap Operations Overhead**
   - **PriorityQueue.poll()**: Each poll operation requires O(log n) time to maintain heap property.
   - **Iteration after sort**: Simple linear iteration, O(1) per element.

5. **No Extra Data Structure**
   - **Arrays.sort()**: Works directly on the input array.
   - **PriorityQueue**: Creates an additional data structure that must be populated and then emptied.

6. **CPU Cache Locality**
   - **Array**: Contiguous memory → better cache performance during sorting.
   - **PriorityQueue**: Heap structure with parent-child relationships → more cache misses.

### Performance Comparison

- **Arrays.sort + LinkedList**: ~5-8 ms typical runtime
- **PriorityQueue + ArrayList**: ~8-12 ms typical runtime
- **Speedup**: ~1.5-2x faster with Arrays.sort approach

### Key Takeaway

When you just need to sort once and process linearly, **Arrays.sort()** is more efficient than building and polling from a **PriorityQueue**. Use PriorityQueue only when you need continuous priority-based extraction or when elements arrive dynamically.

---

## Complexity Analysis

### Time Complexity: O(n²)

- **Sorting**: O(n log n)
- **Insertion**: O(n) per insertion × n people = O(n²)
  - LinkedList insertion at specific index requires traversal to that position
- **Overall**: O(n log n) + O(n²) = O(n²)

### Space Complexity: O(n)

- Result list stores n people
- Sorting is typically O(log n) for recursion stack

---

## Example Walkthrough

### Input

```java
people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
```

### Step 1: Sort by height (desc) and k (asc)

```c
[[7,0],[7,1],[6,1],[5,0],[5,2],[4,4]]
```

### Step 2: Insert each at position k

| Iteration | Person | Insert at | Result |
| ----------- | -------- | ----------- | -------- |
| 1 | [7,0] | 0 | [[7,0]] |
| 2 | [7,1] | 1 | [[7,0],[7,1]] |
| 3 | [6,1] | 1 | [[7,0],[6,1],[7,1]] |
| 4 | [5,0] | 0 | [[5,0],[7,0],[6,1],[7,1]] |
| 5 | [5,2] | 2 | [[5,0],[7,0],[5,2],[6,1],[7,1]] |
| 6 | [4,4] | 4 | [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] |

### Output

```c
[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
```

### Verification

- [5,0]: 0 people ≥ 5 in front ✓
- [7,0]: 0 people ≥ 7 in front ✓
- [5,2]: 2 people ≥ 5 in front (5,7) ✓
- [6,1]: 1 person ≥ 6 in front (7) ✓
- [4,4]: 4 people ≥ 4 in front (5,7,5,6) ✓
- [7,1]: 1 person ≥ 7 in front (7) ✓

---

## Key Insights

1. **Greedy + Insertion Strategy**: Process in a specific order (tallest first) to make the problem simpler.

2. **Height as Constraint**: Taller people don't care about shorter people, so process them first.

3. **K-value as Position**: Once taller people are placed, k directly maps to insertion index.

4. **List vs Array**: Using a List for construction is more flexible for insertion operations.

5. **Sorting Twice**: The two-level sort (height desc, k asc) is crucial for correctness.

---

## Alternative Approaches

### 1. Sort Shortest First (Doesn't Work Well)

- If we process shortest people first, their positions will be affected by taller people inserted later.
- Would require complex adjustment logic.

### 2. Brute Force

- Try all permutations and check validity → O(n! × n) time.
- Not feasible for n > 10.

### 3. Dynamic Programming

- No clear optimal substructure or overlapping subproblems.
- Not suitable for this problem.

---

## Related Problems

- [56. Merge Intervals](../56.%20Merge%20Intervals/) - Similar greedy approach with sorting
- [252. Meeting Rooms](../252.%20Meeting%20Rooms/) - Sorting to solve scheduling problems
- [253. Meeting Rooms II](../253.%20Meeting%20Rooms%20II/) - Priority queue vs sorting comparison
- [435. Non-overlapping Intervals](../435.%20Non-overlapping%20Intervals/) - Greedy with sorting strategy
- [452. Minimum Number of Arrows to Burst Balloons](../452.%20Minimum%20Number%20of%20Arrows%20to%20Burst%20Balloons/) - Similar interval-based greedy approach

---

✅ **Optimal Method**: Sort by Height (Descending) + K (Ascending), then Insert at Index K using LinkedList
