# How & Why: LeetCode 189 - Rotate Array

This solution rotates an array in-place using a clever algorithm that involves three separate reversal steps. It's a highly efficient method that meets the problem's memory constraints.

---

## Problem Restatement

You are given an integer array `nums` and a non-negative integer `k`. Your task is to rotate the array to the right by `k` steps. The rotation should be done in-place, meaning you should modify the original array without using a proportional amount of extra memory ($O(1)$ extra space).

### Example

**Input:**
```
nums = [1, 2, 3, 4, 5, 6, 7], k = 3
```
**Output:**
```
[5, 6, 7, 1, 2, 3, 4]
```

---

## How to Solve

The provided solution uses an elegant and efficient algorithm known as the **Three Reversals** method. It's a non-intuitive but powerful way to achieve rotation in-place.

1. **Normalize k:** If `k` is larger than the array's length, rotating `k` times is the same as rotating `k % n` times. This step ensures we only do the necessary work.
2. **Reverse the Entire Array:** Reverse all the elements in the array.
3. **Reverse the First k Elements:** Reverse only the first `k` elements of the modified array.
4. **Reverse the Remaining Elements:** Reverse the rest of the array, from index `k` to the end.

This sequence of three reversals will leave the array in its correctly rotated state.

### Implementation

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        // Step 1: Normalize k to handle cases where k > n
        k = k % n;

        // Step 2: Reverse the entire array
        Reverse(nums, 0, n - 1);
        // Step 3: Reverse the first k elements
        Reverse(nums, 0, k - 1);
        // Step 4: Reverse the remaining n-k elements
        Reverse(nums, k, n - 1);
    }

    // Helper function for in-place reversal using two pointers
    void Reverse(int[] nums, int start, int end) {
        while (end > start) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
```

---

## Why This Works

Imagine the array consists of two parts: the first `n-k` elements (A) and the last `k` elements (B).

**Original state:** `A | B`

We want to reach the state: `B | A`

Here's how the reversals achieve this:

1. **Reverse the whole array (`A | B`):** This gives us `B_reversed | A_reversed`.
2. **Reverse the first part (`B_reversed`):** This unscrambles the first `k` elements, turning `B_reversed` back into `B`. The array is now `B | A_reversed`.
3. **Reverse the second part (`A_reversed`):** This unscrambles the remaining elements, turning `A_reversed` back into `A`. The array is now `B | A`.

This is exactly the rotated state we wanted to achieve. It's a clever trick that swaps the two blocks of the array perfectly.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$. Each element in the array is visited and swapped a constant number of times across the three reversal passes.
- **Space Complexity:** $O(1)$. The reversal is done in-place, modifying the original array directly. No extra data structures proportional to the input size are needed.

---

## Example Walkthrough

**Input:**
```
nums = [1, 2, 3, 4, 5, 6, 7], k = 3
```

**Process:**

- Initial Array: `[1, 2, 3, 4, | 5, 6, 7]`
- Reverse the whole array: `[7, 6, 5, | 4, 3, 2, 1]`
- Reverse the first k=3 elements: `[5, 6, 7, | 4, 3, 2, 1]`
- Reverse the remaining n-k=4 elements: `[5, 6, 7, | 1, 2, 3, 4]`

**Output:**
```
[5, 6, 7, 1, 2, 3, 4]
```

---

## Alternate Approaches

### 1. Using an Extra Array
   - **How:** Create a new array. Copy the last `k` elements of `nums` to the beginning of the new array, and the first `n-k` elements to the end. Finally, copy the new array back to `nums`.
   - **Complexity:** $O(n)$ time, but also $O(n)$ space, which violates the problem's constraint.

### 2. Cyclic Replacements
   - **How:** Pick an element and move it `k` steps forward. Take the element that was there and move it `k` steps forward, and so on, until you cycle back to the starting position. This is harder to implement correctly as you must handle multiple disjoint cycles.
   - **Complexity:** $O(n)$ time and $O(1)$ space.

---

## Optimal Choice

The Three Reversals method is often considered the most elegant optimal solution. It meets the $O(n)$ time and $O(1)$ space requirements and is generally easier to write and debug than the cyclic replacement approach.

---

## Key Insight

The core idea is to reframe the problem from "shifting elements" to "reordering blocks." The three-reversal algorithm provides a powerful and efficient way to swap two adjacent blocks of elements within an array in-place.