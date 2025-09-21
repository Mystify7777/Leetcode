# How\_Why.md — Problem 46: Permutations

## Problem Restatement

We are given an array of distinct integers `nums`. We need to return **all possible permutations** of the array.

* Example:
  Input: `nums = [1,2,3]`
  Output: `[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]`

---

## 1. Brute Force Approach

### Idea

Generate **all possible orderings** by repeatedly choosing elements without worrying about visited tracking, and then filter out invalid ones.

### Steps

1. For each position in the permutation, try placing every element.
2. Ensure no element is repeated (filter later).
3. Store only valid permutations of length `n`.

### Complexity

* **Time:** $O(n^n)$, because at each of `n` positions we try `n` choices.
* **Space:** $O(n)$ recursion depth.

### Example Walkthrough

Input: `nums = [1,2]`

Generated (before filtering):

* \[1,1], \[1,2], \[2,1], \[2,2]

Valid permutations:

* \[1,2], \[2,1]

**Limitation:** Too slow, generates many invalid permutations.

---

## 2. Backtracking with Visited Set (Your First Version)

### Idea_

Use **DFS with a `Set`** to track which elements are already used. At each step, pick an unused number and continue.

### Steps_

1. Maintain a temporary list `tmpList` and a set `tmpSet`.
2. At each step, iterate over all numbers.
3. If the number is not in `tmpSet`, add it and recurse.
4. Once `tmpList.size() == nums.length`, save it.
5. Backtrack by removing the last number and marking it unused.

### Complexity_

* **Time:** $O(n \cdot n!)$ because each valid permutation takes $O(n)$ to copy.
* **Space:** $O(n)$ recursion depth + $O(n)$ for visited set.

### Example Walkthrough_

Input: `nums = [1,2,3]`

* Start → \[]

  * Choose 1 → \[1]

    * Choose 2 → \[1,2]

      * Choose 3 → \[1,2,3] ✅
    * Backtrack → \[1]

      * Choose 3 → \[1,3]

        * Choose 2 → \[1,3,2] ✅
  * Backtrack → \[]

    * Choose 2 → \[2] … and so on

Result = \[\[1,2,3],\[1,3,2],\[2,1,3],\[2,3,1],\[3,1,2],\[3,2,1]]

---

## 3. Backtracking with In-place Swapping (Your Current Approach)

### Idea__

Instead of using a set, we **swap elements in-place**:

* Fix an element at position `pos`.
* Recursively permute the rest.
* Swap back to restore array.

### Steps__

1. Start with `pos=0`.
2. For each index `j` from `pos` to `n-1`:

   * Swap `nums[pos]` and `nums[j]`.
   * Recurse with `pos+1`.
   * Swap back.
3. When `pos == nums.length`, save current permutation.

### Complexity__

* **Time:** $O(n \cdot n!)$ (same as before).
* **Space:** $O(n)$ recursion stack, no extra `Set` needed.

### Example Walkthrough__

Input: `nums = [1,2,3]`

* pos=0: swap(0,0) → \[1,2,3]

  * pos=1: swap(1,1) → \[1,2,3]

    * pos=2: swap(2,2) → \[1,2,3] ✅
    * backtrack → \[1,2,3]
    * swap(1,2) → \[1,3,2] ✅
  * backtrack → \[1,2,3]
  * swap(0,1) → \[2,1,3] → … ✅
  * swap(0,2) → \[3,2,1] → … ✅

Result = \[\[1,2,3],\[1,3,2],\[2,1,3],\[2,3,1],\[3,2,1],\[3,1,2]]

---

## Best Approach

✅ **In-place Swapping (Approach 3)** is the most efficient:

* Same time complexity as visited-set method but less memory overhead.
* Cleaner and avoids managing an external set.

**Final Complexity:**

* **Time:** $O(n \cdot n!)$
* **Space:** $O(n)$ recursion depth

---

Would you like me to also add a **visual recursion tree diagram** for this problem in the file (like I did for "Power of Two")? That would make backtracking steps easier to follow.
