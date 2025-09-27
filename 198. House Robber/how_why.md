# How_Why.md – House Robber (LeetCode 198)

---

## 🔹 Problem

You are a robber with houses in a row.
Each house has some money, but you **cannot rob two adjacent houses** (security system alert).
Return the **maximum money** you can rob.

---

## 🔸 Approach 1 – Brute Force (Recursion)

### Idea

* At each house `i`, choose:

  1. **Rob it** → money = `nums[i] + rob(i+2)`
  2. **Skip it** → money = `rob(i+1)`
* Take the maximum.

### Pseudocode

```java
int robFrom(int i) {
    if (i >= n) return 0;
    return max(nums[i] + robFrom(i+2), robFrom(i+1));
}
```

### Example: nums = [2,7,9,3,1]

* rob(0) = max(2 + rob(2), rob(1))

  * rob(2) = max(9 + rob(4), rob(3))
  * rob(4) = 1
  * rob(3) = 3
* Works but re-computes many states.

### Complexity

* **Time:** O(2ⁿ) (exponential recursion).
* **Space:** O(n) (recursion depth).

---

## 🔸 Approach 2 – DP Array (Your First Solution) ✅

### Idea

* Use a **dp array** where:

  ```
  dp[i] = max money that can be robbed up to house i
  ```
* Transition:

  ```
  dp[i] = max(dp[i-1], nums[i] + dp[i-2])
  ```

### Code

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-1], nums[i] + dp[i-2]);
        }
        return dp[n-1];
    }
}
```

### Walkthrough (nums = [2,7,9,3,1])

* dp[0] = 2
* dp[1] = max(2,7) = 7
* dp[2] = max(7, 9+2) = 11
* dp[3] = max(11, 3+7) = 11
* dp[4] = max(11, 1+11) = 12

Answer = 12 ✅

### Complexity

* **Time:** O(n)
* **Space:** O(n)

---

## 🔸 Approach 3 – Optimized DP with Rolling Variables (Your Second Solution)

### Idea

* Instead of storing full dp[], keep only two variables:

  * `prevRob` → max money till house `i-2`
  * `maxRob` → max money till house `i-1`
* Update at each step.

### Code

```java
class Solution {
    public int rob(int[] nums) {
        int prevRob = 0, maxRob = 0;

        for (int cur : nums) {
            int temp = Math.max(maxRob, prevRob + cur);
            prevRob = maxRob;
            maxRob = temp;
        }
        return maxRob;
    }
}
```

### Walkthrough (nums = [2,7,9,3,1])

* Start: prevRob=0, maxRob=0
* House 2 → max(0,0+2)=2 → prevRob=0, maxRob=2
* House 7 → max(2,0+7)=7 → prevRob=2, maxRob=7
* House 9 → max(7,2+9=11)=11 → prevRob=7, maxRob=11
* House 3 → max(11,7+3=10)=11 → prevRob=11, maxRob=11
* House 1 → max(11,11+1=12)=12 → prevRob=11, maxRob=12

Answer = 12 ✅

### Complexity

* **Time:** O(n)
* **Space:** O(1) (only 2 variables).

---

## 🚀 Summary

* **Brute Force Recursion:** Simple but exponential.
* **DP Array (your solution):** Clear, O(n) space. ✅
* **Optimized DP (rolling vars):** Best, O(1) space. 🚀

---
