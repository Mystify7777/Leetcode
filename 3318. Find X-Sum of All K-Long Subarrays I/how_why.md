# 3318. Find X-Sum of All K-Long Subarrays I — how and why

## Problem recap

Given an array `nums`, an integer `k` (subarray length), and an integer `x`, you need to compute the **x-sum** for every contiguous subarray of length `k`.

The **x-sum** of a subarray is calculated as:

1. Count the frequency of each distinct element in the subarray
2. Select the top `x` most frequent elements (ties broken by choosing larger values)
3. Sum up `element × frequency` for each selected element

Return an array containing the x-sum for each k-length subarray.

## Core intuition

This is a sliding window problem combined with frequency tracking and top-k selection:

- Use a sliding window to maintain the current k-length subarray
- Track frequencies of elements in the window
- For each window position, find the top x elements by frequency (with value as tiebreaker)
- Calculate the sum of (element × frequency) for these top x elements

The challenge is efficiently computing the x-sum as the window slides.

## Approach 1 — sliding window with sorted frequency list (your solution)

Maintain a frequency map for the current window. As the window slides:

1. Add the new element entering the window
2. Remove the old element leaving the window
3. Convert the frequency map to a list and sort by frequency (descending), then by value (descending)
4. Take the top x elements and compute their contribution

### Implementation (matches `Solution.java`)

```java
import java.util.*;

class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] ans = new int[Math.max(0, n - k + 1)];
        Map<Integer, Integer> freq = new HashMap<>();

        // Initialize first window
        for (int i = 0; i < k; i++) {
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
        }

        ans[0] = computeXSum(freq, x);

        // Slide the window
        for (int i = k; i < n; i++) {
            int add = nums[i];
            int rem = nums[i - k];

            // Add new element
            freq.put(add, freq.getOrDefault(add, 0) + 1);
            
            // Remove old element
            int fr = freq.get(rem) - 1;
            if (fr == 0) freq.remove(rem);
            else freq.put(rem, fr);

            ans[i - k + 1] = computeXSum(freq, x);
        }

        return ans;
    }

    private int computeXSum(Map<Integer, Integer> freq, int x) {
        // Convert to list of [value, frequency] pairs
        List<int[]> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            items.add(new int[]{e.getKey(), e.getValue()});
        }
        
        // Sort by frequency (desc), then by value (desc)
        items.sort((a, b) -> {
            if (a[1] != b[1]) return b[1] - a[1];
            return b[0] - a[0];
        });
        
        // Take top x and compute sum
        long sum = 0;
        int take = Math.min(x, items.size());
        for (int i = 0; i < take; i++) {
            sum += 1L * items.get(i)[0] * items.get(i)[1];
        }
        return (int) sum;
    }
}
```

## Approach 2 — array-based frequency with greedy selection

The alternate approach uses a fixed-size frequency array (since values are bounded 1-50) and rebuilds the window from scratch for each position:

```java
class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int[] result = new int[nums.length - k + 1];

        for (int i = 0; i < result.length; i++) {
            int left = i, right = i + k - 1;
            result[i] = findXSumofSubArray(nums, left, right, x);
        }

        return result;
    }

    private int findXSumofSubArray(int[] nums, int left, int right, int x) {
        int sum = 0, distinctCount = 0;
        int[] freq = new int[51]; // values are 1-50

        // Build frequency table for this window
        for (int i = left; i <= right; i++) {
            sum += nums[i];
            if (freq[nums[i]] == 0) {
                distinctCount++;
            }
            freq[nums[i]]++;
        }

        // If distinct elements < x, all elements contribute
        if (distinctCount < x) {
            return sum;
        }

        // Greedily pick top x elements by frequency, then by value
        sum = 0;
        for (int pick = 0; pick < x; pick++) {
            int bestFreq = -1;
            int bestVal = -1;

            // Scan from highest value to lowest
            for (int val = 50; val >= 1; val--) {
                if (freq[val] > bestFreq) {
                    bestFreq = freq[val];
                    bestVal = val;
                }
            }

            if (bestVal != -1) {
                sum += bestVal * bestFreq;
                freq[bestVal] = 0; // Mark as used
            }
        }
        
        return sum;
    }
}
```

## Comparison of approaches

| Aspect | Approach 1 (Sliding Window + Sort) | Approach 2 (Rebuild + Greedy) |
|--------|-------------------------------------|-------------------------------|
| **Window maintenance** | Efficient sliding (add/remove) | Rebuild from scratch each time |
| **Frequency storage** | HashMap (dynamic) | Fixed array[51] |
| **Top-x selection** | Sort all entries | Greedy x iterations |
| **Time per window** | O(d log d) where d = distinct elements | O(k + 50·x) |
| **Total time** | O(n · d log d) | O(n · (k + x)) |
| **Space** | O(d) for HashMap | O(51) = O(1) |
| **Best when** | k is large, x is small | k is small, values bounded |

For this problem (values 1-50, typically small k), **Approach 2 is often faster** in practice despite higher asymptotic complexity, because:

- Constant-time array access vs HashMap overhead
- Greedy selection over only 50 values is very fast
- No sorting overhead

However, **Approach 1 is more scalable** if values can be arbitrarily large or k is very large.

## Why this works

**Sliding window correctness:**

Each window is independent. By maintaining accurate frequencies and selecting the top x elements by the given criteria, we compute the exact x-sum for that window.

**Sorting vs Greedy:**

- Sorting guarantees we get elements in the correct priority order (frequency first, value second)
- Greedy selection with bounded values achieves the same result by scanning all possible values in priority order

Both correctly implement the tie-breaking rule: among elements with the same frequency, pick those with larger values.

## Complexity

### Approach 1 (your solution)

- **Time:** O((n - k + 1) · d log d) where d is the number of distinct elements per window
  - In worst case, d ≤ k, so O(n · k log k)
- **Space:** O(d) for the frequency map

### Approach 2 (alternate)

- **Time:** O((n - k + 1) · (k + 50 · x)) = O(n · (k + x))
  - Build frequency: O(k)
  - Greedy selection: O(50 · x) = O(x) since 50 is constant
- **Space:** O(51) = O(1) for the frequency array (plus O(n - k + 1) for result)

## Example walkthrough

Suppose `nums = [1, 1, 2, 2, 3, 4, 2, 3]`, `k = 6`, `x = 2`.

**Window [1, 1, 2, 2, 3, 4]:**

Frequencies: {1: 2, 2: 2, 3: 1, 4: 1}

Sorted by (freq desc, value desc): [(1, 2), (2, 2), (4, 1), (3, 1)]

Top 2: (1, 2) and (2, 2)

X-sum = 1×2 + 2×2 = 2 + 4 = 6

**Window [1, 2, 2, 3, 4, 2]:**

Frequencies: {1: 1, 2: 3, 3: 1, 4: 1}

Sorted: [(2, 3), (4, 1), (3, 1), (1, 1)]

Top 2: (2, 3) and (4, 1)

X-sum = 2×3 + 4×1 = 6 + 4 = 10

**Window [2, 2, 3, 4, 2, 3]:**

Frequencies: {2: 3, 3: 2, 4: 1}

Sorted: [(2, 3), (3, 2), (4, 1)]

Top 2: (2, 3) and (3, 2)

X-sum = 2×3 + 3×2 = 6 + 6 = 12

Result: [6, 10, 12]

## Edge cases to consider

- `x >= distinct elements`: all elements contribute (just return sum of window)
- `x = 1`: only the most frequent (or largest if tie) element contributes
- `k = 1`: each single element is a window
- All elements same: top x elements are all the same value
- All elements distinct: select the x largest values

## Takeaways

- Sliding window efficiently maintains the current subarray's frequency distribution
- Two valid strategies: sort-all vs greedy-with-bounded-range
- For bounded value ranges (like 1-50), array-based frequency + greedy can outperform HashMap + sort
- For unbounded or large value ranges, HashMap + sort is more flexible and scalable
- The tie-breaking rule (frequency first, then value) must be carefully implemented in the comparator
