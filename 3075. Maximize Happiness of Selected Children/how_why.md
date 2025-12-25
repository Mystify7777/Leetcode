# Recap

You are given an array `happiness` of length `n`, and a positive integer `k`. There are `n` children standing in a queue, where the `i-th` child has happiness value `happiness[i]`. You want to select `k` children from these `n` children in `k` turns.

In each turn, when you select a child, the happiness value of all children that have not been selected yet decreases by `1`. Note that the happiness value cannot become negative and gets floored at `0`.

Return the maximum sum of the happiness values of the selected children you can achieve by selecting `k` of them.

## Intuition

To maximize the sum of happiness values, we should greedily select the children with the highest happiness values first. However, after each selection, all unselected children's happiness decreases by 1. This means earlier selections preserve more happiness value than later selections.

The key insight is: if we select children in decreasing order of happiness, and account for the cumulative decrease (0 for the first child, 1 for the second, 2 for the third, etc.), we get the optimal result.

## Approach

1. **Sort** the `happiness` array in ascending order (or descending - we'll iterate from the end).
2. **Select k children** starting from the highest happiness values (from index `n-1` down to `n-k`).
3. For each selected child at position `i` (from highest to lowest):
   - This is the `j-th` selection (where `j` starts at 0).
   - The effective happiness is `max(happiness[i] - j, 0)` (accounting for the `j` previous selections that decreased this value).
   - Add this to the result.
4. Return the total sum.

**Why this works:** By selecting greedily from highest to lowest and accounting for the cumulative decrease, we ensure that the largest happiness values suffer the least penalty.

## Code (Java)

```java
public class Solution {
    public long maximumHappinessSum(int[] happiness, int k) {
        Arrays.sort(happiness);
        long res = 0;
        int n = happiness.length, j = 0;

        for (int i = n - 1; i >= n - k; --i) {
            happiness[i] = Math.max(happiness[i] - j++, 0);
            res += happiness[i];
        }

        return res;
    }
}
```

## Complexity Analysis

- **Time Complexity:** `O(n log n)` - dominated by the sorting step.
- **Space Complexity:** `O(1)` - only using constant extra space (sorting is done in-place).

## Why the Greedy Approach Works

The greedy strategy is optimal because:

1. **Maximum value preservation:** Each turn costs all remaining children 1 happiness. By selecting the highest happiness child first, we preserve the maximum possible value.

2. **Diminishing returns:** The penalty for each selection is cumulative (`0, 1, 2, 3, ...`). If we had selected a lower happiness child earlier, we would have wasted a low-penalty turn on a low-value child, while forcing a high-value child to suffer a higher penalty later.

3. **No future benefit from waiting:** There's no advantage to delaying the selection of a high-happiness child, because every turn that passes reduces their value by 1. Selecting them earlier always yields equal or better results.

## Example Walkthrough

**Input:** `happiness = [1, 2, 3]`, `k = 2`

1. Sort: `[1, 2, 3]`
2. Select from highest:
   - Turn 0: Select child with happiness `3`, effective value = `3 - 0 = 3`
   - Turn 1: Select child with happiness `2`, effective value = `2 - 1 = 1`
3. Total: `3 + 1 = 4`

**Output:** `4`

## Alternate Approach: Quickselect Optimization

### Why It's Faster

The alternate approach uses **quickselect** instead of full sorting, reducing average time complexity from `O(n log n)` to `O(n)`.

### How It Works

```java
public long maximumHappinessSum(int[] happiness, int k) {
    int n = happiness.length;
    // Step 1: Use quickselect to partition k largest elements
    quickselect(happiness, 0, n - 1, n - k);

    long ans = 0L;
    // Step 2: Optimization for early termination
    if(happiness[n - k] < k - 1) {
        // Only sort the k largest elements
        Arrays.sort(happiness, n - k + 1, n);
        for(int i = 0; i < k; ++i) {
            if(happiness[n - 1 - i] <= i) return ans - i * (i - 1L) / 2L;
            ans += happiness[n - 1 - i];
        }
    }
    // Step 3: Calculate sum of k largest elements
    for(int i = n - k; i < n; ++i) ans += happiness[i];
    // Step 4: Subtract total penalty: 0 + 1 + 2 + ... + (k-1)
    return ans - k * (k - 1L) / 2L;
}
```

### Key Components

1. **Quickselect Partitioning:** 
   - Partitions array so indices `[n-k, n-1]` contain the k largest elements (unsorted).
   - Average time: `O(n)`, worst case: `O(n²)`.
   - No need to sort all `n` elements, just find the k largest.

2. **Early Termination Optimization:**
   - If `happiness[n-k] < k-1`, the smallest of the k largest elements is small enough that some selections will result in 0 happiness (after penalty).
   - In this case, sort only the k elements and check when happiness becomes non-positive.
   - Formula `ans - i * (i - 1) / 2` represents sum minus penalties for selections made so far.

3. **Penalty Calculation:**
   - Instead of subtracting penalty individually `(happiness[i] - j)`, we add all values first, then subtract the total penalty.
   - Total penalty = `0 + 1 + 2 + ... + (k-1) = k*(k-1)/2`.

### Complexity Comparison

| Approach | Time Complexity | Space Complexity |
|----------|----------------|------------------|
| Full Sort | `O(n log n)` | `O(1)` |
| Quickselect | `O(n)` average, `O(n²)` worst | `O(log n)` stack space |

### Why This Is More Complex

Despite being faster asymptotically, this approach is more complex because:

1. **Quickselect implementation** requires careful partitioning logic.
2. **Early termination logic** adds conditional branching.
3. **Mathematical penalty calculation** is less intuitive than element-by-element subtraction.

### When to Use Each Approach

- **Simple Sort:** Easier to understand, debug, and maintain. Good enough for most cases since `O(n log n)` is still very fast.
- **Quickselect:** Better for very large arrays where the `O(n)` average case provides meaningful performance gains, or when `k << n` and you want to avoid sorting elements you won't use.

### Why Quickselect Matters Here

If `n = 10⁶` and `k = 100`:

- **Full sort:** ~20 million comparisons (`n log n`)
- **Quickselect:** ~1 million operations (linear pass + sorting 100 elements)

The difference becomes significant as `n` grows and `k` remains small.
