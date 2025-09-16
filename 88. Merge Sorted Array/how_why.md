## How & Why: LeetCode 88 — Merge Sorted Array

This note explains an in-place solution that merges two sorted arrays by iterating from the end. That avoids overwriting unprocessed values in `nums1`.

---

### Problem

You are given two integer arrays, `nums1` and `nums2`, sorted in non-decreasing order.

- `nums1` has length `m + n`. The first `m` elements are the valid elements to merge; the last `n` elements are placeholders (set to `0`) and should be ignored.
- `nums2` has length `n` and contains `n` valid elements.

Goal: Merge `nums2` into `nums1` so that `nums1` becomes a single sorted array of length `m + n`. Do this in-place (modify `nums1`).

---

### Key idea

Merge from the back. Since the tail of `nums1` has empty space, place the largest elements at the end and move backwards. Using three pointers allows an O(m + n) in-place merge without extra arrays.

---

### Algorithm (pseudocode)

- Let `p1 = m - 1` (index of last valid element in `nums1`).
- Let `p2 = n - 1` (index of last element in `nums2`).
- Let `p = m + n - 1` (index of last position in `nums1`).

While `p2 >= 0`:
- If `p1 >= 0` and `nums1[p1] > nums2[p2]`:
  - `nums1[p] = nums1[p1]`
  - `p1--`
- Else:
  - `nums1[p] = nums2[p2]`
  - `p2--`
- `p--`

This loop guarantees all elements from `nums2` are placed; any remaining elements from `nums1` are already in correct order.

---

### Implementation (Java)

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;         // last valid in nums1
        int p2 = n - 1;         // last in nums2
        int p = m + n - 1;      // last position in nums1

        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
    }
}
```

---

### Why it works

- No data loss: Placing values from the back prevents overwriting unprocessed values in `nums1`.
- Sorted order maintained: At each step the largest remaining element between the unprocessed portions of `nums1` and `nums2` is placed at the current end position `p`.
- Optimal for constraints: Single pass, in-place, O(m + n) time and O(1) extra space.

---

### Complexity

- Time: O(m + n) — each element is considered at most once.
- Space: O(1) — only a few integer pointers are used.

---

### Example walkthrough

Input:

```
nums1 = [1, 2, 3, 0, 0, 0], m = 3
nums2 = [2, 5, 6], n = 3
```

We will show step-by-step updates of pointers and `nums1`.

| Step | p1 (index:value) | p2 (index:value) | p | Comparison    | Action                     | nums1 state           |
|------|-------------------|-------------------|---|---------------|----------------------------|-----------------------|
| 1    | 2 : 3             | 2 : 6             | 5 | 3 < 6         | write 6 from nums2 -> p=5  | [1,2,3,0,0,6]         |
| 2    | 2 : 3             | 1 : 5             | 4 | 3 < 5         | write 5 from nums2 -> p=4  | [1,2,3,0,5,6]         |
| 3    | 2 : 3             | 0 : 2             | 3 | 3 > 2         | write 3 from nums1 -> p=3  | [1,2,3,3,5,6]         |
| 4    | 1 : 2             | 0 : 2             | 2 | 2 <= 2        | tie -> write 2 from nums2  | [1,2,2,3,5,6]         |
| 5    | 1 : 2             | -1 : —            | 1 | p2 < 0        | done                       | [1,2,2,3,5,6]         |

Final `nums1`: `[1, 2, 2, 3, 5, 6]`.

Notes on the walkthrough:
- When values tie (e.g., 2 and 2), the implementation places the value from `nums2` (because of the `else`), but placing from `nums1` would also be correct; it doesn't affect correctness.
- The loop condition `p2 >= 0` ensures all elements from `nums2` are consumed. If `p1` becomes negative first, the loop continues copying remaining `nums2` elements into `nums1`.

---

### Alternate approaches (for comparison)

1. Merge then sort:
   - Copy `nums2` elements into `nums1` tail and call `Arrays.sort(nums1)`.
   - Time: O((m + n) log(m + n)). Space: O(1) extra (but slower).

2. Use extra array:
   - Allocate new array of size m + n, merge from front using two pointers, then copy back into `nums1`.
   - Time: O(m + n). Space: O(m + n) extra — violates the in-place constraint.

---

### Key insight

Reverse the direction: because the available buffer is at the end of `nums1`, merging from the end avoids shifting and makes the solution simple, efficient, and in-place.