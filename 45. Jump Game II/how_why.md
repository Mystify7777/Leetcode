
# How & Why: LeetCode 45 - Jump Game II

## Problem

Given an array `nums` where `nums[i]` is the max jump length from index `i`, return the minimum number of jumps needed to reach the last index (start at index 0). It’s guaranteed reachable.

## Intuition

- This is a shortest-steps-to-end problem, but weights are uniform (each jump counts 1). Greedy layer-by-layer (BFS over indices) works: from the current reachable window, compute the furthest next reach. Each window expansion equals one jump.

## Brute Force Approach

- **Idea:** DFS/recursion from index 0 exploring all jump lengths, take min.
- **Complexity:** Exponential; impractical.

## My Approach (Greedy BFS on Range) — from Solution.java

- **Idea:** Maintain current layer `[near, far]` of reachable indices with the current jump count. In one jump, you can reach up to `farthest = max(i + nums[i])` for `i` in `[near, far]`. Advance to next layer and increment jumps until `far` crosses last index.
- **Complexity:** Time $O(n)$, Space $O(1)$.
- **Core snippet:**

```java
int near=0, far=0, jumps=0;
while (far < n-1) {
	int nxt=0;
	for (int i=near; i<=far; i++) nxt = Math.max(nxt, i + nums[i]);
	near = far + 1;
	far = nxt;
	jumps++;
}
return jumps;
```

## Most Optimal Approach

- The greedy range-BFS is optimal at $O(n)$ time, $O(1)$ space. Another common variant tracks `currentEnd` and `farthest` in one pass (without explicit near pointer) with same complexity.

## Edge Cases

- Single element array → 0 jumps.
- Large jump at start can reach end in 1.
- Arrays with many zeros still reachable per guarantee; algorithm naturally handles as long as end lies within reach.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| DFS / recursion | Explore all jump choices | Exponential | O(n) stack | Not feasible |
| DP from end | Min jumps via backwards DP | O(n^2) | O(n) | Slower than greedy |
| Greedy range BFS (used) | Expand reachable window per jump | O(n) | O(1) | Optimal |

## Example Walkthrough

`nums = [2,3,1,1,4]`

- Start window [0,0], jumps=0 → farthest from 0 is 2 → next window [1,2], jumps=1.
- From [1,2], farthest = max(1+3, 2+1)=4 → window [3,4], jumps=2. `far>=last` → answer 2.

## Insights

- Treat reachable indices per jump as a layer; one sweep per layer is enough to guarantee minimal jumps because each jump has unit cost.

## References to Similar Problems

- 55. Jump Game (reachability variant)
- 1029. Two City Scheduling (different greedy pattern but similar optimality reasoning)
