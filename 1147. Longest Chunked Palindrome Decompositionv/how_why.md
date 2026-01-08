# 1147. Longest Chunked Palindrome Decomposition — How & Why

## Problem

- Split a string into the maximum number of non-empty chunks so that reading chunks from left to right equals reading chunks from right to left.
- Formally, for a decomposition `S = C1 C2 ... Ck`, it must hold that `C1 == Ck`, `C2 == C(k-1)`, etc.

## Requirements (in plain terms)

- Chunks must be contiguous and non-empty.
- The concatenation of all chunks must be the original string (no reordering).
- You want as many chunks as possible while keeping the “mirror equality” property.

## Simple Intuition

- Start from both ends and look for the smallest prefix that matches the corresponding suffix. When you find such a matching pair, you can “peel” them off as two valid chunks and repeat the same logic on the remaining middle.
- If you can’t find any prefix/suffix match, the entire remaining middle is just one chunk.

## Approach in This Repo

- The solution scans prefix lengths `i = 0..n/2` and compares `text[0..i]` with `text[n-1-i..n-1]`.
	- On the first match, it returns `2 + longestDecomposition(text[i+1..n-2-i])` (count 2 chunks and recurse on the middle).
	- If no prefix equals the mirrored suffix, return `1` if the string is non-empty (the whole string is one chunk), or `0` if empty.

See code: [1147. Longest Chunked Palindrome Decompositionv/Solution.java](1147.%20Longest%20Chunked%20Palindrome%20Decompositionv/Solution.java)

## Why This Greedy Works

- Taking the shortest possible matching prefix/suffix pair maximizes the number of chunks: any longer pair would consume more characters per step and reduce opportunities to form additional chunks.
- Recursing on the middle preserves the same problem structure.

## Complexity

- Time: Worst-case about `O(n^2)` due to substring comparisons at each recursion level.
- Space: `O(k)` recursion depth where `k` is the number of chunk pairs (plus string slicing overhead depending on the language/runtime).

## Edge Cases

- Entire string has no matching prefix/suffix → result is `1` (single chunk).
- Single-character string → `1`.
- Already a palindrome with repeating patterns → multiple short chunks.

## Optional Optimization Ideas

- Build prefix and suffix incrementally (without creating new substrings each time) and compare on the fly.
- Use rolling hashes on prefixes/suffixes to reduce comparisons to `O(1)` on matches (careful with collisions) and achieve near `O(n)` time.

## Example Walkthroughs

1) `text = "volvo"`

- Try shortest prefix/suffix matches:
	- `"v"` vs `"o"` → no
	- `"vo"` vs `"vo"` → yes
- Peel these off as two chunks: `"vo"` and `"vo"`; recurse on the middle `"l"`.
- Middle has no further match → one chunk `"l"`.
- Final decomposition: `"vo" | "l" | "vo"` → answer `3`.

2) `text = "ghiabcdefhelloadamhelloabcdefghi"`

- Scan from both ends for shortest matching prefix/suffix:
	- `"g"` vs `"i"` → no
	- … continue …
	- `"ghi"` vs `"ghi"` → yes → take `"ghi"` (left) and `"ghi"` (right)
- Recurse on middle: `"abcdefhelloadamhelloabcdef"`
	- Find `"abcdef"` vs `"abcdef"` → take both
	- Recurse on `"helloadamhello"`
		- Find `"hello"` vs `"hello"` → take both
		- Recurse on `"adam"` → no match → one chunk
- Final decomposition: `"ghi" | "abcdef" | "hello" | "adam" | "hello" | "abcdef" | "ghi"` → answer `7`.
