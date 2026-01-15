
# How & Why: LeetCode 593 - Valid Square

## Problem

Given four points in 2D, determine if they can form a square (four equal sides, two equal diagonals, non-zero area).

## Intuition

- All pairwise distances of a square consist of exactly two unique positive lengths: side (appears 4 times) and diagonal (appears 2 times). Checking squared distances avoids floating error.

## Brute Force Approach

- **Idea:** Try all permutations of the 4 points as a quadrilateral, check side equality and right angles via dot products.
- **Complexity:** Time $O(4! )$ per check (~constant), but with more implementation overhead.

## My Approach (Distance Multiset) — from Solution.java

- **Idea:** Compute all 6 pairwise squared distances. Valid square iff set has exactly 2 distinct positive values (side and diagonal). The smaller one is the side (should appear 4 times), larger is diagonal (2 times), but checking set size > 0 suffices alongside non-zero.
- **Complexity:** Time $O(1)$, Space $O(1)$.
- **Core snippet:**

```java
Set<Integer> s = new HashSet<>();
s.add(d2(p1,p2)); s.add(d2(p1,p3)); s.add(d2(p1,p4));
s.add(d2(p2,p3)); s.add(d2(p2,p4)); s.add(d2(p3,p4));
return !s.contains(0) && s.size() == 2;
```

## Most Optimal Approach

- The distance-multiset check is already optimal and simple. Alternatively, verify counts (4 sides, 2 diagonals) explicitly for extra strictness, but same complexity.

## Edge Cases

- Duplicate points → yields distance 0; immediately invalid.
- All points collinear → distances collapse to 2 unique but one is 0 or angles fail; set test with non-zero prevents false positive.
- Large coordinates: use `long` for squared distance to avoid overflow.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Permute and check geometry | Fix order, check sides+angles | O(1) | O(1) | More code, same asymptotic |
| Pairwise distance set (used) | 6 distances → expect 2 positive uniques | O(1) | O(1) | Concise; add non-zero guard |

## Example Walkthrough

Points: `(0,0),(1,1),(1,0),(0,1)`

- Squared distances multiset: {1,1,1,1,2,2} → unique = {1,2}, none zero → valid square.

## Insights

- Distances alone (with non-zero) characterize a square up to rotation/translation; diagonals distinguish square from rhombus.

## References to Similar Problems

- 149. Max Points on a Line (geometry with integer arithmetic)
- 836. Rectangle Overlap (axis-aligned shape checks)
