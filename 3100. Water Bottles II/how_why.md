# How_Why.md – Water Bottles II (LeetCode 3100)

## ❌ Brute Force Idea

The problem:

* You start with `numBottles` full bottles.
* To get a new full bottle, you must exchange **x empty bottles**, but after **each exchange**, the cost `x` increases by `1`.
* Goal: maximize how many bottles you can drink.

Naive approach:

* Simulate drinking one bottle at a time.
* Keep track of empties.
* Whenever possible, exchange empties for a new full bottle and update the cost `x`.

This is correct but inefficient (too many steps when `numBottles` is large).

---

## ✅ Optimized Approach (Greedy Simulation)

Instead of doing it bottle by bottle:

* Notice that at each exchange:

  * You spend `x` empty bottles.
  * But the new full bottle will eventually return 1 empty again.
  * So effectively, each exchange reduces your empty count by `(x - 1)` and gives you **1 more drink**.
* After the exchange, the required cost `x` increases by 1.

Thus, the process is:

1. Start with `ans = numBottles` (all initial bottles are drunk).
2. While you can afford the current exchange `x`:

   * Subtract `(x - 1)` from your stock.
   * Increment `x`.
   * Add 1 to your total answer.

---

### Implementation

```java
class Solution {
    public int maxBottlesDrunk(int numBottles, int x) {
        int ans = numBottles; // initially drink all bottles
        while (numBottles >= x) {
            numBottles -= (x - 1); // spend empties effectively
            x++;                   // cost increases
            ans++;                 // drink the new bottle
        }
        return ans;
    }
}
```

---

### Example Walkthrough

Input: `numBottles = 13, x = 6`

* Start: ans = 13.
* Exchange 1: need 6 empties → 13 ≥ 6 ✅

  * Use 6, but get 1 back → effective loss = 5.
  * numBottles = 13 - 5 = 8.
  * x = 7, ans = 14.
* Exchange 2: need 7 empties → 8 ≥ 7 ✅

  * Effective loss = 6.
  * numBottles = 8 - 6 = 2.
  * x = 8, ans = 15.
* Exchange 3: need 8 empties → 2 < 8 ❌ stop.

Final Answer = **15**.

---

## 🔢 Can We Do a Formula?

Unlike 1518, here the exchange rate **increases after every exchange**, so a neat closed-form formula isn’t straightforward.

* You’d need to sum a sequence where each step reduces by `(x-1), (x), (x+1), …`.
* The greedy simulation is already **O(log n)** at worst because each step reduces the bottle count significantly.

---

## 📊 Complexity

* **Time:** O(k), where k = number of exchanges possible. (Much smaller than numBottles itself).
* **Space:** O(1).

---

## ✅ Key Takeaways

* Each exchange is more "expensive" than the last → cost grows linearly.
* You can’t use a direct formula like in 1518 because `x` is dynamic.
* Simulation with greedy subtraction is the cleanest and optimal way.

---
