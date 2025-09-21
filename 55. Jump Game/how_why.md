# How\_Why.md: Jump Game (LeetCode 55)

## Problem

Given an array of non-negative integers `nums`, each element represents the **maximum jump length** from that position. Determine if you can reach the last index starting from the first index.

**Example:**

```
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

---

## Brute-force Approach

### Idea

* Recursively try all possible jumps from the current index.
* If any path reaches the last index, return `true`.

### Code

```java
public boolean canJumpBF(int[] nums, int pos) {
    if (pos >= nums.length - 1) return true;
    int furthestJump = Math.min(pos + nums[pos], nums.length - 1);
    for (int next = pos + 1; next <= furthestJump; next++) {
        if (canJumpBF(nums, next)) return true;
    }
    return false;
}
```

### Example Walkthrough

`nums = [2,3,1,1,4]`:

1. Start at index `0` → max jump `2`.
2. Try jump to index `1` → max jump `3` → recursively check indices `2`, `3`, `4`.
3. Jump to index `4` → reached end → return `true`.

**Limitation:**

* Exponential time complexity `O(2^N)` in the worst case.
* Redundant recalculation of same indices.
* Not feasible for large arrays.

---

## User Approach / Greedy (Backward)

### Idea_

* Start from the last index (goal) and move backward.
* If `i + nums[i] >= goal`, update `goal = i`.
* If at the end `goal == 0`, first index can reach the last.

### Code_

```java
public boolean canJump(int[] nums) {
    int goal = nums.length - 1;
    for (int i = nums.length - 2; i >= 0; i--) {
        if (i + nums[i] >= goal) {
            goal = i;
        }
    }
    return goal == 0;
}
```

### Example Walkthrough_

`nums = [2,3,1,1,4]`:

1. `goal = 4` (last index)
2. Index `3`: `3 + 1 < 4` → goal unchanged
3. Index `2`: `2 + 1 < 4` → goal unchanged
4. Index `1`: `1 + 3 >= 4` → update `goal = 1`
5. Index `0`: `0 + 2 >= 1` → update `goal = 0`
6. End → `goal == 0` → can reach last index → return `true`.

**Time Complexity:** `O(N)`
**Space Complexity:** `O(1)`

---

## Optimized Approach

* The user approach is already optimal: single backward pass, no extra memory.
* Alternative forward greedy approach:

  * Track the furthest reachable index while scanning from left to right.
  * If at any index `i` beyond furthest reachable → return false.
  * If furthest reachable >= last index → return true.

### Forward Greedy (Optional)

```java
public boolean canJumpForward(int[] nums) {
    int furthest = 0;
    for (int i = 0; i <= furthest; i++) {
        furthest = Math.max(furthest, i + nums[i]);
        if (furthest >= nums.length - 1) return true;
    }
    return false;
}
```

---

### Key Takeaways

1. Brute-force recursion is simple but exponential (`O(2^N)`).
2. Greedy backward or forward passes achieve linear time (`O(N)`).
3. Minimal space usage: `O(1)`.

---
