# How\_Why.md: Partition Equal Subset Sum

## Problem

Given a non-empty array `nums` of positive integers, determine if it can be partitioned into **two subsets with equal sum**.

**Example:**

```java
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1,5,5] and [11].
```

---

## Brute-force Approach

### Idea

* Try **all possible subsets** and check if any subset sums to `totalSum / 2`.
* Use **recursion/backtracking**.

### Code

```java
public boolean canPartitionBF(int[] nums) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % 2 != 0) return false;
    return dfs(nums, 0, sum / 2);
}

private boolean dfs(int[] nums, int index, int target) {
    if (target == 0) return true;
    if (index == nums.length || target < 0) return false;
    // include current number or skip it
    return dfs(nums, index + 1, target - nums[index]) || dfs(nums, index + 1, target);
}
```

### Example Walkthrough

* Input: `[1,5,11,5]` → total sum = 22 → target = 11
* Try subsets recursively:

  * `[1,5,5]` → sum = 11 → valid
* Returns `true`

### Limitation

* **Exponential time:** O(2^n)
* Not feasible for large `nums` (n ≤ 200 in constraints).

---

## Dynamic Programming Approach (Classic)

### Idea__

* Use **1D DP array** `dp[s]` = whether sum `s` is achievable.
* Iterate through numbers; update DP **backwards** to avoid double-counting.

### Code__

```java
public boolean canPartitionDP(int[] nums) {
    int totalSum = 0;
    for (int num : nums) totalSum += num;
    if (totalSum % 2 != 0) return false;
    int target = totalSum / 2;

    boolean[] dp = new boolean[target + 1];
    dp[0] = true;

    for (int num : nums) {
        for (int s = target; s >= num; s--) {
            dp[s] = dp[s] || dp[s - num];
            if (dp[target]) return true;
        }
    }
    return dp[target];
}
```

### Example Walkthrough__

* Input: `[1,5,11,5]`, target = 11
* DP table after processing 1: `[T,F,F,F,F,F,F,F,F,F,F,F]`
* DP after 5: `[T,F,F,F,F,F,T,F,F,F,F,F]`
* DP after next 5: `[T,F,F,F,F,F,T,F,F,F,F,T]`
* DP after 11: `[T,F,...,T]` → `dp[11] = true` → return `true`

### Advantages

* O(n × target) time, O(target) space
* Deterministic and efficient

---

## BitSet Optimization (Your Approach)

### Idea_

* Represent the **achievable sums** using a `BitSet`.
* Each number `num` shifts the reachable sums downward, using `or()` and `get()` to update all achievable sums at once.
* Check if **sum 0** is reachable (after inversion).

### Code_

```java
public boolean canPartitionBitSet(int[] nums) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % 2 != 0) return false;
    sum /= 2;

    BitSet ans = new BitSet(sum + 1);
    ans.set(sum);

    for (int num : nums) {
        if (num > sum) continue;
        ans.or(ans.get(num, sum + 1));
        if (ans.get(0)) return true;
    }
    return false;
}
```

### Example Walkthrough_

* Input: `[1,5,11,5]`, target = 11
* `BitSet` initially: `100000000000` (index 11 set)
* Process `1`: shifts bits → sum 10 achievable
* Process `5`: bits shift → sum 6 achievable
* Process `5` again: bits shift → sum 1 achievable
* Check `ans.get(0)` → true → partition possible

### Advantages_

* **Memory efficient:** BitSet uses 1 bit per possible sum instead of 1 boolean per sum.
* **Faster operations:** Bitwise OR updates many sums at once.
* Suitable when `targetSum` is large.

---

## Key Takeaways

1. **Brute-force:** simple, exponential → not feasible.
2. **DP array:** classic solution, O(n × target) → optimal for medium constraints.
3. **BitSet:** memory-efficient and fast variant of DP array, ideal for large sums.

---
