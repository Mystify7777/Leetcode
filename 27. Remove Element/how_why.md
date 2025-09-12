# How & Why: LeetCode 27 - Remove Element

---

## Problem Restatement
You are given an integer array `nums` and an integer `val`. You need to **remove all occurrences of `val` in-place**, while keeping the order of the remaining elements.

Return the new length of the array after removal. The first part of `nums` should contain the elements that are not equal to `val`.

---

## How to Solve
The idea is to use a **two-pointer technique**:
- One pointer `i` scans through the array.
- Another pointer `index` tracks the position where the next valid element (not equal to `val`) should go.

### Steps
1. Initialize `index = 0`.
2. Loop through the array with `i`.
   - If `nums[i] != val`, copy `nums[i]` to `nums[index]` and increment `index`.
   - If `nums[i] == val`, skip it.
3. After the loop, `index` will represent the new length of the array with all valid elements.

### Implementation
```java
public int removeElement(int[] nums, int val) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != val) {
            nums[index] = nums[i];
            index++;
        }
    }
    return index;
}
```

---

## Why This Works
1. **In-place Update**: Instead of creating a new array, we overwrite unwanted elements with valid ones.
2. **Efficient**: Each element is processed once, and no extra space is used.
3. **Correctness**: At the end, the first `index` elements are guaranteed to be valid, and the rest can be ignored.

---

## Complexity Analysis
- **Time Complexity**: O(n), where `n` = length of `nums`, since we scan the array once.
- **Space Complexity**: O(1), no extra space used beyond a few variables.

---

## Example Walkthrough
Input:
```
nums = [3,2,2,3], val = 3
```

Process:
- i=0 → nums[0]=3 → skip
- i=1 → nums[1]=2 → place at nums[0] → nums=[2,2,2,3], index=1
- i=2 → nums[2]=2 → place at nums[1] → nums=[2,2,2,3], index=2
- i=3 → nums[3]=3 → skip

Result:
```
New length = 2
Array = [2,2,...] (rest ignored)
```

---

## Alternate Approaches
1. **Two-Pointer Swap from End**:
   - Another common method is to swap the unwanted element with the last valid element and shrink the array.
   - More efficient if `val` is rare, since fewer elements are copied.

2. **Stream Filtering (Java 8+)**:
   - Use streams to filter elements and rebuild a new array.
   - Not in-place and uses extra space, so less optimal for this problem.

### Optimal Choice
The provided **two-pointer overwrite method** is the best balance:
- Simple
- In-place
- Linear time

---

## Key Insight
The problem is essentially about **filtering elements in-place**. The overwrite technique guarantees minimal memory use and a clean O(n) runtime.