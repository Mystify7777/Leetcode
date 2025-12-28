# @Mystify7777

## How & Why: LeetCode 347 — Top K Frequent Elements

This note explains multiple solutions for finding the top K most frequent elements in an array, including bucket sort (optimal O(n)), heap-based approaches, and TreeMap solutions.

---

### Problem

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements. You may return the answer in any order.

**Example:**

- Input: `nums = [1,1,1,2,2,3]`, `k = 2`
- Output: `[1,2]`

**Constraints:**

- The answer is guaranteed to be unique.
- Your algorithm's time complexity must be better than O(n log n).

---

## Solution 1: Bucket Sort (Optimal) ⭐

### Key Idea

Use **bucket sort** where the bucket index represents the frequency. Since the maximum frequency is `n` (all elements are the same), we need `n + 1` buckets. After populating buckets, iterate from the highest frequency downward to collect the top K elements.

### Algorithm

1. **Count frequencies**: Use a HashMap to count occurrences of each number.
2. **Bucket by frequency**: Create an array of lists where `bucket[i]` contains all numbers with frequency `i`.
3. **Collect top K**: Iterate from the highest frequency bucket downward, collecting elements until we have `k` elements.

### Implementation (Java)

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count frequencies
        List<Integer>[] bucket = new List[nums.length + 1];
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int num : nums) {
            hm.put(num, hm.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Bucket by frequency
        for (int key : hm.keySet()) {
            int freq = hm.get(key);
            if (bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(key);
        }
        
        // Step 3: Collect top K from highest frequency
        int[] ans = new int[k];
        int pos = 0;
        for (int i = bucket.length - 1; i >= 0; i--) {
            if (bucket[i] != null) {
                for (int j = 0; j < bucket[i].size() && pos < k; j++) {
                    ans[pos] = bucket[i].get(j);
                    pos++;
                }
            }
        }
        return ans;
    }
}
```

### Complexity

- **Time**: O(n)
  - O(n) to count frequencies
  - O(n) to populate buckets
  - O(n) to collect top K (at most visit all elements)
- **Space**: O(n) for HashMap and bucket array

### Why This Works

- **Frequency Range**: Since there are `n` elements, the maximum frequency is `n`. We create `n + 1` buckets (index 0 to n).
- **Bucket Sort Advantage**: By using frequency as the bucket index, we automatically sort by frequency without comparison-based sorting.
- **Efficient Extraction**: Starting from the highest frequency bucket guarantees we get the most frequent elements first.

---

## Solution 2: Max Heap / Priority Queue

### Key Idea Alternate

Use a **max heap** ordered by frequency. Add all frequency entries to the heap, then poll the top K elements.

### Implementation (Java) Alternate

```java
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        
        // Create max heap ordered by frequency (descending)
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
            new PriorityQueue<>((a, b) -> (b.getValue() - a.getValue()));
        
        // Add all entries to heap
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            maxHeap.add(entry);
        }
        
        // Extract top K
        List<Integer> res = new ArrayList<>();
        while (res.size() < k) {
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            res.add(entry.getKey());
        }
        return res;
    }
}
```

### Complexity Alternate

- **Time**: O(n log m) where m is the number of unique elements
  - O(n) to count frequencies
  - O(m log m) to build heap
  - O(k log m) to extract K elements
- **Space**: O(m) for HashMap and heap

### Trade-offs

- **Pros**: Simple and intuitive; works well for streaming data
- **Cons**: Slower than bucket sort due to heap operations

---

## Solution 3: TreeMap (Sorted Map)

### Key Idea Alternate+

Use a **TreeMap** with frequency as the key, automatically maintaining sorted order. Poll the last (highest frequency) entries until we have K elements.

### Implementation (Java) Alternate+

```java
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        
        // TreeMap with frequency as key (automatically sorted)
        TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
        for (int num : map.keySet()) {
            int freq = map.get(num);
            if (!freqMap.containsKey(freq)) {
                freqMap.put(freq, new LinkedList<>());
            }
            freqMap.get(freq).add(num);
        }
        
        // Extract from highest frequency
        List<Integer> res = new ArrayList<>();
        while (res.size() < k) {
            Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
            res.addAll(entry.getValue());
        }
        return res;
    }
}
```

### Complexity Alternate+

- **Time**: O(n log m) where m is the number of unique elements
  - O(n) to count frequencies
  - O(m log m) to populate TreeMap
  - O(k) to extract K elements
- **Space**: O(m) for HashMap and TreeMap

### Trade-offs Alternate+

- **Pros**: Clean code; automatically maintains sorted order
- **Cons**: TreeMap overhead makes it slower than bucket sort

---

## Solution 4: Bucket Sort Variant (List Return)

### Key Idea Alternate++

Same bucket sort approach but returns a List and uses LinkedList for buckets. Stops early when K elements are collected.

### Implementation (Java) Alternate++

```java
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        
        // Bucket array (index = frequency)
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int n : map.keySet()) {
            int freq = map.get(n);
            if (bucket[freq] == null)
                bucket[freq] = new LinkedList<>();
            bucket[freq].add(n);
        }
        
        // Collect from highest frequency
        List<Integer> res = new LinkedList<>();
        for (int i = bucket.length - 1; i > 0 && k > 0; --i) {
            if (bucket[i] != null) {
                List<Integer> list = bucket[i];
                res.addAll(list);
                k -= list.size();
            }
        }
        
        return res;
    }
}
```

### Complexity Alternate++

- **Time**: O(n)
- **Space**: O(n)

---

## Comparison Summary

| Approach | Time | Space | Notes |
| ---------- | ------ | ------- | ------- |
| **Bucket Sort** | O(n) | O(n) | ⭐ Optimal for this problem |
| **Max Heap** | O(n log m) | O(m) | Good for streaming; intuitive |
| **TreeMap** | O(n log m) | O(m) | Clean code; automatic sorting |
| **Min Heap** | O(n log k) | O(k) | Best when k << n (not shown) |

---

## When to Use Each

1. **Bucket Sort**: Best for this problem with fixed constraints. O(n) time complexity.
2. **Max Heap**: When you need top K from a stream or when elements arrive dynamically.
3. **TreeMap**: When you want clean, readable code and don't need optimal performance.
4. **Min Heap** (size k): When `k` is very small compared to `n` and space is constrained.

---

## Example Walkthrough (Bucket Sort)

Input: `nums = [1,1,1,2,2,3]`, `k = 2`

- **Step 1: Count Frequencies**

    ```java
    HashMap: {1: 3, 2: 2, 3: 1}
    ```

- **Step 2: Bucket by Frequency**

    ```java
    bucket[0] = null
    bucket[1] = [3]       // 3 appears 1 time
    bucket[2] = [2]       // 2 appears 2 times
    bucket[3] = [1]       // 1 appears 3 times
    bucket[4..6] = null
    ```

- **Step 3: Collect Top K**

  - Start at `i = 6` (highest): null, skip
  - `i = 5`: null, skip
  - `i = 4`: null, skip
  - `i = 3`: bucket[3] = [1] → Add 1 to result, pos = 1
  - `i = 2`: bucket[2] = [2] → Add 2 to result, pos = 2
  - pos == k, done!

    **Result**: `[1, 2]`
