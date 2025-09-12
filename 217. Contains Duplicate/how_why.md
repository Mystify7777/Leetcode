# How & Why: LeetCode 217 - Contains Duplicate

---

## Problem Restatement
You are given an integer array `nums`. You need to determine if there are **any duplicate elements** in the array.

Return `true` if any value appears at least twice, and `false` if all elements are distinct.

---

## How to Solve
The simplest and most efficient way is to use a **HashSet**:
- A `HashSet` stores only unique elements.
- As we iterate through `nums`, we check:
  - If the number is already in the set → duplicate found → return `true`.
  - Otherwise, add it to the set.
- If we finish scanning with no duplicates, return `false`.

### Implementation
```java
public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (seen.contains(num))
            return true;
        seen.add(num);
    }
    return false;
}
```

---

## Why This Works
1. **Set Property**: A set automatically enforces uniqueness.
2. **Early Exit**: The method stops immediately upon finding a duplicate.
3. **Simplicity**: Clear and concise logic without extra data structures.

---

## Complexity Analysis
- **Time Complexity**: O(n), where `n` = length of `nums`.
  - Each lookup and insertion in a HashSet is O(1) on average.
- **Space Complexity**: O(n), in the worst case (all elements unique).

---

## Example Walkthrough
Input:
```
nums = [1, 2, 3, 1]
```

Process:
- Add 1 → seen = {1}
- Add 2 → seen = {1,2}
- Add 3 → seen = {1,2,3}
- Next is 1 → already in set → return `true`

Output:
```
true
```

---

## Alternate Approaches
1. **Sorting Method**:
   - Sort the array.
   - Check consecutive elements for equality.
   - Time complexity: O(n log n), Space: O(1) if done in-place.

2. **Brute Force Check**:
   - Compare every pair of elements.
   - Time complexity: O(n²), not feasible for large input.

### Optimal Choice
The **HashSet approach** is the best trade-off:
- O(n) runtime
- Easy to implement
- Minimal additional logic

---

## Key Insight
The problem boils down to **detecting repetition**. HashSet provides the most direct and efficient way to detect duplicates during iteration.

