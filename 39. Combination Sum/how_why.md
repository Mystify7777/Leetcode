# How\_Why.md — Problem 39: Combination Sum

## Problem Restatement

We are given an array of distinct integers `candidates` and a target integer `target`. We need to return **all unique combinations** of candidates where the chosen numbers sum up to `target`. A candidate may be used **unlimited times**.

* Example:
  Input: `candidates = [2,3,6,7], target = 7`
  Output: `[[2,2,3],[7]]`

---

## 1. Brute Force Approach

### Idea

Generate **all possible subsets (or sequences)** of the given numbers, then check which ones sum to the target.

### Steps

1. Generate every possible subset of `candidates`.
2. For each subset, check if the sum equals `target`.
3. Collect only those subsets that match.

### Complexity

* **Time:** $O(2^n \cdot n)$ because each subset can take $O(n)$ to compute sum.
* **Space:** $O(n)$ recursion depth.

### Example

`candidates = [2,3], target = 6`

All subsets:

* \[] → sum=0
* \[2] → sum=2
* \[3] → sum=3
* \[2,2] → sum=4
* \[2,3] → sum=5
* \[3,3] → sum=6 ✅

Answer = \[\[3,3]]

**Limitation:**
This wastes time generating invalid combinations and doesn’t handle “reuse of elements” efficiently.

---

## 2. Backtracking (Your Approach)

### Idea__

Use **DFS + backtracking**:

* At each step, choose whether to include the current number.
* If included, we don’t move forward (because repetition is allowed).
* If excluded, we move to the next index.

### Steps__

1. Start with index `0`, total sum = `0`.
2. If `total == target`, save combination.
3. If `total > target` or `idx >= n`, stop.
4. Recursive choices:

   * **Include candidates\[idx]** → stay at same index.
   * **Skip candidates\[idx]** → move to `idx+1`.

### Complexity__

* **Time:** Approx. $O(2^t)$, where $t$ = target / smallest candidate (worst case).
* **Space:** $O(n)$ recursion depth.

### Example Walkthrough

Input: `candidates = [2,3,6,7], target = 7`

* Start: idx=0, total=0

  * Include 2 → total=2

    * Include 2 → total=4

      * Include 2 → total=6

        * Include 2 → total=8 (too large, backtrack)
        * Skip → idx=1, total=6

          * Include 3 → total=9 (too large, backtrack)
          * Skip → idx=2… end
      * Skip → idx=1, total=4

        * Include 3 → total=7 ✅ \[2,2,3]
  * Skip → idx=1, total=0

    * Include 3 → total=3

      * Include 3 → total=6

        * Include 3 → total=9 (too large, backtrack)
        * Skip → idx=2… end
      * Skip → idx=2… end
    * Skip → idx=2…

      * Include 6 → total=6 (not target)
      * Skip → idx=3 → include 7 → total=7 ✅ \[7]

Result = \[\[2,2,3],\[7]]

---

## 3. Optimized Backtracking (Cleaner Version)

### Idea_

Instead of splitting into “include” vs “exclude”, we iterate over candidates starting from an index and directly build combinations.

### Steps_

* For each candidate starting from `index`,

  * Subtract it from target.
  * If remaining >= 0, recurse with same index.
  * If remaining == 0, save the path.
* This avoids unnecessary branches.

### Complexity_

* **Time:** Still exponential in worst case, but fewer useless calls than brute force.
* **Space:** $O(n)$ recursion stack.

### Example Walkthrough_

Input: `candidates = [2,3,6,7], target = 7`

1. Start with `2`: path = \[2], remain=5

   * Take `2`: path = \[2,2], remain=3

     * Take `2`: path = \[2,2,2], remain=1 (dead end)
     * Take `3`: path = \[2,2,3], remain=0 ✅
   * Try `3`: path = \[2,3], remain=2 (dead end)
2. Start with `3`: path = \[3], remain=4

   * Take `3`: path = \[3,3], remain=1 (dead end)
3. Start with `6`: path = \[6], remain=1 (dead end)
4. Start with `7`: path = \[7], remain=0 ✅

Result = \[\[2,2,3],\[7]]

---

## Best Approach

✅ **Optimized Backtracking** is the best method:

* Avoids generating invalid subsets.
* Efficient pruning when sum exceeds target.
* Cleaner and faster than brute force.

**Final Complexity:**

* **Time:** Exponential, but optimal for this problem.
* **Space:** $O(n)$ for recursion depth.

---
