# How Why Explanation - 3713. Longest Balanced Substring I

## Problem

Given a string `s` (lowercase letters), find the length of the longest substring where every distinct character appears the same number of times.

## Intuition

A substring is balanced if `maxFreq * distinct == length`. While expanding a window we can maintain counts, the number of distinct chars, and the current maximum frequency; a simple check tells us whether the window is balanced.

## Brute Force (Used)

- Enumerate all substrings `(l, r)`, maintain counts as you extend `r`, and test balance.
- With small constraints for this variant, $O(n^2)$ is acceptable.

## Approach (Quadratic expand with counts)

1. Convert chars to ints for quick indexing.
2. For each start `l`, early-break if remaining length cannot beat the best.
3. Use an array `cnt[26]` while extending `r`:
   - When a char count goes from 0→1, increment `uniq`.
   - Update `maxFreq` for the touched char.
   - The window length is `len = r - l + 1`; if `uniq * maxFreq == len`, window is balanced; update answer.
4. Return the maximum length found.

Why it works: In any window, if all distinct characters share the same frequency `f`, then `len = distinct * f` and `f` is the maximum frequency. The check is constant time with maintained `uniq` and `maxFreq`.

## Complexity

- Time: $O(n^2)$ due to the double loop.
- Space: $O(26)$ per start for counts.

## Optimality

For the I version, the quadratic approach is sufficient. More advanced balancing (e.g., bitmask + prefix) is unnecessary given limits.

## Edge Cases

- Single character string → answer 1.
- All identical → whole string balanced.
- No two chars can balance with different counts; the check enforces equality.

## Comparison Table

| Aspect | Count-array quadratic (Solution) |
| --- | --- |
| Time | $O(n^2)$ |
| Space | $O(1)$ (26) |
| Early exit | Break if remaining length ≤ best |

## Key Snippet (Java)

```java
int result = 0;
for (int l = 0; l < n; l++) {
	if (n - l <= result) break;
	int[] cnt = new int[26];
	int uniq = 0, maxfreq = 0;
	for (int r = l; r < n; r++) {
		int i = a[r];
		if (cnt[i] == 0) uniq++;
		cnt[i]++;
		maxfreq = Math.max(maxfreq, cnt[i]);
		int len = r - l + 1;
		if (uniq * maxfreq == len) result = Math.max(result, len);
	}
}
```

## Example Walkthrough

`s = "aabbccc"`

- Best balanced substring is `"bbccc"`? counts: b:2,c:3 not equal. Instead `"aabb"` (a:2,b:2) length 4 and `"bbcc"` length 4 are balanced. Answer = 4.

## Insights

- The balance condition reduces to one multiplication check once `uniq` and `maxFreq` are known.
- Early termination by remaining length prunes work in practice.

## References

- Solution implementation in [3713. Longest Balanced Substring I/Solution.java](3713.%20Longest%20Balanced%20Substring%20I/Solution.java)
