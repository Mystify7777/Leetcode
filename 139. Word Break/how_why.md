# How\_Why.md — Word Break (LeetCode 139)

## ❌ Brute-Force Approach (DFS without memoization)

### Idea

* Start from index `0` in the string.
* For each word in the dictionary, check if the string at the current index begins with that word.
* If yes, recursively try breaking the rest of the string.
* If any path works, return `true`.
* If all fail, return `false`.

### Limitation

* Exponential time complexity (`O(2^n)` in worst case).
* Many overlapping subproblems (recomputes the same suffix multiple times).

### Example Walkthrough

`s = "leetcode", wordDict = ["leet", "code"]`

* Start at index 0: try `"leet"` → recursion continues from index 4 (`"code"`)
* `"code"` matches → success.
* But in harder cases like `"aaaaaaa"` with `["a","aa","aaa"]`, recursion explodes.

---

## ⚡ Your Approach 1 (boolean\[] memo for failed indices)

### Idea

* Use a `boolean[] memo` where `memo[k] = true` means:
  *“We have already tried breaking from index `k` and it failed, so don’t try again.”*
* At each index, try dictionary words.
* If recursion ever reaches `s.length()`, return `true`.
* If all words fail at `k`, mark `memo[k] = true` and return `false`.

### Example Walkthrough

`s = "leetcode", wordDict = ["leet","code"]`

* At index 0: `"leet"` matches → go to index 4.
* At index 4: `"code"` matches → reach end → return `true`.
* No recomputation of failed paths thanks to memo.

### Complexity

* **Time:** `O(n * m * k)` where `n = length of string`, `m = number of words`, `k = max word length`.
* **Space:** `O(n)` for memo array + recursion stack.

### Limitation

* The meaning of `memo[k] = true` is **“failure”**, which is unintuitive.
* Works, but harder to read/maintain.

---

## ✅ Optimized Approach (Boolean\[] dp storing result)

### Idea

* Use `Boolean[] dp` where each entry directly means:

  * `true`: substring from `i` is breakable
  * `false`: substring from `i` is not breakable
  * `null`: not yet computed
* This makes code more natural.
* Still recursive with memoization → avoids redundant recomputation.

### Example Walkthrough

`s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]`

* Start at 0: try `"cats"` → jump to index 4.
* At 4: try `"and"` → jump to index 7.
* At 7: `"og"` fails (not in dict).
* Backtrack, mark dp\[4] = false.
* Eventually, all options fail → return false.

### Complexity

* **Time:** `O(n * m * k)` same as above, but cleaner logic.
* **Space:** `O(n)` for dp array.

---

## 🚀 Most Optimized Approach (Bottom-Up DP)

### Idea

* Use an iterative `dp[i]` = whether substring `s[0..i)` can be segmented.
* Base case: `dp[0] = true`.
* For each `i`, check if there exists a `j < i` such that `dp[j] = true` and `s[j..i)` is in dictionary.

### Example Walkthrough

`s = "leetcode", wordDict = ["leet","code"]`

* `dp[0] = true`
* `dp[4] = true` because `dp[0] = true` and `"leet"` ∈ dict.
* `dp[8] = true` because `dp[4] = true` and `"code"` ∈ dict.
* Final answer: `dp[n] = dp[8] = true`.

### Complexity

* **Time:** `O(n^2)` in worst case (checking substrings).
* With hashset lookup: substring check is `O(1)` on average.
* **Space:** `O(n)` for dp array.

---

## 🏆 Summary

* **Brute Force:** Exponential, too slow.
* **Your boolean\[] memo:** Works but memoization meaning is inverted → less intuitive.
* **Boolean\[] dp (recursive):** Clearer memoized recursion.
* **Bottom-Up DP:** Best for interviews; iterative, clean, avoids recursion stack.

---
