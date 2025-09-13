
# How & Why: LeetCode 202 - Happy Number

This solution cleverly detects if a number is "happy" by identifying cycles using Floyd's Cycle-Finding Algorithm, also known as the "tortoise and hare" method.

---

## Problem Restatement

You are given a positive integer $n$. You need to determine if it is a "happy number".

A number is happy if you follow this process:
1. Start with the number.
2. Replace it with the sum of the squares of its digits.
3. Repeat this process.
4. If the number eventually becomes 1, it is happy. If the process enters a cycle that does not include 1, it is not happy.

### Example

**Input:**
```
n = 19
```
**Output:**
```
true
```
**Explanation:**

1² + 9² = 1 + 81 = 82
8² + 2² = 64 + 4 = 68
6² + 8² = 36 + 64 = 100
1² + 0² + 0² = 1 (The process ends at 1)

---

## How to Solve

The problem can be modeled as a linked list where each number points to the next number in the sequence (the sum of the squares of its digits). A non-happy number will eventually get stuck in a loop, just like a cycle in a linked list. This allows us to use a classic algorithm to detect the cycle.

1. **Two Pointers:** Initialize two pointers, a slow pointer and a fast pointer, both starting at the initial number $n$.
2. **Move at Different Speeds:** In a loop, advance the pointers through the sequence:
    - The slow pointer moves one step at a time (`slow = square(slow)`).
    - The fast pointer moves two steps at a time (`fast = square(square(fast))`).
3. **Detect a Meeting:** Continue this process until the slow and fast pointers are equal.
    - If a cycle exists, the fast pointer will eventually lap the slow pointer and they will meet inside the cycle.
    - If the sequence ends at 1, they will both eventually reach 1 and meet there.
4. **Check the Meeting Point:** Once they meet, check the value. If `slow == 1`, the number is happy. Otherwise, it's trapped in an unhappy cycle.

### Implementation

```java
class Solution {
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;

        // Use a do-while loop because slow and fast start at the same value.
        do {
            // Slow pointer moves one step.
            slow = square(slow);
            // Fast pointer moves two steps.
            fast = square(square(fast));
        } while (slow != fast);

        // If they met at 1, the number is happy. Otherwise, they met in a cycle.
        return slow == 1;
    }

    // Helper function to find the sum of the squares of the digits.
    public int square(int num) {
        int ans = 0;
        while(num > 0) {
            int remainder = num % 10;
            ans += remainder * remainder;
            num /= 10;
        }
        return ans;
    }
}
```

---

## Why This Works

- **Guaranteed Cycle or Convergence:** For any starting number, the sequence of sums is guaranteed to either reach 1 or enter a cycle. This is because the sum of squares for a very large number will eventually produce a smaller number, meaning the sequence is bounded and must repeat numbers at some point.
- **Cycle Detection:** The "tortoise and hare" algorithm is a proven method for detecting cycles. By moving at different speeds, if there's a loop, the fast runner is guaranteed to eventually catch up to the slow runner from behind.
- **Correctness:** The only "happy" cycle is the one where the number is 1 (since $1^2 = 1$). Therefore, if the pointers meet at any value other than 1, we have definitively found an "unhappy" cycle. If they meet at 1, we know the sequence terminated correctly.

---

## Complexity Analysis

- **Time Complexity:** $O(\log n)$. The numbers in the sequence don't grow indefinitely; they are bounded. The time taken depends on how long it takes to enter the cycle, which is generally logarithmic with respect to the initial number $n$.
- **Space Complexity:** $O(1)$. This is the main advantage of this approach. We only use two integer variables (slow and fast), providing a constant-space solution.

---

## Example Walkthrough

**Input:**
```
n = 2 (An unhappy number)
```

**Process:**
The sequence is: 2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4... (cycle begins)

| Iteration | slow (1 step) | fast (2 steps) | slow == fast? |
|-----------|--------------|---------------|---------------|
| 1         | 4            | 16            | No            |
| 2         | 16           | 58            | No            |
| 3         | 37           | 145           | No            |
| 4         | 58           | 20            | No            |
| 5         | 89           | 4             | No            |
| ...       | ...          | ...           | ...           |
| eventually| 20           | 20            | Yes           |

The loop terminates when slow and fast both equal 20.

**Output:**
```
Return slow == 1 (which is 20 == 1), so the result is false.
```

---

## Alternate Approaches

### 1. Using a HashSet ✨
   - **How:** Keep a HashSet to store every number you encounter in the sequence. In each step, before computing the next number, check if it's already in the set. If it is, you've found a cycle, and the number is not happy. If you reach 1, it's happy.
   - **Complexity:** $O(\log n)$ time, but also $O(\log n)$ space to store the numbers in the set.
   - **Trade-off:** This approach is often easier to reason about but uses more memory.

---

## Optimal Choice

The two-pointer (Floyd's Algorithm) solution is optimal in terms of space complexity, making it a superior choice for memory-constrained environments.

---

## Key Insight

The crucial insight is to reframe the problem from a simple calculation into a problem about the structure of a sequence. By treating the sequence of numbers as a linked list, you can unlock powerful algorithms like cycle detection to solve the problem efficiently without extra memory.