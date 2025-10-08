# How_Why.md — Find K-th Smallest Pair Distance (LeetCode #719)

## 🧩 Problem Statement

Given an integer array `nums` and an integer `k`,  
return the **k-th smallest distance** among all the pairs `(nums[i], nums[j])` where `i < j`.

The **distance of a pair** `(a, b)` is defined as `|a - b|`.

---

## ⚙️ Brute-Force Method

### Idea

1. Generate **all possible pairs** `(i, j)` where `i < j`.
2. Compute their **absolute differences**.
3. Sort all these distances.
4. Return the **k-th smallest** one.

### Code (Conceptual)

```java
List<Integer> distances = new ArrayList<>();
for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        distances.add(Math.abs(nums[i] - nums[j]));
    }
}
Collections.sort(distances);
return distances.get(k - 1);
```

### ⚠️ Limitations

* **Time Complexity:** `O(n² log n)` — too slow for large inputs.
* **Space Complexity:** `O(n²)` — not scalable.
* This approach will **TLE (Time Limit Exceeded)** on big arrays (e.g. n = 10⁵).

---

## 💡 Your Approach (Binary Search + Sliding Window)

### Core Idea

We don’t need to build all pairs explicitly.

Instead, we:

1. **Sort** the array.
2. **Binary Search** the possible *distance values* (`0 → max(nums) - min(nums)`).
3. For each middle distance `mid`, **count** how many pairs have distance `≤ mid` using a **two-pointer sliding window**.
4. Narrow the search based on the pair count:

   * If there are **fewer than k pairs**, increase the distance (`min = mid + 1`).
   * Otherwise, shrink the range (`max = mid`).

---

### Step-by-Step Logic

1. **Sort** the input array.
2. **Binary Search Range**: `minDistance = 0`, `maxDistance = nums[n-1] - nums[0]`.
3. **Mid Calculation:**
   `mid = (minDistance + maxDistance) / 2`
4. **Count pairs** with distance `≤ mid` using two pointers:

   * Move `right` pointer from left to right.
   * For each `right`, advance `left` pointer while `nums[right] - nums[left] > mid`.
   * Add `(right - left)` to the count.
5. **Adjust binary search range** based on the count.

---

### Code (Your Version)

```java
class Solution {
    public int smallestDistancePair(int[] numbers, int k) {
        Arrays.sort(numbers);
        int minDistance = 0;
        int maxDistance = numbers[numbers.length - 1] - numbers[0];
        
        while (minDistance < maxDistance) {
            int midDistance = minDistance + (maxDistance - minDistance) / 2;
            int pairsCount = countPairsWithinDistance(numbers, midDistance);
            
            if (pairsCount < k) {
                minDistance = midDistance + 1;
            } else {
                maxDistance = midDistance;
            }
        }
        
        return minDistance;
    }

    private int countPairsWithinDistance(int[] numbers, int targetDistance) {
        int count = 0;
        int left = 0;
        
        for (int right = 1; right < numbers.length; right++) {
            while (numbers[right] - numbers[left] > targetDistance) {
                left++;
            }
            count += right - left;
        }
        
        return count;
    }
}
```

---

## 🧠 Example Walkthrough

### Input

```c
nums = [1, 3, 1]
k = 1
```

### Step 1 — Sort

```c
nums = [1, 1, 3]
```

### Step 2 — Binary Search Range

```c
minDistance = 0
maxDistance = 2
```

#### Iteration 1

* `mid = 1`
* Count pairs with distance ≤ 1 → pairs = 2 (`(1,1)` and `(1,3)`)
* Since `pairs >= k`, shrink: `maxDistance = 1`

#### Iteration 2

* `mid = 0`
* Count pairs with distance ≤ 0 → pairs = 1 (`(1,1)`)
* Still `pairs >= k`, shrink again: `maxDistance = 0`

✅ Result: `minDistance = 0`

---

## 🚀 Alternate Optimized Version (Cleaner Binary Search)

```java
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int l = 0, r = nums[nums.length - 1], result = 0;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            int pairs = countPairs(nums, mid);
            if (pairs < k) {
                l = mid + 1;
            } else {
                result = mid;
                r = mid - 1;
            }
        }
        return result;
    }

    private int countPairs(int[] nums, int distance) {
        int i = 0, count = 0;
        for (int j = 0; j < nums.length; j++) {
            while (nums[j] - nums[i] > distance) i++;
            count += j - i;
        }
        return count;
    }
}
```

---

## 🧮 Complexity Analysis

| Aspect | Complexity               | Explanation                                                 |
| :----- | :----------------------- | :---------------------------------------------------------- |
| Time   | **O(n log n + n log W)** | Sorting + binary search on distance range (`W = max - min`) |
| Space  | **O(1)**                 | In-place two-pointer count                                  |

---

## ✅ Summary

| Method                                         | Approach                  | Time                 | Space | Remarks                              |
| :--------------------------------------------- | :------------------------ | :------------------- | :---- | :----------------------------------- |
| Brute-force                                    | Generate all pairs & sort | O(n² log n)          | O(n²) | Too slow                             |
| Binary Search + Sliding Window (your approach) | Optimal                   | O(n log n + n log W) | O(1)  | Elegant & efficient                  |
| Heap or Counting sort variant                  | Alternative               | O(n log n + W)       | O(W)  | Not practical for large value ranges |

🔹 **Final Verdict:**
Your approach is the most efficient and accepted method — combining **binary search** on the answer space with a **sliding window** to count valid pairs efficiently.

---
