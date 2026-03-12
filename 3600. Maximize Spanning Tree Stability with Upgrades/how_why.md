# How Why Explanation - 3600. Maximize Spanning Tree Stability with Upgrades

## Problem

Given `n` nodes and edges `edges[i] = [u, v, strength, mandatory]` where `mandatory` is 1 for forced edges and 0 for optional, build a spanning tree. You may “upgrade” at most `k` optional edges, doubling their strength. The *stability* of a tree is the minimum edge strength in the tree. Return the maximum achievable stability; if the mandatory edges already form a cycle (no spanning tree), return `-1`.

## Intuition

Fix a target stability `x`. An edge can support `x` if:

- mandatory edge: strength must be `>= x` (cannot upgrade, must be used if connects new components),
- optional edge: either strength `>= x` (use as-is) or `2 * strength >= x` if we spend an upgrade.

So feasibility for a given `x` is a connectivity question with a budget of at most `k` upgraded optional edges. This suggests binary searching the answer over possible `x` and testing feasibility with DSU.

## Approach (binary search on stability + DSU feasibility)

1. **Early invalid check:** If mandatory edges themselves create a cycle, no tree exists → return `-1` (see [Solution.java](3600.%20Maximize%20Spanning%20Tree%20Stability%20with%20Upgrades/Solution.java#L43-L54)).
2. **Binary search `x`** between 1 and max possible strength (200000 here).
3. **Feasibility `canAchieve(x)`** ([Solution.java](3600.%20Maximize%20Spanning%20Tree%20Stability%20with%20Upgrades/Solution.java#L6-L40)):
    - Start DSU.
    - Add all mandatory edges; if any strength < `x` or forms a cycle, fail.
    - Add optional edges with `strength >= x` for free.
    - Try to connect remaining components using optional edges that need an upgrade (`strength < x` but `2*strength >= x`), counting upgrades. If upgrades used exceed `k`, fail.
    - Success if DSU ends with one component.
4. Binary search keeps the best feasible `x`.

## Complexity

- Feasibility check: O(E α(V)). Binary search over strength range (log 2e5) → overall O(E log W).
- Space: O(V) for DSU.

## Edge Cases

- Mandatory cycle → immediately `-1`.
- Disconnected even after all eligible/upgradeable edges → infeasible for that `x`.
- `k = 0` means only edges with strength `>= x` can connect components.
- Very strong optional edges may make upgrades unnecessary.

## Alternate Approaches

- The second class shows an MST-like greedy on sorted optional edges with ad-hoc handling of upgrades; binary search + feasibility is clearer and guarantees correctness.
