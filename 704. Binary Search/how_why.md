
# How & Why: LeetCode 704 - Binary Search

This solution implements the classic binary search algorithm, which is a highly efficient method for finding an element within a sorted array.

---

## Problem Restatement

You are given an array of integers `nums` which is sorted in ascending order, and an integer `target`. Your task is to write a function that searches for the target within `nums`.

If the target value exists, the function should return its index. Otherwise, it should return -1. The algorithm must have a runtime complexity of $O(\log n)$.

### Example

**Input:**
```
nums = [-1,0,3,5,9,12], target = 9
```
**Output:**
```
4
```

---

## How to Solve

Binary search works on the principle of "divide and conquer." Since the array is sorted, we can eliminate half of the remaining elements in every step.

1. **Set Boundaries:** Initialize two pointers, `low` at the beginning of the array (index 0) and `high` at the end of the array (index `nums.length - 1`). These pointers define our current search space.
2. **Loop and Divide:** Continue a loop as long as `low` is less than or equal to `high`.
3. **Find the Middle:** In each iteration, calculate the middle index `mid` of the current search space.
4. **Compare:** Compare the element at the middle index, `nums[mid]`, with the target.
5. **Match Found:** If `nums[mid]` equals the target, the search is successful. Return `mid`.
6. **Target is Smaller:** If the target is less than `nums[mid]`, it means the target must be in the left half of the current search space. Discard the right half by setting `high = mid - 1`.
7. **Target is Larger:** If the target is greater than `nums[mid]`, it must be in the right half. Discard the left half by setting `low = mid + 1`.
8. **Target Not Found:** If the loop finishes (meaning `low` has crossed `high`), the target was not present in the array. Return -1.

### Implementation

```java
class Solution {
    /**
     * Searches for a target value in a sorted array using Binary Search.
     *
     * @param nums The sorted array of integers.
     * @param target The integer to search for.
     * @return The index of the target if found, otherwise -1.
     */
    public int search(int[] nums, int target) {
        // Initialize pointers for the start and end of the array.
        int low = 0;
        int high = nums.length - 1;

        // Loop until the two pointers cross.
        while (low <= high) {
            // Calculate the middle index to avoid potential overflow.
            int mid = low + (high - low) / 2;

            // Case 1: The middle element is the target.
            if (nums[mid] == target) {
                return mid;
            }
            // Case 2: The target is smaller, so it must be in the left half.
            else if (nums[mid] > target) {
                high = mid - 1;
            }
            // Case 3: The target is larger, so it must be in the right half.
            else {
                low = mid + 1;
            }
        }

        // If the loop finishes, the target was not found.
        return -1;
    }
}
```

---

## Why This Works

- **Efficiency Through Elimination:** The power of binary search comes from its ability to discard half of the search area with a single comparison. This dramatically reduces the number of elements to check, especially for large arrays.
- **Leveraging the Sorted Property:** The algorithm fundamentally relies on the array being sorted. This sorted nature guarantees that if the middle element isn't the target, we know with certainty which half the target must be in (or if it's absent).
- **Guaranteed Termination:** The search space (`high - low`) shrinks with every iteration. The loop condition `low <= high` ensures that the process will always terminate, either by finding the element or by exhausting the search space.

---

## Complexity Analysis

- **Time Complexity:** $O(\log n)$. With each step, we halve the size of the input array. The number of times you can halve $n$ until you get to 1 is $\log_2(n)$.
- **Space Complexity:** $O(1)$. The search is done in-place using only a few variables, so it requires no extra memory proportional to the input size.

---

## Example Walkthrough

**Input:**
```
nums = [-1, 0, 3, 5, 9, 12], target = 9
```

**Process:**

- Initial: `low = 0`, `high = 5`.
- `mid = 0 + (5-0)/2 = 2`. `nums[2]` is 3.
- 3 < 9, so the target is in the right half. New range: `low = mid + 1 = 3`, `high = 5`.
- Next: `low = 3`, `high = 5`.
- `mid = 3 + (5-3)/2 = 4`. `nums[4]` is 9.
- 9 == 9. Match found!

**Output:**
```
Return mid, which is 4.
```

---

## Alternate Approaches

### 1. Linear Search
   - **How:** Iterate through the array from the beginning, one element at a time, and compare each element to the target.
   - **Complexity:** $O(n)$ time. This is far less efficient and will fail the time complexity requirements for this problem on large inputs.

---

## Optimal Choice

Binary search is the definitive optimal solution for searching within a sorted array due to its logarithmic time complexity.

---

## Key Insight

The fundamental principle is to exploit the sorted order of the data. This property allows you to make an intelligent guess at the middle and instantly eliminate half of the possibilities, leading to a massive gain in efficiency over a simple linear scan.