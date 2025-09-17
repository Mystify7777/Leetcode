# How_Why.md

## Problem

Implement the function `nextPermutation(int[] nums)` which rearranges numbers into the **lexicographically next greater permutation** of numbers.  
If such arrangement is not possible, it rearranges it as the **lowest possible order** (i.e., sorted in ascending order).  

The replacement must be done in-place with only constant extra memory.

---

## How (Step-by-step Solution)

### Algorithm

1. **Find the longest non-increasing suffix**:
   - Traverse from the right until you find the first element that breaks the non-increasing order.
   - Let that index be `i-1`.  
   - If no such element exists (`i == 0`), the array is in descending order → simply reverse it.

2. **Find the successor to `nums[i-1]`**:
   - From the right, find the first element greater than `nums[i-1]`. Call this index `j`.

3. **Swap**:
   - Swap `nums[i-1]` and `nums[j]`.

4. **Reverse the suffix**:
   - Reverse the subarray from `i` to the end of the array to get the next lexicographically smallest sequence.

---

## Why (Reasoning)

- Lexicographically next permutation means the smallest permutation that is **larger** than the current one.
- Step 1 identifies the pivot where we can "increase" the number.
- Step 2 ensures we replace it with the **smallest larger element**.
- Step 3 guarantees correctness by performing the swap.
- Step 4 arranges the suffix into the smallest possible order, ensuring minimal increase.

This process ensures the next permutation is achieved in **O(n)** time with **O(1)** space.

---

## Complexity Analysis

- **Time Complexity**: O(n), we traverse the array a few times (once backward, once forward, once to reverse).
- **Space Complexity**: O(1), in-place rearrangement.

---

## Example Walkthrough

### Example 1

`Input: nums = [1,2,3]`

**Process:**

- Find pivot: i=2 (nums[1]=2 < nums[2]=3)

- Find successor: j=2 (nums[2]=3)

- Swap nums[1] and nums[2] → [1,3,2]

- Reverse suffix (from i=2 to end) → [1,3,2]

`Output: [1,3,2]`

### Example 2

`Input: nums = [3,2,1]`

**Process:**

- No pivot (completely descending).

- Reverse entire array → [1,2,3]

`Output: [1,2,3]`

### Example 3

`Input: nums = [1,1,5]`

**Process:**

- Pivot at i=2 (nums[1]=1 < nums[2]=5)

- Successor j=2 (nums[2]=5)

- Swap → [1,5,1]

- Reverse suffix → [1,5,1]

`Output: [1,5,1]`

---

## Alternate Approaches

1. **Brute Force**:
   - Generate all permutations, sort them, and pick the next one.
   - Time: O(n!) → infeasible.

2. **Library Functions**:
   - In some languages, built-in functions (like `std::next_permutation` in C++) solve it directly.
   - But the optimal manual method is the pivot + swap + reverse approach.

✅ Optimal: **Pivot + Successor + Swap + Reverse** method (used in the code).
