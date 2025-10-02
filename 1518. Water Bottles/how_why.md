# How_Why.md – Water Bottles (LeetCode 1518)

## ❌ Brute Force Idea

The problem:

* You start with `numBottles` full bottles.
* Each empty bottle can be exchanged for a new full one if you collect `numExchange` empty bottles.
* Goal: count the maximum number of bottles you can drink.

Naive approach:

* Simulate drinking one bottle at a time.
* Keep a counter for empties.
* Every time you have `numExchange` empties, exchange them for 1 new bottle.

This works but is inefficient since it simulates **one bottle at a time**.

---

## ✅ Optimized Simulation (Bulk Exchanges)

Instead of simulating each bottle:

* Always exchange as many as possible in bulk.
* Formula per loop:

  * `newBottles = numBottles / numExchange`
  * Add `newBottles` to total.
  * Update `numBottles = newBottles + (numBottles % numExchange)`.

This avoids unnecessary steps.

### Implementation

```java
class Solution {
    public int numWaterBottles(int numBottles, int numExchange) {
        int totalBottles = numBottles;

        while (numBottles >= numExchange) {
            totalBottles += numBottles / numExchange;
            numBottles = (numBottles / numExchange) + (numBottles % numExchange);
        }

        return totalBottles;
    }
}
```

---

### Example Walkthrough

Input: `numBottles=9, numExchange=3`

* Start: total = 9.
* Step 1: 9 ÷ 3 = 3 new bottles → total = 12.
  Remaining = 3.
* Step 2: 3 ÷ 3 = 1 new bottle → total = 13.
  Remaining = 1.
* Now `1 < 3` → stop.

Answer = **13**.

---

## 🔢 Closed-Form Formula (No Loop)

We can also derive a **direct formula**:

* Each time you exchange `numExchange` bottles, you lose `(numExchange - 1)` bottles permanently (since 1 comes back as full, but the rest are gone).
* So starting with `numBottles`, the total you can drink is:

[
\text{Total} = \text{numBottles} + \frac{\text{numBottles} - 1}{\text{numExchange} - 1}
]

Here, the fraction uses **integer division** (floor).

---

### Example with Formula

For `numBottles = 9, numExchange = 3`:

[
\text{Total} = 9 + \frac{9-1}{3-1} = 9 + \frac{8}{2} = 9 + 4 = 13
]

✅ Same result, but no loops.

---

## 📊 Complexity

* **Simulation Method:** O(log n)
* **Formula Method:** O(1)

---

## ✅ Key Takeaways

* Loop version = intuitive and easy to write.
* Formula version = elegant, O(1), but requires mathematical insight.
* Both are correct, and LeetCode accepts both.

---

