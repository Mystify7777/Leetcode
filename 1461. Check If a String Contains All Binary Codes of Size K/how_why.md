# 1461. Check If a String Contains All Binary Codes of Size K — How/Why

## Problem

Determine whether a binary string `s` contains every possible binary code of length `k` as a substring. There are $2^k$ such codes.

## Intuition

Use a sliding window of length `k` and encode the window as a rolling bitmask. Mark which masks have appeared; once all $2^k$ masks are seen, return `true` early.

## Approach

- Quick impossibility: if `s.length() < k` or the number of windows `n - k + 1` is smaller than $2^k$, return `false`.
- Let `need = 1 << k`, `allOnes = need - 1`. Maintain `hash` as the bitmask of the current window.
- Slide across `s`:
	- Update `hash = ((hash << 1) & allOnes) | currentBit` to drop the leftmost bit and add the new bit.
	- After reaching index `k - 1`, mark `seen[hash]` if new; increment a `count` of distinct codes.
	- If `count == need`, return `true` early.
- If the scan ends without collecting all codes, return `false`.

## Correctness

- The rolling mask always represents the last `k` bits seen because shifting left and masking by `allOnes` discards bits older than `k` and appends the current bit.
- Every length-`k` substring corresponds to exactly one window position during the scan; each window’s mask is recorded in `seen`.
- `count` increases only when encountering a new code, so when `count` reaches `need`, every possible length-`k` binary code has been observed. If `count` never reaches `need`, at least one code is missing, so returning `false` is correct.

## Complexity

- Time: $O(n)$ over the string length `n`.
- Space: $O(2^k)` for the boolean `seen` array.

## Edge Cases

- `n < k` or `n - k + 1 < 2^k`: impossible to contain all codes.
- `k = 1`: need both `0` and `1` to appear.
- Repeated patterns: early-exit when all codes are found prevents unnecessary scanning.

## Alternatives

- Substring + `HashSet<String>`: simpler but slower and higher memory due to string allocations (see `Solution`).
- Rolling mask with `HashSet<Integer>` instead of boolean array (see `Solution2`): similar logic, slightly more overhead than the boolean array approach.

## Key Code

- Rolling bitmask with boolean tracking: [1461. Check If a String Contains All Binary Codes of Size K/Solution.java](1461.%20Check%20If%20a%20String%20Contains%20All%20Binary%20Codes%20of%20Size%20K/Solution.java#L30-L55)
