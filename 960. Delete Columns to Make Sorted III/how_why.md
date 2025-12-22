# 960. Delete Columns to Make Sorted III — How & Why

## Problem recap

- **Given:** An array `A` of `N` strings, all of equal length `M` (columns indexed `0..M-1`).
- **Operation allowed:** Choose some column indices and delete those columns from every string.
- **Goal:** Delete the minimum number of columns so that the resulting list of strings is sorted in non-decreasing lexicographic order (i.e., `A[0] <= A[1] <= ... <= A[N-1]`).

Return the minimum number of deletions required.

## Approach (high level)

- Viewing columns as positions that can be kept or deleted, the problem reduces to selecting the longest sequence of column indices (in increasing order) that can be kept while still keeping the rows sorted.
- If we can find the length `L` of the longest valid sequence of kept columns, the answer is `M - L` deletions.

This can be solved by modeling an ordering on columns and finding a longest chain under that ordering (similar to Longest Increasing Subsequence, LIS), with a validity check between two columns defined by pairwise comparisons across all rows.

## Bruteforce

- Try every subset of columns (or every combination of columns of a given size) and check whether the resulting strings are sorted. This requires checking up to 2^M subsets — exponential and infeasible for typical constraints (e.g., M up to tens or hundreds).
- Complexity: `O(2^M * N * M)` in the worst case for naive checks — not practical.

## Optimized (DP) — common accepted solution

- Idea: Let columns be indexed `0..M-1`. Define a relation: column `i` can be followed by column `j` (with i < j) if for every row r, character `A[r][i] <= A[r][j]`. If this holds, keeping `i` and `j` (with `i` before `j`) will not break order when both are kept.
- Define `dp[j]` = length of the longest valid sequence of kept columns that ends at column `j`.
- Transition: dp[j] = 1 + max(dp[i]) over all `i < j` such that column `i` can be followed by column `j`. If no such `i`, dp[j] = 1.
- Answer: M - max_j dp[j].

- Validity check for a pair (i, j): for all rows r in `0..N-1`, check `A[r][i] <= A[r][j]`. If any row violates this, `i` cannot be followed by `j`.

- Complexity: Precomputing checks or checking on the fly leads to O(N * M^2) time and O(M) space for `dp` (or O(M^2) extra if caching pair checks). This is typically acceptable for constraints like N up to ~100 and M up to few hundreds.

## Best / Practical improvements

- Use early termination when checking a pair (i, j): as soon as a row violates `A[r][i] <= A[r][j]`, stop checking that pair.
- Precompute a boolean matrix `ok[i][j]` (i<j) where `ok[i][j] = true` iff column `i` can precede column `j`. This avoids re-checking rows repeatedly while computing dp but costs O(N*M^2) time and O(M^2) memory.
- Use bitsets (or 64-bit words) to represent rows where `A[r][i] <= A[r][j]` for speed: compute per column a bitset of rows per character, or compute comparison bitsets to do pair checks with bitwise ANDs. This reduces the constant factor and can reduce actual runtime when implemented in low-level languages.
- Greedy stack-based pruning: In some variants a greedy check removing columns that break order left-to-right can be used (this is the solution for Delete Columns to Make Sorted I/II), but for the III variant where columns order matters across all rows, the DP / LIS framing is the general solution.

## Why my DP approach works

- The relation “column i can go before column j” is transitive in the sense needed for building sequences: if i can precede j and j can precede k then keeping columns (i, j, k) in that order will keep rowwise lexicographic ordering (because for every row the characters are non-decreasing across those columns). Thus columns form a partially ordered set and the task becomes finding a longest chain in that partial order.
- Dynamic programming for longest chain (LIS-style) is a standard method to find the longest increasing subsequence under any comparability relation where compatibility between two positions can be tested.
- Since each kept column contributes one character per row and the final string comparison is lexicographic left-to-right, ensuring non-decreasing characters for every row between consecutive kept columns guarantees that concatenated kept columns will keep the ordering of entire strings.

## How this problem can be scaled

- If `M` (columns) grows large (thousands): the O(N*M^2) DP can become expensive.
  - Use bitsets so that comparing a pair (i, j) across N rows costs O(N / W) machine words (W = word size, 32/64) instead of O(N) scalar comparisons.
  - Compress characters per column into small integers (if alphabet small) to accelerate comparisons.
  - Parallelize pairwise checks across multiple threads when running on multi-core machines.
- If `N` (rows) grows large: comparisons across rows dominate. One can sample or use rolling-hash techniques to reject unequal pairs quickly (hash per column across rows), then only run full checks for hash-matches. Use caution: hashes require collision handling.
- If both N and M are huge: consider approximate or streaming approaches, or limit to special cases (e.g., small alphabet) to exploit bit-parallelism.

## Examples / Step-by-step

- Example 1:

  A = ["cba",
       "daf",
       "ghi"]

  M = 3 columns indexed 0,1,2.

  Pair checks:
  - (0,1): rows compare {'c' <= 'b'? no} -> fails (so 0 cannot precede 1).
  - (0,2): {'c' <= 'a'? no} -> fails.
  - (1,2): check rows: 'b' <= 'a'? no -> fails.

  No two columns can be kept together, so best chain length L = 1. Deletions = M - L = 3 - 1 = 2.

  Expected answer: 2.

- Example 2:

  A = ["aab",
       "aac",
       "aaz"]

  Compare columns:
  - (0,1): for all rows, col0 <= col1 ('a' <= 'a') -> ok
  - (0,2): 'a' <= 'b' / 'a' <= 'c' / 'a' <= 'z' -> ok
  - (1,2): 'a' <= 'b' / 'a' <= 'c' / 'a' <= 'z' -> ok

  dp progression (one valid chain is 0->1->2): dp = [1, 2, 3]. L=3, deletions = 3-3=0.

- Example 3 (illustrating DP choices):

  A = ["dbc",
       "dac",
       "dbc"]

  Columns 0,1,2 comparisons may allow chains like 0->2 but not 0->1. DP finds the maximum chain, say L = 2, so deletions = M - 2.

## Final conclusion & comparison of methods

- **Bruteforce:** Try all subsets. Time: exponential `O(2^M * N * M)`. Use only for tiny M or to validate small tests.
- **DP (O(N * M^2), space O(M) or O(M^2) with caching):** Reliable, easy to implement, and the typical accepted solution. Works by finding longest compatible sequence of columns (LIS-style) where compatibility is defined rowwise. Best general-purpose solution when M is moderate (hundreds) and N is small/moderate.
- **Optimized DP with bitsets / hashing / caching:** Same DP idea but with faster pair checks. Improves constants and can handle larger N and/or M in practice. Recommended when N is large or many pair checks are repeated.

- Recommendation: implement the DP with early failure checks and, if needed for performance, add caching of pair checks (`ok[i][j]`) or a bitset-based comparison to reduce per-pair cost. The answer is `M - L` where `L = max(dp[j])`.

## Snippet (pseudo-code)

```c
let M = length of strings (columns), N = number of strings (rows)
dp = array of size M, filled with 1
for j in 0..M-1:
  for i in 0..j-1:
    if column_i_can_precede_column_j():
      dp[j] = max(dp[j], dp[i] + 1)
answer = M - max(dp)
```

Where `column_i_can_precede_column_j()` checks for all rows r: `A[r][i] <= A[r][j]` with early termination on first failure.

---
