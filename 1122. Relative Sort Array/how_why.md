
# 1122. Relative Sort Array — how/why

## Recap

Given two arrays `arr1` and `arr2` where:

- All elements of `arr2` are distinct.
- Every element of `arr2` appears in `arr1`.

Sort `arr1` so that:

1) Numbers that appear in `arr2` follow the exact order of `arr2`.
2) Numbers not in `arr2` are placed at the end in ascending order.
Return the reordered `arr1`.

## Intuition

We only need two ordering rules: the fixed `arr2` order first, then ascending for the rest. This is a perfect fit for **counting**:

- Count how many times each value appears in `arr1`.
- Emit counts following `arr2` for the “priority” part.
- Emit remaining values in ascending order using the same counts.
Counting avoids custom comparators and repeatedly scanning the arrays.

## Approach (Counting Array)

1) Build `freq[0..1000]` (constraints allow this) by scanning `arr1` once.
2) For each `val` in `arr2`, output it `freq[val]` times and zero that count.
3) For `v` from `0` to `1000`, output `v` `freq[v]` times (these are the leftovers, naturally ascending).
4) Return the filled array.

Why it works: `arr2` order is preserved because we emit in the given sequence; leftovers are emitted in numeric order because we iterate the counts increasing.

## Code (Java, counting sort)

```java
class Solution {
	public int[] relativeSortArray(int[] arr1, int[] arr2) {
		int[] freq = new int[1001];
		for (int v : arr1) freq[v]++;

		int idx = 0;
		for (int v : arr2) {
			while (freq[v]-- > 0) arr1[idx++] = v;
		}

		for (int v = 0; v < freq.length; v++) {
			while (freq[v]-- > 0) arr1[idx++] = v;
		}

		return arr1;
	}
}
```

## Correctness

- For every `val` in `arr2`, we place all its occurrences consecutively before any value not in `arr2`, matching the required priority order.
- After zeroing used counts, leftover positive counts correspond exactly to values absent from `arr2`; iterating `v` from small to large appends them sorted ascending.
- No occurrences are lost: we decrement `freq` exactly once per emitted element until all initial counts are consumed.

## Complexity

- Time: `O(n + R)` where `n = |arr1|`, `R = 1001` (fixed upper bound). Effectively linear in input size.
- Space: `O(R)` for the frequency array (constant-sized under constraints).

## Edge Cases

- All elements of `arr1` are in `arr2`: second pass over leftovers appends nothing.
- Elements in `arr1` but not in `arr2`: they are added sorted in the leftover pass.
- Duplicates in `arr1`: handled naturally by counts.
- Minimum/maximum values (0 or 1000): within frequency array bounds.

## Alternative (Comparator + rank map)

If value range were large/unknown, use a rank map for custom sort:

```java
class Solution {
	public int[] relativeSortArray(int[] arr1, int[] arr2) {
		Map<Integer, Integer> rank = new HashMap<>();
		for (int i = 0; i < arr2.length; i++) rank.put(arr2[i], i);

		Integer[] boxed = Arrays.stream(arr1).boxed().toArray(Integer[]::new);
		Arrays.sort(boxed, (a, b) -> {
			int ra = rank.getOrDefault(a, Integer.MAX_VALUE);
			int rb = rank.getOrDefault(b, Integer.MAX_VALUE);
			if (ra != rb) return ra - rb;          // priority by arr2 order
			return a - b;                          // natural order for leftovers
		});

		for (int i = 0; i < boxed.length; i++) arr1[i] = boxed[i];
		return arr1;
	}
}
```

Trade-off: `O(n log n)` time from sorting, but works when values are not bounded for counting.
