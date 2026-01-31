# How Why Explanation - 744. Find Smallest Letter Greater Than Target

## Problem

Given a sorted circular array of lowercase letters `letters` (wrapping from end to start), return the smallest letter strictly greater than `target`. If no letter is greater, wrap and return the first element.

## Intuition

In a sorted array, the first element greater than `target` can be found with binary search. If none exists, the wrap rule says to return the first element.

## Brute Force (Not Used)

- Scan linearly until a letter `> target` is found; if none, return first element.
- Complexity: $O(n)$ time, $O(1)$ space.

## Approach (Binary Search for Upper Bound)

1. Initialize `low = 0`, `high = n-1`, `pos = -1`.
2. Standard upper-bound search: if `letters[mid] > target`, record `pos = mid` and move left (`high = mid-1`); else move right (`low = mid+1`).
3. After the loop, if `pos == -1`, no greater letter exists — return `letters[0]` (wrap). Otherwise return `letters[pos]`.

Why it works: binary search finds the leftmost element greater than `target` in $O(\log n)$; the wrap rule is a simple fallback.

## Complexity

- Time: $O(\log n)$.
- Space: $O(1)$.

## Optimality

You need at least $\Omega(\log n)$ comparisons to locate the boundary in a sorted array without extra structure. This matches the lower bound and improves on linear scan for large `n`.

## Edge Cases

- `target` smaller than all letters -> returns first element.
- `target` >= last element or equal to/greater than every letter -> wrap to first element.
- Duplicate letters are fine; we still want the first position strictly greater.
- Single-element array -> always returns that element (wrap).

## Comparison Table

| Aspect | Binary search (Solution) | Linear scan (Solution2) |
| --- | --- | --- |
| Time | $O(\log n)$ | $O(n)$ |
| Space | $O(1)$ | $O(1)$ |
| Best for | Large `n` | Tiny `n` / simplicity |

## Key Snippet (Java)

```java
int low = 0, high = letters.length - 1, pos = -1;
while (low <= high) {
	int mid = (low + high) / 2;
	if (letters[mid] > target) {
		pos = mid;
		high = mid - 1;
	} else {
		low = mid + 1;
	}
}
return pos == -1 ? letters[0] : letters[pos];
```

## Example Walkthrough

Input: `letters = ['c','f','j']`, `target = 'd'`

- Binary search finds first greater than 'd' at index 1 ('f').
- Return 'f'. If `target = 'j'`, no greater letter, so wrap to 'c'.

## Insights

- This is an upper-bound query; the wrap rule is equivalent to returning `letters[low % n]` after standard binary search.
- Using strictly greater means equals do not satisfy; duplicates require finding the leftmost greater.

## References

- Solution implementation in [744. Find Smallest Letter Greater Than Target/Solution.java](744.%20Find%20Smallest%20Letter%20Greater%20Than%20Target/Solution.java)
