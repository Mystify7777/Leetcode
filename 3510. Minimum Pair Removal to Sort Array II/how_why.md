
# How & Why: LeetCode 3510 - Minimum Pair Removal to Sort Array II

## Problem

You may repeatedly merge an adjacent pair in the array by replacing it with their sum (removing one element). Return the minimum number of such operations needed to make the array non-decreasing.

## Intuition

- Each merge shrinks the array and may fix inversions. A greedy that always merges the smallest-sum adjacent pair (Problem I) isn’t enough here; we need to minimize operations globally.
- Observations: Only adjacent inversions matter. Merging a pair affects at most its neighboring edges. A priority queue over adjacent pairs, ordered by their sum (and left index tie-break), lets us try the “cheapest” merge first while maintaining adjacency with a linked structure.

## Brute Force Approach

- **Idea:** Try all merge sequences (state search) to find minimal operations.
- **Complexity:** Exponential in `n`; infeasible.

## My Approach (Heap + Doubly Linked List for Adjacent Pairs) — from Solution.java

- **Idea:**
	1) Build a doubly linked list of nodes (to support O(1) neighbor updates) and compute initial inversions between neighbors.
	2) Push every adjacent pair into a min-heap keyed by pair sum (tie by left index).
	3) While inversions remain: pop the smallest-sum valid adjacent pair (lazy-skip stale/removed). Merge them into a new node (sum, leftmost index), relink neighbors, update inversion count for affected edges, and push new adjacent pairs involving the merged node.
	4) Count merges performed; once inversions == 0, the array is non-decreasing.
- **Complexity:** Each merge does O(log n) heap ops; up to O(n) merges → O(n log n) time. Space O(n) for nodes, prev/next, and heap.
- **Core snippet:**

```java
// pop until a valid adjacent pair
while (inv > 0) {
	Pair p = pq.poll();
	if (p == null) break;
	Node L = p.left, R = p.right;
	if (L.removed || R.removed || L.next != R) continue; // stale
	inv -= invEdge(L.prev, L) + invEdge(L, R) + invEdge(R, R.next);
	Node merged = new Node(L.val + R.val, L.idx);
	// relink prev <-> merged <-> next
	// mark L,R removed
	inv += invEdge(merged.prev, merged) + invEdge(merged, merged.next);
	// push new adjacent pairs
	ops++;
	if (inv == 0) return ops;
}
```

## Most Optimal Approach

- The heap + DLL approach achieves O(n log n) and is optimal for this strategy. A balanced-tree of pairs would be similar; a segment tree over pairs could also work but adds complexity without asymptotic gain.

## Edge Cases

- Already non-decreasing → 0 ops (inversions==0 initially).
- Length 1 → 0 ops.
- Duplicate values handled; inversions defined by `>` only.
- Large sums: store as `long` to avoid overflow when merging.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Exhaustive search | Explore all merge orders | Exponential | Exponential | Optimal but impossible |
| Greedy min-sum (Problem I) | Always merge smallest sum | O(n²) | O(1) | Not guaranteed minimal ops here |
| Heap + DLL (used) | Merge cheapest valid pair, update inversions | O(n log n) | O(n) | Efficient and correct |

## Example Walkthrough (conceptual)

- Start with inversions counted between neighbors; heap seeded with all pair sums.
- Pop smallest pair that is still adjacent; merge, relink, update inversions, push new neighbor pairs.
- Repeat until inversions = 0; merges performed = answer.

## Insights

- Only local adjacencies matter; lazy skipping of stale heap entries keeps the heap small without expensive deletions.
- Tracking inversion count lets us stop early once the list is sorted.

## References to Similar Problems

- 3507. Minimum Pair Removal to Sort Array I (simpler greedy variant)
- Data-structure patterns: “lazy heap with removed markers” + linked list for dynamic adjacency
