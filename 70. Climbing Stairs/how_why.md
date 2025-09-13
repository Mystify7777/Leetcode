
# How & Why: LeetCode 70 - Climbing Stairs

This solution uses dynamic programming to solve the problem by building up the answer from the base cases, recognizing that the problem is a form of the Fibonacci sequence.

---

## Problem Restatement

You are climbing a staircase that takes $n$ steps to reach the top. You can climb either 1 or 2 steps at a time. Your task is to determine how many distinct ways you can climb to the top.

### Example

**Input:**
```
n = 3
```
**Output:**
```
3
```
**Explanation:** There are three ways to climb to the top:
- 1 step + 1 step + 1 step
- 1 step + 2 steps
- 2 steps + 1 step

---

## How to Solve

This is a classic dynamic programming problem. The key insight is that to reach a particular step $i$, you must have come from either step $i-1$ (by taking a 1-step hop) or step $i-2$ (by taking a 2-step hop).

Therefore, the total number of ways to reach step $i$ is the sum of the ways to reach step $i-1$ and the ways to reach step $i-2$. This gives us a recurrence relation: $\text{ways}(i) = \text{ways}(i-1) + \text{ways}(i-2)$.

This can be solved bottom-up:

1. **Create a DP Array:** Create an array `dp` of size $n+1$ to store the number of ways to reach each step.
2. **Establish Base Cases:** We know there's 1 way to get to step 0 (by not starting) and 1 way to get to step 1 (taking one 1-step). So, we initialize `dp[0] = 1` and `dp[1] = 1`.
3. **Iterate and Build:** Loop from $i = 2$ up to $n$. In each iteration, calculate `dp[i]` using the formula `dp[i] = dp[i-1] + dp[i-2]`.
4. **Return the Result:** The final answer for $n$ stairs is the value stored in `dp[n]`.

### Implementation

```java
class Solution {
    public int climbStairs(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int[] dp = new int[n+1];
        // Base cases
        dp[0] = 1;
        dp[1] = 1;

        // Build up the solution using the recurrence relation
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
```

---

## Why This Works

- **Optimal Substructure:** The problem has an optimal substructure. The solution to a larger problem ($n$ stairs) is built directly from the solutions to smaller subproblems ($n-1$ and $n-2$ stairs).
- **Connection to Fibonacci:** The recurrence relation `dp[i] = dp[i-1] + dp[i-2]` is the exact definition of the Fibonacci sequence. By solving for the base cases and building up, the algorithm is effectively calculating the $(n+1)$-th Fibonacci number.
- **Avoiding Redundant Calculations:** A naive recursive solution would re-calculate the ways for the same step multiple times. The DP array acts as a cache (or memoization table), storing the result for each step so it only needs to be computed once.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$. A single loop runs from 2 to $n$, performing a constant amount of work at each step.
- **Space Complexity:** $O(n)$. We use an array of size $n+1$ to store the intermediate results.

---

## Example Walkthrough

**Input:**
```
n = 4
```

**Process:**

- Initial: `dp` array of size 5 is created.
- Base Cases: `dp[0] = 1`, `dp[1] = 1`.
- Loop $i = 2$: `dp[2] = dp[1] + dp[0] = 1 + 1 = 2`. (Ways to reach step 2: {1,1}, {2})
- Loop $i = 3$: `dp[3] = dp[2] + dp[1] = 2 + 1 = 3`. (Ways to reach step 3: {1,1,1}, {1,2}, {2,1})
- Loop $i = 4$: `dp[4] = dp[3] + dp[2] = 3 + 2 = 5`. (Ways to reach step 4: {1,1,1,1}, {1,1,2}, {1,2,1}, {2,1,1}, {2,2})
- Loop finishes.

**Output:**
```
Return dp[4], which is 5.
```

---

## Alternate Approaches

### 1. Space-Optimized DP ✨
   - **How:** Since the calculation for `dp[i]` only depends on the previous two values (`dp[i-1]` and `dp[i-2]`), we don't need to store the entire array. We can use two variables to keep track of the last two results and update them in a loop.
   - **Complexity:** $O(n)$ time and $O(1)$ space. This is the most optimal solution.

---

## Optimal Choice

The space-optimized DP is the best solution as it provides the same fast runtime with minimal memory usage. However, the provided array-based DP solution is an excellent and clear way to demonstrate the core dynamic programming logic.

---

## Key Insight

The key is to recognize that the problem can be broken down into smaller, overlapping subproblems. By identifying the recurrence relation ($\text{ways}(i) = \text{ways}(i-1) + \text{ways}(i-2)$), you can transform a complex counting problem into a simple iterative calculation, which is the essence of bottom-up dynamic programming.