# 1578. Minimum Time to Make Rope Colorful — how and why

## Problem recap

You have a rope with `n` balloons, where each balloon has a color (represented by a character) and a removal cost. You cannot have two consecutive balloons of the same color. When you find consecutive same-colored balloons, you must remove some of them until no two consecutive balloons have the same color. Find the minimum total cost to achieve this.

For example, if the rope is `"aabaa"` with costs `[1, 2, 3, 4, 1]`:

- The first two `'a'` balloons cost 1 and 2 → remove the cheaper one (cost 1)
- The last two `'a'` balloons cost 4 and 1 → remove the cheaper one (cost 1)
- Total minimum cost = 1 + 1 = 2

## Core intuition

When you encounter consecutive balloons of the same color, you must remove all but one to avoid adjacency. The optimal strategy is to **keep the most expensive one** and remove all the others, minimizing the total removal cost.

For any group of consecutive same-colored balloons, the minimum cost to fix that group is:

> **Total cost of the group - Maximum cost in the group**

This is because you keep the balloon with the maximum cost and remove the rest.

## Approach 1 — pairwise greedy (your current solution)

Instead of identifying full groups upfront, process pairs as you scan left to right:

1. Compare each balloon with its predecessor
2. If they match colors, remove the cheaper one and add its cost to the result
3. Update the current position's cost to be the maximum (as if we "kept" the more expensive one)
4. Continue scanning

This works because when you have a run like `aaa` with costs `[5, 3, 7]`:

- Compare positions 0-1: remove cost 3, keep 5 (result += 3, cost[1] = 5)
- Compare positions 1-2: remove cost 5, keep 7 (result += 5, cost[2] = 7)
- Total: 3 + 5 = 8 (which equals 5 + 3 + 7 - 7)

### Implementation (matches `Solution.java`)

```java
class Solution {
    public int minCost(String s, int[] cost) {
        int n = s.length();
        int result = 0;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                result += Math.min(cost[i], cost[i - 1]);
                cost[i] = Math.max(cost[i], cost[i - 1]);
            }
        }
        return result;
    }
}
```

**Why is this approach fast?**

This is fast because:

- **Single pass:** O(n) time with just one loop
- **In-place:** Updates the `cost` array directly without extra data structures
- **Minimal operations:** Only compares adjacent elements and does simple min/max calculations
- **No nested loops:** Processes each element exactly once

The key insight is that by updating `cost[i]` to the maximum, you're "carrying forward" the best balloon to keep, making the next comparison correct even if the run continues.

## Approach 2 — group-based sweep (alternative in comments)

The commented alternative explicitly finds each group of consecutive same-colored balloons and calculates (total - max) for each group:

```java
class Solution {
    public int minCost(String colors, int[] neededTime) {
        char[] arr = colors.toCharArray();
        int res = 0;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                // Found the start of a group
                int total = neededTime[i - 1];
                int max = neededTime[i - 1];
                
                // Extend the group while colors match
                while (i < arr.length && arr[i] == arr[i - 1]) {
                    total += neededTime[i];
                    max = Math.max(max, neededTime[i]);
                    i++;
                }
                
                // Add cost: remove all except the most expensive
                res += total - max;
            }
        }
        return res;
    }
}
```

**Differences from Approach 1:**

| Aspect | Pairwise Greedy (Approach 1) | Group-based Sweep (Approach 2) |
|--------|------------------------------|--------------------------------|
| **Logic** | Process pairs, update cost array | Identify full groups, calculate total - max |
| **Space** | Modifies input array | Reads input only |
| **Clarity** | More subtle (requires understanding the carry-forward trick) | More explicit (clear group processing) |
| **Performance** | Slightly faster (fewer operations per element) | Slightly more work (inner while loop) |
| **Edge cases** | Naturally handles single-pair groups | Explicit group boundary handling |

Both are O(n) time, but Approach 1 is marginally faster due to simpler per-element operations.

## Why this works

**Mathematical equivalence:**

For any consecutive group of k same-colored balloons with costs `[c₁, c₂, ..., cₖ]`, the minimum removal cost is:

sum(c₁ to cₖ) - max(c₁ to cₖ)

This is because you must keep exactly one balloon (the most expensive to minimize removals) and remove the other k-1.

**Pairwise processing:**

When processing pairs and updating costs:

- Each removal cost is added exactly once
- The "carried forward" maximum ensures subsequent comparisons are against the best balloon seen so far in the run
- The final result equals the sum of all removed balloons

## Complexity

- **Time:** O(n) — single pass through the string/array
- **Space:** O(1) — only constant extra variables (Approach 1 modifies input; Approach 2 uses char array conversion which is O(n) but not additional)

## Example walkthrough

Suppose `colors = "aaabbb"` and `neededTime = [3, 5, 1, 2, 4, 9]`.

### Using Approach 1 (pairwise)

- i=1: `'a'=='a'` → result += min(3,5)=3, cost[1]=max(3,5)=5
- i=2: `'a'=='a'` → result += min(5,1)=1, cost[2]=max(5,1)=5
- i=3: `'b'!='a'` → skip
- i=4: `'b'=='b'` → result += min(2,4)=2, cost[4]=max(2,4)=4
- i=5: `'b'=='b'` → result += min(4,9)=4, cost[5]=max(4,9)=9
- Total: 3+1+2+4 = 10

### Using Approach 2 (group-based)

- Group 1 (indices 0-2, 'a'): total=3+5+1=9, max=5 → cost = 9-5 = 4
- Group 2 (indices 3-5, 'b'): total=2+4+9=15, max=9 → cost = 15-9 = 6
- Total: 4+6 = 10 ✓

Both approaches yield the same correct answer.

## Edge cases to consider

- No consecutive duplicates: result is 0
- All balloons same color: remove all except the most expensive
- Only two balloons: if same color, remove the cheaper one
- Single balloon: result is 0
- Alternating colors: result is 0

## Takeaways

- When removing elements to break consecutive duplicates, always keep the most expensive to minimize cost
- Pairwise greedy with cost update is a clever optimization that processes groups implicitly
- The "carry forward" trick (updating cost[i] to max) ensures correctness for runs of length > 2
- Both explicit group processing and implicit pairwise processing are valid O(n) solutions
- The pairwise approach is slightly faster due to fewer operations per element
