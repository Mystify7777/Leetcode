# 2906. Construct Product Matrix - How Why Explanation

## Goal

Return a matrix where each cell `(i,j)` is the product of all other cells in `grid`, modulo `12345`.

## Idea in 3 lines

- Treat the `m x n` grid as a flattened array; each output cell needs product of all elements except its own.
- Precompute prefix products and suffix products modulo `12345`; the result for position `k` is `prefix[k] * suffix[k] % MOD`.
- Build these in one forward pass and one backward pass for O(mn) time and O(mn) or O(1) extra (besides output).

## Algorithm (matches `Solution`)

1. Flatten grid row-major into `a[0..size-1]` where `size = m*n`.
2. Build prefix array `p`: `p[0]=1`, `p[i]=p[i-1]*a[i-1] % MOD`.
3. Build suffix array `s`: `s[last]=1`, `s[i]=a[i+1]*s[i+1] % MOD`.
4. For each index `k`, the desired product excluding `a[k]` is `(p[k]*s[k]) % MOD`; write back into the grid-shaped answer.
5. Return the filled matrix.

## Alternative (matches `Solution2`)

- Skip explicit flattening: two sweeps over the grid in reverse then forward, carrying `suffix` then `prefix` multiplicatively and storing intermediate products directly into output.

## Why it works

- Product of all elements except `a[k]` equals product of elements before `k` times product of elements after `k`; prefix/suffix capture these in O(1) per index.
- Modular arithmetic keeps values bounded; multiplication distributes over modulo, so results remain correct.
- Two linear passes cover all positions exactly once.

## Complexity

- Time: O(m * n).
- Space: O(m * n) for explicit arrays in the flattened version; O(1) extra with rolling prefix/suffix in the two-pass in-place variant.
