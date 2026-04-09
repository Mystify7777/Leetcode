# 3655. XOR After Range Multiplication Queries II - How Why Explanation

## Goal

Apply range-multiplication queries on `nums` where each query updates indices
`l, l+k, l+2k, ... <= r` by multiplying with `v` modulo `1e9+7`, then return XOR of final array.

## Idea in 3 lines

- Split queries by step size `k` using a threshold around `sqrt(n)`.
- Large `k` queries touch few elements, so apply them directly.
- Small `k` queries are grouped by same `k` and processed together using multiplicative difference + prefix propagation with modular inverses.

## Algorithm (matches `Solution.java`)

1. Let `limit = floor(sqrt(n))`.
2. For each query `[l, r, k, v]`:
	 - If `k >= limit`, update `nums[i]` directly for `i = l, l+k, ... <= r`.
	 - Else, store query in a bucket keyed by `k`.
3. For each small-`k` bucket:
	 - Create `diff[]` initialized to multiplicative identity `1`.
	 - For query `[l, r, k, v]`:
		 - Apply start marker: `diff[l] *= v`.
		 - Find first index after the query progression:
			 `next = l + (((r-l)/k) + 1) * k`.
		 - If `next < n`, apply cancel marker: `diff[next] *= inv(v)`.
	 - Sweep `i = 0..n-1`:
		 - If `i >= k`, propagate: `diff[i] *= diff[i-k]`.
		 - Apply to array: `nums[i] *= diff[i]`.
4. XOR all final `nums[i]` and return.

## Why it works

- For fixed small `k`, indices with same modulo class form arithmetic chains spaced by `k`.
- Marking `v` at `l` and `inv(v)` at first out-of-range index makes multiplicative prefix along that chain apply `v` exactly on intended positions.
- Grouping by `k` allows one linear propagation per `k` bucket instead of replaying every query position-by-position.
- Large `k` has small per-query work (`~(r-l)/k + 1`), so direct processing is efficient.

## Complexity

- Let `B = sqrt(n)`.
- Large-`k` part: roughly `O(q * B)` in worst-case aggregate.
- Small-`k` part: for each distinct `k < B`, one `O(n)` sweep plus query markers.
- Overall typical complexity is near `O((n + q) * sqrt(n))` with modulo arithmetic.
- Space: `O(n)` for `diff` during a bucket and `O(q)` for grouped queries.

## Utilities

- `power(base, exp)` uses fast exponentiation.
- `modInv(v) = v^(MOD-2) mod MOD` (Fermat) because `MOD` is prime.
