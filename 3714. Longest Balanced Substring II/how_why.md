# How Why Explanation - 3714. Longest Balanced Substring II

## Problem

Given a string `s` consisting of `'a'`, `'b'`, and `'c'`, find the length of the longest **balanced** substring where all distinct characters present in the substring occur the same number of times. A balanced substring can contain 1, 2, or all 3 characters, but their counts must be equal among those present.

## Intuition

Balance for 1 char is just the longest run of that char. For 2 chars, it reduces to finding the longest substring with equal counts of those two letters while excluding the third. For all 3 chars, we need equal counts of `a`, `b`, and `c` simultaneously. We can solve each case in linear time and take the maximum.

## Brute Force (Not Used)

- Enumerate all substrings and test count equality; $O(n^2)$ time, $O(1)$ space.
- Too slow; we can do $O(n)$ per scenario with prefix-difference hashing.

## Approach (Three linear passes)

1. **Single-char runs**: Scan once to find the longest consecutive run of each char; keep the best.
2. **Two-char balance (`find2`)**: For each ordered pair `(x, y)` among `a,b,c`, scan while ignoring the third char by resetting state when it appears. Track `diff = count(x) - count(y)`. Using an offset array `first[diff]`, the longest segment with `diff=0` gives equal counts of `x` and `y` in that block. Repeat for all 3 pairs.
3. **Three-char balance (`find3`)**: Maintain a 2D encoded state of differences between counts (e.g., a linearized pair of diffs). When the state repeats at indices `i` and `j`, the substring `(i+1..j)` has equal counts of all three. Store earliest index per state in a map to get max length.
4. Take the maximum over all cases.

Why it works: Equal-count substrings correspond to repeated prefix-difference states. Resetting when a third character appears (in `find2`) isolates valid two-letter substrings. Hashing the 2D diff (in `find3`) captures equality of all three counts in one pass.

## Complexity

- Time: $O(n)$ overall (constant passes and constant alphabet).
- Space: $O(n)$ for maps/arrays of prefix states.

## Optimality

With fixed alphabet size, prefix-difference methods give linear time; this is asymptotically optimal since every character must be read.

## Edge Cases

- String of one letter: answer is its length (single-char case).
- Presence of only two letters: handled by two-char pass.
- Mixed but no equal-count substring longer than 1: result may be 1.

## Comparison Table

| Aspect | Provided solution |
| --- | --- |
| Single-char | Track max consecutive run |
| Two-char | Prefix diff with reset on third char |
| Three-char | Hashed 2D diff state |
| Time/Space | $O(n)$ / $O(n)$ |

## Key Snippet (Two-char balance)

```java
int[] first = new int[2 * n + 1];
Arrays.fill(first, -2);
int clear = -1, diff = n; // offset n
first[diff] = -1;
for (int i = 0; i < n; i++) {
	if (c[i] != x && c[i] != y) { // third char
		clear = i; diff = n; first[diff] = clear;
	} else {
		diff += (c[i] == x ? 1 : -1);
		if (first[diff] < clear) first[diff] = i;
		else max = Math.max(max, i - first[diff]);
	}
}
```

## Example Walkthrough

`s = "aaabbbccc"`

- Single-char runs: 3.
- Two-char equal counts: `"aaabbb"` length 6 for (a,b); similarly for (b,c).
- Three-char equal counts: whole string counts (3,3,3) → length 9 (best).

## Insights

- Equal-count substrings correspond to repeating prefix-difference vectors; hashing avoids nested loops.
- Resetting when a disallowed char appears lets a single pass handle “only two chars” segments cleanly.

## References

- Solution implementation in [3714. Longest Balanced Substring II/Solution.java](3714.%20Longest%20Balanced%20Substring%20II/Solution.java)
