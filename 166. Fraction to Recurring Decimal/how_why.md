
# How\_Why.md — Fraction to Recurring Decimal (LeetCode 166)

---

## 1. Brute Force Approach

### How it works

* Perform **manual long division**.
* At each step, keep dividing and appending digits.
* To detect repetition, check if the same **remainder** has already appeared in the fractional part by scanning the list of previous remainders.

### Why it fails

* Storing remainders in a **list/array** and linearly searching each time makes it **O(n²)**.
* Inefficient for large repeating cycles.

### Example

`1 / 3`

* Step 1: remainder = 1, multiply by 10 → 10 ÷ 3 = 3 (remainder 1).
* Step 2: remainder 1 already appeared → stop.
* Result: `0.(3)`.

---

## 2. HashMap Approach (Your Code)

### How it works

* Use a **HashMap\<remainder, index>** to store the index in the fractional part where each remainder first appeared.
* When a remainder repeats, insert `(` at the stored index and append `)` at the end → cycle detected.
* Handles sign, integer part, fractional part, and cycles cleanly.

### Why it works better

* Checking for remainder repetition is **O(1)** with a HashMap.
* Full algorithm is **O(n)** time and **O(n)** space.

### Example

`2 / 7`

* Remainders: 2, 6, 4, 5, 1, 3, then 2 again.
* HashMap catches the first occurrence of `2`.
* Digits generated: `285714`.
* Insert parentheses → `0.(285714)`.

---

## 3. Optimized Approach (Floyd’s Cycle Detection)

### How it works

* The repeating decimal comes from a **cycle in remainders**.
* Use **Floyd’s Tortoise and Hare**:

  * `slow` moves one remainder step at a time.
  * `fast` moves two steps.
  * If they meet → cycle detected.
* Once cycle length is found, rebuild fractional part and place parentheses accordingly.

### Why it works best

* **Cycle detection uses O(1) extra space**, unlike the HashMap approach.
* Still runs in **O(n)** time.
* The reconstruction phase requires handling digits, but detection itself is memory-efficient.

### Example

`1 / 6`

* Sequence: `1 → 4 → 4 → 4 ...`
* Cycle detected at remainder `4`.
* Fractional part: `1(6)` → `0.1(6)`.

---

## Summary

| Approach        | Time  | Space | Notes                             |
| --------------- | ----- | ----- | --------------------------------- |
| Brute Force     | O(n²) | O(n)  | Scans list each time, inefficient |
| HashMap (yours) | O(n)  | O(n)  | Clean and widely used             |
| Floyd’s Cycle   | O(n)  | O(1)  | Elegant space optimization        |

✅ For interviews → HashMap solution is standard.
✅ For theory / memory-constrained systems → Floyd’s cycle detection is optimal.

---

