# How & Why: LeetCode 278 - First Bad Version

-----

This solution uses **binary search** to find the first bad version in logarithmic time. It works by repeatedly dividing the search space in half until the boundary between "good" and "bad" versions is found.

-----

## Problem Restatement

You're a product manager and one of your software versions is bad, causing all subsequent versions to also be bad. You are given:

  - An integer `n` representing the total number of versions `[1, 2, ..., n]`.
  - An API function `isBadVersion(int version)` which returns `true` if a version is bad and `false` otherwise.

Your goal is to find the index of the **very first bad version** with the minimum number of calls to the API.

-----

## How to Solve

The problem has a hidden structure that makes it perfect for **binary search**. If you imagine the versions, they form a sequence like this:

`[Good, Good, Good, ..., Bad, Bad, Bad]`

We need to find the first "Bad".

1.  **Set Boundaries**: Create two pointers, `low` and `high`, to define the range of versions you're currently searching within (e.g., from `1` to `n`).
2.  **Find the Middle**: Calculate the middle version in your current range.
3.  **Check the Middle**: Call `isBadVersion()` on the middle version.
      - If it's **bad**, you know the first bad version must be this middle one or somewhere to its **left**. So, you can discard the entire right half.
      - If it's **good**, you know the first bad version must be somewhere to its **right**. You can discard the entire left half.
4.  **Repeat**: Keep shrinking your search range this way until the pointers cross. The `low` pointer will naturally land on the first bad version.

### Implementation

```java
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int low = 1;
        int high = n;
        
        // Loop until the search space is exhausted (low > high)
        while (low <= high) {
            // Calculate mid to avoid potential integer overflow
            int mid = low + (high - low) / 2;
            
            if (isBadVersion(mid)) {
                // If mid is bad, the answer could be mid or something to its left.
                // We shrink the boundary to search the left half.
                high = mid - 1;
            } else {
                // If mid is good, the answer must be to its right.
                low = mid + 1;
            }
        }
        
        // When the loop ends, 'low' is the first bad version.
        return low;
    }
}
```

-----

## Why This Works

1.  **Efficiency 🚀**: Binary search is incredibly fast. Instead of checking every single version one-by-one (which would be slow), it eliminates **half** of the remaining possibilities with every single API call. This leads to a logarithmic time complexity.
2.  **Monotonic Property**: The solution hinges on the fact that once a version is bad, all future versions are also bad. This creates a clear, sorted boundary between `false` and `true` values, which is the exact scenario where binary search excels.
3.  **Correct Termination**: When the `low` and `high` pointers cross, the loop stops. At this point, `low` has been pushed past all the "good" versions and is resting on the first "bad" one, guaranteeing the correct answer.

-----

## Complexity Analysis

  - **Time Complexity**: $O(\\log n)$. With each API call, we cut the number of versions to check in half.
  - **Space Complexity**: $O(1)$. We only use a few variables (`low`, `high`, `mid`) to keep track of our position, regardless of how large `n` is.

-----

## Example Walkthrough

**Input:**
`n = 10`, and the first bad version is `7`.

**Process:**

1.  **Initial**: `low = 1`, `high = 10`.
      - `mid = 1 + (10-1)/2 = 5`.
      - `isBadVersion(5)` returns `false`. The first bad version must be after 5.
      - New range: `low` becomes `6`, `high` is `10`.
2.  **Next**: `low = 6`, `high = 10`.
      - `mid = 6 + (10-6)/2 = 8`.
      - `isBadVersion(8)` returns `true`. The first bad version could be 8 or before it.
      - New range: `low` is `6`, `high` becomes `7`.
3.  **Next**: `low = 6`, `high = 7`.
      - `mid = 6 + (7-6)/2 = 6`.
      - `isBadVersion(6)` returns `false`. The first bad version must be after 6.
      - New range: `low` becomes `7`, `high` is `7`.
4.  **Next**: `low = 7`, `high = 7`.
      - `mid = 7`.
      - `isBadVersion(7)` returns `true`.
      - New range: `low` is `7`, `high` becomes `6`.
5.  **End**: The loop condition `low <= high` (`7 <= 6`) is now false. The loop terminates.

**Output:**
Return `low`, which is **7**.

-----

## Alternate Approaches

1.  **Linear Scan**:
      - **How**: Simply call `isBadVersion(1)`, then `isBadVersion(2)`, and so on, until you get the first `true`.
      - **Complexity**: $O(n)$ time. This is too slow for large `n` and would fail the time limits of the problem.

### Optimal Choice

**Binary search is the only truly optimal solution**. Its $O(\\log n)$ performance is essential for solving the problem efficiently, as it drastically reduces the number of required API calls compared to a linear scan.

-----

## Key Insight

This problem is a classic binary search question disguised as a software versioning problem. The key is to recognize the underlying "sorted" structure of the data (`[false, false, ..., true, true]`) which allows you to discard half the data at every step.
