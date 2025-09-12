# How & Why: LeetCode 349 - Intersection of Two Arrays

-----

## Problem Restatement

You are given two integer arrays, `nums1` and `nums2`. Your task is to find their **intersection**. This means you need to return an array that contains all the elements that are common to both `nums1` and `nums2`.

The key constraints are:

1.  Each element in the result must be **unique**.
2.  The result can be in **any order**.

-----

## How to Solve

The most effective way to solve this is by using **Hash Sets**. A `HashSet` is a data structure that stores only unique elements and provides very fast lookups.

1.  **Create a Set from `nums1`**: Iterate through the first array, `nums1`, and add all its elements to a `HashSet`. This automatically handles duplicates and prepares for fast searching.
2.  **Find Common Elements**: Iterate through the second array, `nums2`. For each number in `nums2`, check if it `contains()` in the set created from `nums1`.
3.  **Store the Intersection**: If an element from `nums2` is found in the first set, add it to a second `HashSet` that will store the final intersection. Using a set here ensures the final result is also unique.
4.  **Convert to Array**: Finally, convert the intersection set into an array to match the required return type.

### Implementation

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // 1. Create a set for the first array for unique elements and fast lookups
        HashSet<Integer> set1 = new HashSet<>();
        for (int num : nums1) {
            set1.add(num);
        }

        // 2. Create a set to store the intersection
        HashSet<Integer> intersectionSet = new HashSet<>();
        
        // 3. Iterate through the second array and check for common elements
        for (int num : nums2) {
            if (set1.contains(num)) {
                intersectionSet.add(num);
            }
        }

        // 4. Convert the intersection set to an array
        int[] result = new int[intersectionSet.size()];
        int i = 0;
        for (int num : intersectionSet) {
            result[i++] = num;
        }
        return result;
    }
}
```

-----

## Why This Works

1.  **Efficiency ⚡**: The power of a `HashSet` is its `contains()` method, which runs in average constant time, or $O(1)$. By converting the first array to a set, we avoid a slow, linear search for each element of the second array. This prevents a sluggish $O(n \\cdot m)$ complexity that a naive nested loop would have.
2.  **Uniqueness Guaranteed ✅**: The `HashSet` data structure inherently does not allow duplicate values. By using sets to store the initial numbers and the final result, we effortlessly satisfy the requirement that the output must contain only unique elements.

-----

## Complexity Analysis

  - **Time Complexity**: $O(n + m)$, where `n` and `m` are the lengths of `nums1` and `nums2` respectively.
      - It takes $O(n)$ time to build the set from `nums1`.
      - It takes $O(m)$ time to iterate through `nums2` and perform the $O(1)$ `contains` check for each element.
  - **Space Complexity**: $O(n + m)$ in the worst-case scenario. We need $O(n)$ space for the first set and up to $O(m)$ space for the intersection set.

-----

## Example Walkthrough

**Input:**

```
nums1 = [4, 9, 5]
nums2 = [9, 4, 9, 8, 4]
```

**Process:**

1.  Create `set1` from `nums1`. The set becomes `{4, 5, 9}`.
2.  Create an empty `intersectionSet`.
3.  Iterate through `nums2`:
      - `num = 9`: `set1.contains(9)` is true. Add `9` to `intersectionSet`. It's now `{9}`.
      - `num = 4`: `set1.contains(4)` is true. Add `4` to `intersectionSet`. It's now `{9, 4}`.
      - `num = 9`: `set1.contains(9)` is true. Add `9` to `intersectionSet`. It remains `{9, 4}` (no duplicates).
      - `num = 8`: `set1.contains(8)` is false. Do nothing.
      - `num = 4`: `set1.contains(4)` is true. Add `4` to `intersectionSet`. It remains `{9, 4}`.
4.  Convert `intersectionSet` to an array.

**Output:**

```
[9, 4]  // or [4, 9], as order doesn't matter
```

-----

## Alternate Approaches

1.  **Two Pointers (After Sorting)**:
      - **How**: First, sort both `nums1` and `nums2`. Then, use a pointer for each array, starting at the beginning. Compare the elements: if they match, add the element to the result (if it's not already there) and advance both pointers. If they don't match, advance the pointer of the smaller element.
      - **Complexity**: Time is dominated by sorting, making it $O(n \\log n + m \\log m)$. Space is $O(\\log n + \\log m)$ or $O(n+m)$ depending on the sort implementation.
      - **Trade-off**: This approach is better if the arrays are already sorted or if memory is a significant constraint (as it avoids creating a large hash set).

### Optimal Choice

For general-purpose use where memory isn't extremely limited, the **Hash Set method is superior** due to its better average time complexity and simpler logic.

-----

## Key Insight

The core idea is to trade space for time. By using a `HashSet` (which takes up extra space), we can drastically reduce the time it takes to check for the existence of elements from $O(n)$ per lookup to just $O(1)$. This is a common and powerful pattern in algorithm design.