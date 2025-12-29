# 213. House Robber II - Explanation

## Problem Understanding

This is an extension of the classic House Robber problem with a circular constraint: the houses are arranged in a **circle**, meaning the **first and last houses are adjacent**.

**Key Constraint:**

- You cannot rob both the first house AND the last house (they're neighbors in the circle)
- You must rob non-adjacent houses to avoid triggering alarms
- Goal: Maximize the total amount of money robbed

**Example:**

```m
Input: nums = [2,3,2]

Houses arranged in circle:
    2
   / \
  3   2  (first and last are neighbors!)

If rob house 0 (value=2): can't rob house 2 (value=2)
If rob house 2 (value=2): can't rob house 0 (value=2)
Best: rob house 1 (value=3)

Output: 3
```

## Key Insight

Since the first and last houses are neighbors, we have **3 scenarios**:

1. Rob the first house → cannot rob the last house
2. Rob the last house → cannot rob the first house  
3. Rob neither the first nor last house

However, we can simplify this to **2 cases**:

- **Case 1**: Consider houses [0...n-2] (exclude last house)
- **Case 2**: Consider houses [1...n-1] (exclude first house)

Why? Because:

- If we rob the first house, we naturally exclude the last (Case 1)
- If we rob the last house, we naturally exclude the first (Case 2)
- If we rob neither, we're already covered by both cases

**The answer is the maximum of these two cases.**

## Approach: Two Linear House Robber Problems

### Algorithm Overview

1. **Handle edge case**: If only one house, rob it
2. **Solve Case 1**: Rob houses [0...n-2] using standard House Robber I algorithm
3. **Solve Case 2**: Rob houses [1...n-1] using standard House Robber I algorithm
4. **Return maximum** of the two cases

### House Robber I (Linear) Algorithm

For a linear array of houses, the optimal solution uses dynamic programming:

```java
dp[i] = max(dp[i-1], dp[i-2] + nums[i])

Meaning:
- Either skip current house (take previous max)
- Or rob current house + max from 2 houses ago
```

Space-optimized version uses two variables instead of an array:

- `prevRob`: Maximum money robbed up to 2 houses ago
- `maxRob`: Maximum money robbed up to the previous house

## Code Walkthrough

```java
public int rob(int[] nums) {
    // Edge case: only one house - must rob it
    if (nums.length == 1) return nums[0];
    
    // Case 1: Rob houses [0...n-2] (exclude last)
    // Case 2: Rob houses [1...n-1] (exclude first)
    // Return the maximum of both scenarios
    return Math.max(
        getMax(nums, 0, nums.length - 2),  // exclude last
        getMax(nums, 1, nums.length - 1)   // exclude first
    );
}

private int getMax(int[] nums, int start, int end) {
    int prevRob = 0;  // max money 2 houses ago
    int maxRob = 0;   // max money up to previous house
    
    for (int i = start; i <= end; i++) {
        // Current decision: rob this house or skip it
        int temp = Math.max(
            maxRob,              // skip current house
            prevRob + nums[i]    // rob current house + money from 2 houses ago
        );
        
        // Update for next iteration
        prevRob = maxRob;  // what was previous max becomes 2-houses-ago max
        maxRob = temp;     // current decision becomes new previous max
    }
    
    return maxRob;
}
```

### Variable Meaning in getMax

At each iteration `i`:

- `prevRob`: Represents `dp[i-2]` - max money we could rob up to house `i-2`
- `maxRob`: Represents `dp[i-1]` - max money we could rob up to house `i-1`
- `temp`: Represents `dp[i]` - max money we can rob up to house `i`

The update pattern:

```java
temp = dp[i] = max(dp[i-1], dp[i-2] + nums[i])
prevRob = dp[i-1] (for next iteration)
maxRob = dp[i] (for next iteration)
```

## Example Trace

```java
Input: nums = [2,7,9,3,1]

Case 1: Houses [0...3] = [2,7,9,3]
-------------------------------------------------
i=0: prevRob=0, maxRob=0
     temp = max(0, 0+2) = 2
     prevRob=0, maxRob=2

i=1: prevRob=0, maxRob=2
     temp = max(2, 0+7) = 7
     prevRob=2, maxRob=7

i=2: prevRob=2, maxRob=7
     temp = max(7, 2+9) = 11
     prevRob=7, maxRob=11

i=3: prevRob=7, maxRob=11
     temp = max(11, 7+3) = 11
     prevRob=11, maxRob=11

Result: 11 (rob houses 0 and 2: 2+9=11)

Case 2: Houses [1...4] = [7,9,3,1]
-------------------------------------------------
i=1: prevRob=0, maxRob=0
     temp = max(0, 0+7) = 7
     prevRob=0, maxRob=7

i=2: prevRob=0, maxRob=7
     temp = max(7, 0+9) = 9
     prevRob=7, maxRob=9

i=3: prevRob=7, maxRob=9
     temp = max(9, 7+3) = 10
     prevRob=9, maxRob=10

i=4: prevRob=9, maxRob=10
     temp = max(10, 9+1) = 10
     prevRob=10, maxRob=10

Result: 10 (rob houses 1 and 3: 7+3=10)

Final Answer: max(11, 10) = 11
```

## Complexity Analysis

**Time Complexity**: O(N)

- Run the linear algorithm twice: once for [0...n-2], once for [1...n-1]
- Each run is O(N)
- Total: O(N) + O(N) = O(N)

**Space Complexity**: O(1)

- Only use two variables (`prevRob`, `maxRob`) regardless of input size
- No recursion, no extra data structures

## Why This Works

### Proof of Correctness

The optimal solution must fall into one of these categories:

1. **First house is robbed** → Last house cannot be robbed → Solve [0...n-2]
2. **Last house is robbed** → First house cannot be robbed → Solve [1...n-1]
3. **Neither first nor last is robbed** → This is a subset of both [0...n-2] and [1...n-1]

Since case 3 is covered by both case 1 and case 2, we only need to check these two ranges and take the maximum.

### Why Two Variables Suffice

The recurrence relation only depends on the previous two states:

```java
dp[i] = max(dp[i-1], dp[i-2] + nums[i])
```

We don't need to store all previous values—just the last two. This is the **space optimization** that reduces O(N) space to O(1).

## Comparison with House Robber I

| Aspect | House Robber I | House Robber II |
| -------- | ---------------- | ----------------- |
| **Layout** | Linear (street) | Circular (first and last adjacent) |
| **Constraint** | No adjacent houses | No adjacent + first/last not both |
| **Solution** | Single DP pass | Two DP passes (exclude first OR last) |
| **Time** | O(N) | O(N) |
| **Space** | O(1) optimized | O(1) optimized |

## Key Insights

1. **Circular to Linear**: Transform the circular constraint into two linear subproblems

2. **Overlapping scenarios**: The case where neither first nor last is robbed is automatically covered by both subproblems

3. **Space optimization**: Using rolling variables instead of DP array saves memory without losing correctness

4. **Greedy doesn't work**: We can't just skip first or last - we need to explore both options and take the max

## Common Pitfalls

1. **Forgetting edge case**: When `nums.length == 1`, there's only one house to rob

   ```java
   if (nums.length == 1) return nums[0];
   ```

2. **Wrong range logic**: Must be `[0...n-2]` and `[1...n-1]`, not `[0...n-1]` and `[1...n]`

3. **Not considering both cases**: Only checking one scenario misses the optimal solution

4. **Overcomplicated logic**: Some try to track whether first house was robbed—the two-case approach is simpler

## Edge Cases

- **Single house** (`[5]`): Return 5
- **Two houses** (`[1,2]`): Return max(1, 2) = 2
- **All same values** (`[1,1,1,1]`): Return 2 (rob alternating)
- **Decreasing values** (`[5,3,1]`): Return 5 (rob first)
- **Increasing values** (`[1,3,5]`): Return 5 (rob last)

## Variants and Extensions

This problem teaches a valuable technique: **breaking circular constraints into linear subproblems**. Similar patterns appear in:

- House Robber III (binary tree variant)
- Paint House problems with circular constraints
- Job scheduling with cyclic dependencies

The core idea: when faced with circular constraints, consider breaking the circle at strategic points and solving multiple linear subproblems.
