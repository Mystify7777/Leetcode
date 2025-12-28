# @Mystify7777

## How & Why: LeetCode 703 — Kth Largest Element in a Stream

This note explains how to efficiently maintain the kth largest element in a stream using a min-heap of size k. Two implementations are provided: using Java's PriorityQueue and a manual heap implementation.

---

### Problem

Design a class `KthLargest` that:

- Initializes with an integer `k` and an array of integers `nums`
- Has a method `add(val)` that appends `val` to the stream and returns the kth largest element

**Example:**

```java
KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
kthLargest.add(3);   // returns 4
kthLargest.add(5);   // returns 5
kthLargest.add(10);  // returns 5
kthLargest.add(9);   // returns 8
kthLargest.add(4);   // returns 8
```

---

## Solution 1: Min-Heap with PriorityQueue ⭐

### Key Idea

Maintain a **min-heap of size k** containing the k largest elements seen so far. The root of this min-heap is always the kth largest element.

**Why min-heap?** A min-heap keeps the smallest element at the top. By limiting size to k, the top element is the smallest among the k largest values, which is exactly the kth largest overall.

### Algorithm

**Constructor:**

1. Initialize min-heap with capacity k
2. For each number in `nums`:
   - If heap size < k, add the number
   - Else if number > heap.peek(), add it and remove the smallest (poll)

**Add method:**

1. If heap size < k, add the value
2. Else if value > heap.peek():
   - Add the value
   - Remove the smallest element (poll)
3. Return heap.peek() (the kth largest)

### Implementation (Java)

```java
class KthLargest {
    private int k;
    private PriorityQueue<Integer> minHeap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>(k);
        
        // Initialize heap with nums array
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.offer(num);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
        }
    }

    public int add(int val) {
        if (minHeap.size() < k) {
            minHeap.offer(val);
        } else if (val > minHeap.peek()) {
            minHeap.offer(val);
            minHeap.poll();
        }
        return minHeap.peek();
    }
}
```

### Complexity

- **Constructor Time**: O(n log k) where n = nums.length
  - Each insertion/removal is O(log k)
- **Add Time**: O(log k)
  - At most one insertion and one removal
- **Space**: O(k) for the heap

### Why This Works

1. **Heap Invariant**: The heap always contains the k largest elements seen so far.
2. **Min Property**: The minimum of these k largest elements is at the root.
3. **Kth Largest**: This minimum is exactly the kth largest element in the stream.
4. **Updates**: When a new element comes:
   - If heap not full, add it (we need k elements)
   - If new element > smallest in heap, it replaces the smallest
   - Otherwise, ignore it (it's not in top k)

---

## Solution 2: Manual Min-Heap Implementation

### Key Idea 2

Same algorithm but with a custom array-based min-heap implementation to avoid PriorityQueue overhead and show the underlying mechanics.

### Implementation (Java) 2

```java
class KthLargest {
    int[] heap;
    int size;

    public KthLargest(int k, int[] nums) {
        this.size = 0;
        this.heap = new int[k];
        for (int num : nums)
            add(num); 
    }
    
    public int add(int val) {
        if (!isFull()) {
            offer(val);
        } else {
            int peek = peek();
            if (peek < val) {
                poll();
                offer(val);
            }
        }
        return peek();
    }

    int peek() {
        return heap[0];
    }

    boolean isFull() {
        return size == heap.length;
    }
    
    int poll() {
        int result = heap[0];
        int half = --size / 2;
        int k = 0;
        int x = heap[size]; // element to sift down

        // Sift down
        while (k < half) {
            int child = 2 * k + 1;
            int c = heap[child];
            int right = child + 1;
            
            // Pick smaller child
            if (right < size && c > heap[right]) {
                c = heap[child = right];
            }
            
            if (x <= c) break;
            
            heap[k] = c;
            k = child;
        }
        heap[k] = x;
        return result;
    }

    void offer(int x) {
        int k = size;
        
        // Sift up
        while (k > 0) {
            int parent = (k - 1) / 2;
            int e = heap[parent];
            
            if (x >= e) break;
            
            heap[k] = e;
            k = parent;
        }
        heap[k] = x;
        size++;
    } 
}
```

### Complexity 2

Same as Solution 1: O(log k) per operation, O(k) space.

### Heap Operations Explained

**Offer (Insert):**

- Add element at the end
- Sift up: compare with parent, swap if smaller
- Continue until heap property restored

**Poll (Remove Min):**

- Save root (minimum)
- Move last element to root
- Sift down: compare with children, swap with smaller child
- Continue until heap property restored

**Heap Property:** For min-heap, `heap[parent] <= heap[child]`

---

## Comparison: PriorityQueue vs Manual

| Aspect | PriorityQueue | Manual Heap |
| -------- | -------------- | ------------- |
| **Code** | Concise | Verbose |
| **Performance** | Optimized, tested | Same O() but more control |
| **Readability** | Higher | Lower |
| **Use Case** | Production code | Learning, optimization |

**Recommendation:** Use PriorityQueue (Solution 1) for cleaner, maintainable code.

---

## Example Walkthrough

Input: `k = 3`, `nums = [4, 5, 8, 2]`

**Constructor:**

1. Add 4: heap = [4], size = 1
2. Add 5: heap = [4, 5], size = 2
3. Add 8: heap = [4, 5, 8], size = 3 (full)
4. Add 2: 2 < 4 (peek), ignore it
   - Final heap = [4, 5, 8] (min-heap structure: root is 4)

**add(3):**

- 3 < 4 (peek), ignore
- Return 4

**add(5):**

- 5 > 4 (peek), add 5 and remove 4
- heap = [5, 5, 8]
- Return 5

**add(10):**

- 10 > 5 (peek), add 10 and remove 5
- heap = [5, 8, 10]
- Return 5

**add(9):**

- 9 > 5 (peek), add 9 and remove 5
- heap = [8, 9, 10]
- Return 8

**add(4):**

- 4 < 8 (peek), ignore
- Return 8

---

## Common Pitfalls

1. **Using Max-Heap**: Wrong! You'd need to maintain all elements and extract kth, which is inefficient.

2. **Not Checking Heap Size**: Always check if size < k before comparing with peek():

   ```java
   // Wrong: minHeap.peek() when empty
   // Right: if (minHeap.size() < k) minHeap.offer(val);
   ```

3. **Forgetting to Poll**: When adding an element that makes size > k, must remove the smallest:

   ```java
   minHeap.offer(val);
   if (minHeap.size() > k) minHeap.poll();  // Important!
   ```

4. **Index Calculation in Manual Heap**:
   - Parent of i: `(i - 1) / 2`
   - Left child: `2 * i + 1`
   - Right child: `2 * i + 2`

---

## Why Not Other Approaches?

1. **Sorting on every add**: O(n log n) per operation — too slow
2. **Max-heap of all elements**: O(n) space and O(k log n) to extract kth — wasteful
3. **Array with linear search**: O(n) per add — acceptable only for tiny streams

Min-heap of size k is optimal for this streaming scenario: O(log k) time with O(k) space.
