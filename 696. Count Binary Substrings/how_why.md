# How Why Explanation - 696. Count Binary Substrings

## Problem

Given a binary string `s`, count the non-empty substrings with the same number of consecutive `0`s and `1`s, and all the `0`s and all the `1`s within the substring are grouped together (i.e., substrings like `000111` or `01`). Return the total count.

## Intuition

Every valid substring consists of a run of identical bits followed by a run of the other bit, and its length is twice the smaller of the two run lengths. If we know the lengths of consecutive runs, each adjacent pair contributes `min(run[i], run[i+1])` valid substrings.

## Brute Force (Not Used)

- Enumerate all substrings and test if they are grouped and balanced. $O(n^2)$ time.

## Approach (Run-length compression)

1. Scan `s` to accumulate the length of the current run `curr`.
2. When the bit flips, add `min(prev, curr)` to the answer, set `prev = curr`, and reset `curr = 1`.
3. After the loop, add `min(prev, curr)` for the last pair.
4. Return the accumulated answer.

Why it works: For two adjacent runs of lengths `a` and `b`, there are `min(a, b)` substrings centered at their boundary that are balanced (take 1..min(a,b) chars from each run). Summing this over all boundaries yields the total.

## Complexity

- Time: $O(n)$ single pass.
- Space: $O(1)$.

## Optimality

Linear time and constant space are optimal; every character must be inspected.

## Edge Cases

- String length 1 → answer 0.
- All identical bits → answer 0.
- Alternating bits (e.g., `1010`) → each boundary contributes 1; answer equals `n-1`.

## Comparison Table

| Aspect | Run-length pair sum (Solution) | Boundary expansion (Solution2) |
| --- | --- | --- |
| Idea | Sum `min(prev, curr)` per run boundary | Expand around boundaries, track left start |
| Time | $O(n)$ | $O(n)$ |
| Space | $O(1)$ | $O(1)$ |
| Readability | Straightforward | Trickier index handling |

## Key Snippet (Java)

```java
int curr = 1, prev = 0, ans = 0;
for (int i = 1; i < s.length(); i++) {
	if (s.charAt(i) == s.charAt(i - 1)) curr++;
	else {
		ans += Math.min(curr, prev);
		prev = curr;
		curr = 1;
	}
}
ans += Math.min(curr, prev);
return ans;
```

## Example Walkthrough

`s = "00110011"`

- Runs: 2,2,2,2 → contributions: min(2,2)=2 four times → total 6.
- Valid substrings include `00|11`, `0|1`, `11|00`, etc.; count matches 6.

## Insights

- Balanced substrings are fully determined by adjacent run lengths; no need to explore all substrings.
- Adding after the loop captures the final boundary without a trailing flip.

## References

- Solution implementation in [696. Count Binary Substrings/Solution.java](696.%20Count%20Binary%20Substrings/Solution.java)
