# How_Why.md: Missing Number

## Problem

Given an array `nums` containing `n` distinct numbers taken from the range `[0, n]`, return the only number in the range that is missing from the array.

**Example:**

```java
Input: nums = [3,0,1]
Output: 2

Input: nums = [9,6,4,2,3,5,7,0,1]
Output: 8
```

---

## Approach: XOR Bit Manipulation

### Idea

* Use the **XOR (exclusive OR) operation** property: `a ^ a = 0` and `a ^ 0 = a`
* XOR all numbers from 1 to n with all numbers in the array
* The duplicate numbers will cancel out, leaving only the missing number

### Code

```java
public class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int ans = 0;
        
        // XOR all numbers from 1 to n
        for (int i = 1; i <= n; i++) {
            ans = ans ^ i;
        }
        
        // XOR all numbers in the array
        for (int i = 0; i < nums.length; i++) {
            ans = ans ^ nums[i];
        }
        
        return ans;
    }
}
```

### Why This Works

* **XOR Properties:**
  * `a ^ a = 0` (any number XORed with itself is 0)
  * `a ^ 0 = a` (any number XORed with 0 is itself)
  * XOR is **commutative and associative**

* **Example:** nums = [3, 0, 1]
  * XOR 1, 2, 3: `0 ^ 1 ^ 2 ^ 3 = 0`
  * XOR with array: `0 ^ 0 ^ 3 ^ 1 = 0 ^ 0 ^ 3 ^ 0 ^ 1 ^ 0`
  * After canceling duplicates: only `2` remains

* Time Complexity: **O(n)** - single pass for both loops
* Space Complexity: **O(1)** - only using a single integer variable

### Alternative Approaches

**Sum Method (Simpler):**

```java
public int missingNumber(int[] nums) {
    int n = nums.length;
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    for (int num : nums) {
        actualSum += num;
    }
    return expectedSum - actualSum;
}
```

### Why XOR Over Sum?

* **XOR Approach:**
  * Avoids potential **integer overflow** issues
  * Works with very large numbers
  * Elegant bit manipulation solution

* **Sum Approach:**
  * More intuitive and easier to understand
  * Requires careful handling of overflow in languages without BigInteger
