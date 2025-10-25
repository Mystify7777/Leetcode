# How & Why: LeetCode 1716 - Calculate Money in Leetcode Bank

This solution uses mathematical formulas to efficiently calculate the total money deposited in the bank over $n$ days, avoiding the need for iteration.

---

## Problem Restatement

Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.

On **Monday** of the first week, he deposits $1$. Every subsequent day (Tuesday through Sunday), he deposits $1 more than the previous day.

Starting from the **second week**, he deposits $1 more than what he deposited on Monday of the previous week. This pattern continues for every subsequent week.

Given $n$ (the number of days), return the total amount of money he will have in the bank at the end of the $n$-th day.

### Example

**Input:**
```
n = 10
```

**Output:**
```
37
```

**Explanation:**

| Week | Day | Deposit | Total |
|------|-----|---------|-------|
| 1 | Monday | 1 | 1 |
| 1 | Tuesday | 2 | 3 |
| 1 | Wednesday | 3 | 6 |
| 1 | Thursday | 4 | 10 |
| 1 | Friday | 5 | 15 |
| 1 | Saturday | 6 | 21 |
| 1 | Sunday | 7 | 28 |
| 2 | Monday | 2 | 30 |
| 2 | Tuesday | 3 | 33 |
| 2 | Wednesday | 4 | 37 |

After 10 days, total = 37.

---

## Naive Approach (Simulation)

### Idea

Simply iterate through each day, keeping track of the current week and day of the week, and add the appropriate amount to the total.

### Pseudocode

```java
public int totalMoney(int n) {
    int total = 0;
    int currentWeek = 0;
    
    for (int day = 1; day <= n; day++) {
        int dayOfWeek = (day - 1) % 7;
        if (dayOfWeek == 0) currentWeek++;
        total += currentWeek + dayOfWeek;
    }
    
    return total;
}
```

### Analysis

* ✅ Simple and intuitive
* ✅ Easy to understand
* ❌ **Time Complexity:** $O(n)$ - iterates through all days
* ❌ Not optimal for large $n$

---

## Optimized Approach (Mathematical Formula)

### Key Insight

Instead of simulating each day, we can use mathematical formulas to calculate:
1. **Complete weeks contribution:** Money deposited in all complete weeks
2. **Remaining days contribution:** Money deposited in the incomplete final week

### Pattern Analysis

**Week 1 deposits:** 1, 2, 3, 4, 5, 6, 7 → Sum = 28  
**Week 2 deposits:** 2, 3, 4, 5, 6, 7, 8 → Sum = 35  
**Week 3 deposits:** 3, 4, 5, 6, 7, 8, 9 → Sum = 42  
**Week $k$ deposits:** $k, k+1, k+2, ..., k+6$ → Sum = $7k + 21$

**Formula for week $k$:**
$$\text{Sum}_k = k + (k+1) + (k+2) + ... + (k+6) = 7k + (0+1+2+...+6) = 7k + 21$$

### Your Solution Breakdown

```java
public int totalMoney(int n) {
    int week_count = n / 7;              // Number of complete weeks
    int remaining_days = n % 7;          // Days in incomplete week
    
    // Contribution from complete weeks
    int total = ((week_count * (week_count - 1)) / 2) * 7;
    total += week_count * 28;
    
    // Contribution from remaining days
    total += ((remaining_days * (remaining_days + 1)) / 2) + (week_count * remaining_days);
    
    return total;
}
```

### How It Works

#### Part 1: Complete Weeks Contribution

For $w$ complete weeks, we need to sum weeks 1 through $w$:

$$\text{Total}_{\text{weeks}} = \sum_{k=1}^{w} (7k + 21)$$

Breaking this down:
$$= 7 \sum_{k=1}^{w} k + 21w$$
$$= 7 \cdot \frac{w(w+1)}{2} + 21w$$
$$= \frac{7w(w+1)}{2} + 21w$$

In the code:
* `week_count * 28` = $21w + 7w$ (base 28 per week for week 1)
* `((week_count * (week_count - 1)) / 2) * 7` = Additional increment per week

Actually, let's recalculate more carefully:

**Week 1:** 1+2+3+4+5+6+7 = 28  
**Week 2:** 2+3+4+5+6+7+8 = 35 = 28 + 7  
**Week 3:** 3+4+5+6+7+8+9 = 42 = 28 + 14  
**Week $k$:** 28 + 7(k-1)

Sum of all complete weeks:
$$\sum_{k=1}^{w} [28 + 7(k-1)] = 28w + 7\sum_{k=0}^{w-1} k = 28w + 7 \cdot \frac{w(w-1)}{2}$$

This matches the code!

#### Part 2: Remaining Days Contribution

For the remaining days in week $(w+1)$:
* Start value: $(w+1)$
* Days: $r$ (where $r < 7$)
* Deposits: $(w+1), (w+2), ..., (w+r)$

Sum formula:
$$\sum_{i=1}^{r} (w+i) = rw + \sum_{i=1}^{r} i = rw + \frac{r(r+1)}{2}$$

In the code:
* `week_count * remaining_days` = $rw$
* `(remaining_days * (remaining_days + 1)) / 2` = $\frac{r(r+1)}{2}$

Perfect match! ✓

---

## Why This Works

**Mathematical Optimization:**
* Instead of iterating $n$ times, we use closed-form formulas
* Arithmetic series sum: $\sum_{i=1}^{n} i = \frac{n(n+1)}{2}$
* Break problem into complete weeks + partial week

**Key Formulas Used:**
1. **Sum of first $n$ natural numbers:** $\frac{n(n+1)}{2}$
2. **Sum of arithmetic sequence:** $\frac{n(\text{first} + \text{last})}{2}$

---

## Complexity Analysis

**Your Solution (Mathematical):**
* **Time Complexity:** $O(1)$ - Only arithmetic operations, no loops
* **Space Complexity:** $O(1)$ - Only a few variables

**Naive Simulation:**
* **Time Complexity:** $O(n)$ - Iterates through all days
* **Space Complexity:** $O(1)$ - Few variables

**Winner:** Mathematical approach is far superior! 🏆

---

## Example Walkthrough

**Input:** $n = 10$

**Step 1: Calculate complete weeks**
```
week_count = 10 / 7 = 1
remaining_days = 10 % 7 = 3
```

**Step 2: Complete weeks contribution**
```
Contribution = ((1 * 0) / 2) * 7 + 1 * 28
            = 0 + 28
            = 28
```
Week 1: 1+2+3+4+5+6+7 = 28 ✓

**Step 3: Remaining days contribution**
```
Week 2, first 3 days: 2, 3, 4

Contribution = ((3 * 4) / 2) + (1 * 3)
            = 6 + 3
            = 9
```
2+3+4 = 9 ✓

**Step 4: Total**
```
total = 28 + 9 = 37
```

**Output:** `37` ✓

---

## Comparison

| Approach | Time | Space | When to Use |
|----------|------|-------|-------------|
| Simulation | $O(n)$ | $O(1)$ | Small $n$, learning |
| Mathematical | $O(1)$ | $O(1)$ | Any $n$, optimal |

---

## Visual Pattern

```
Week 1: [1] [2] [3] [4] [5] [6] [7]      = 28
Week 2:     [2] [3] [4] [5] [6] [7] [8]  = 35 (+7)
Week 3:         [3] [4] [5] [6] [7] [8] [9] = 42 (+7)
...
```

Each week starts $1$ higher than the previous week's Monday!

---

## Alternative Formula

Another way to think about it:

```java
public int totalMoney(int n) {
    int weeks = n / 7;
    int days = n % 7;
    
    // Sum of weeks: 28 + 35 + 42 + ... (arithmetic sequence)
    int weekSum = weeks * 28 + 7 * weeks * (weeks - 1) / 2;
    
    // Remaining days starting from (weeks + 1)
    int daySum = days * (weeks + 1) + days * (days - 1) / 2;
    
    return weekSum + daySum;
}
```

Same result, slightly different grouping of terms!

---

## Key Takeaways

1. **Pattern Recognition:** Identify arithmetic sequences and series
2. **Mathematical Optimization:** Replace loops with formulas when possible
3. **Break Down Complex Problems:** Complete weeks + remaining days
4. **Use Sum Formulas:** $\sum_{i=1}^{n} i = \frac{n(n+1)}{2}$ is your friend
5. **Verify with Examples:** Always test edge cases (n=1, n=7, n=8, etc.)

---

## Edge Cases to Consider

* **$n = 1$:** Only Monday of week 1 → $1$
* **$n = 7$:** Exactly one complete week → $28$
* **$n = 8$:** One week + Monday of week 2 → $28 + 2 = 30$
* **Large $n$:** Formula handles efficiently in $O(1)$ time

---

## Optimal Choice

**Your mathematical solution is optimal!** It achieves constant time complexity by leveraging arithmetic series formulas, making it perfect for any value of $n$.

---
