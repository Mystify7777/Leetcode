# 2141. Maximum Running Time of N Computers — how/why

## Recap

You have `n` computers and an array of `batteries` where `batteries[i]` is the power in the i-th battery. You may use any number of batteries to power the computers simultaneously. At any moment, you can insert a battery into a computer and replace the current one. Return the maximum number of minutes you can run all `n` computers simultaneously.

## Intuition

The key insight is that batteries can be swapped freely, so we can pool their energy. The challenge: some batteries might be so large that dedicating them to a single computer is optimal. The strategy is to identify "dedicated" batteries (those large enough to single-handedly power one computer for the entire duration) and separate them from the "pooled" batteries that we'll distribute among the remaining computers.

## Approach

### Greedy Approach (O(n log n))

1. **Sort batteries** in ascending order.
2. **Compute total sum** of all battery powers.
3. **Identify dedicated batteries**:
   - Start with largest batteries.
   - For each large battery, check if `battery[i] > sum / (n - k)` where `k` is the number of batteries already dedicated.
   - If yes, this battery can power one computer for longer than the average, so dedicate it to one computer.
   - Subtract its value from `sum` and increment `k`.
4. **Calculate max time**: `sum / (n - k)` gives the maximum time the remaining `(n - k)` computers can run using the pooled batteries.

**Visualization**: Imagine `n = 3` computers and batteries `[1, 1, 1, 1, 10]`. Total sum = 14. Average = 14/3 ≈ 4.67. The battery with 10 can power one computer for 10 minutes (> 4.67), so dedicate it. Now we have 2 computers sharing sum = 4, giving 4/2 = 2 minutes each. Final answer: 2 minutes.

### Binary Search Approach (Alternative)

Binary search on the answer (running time `t`). For each `t`, check if we can sustain all `n` computers for `t` minutes:
- Batteries with power ≥ `t` can fully power one computer.
- Batteries with power < `t` contribute to a pool.
- If `pool ≥ (remaining computers) * t`, then `t` is feasible.

## Code (Java)

```java
class Solution {
    public long maxRunTime(int n, int[] A) {
        Arrays.sort(A);
        long sum = 0;
        for (int a : A) {
            sum += a;
        }
        
        int k = 0, na = A.length;
        while (A[na - 1 - k] > sum / (n - k)) {
            sum -= A[na - 1 - k++];
        }
        
        return sum / (n - k);
    }
}
```

## Correctness

- **Greedy choice**: If a battery can power a computer for longer than the average time all computers can run, it's optimal to dedicate it. Otherwise, pooling provides more flexibility.

- **Average calculation**: After removing dedicated batteries, the remaining batteries are pooled. Since we can swap batteries freely, the limiting factor is the total pooled energy divided by the number of computers that need it.

- **Sorting ensures optimal selection**: Processing largest batteries first ensures we correctly identify which should be dedicated.

- **Termination**: The while loop terminates when the largest remaining battery is ≤ average pooled time, meaning all remaining batteries should be pooled.

## Complexity

- **Time**: `O(n log n)` for sorting, plus `O(n)` for the while loop = `O(n log n)`.
- **Space**: `O(1)` auxiliary space (excluding sorting space).

## Edge Cases

- Single computer (`n = 1`): return sum of all batteries (use everything).
- Single battery: return `battery[0] / n` (split equally if possible, or 0 if insufficient).
- All batteries equal: all pooled, return `sum / n`.
- One huge battery: dedicate to one computer, distribute rest.
- More batteries than computers: extra batteries contribute to the pool.
- Very large values: use `long` to prevent overflow in sum calculations.

## Takeaways

- **Pooling resources with swapping**: When items can be freely exchanged, think about pooling vs. dedicated allocation.
- **Greedy vs. binary search**: Greedy is more efficient here but requires careful reasoning; binary search is more intuitive (guess answer, verify feasibility).
- **Average as a threshold**: Comparing individual values to the average determines whether to dedicate or pool.
- **Sorting reveals structure**: Sorting by size helps identify outliers that should be handled specially.
- This pattern applies to resource allocation problems where items can be dynamically reassigned.

## Alternative (Binary Search)

```java
class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;
        int max = 0;
        
        for (int battery : batteries) {
            sum += battery;
            max = Math.max(max, battery);
        }
        
        long left = 0, right = sum / n;
        long result = 0;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (canRunForTime(batteries, n, mid)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    private boolean canRunForTime(int[] batteries, int n, long time) {
        long pool = 0;
        int dedicated = 0;
        
        for (int battery : batteries) {
            if (battery >= time) {
                dedicated++;
            } else {
                pool += battery;
            }
            
            // Early success: enough dedicated + pool can cover remaining
            if (dedicated + pool / time >= n) {
                return true;
            }
        }
        
        return dedicated >= n || (dedicated + pool / time >= n);
    }
}
```

**Trade-off**: Binary search is O(n log(sum/n)) with clearer logic but potentially slower due to multiple feasibility checks. Greedy is O(n log n) with one pass after sorting. Both are acceptable; choose based on preference for clarity vs. slight performance edge.
