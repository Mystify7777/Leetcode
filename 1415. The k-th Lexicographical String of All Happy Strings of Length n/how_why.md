# How Why Explanation - 1415. The k-th Lexicographical String of All Happy Strings of Length n

## Problem

Generate all *happy strings* of length `n` over `{a,b,c}` (no two adjacent chars equal), sort them lexicographically, and return the `k`-th string (1-indexed); return empty string if `k` exceeds the count.

## Intuition

Each position offers at most two choices (cannot repeat previous char). The total number of happy strings is `3 * 2^(n-1)`. We can construct the `k`-th string directly using combinatorics: at any step, the number of strings for a chosen char is `2^(remaining-1)`. Walk lexicographic order, skipping whole blocks when `k` surpasses a block size.

## Approach (DFS with counting to skip blocks)

1. Keep a `prefix`. At each depth, iterate chars `'a'..'c'`.
2. Skip any char equal to the last char in `prefix`.
3. Let `cnt = 2^(n - prefix.length() - 1)` be the number of happy strings that start with `prefix + c` (since each further position has 2 choices).
4. If `k <= cnt`, pick `c` and recurse; else `k -= cnt` and continue to next char.
5. When length reaches `n`, return the built string. If we exhaust options, return empty.

Implementation in [1415. The k-th Lexicographical String of All Happy Strings of Length n/Solution.java](1415.%20The%20k-th%20Lexicographical%20String%20of%20All%20Happy%20Strings%20of%20Length%20n/Solution.java#L4-L22).

## Complexity

- Time: O(n) decisions; the loop over 3 chars per level is constant. Power computation can be cached; current code uses `Math.pow`.
- Space: O(n) recursion stack / prefix.

## Edge Cases

- `k > 3 * 2^(n-1)` → return "".
- `n = 1`: happy strings are `a,b,c`; pick kth if within 3.
- Large `k` steps over blocks cleanly; no need to generate all strings.

## Alternate Approaches

- **Iterative block-walk:** Build string iteratively using precomputed powers of 2 instead of recursion.
- **Backtracking enumeration:** Generate all happy strings then index; simple but exponential and unnecessary.
