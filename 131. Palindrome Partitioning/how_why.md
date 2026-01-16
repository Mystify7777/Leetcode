
# How & Why: LeetCode 131 - Palindrome Partitioning

## Problem

Given a string `s`, partition it into all possible lists of substrings where each substring is a palindrome. Return every valid partition.

## Intuition

- The decision at each position is where to cut next. A cut is valid only if the chosen prefix is a palindrome. This naturally suggests backtracking over cut positions.
- Precomputing palindrome substrings with DP lets backtracking run in $O(1)$ per palindrome query instead of $O(len)$ checks.

## Brute Force Approach

- **Idea:** Backtrack over all cut positions, and every time you pick a substring, check palindrome by scanning both ends.
- **Complexity:** Exponential number of partitions, with $O(n)$ extra per palindrome check → slower in practice.

## My Approach (DP Palindrome + Backtracking) — from Solution.java

- **Idea:**
	1) Precompute `dp[l][r]` = true if `s[l..r]` is palindrome using expanding window: `s[l]==s[r]` and inner is palindrome.
	2) Backtrack from index 0; for each end `i`, if `dp[pos][i]` is true, choose substring and recurse from `i+1`.
- **Complexity:** Palindrome DP $O(n^2)$ time/space; backtracking explores all partitions (exponential) but with $O(1)$ palindrome lookups.
- **Core snippet:**

```java
// build palindrome table
for (int r=0; r<n; r++)
	for (int l=0; l<=r; l++)
		if (s.charAt(l)==s.charAt(r) && (r-l<=2 || dp[l+1][r-1])) dp[l][r]=true;

void dfs(int pos){
	if (pos==n){ ans.add(new ArrayList<>(path)); return; }
	for (int i=pos; i<n; i++) if (dp[pos][i]) {
		path.add(s.substring(pos,i+1));
		dfs(i+1);
		path.remove(path.size()-1);
	}
}
```

## Most Optimal Approach

- DP + backtracking is standard and efficient. An alternative is on-the-fly expansion during DFS (no $O(n^2)$ memory) but similar time; choose based on memory vs. constant factors.

## Edge Cases

- Empty string (if allowed) → single empty partition.
- All same characters (`aaaa`) → many partitions; backtracking still valid.
- No palindromes longer than 1 → only partition into single chars.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Backtrack + on-the-fly palindrome check | Expand each candidate substring | Exponential, extra per check | O(n) stack | Less precompute, more repeated work |
| DP palindrome + backtrack (used) | Precompute pal table, then DFS | O(n^2) precompute + exponential output | O(n^2) dp + O(n) stack | Faster per branch |

## Example Walkthrough

`s = "aab"`

- Pal table marks `"a"` and `"aa"` as palindromes.
- DFS paths:
	- Choose `"a"` (0,0) → recurse from 1; choose `"a"` (1,1) → from 2 choose `"b"` (2,2) → partition `["a","a","b"]`.
	- Backtrack; from 0 choose `"aa"` (0,1) → from 2 choose `"b"` → partition `["aa","b"]`.
- Results: `["a","a","b"]`, `["aa","b"]`.

## Insights

- Precomputing palindromes converts repeated substring checks into table lookups, a common trick in palindrome-partition problems.

## References to Similar Problems

- 132. Palindrome Partitioning II (min cuts using similar pal DP)
- 5. Longest Palindromic Substring (expand/DP patterns)
