# 2048. Next Greater Numerically Balanced Number – How & Why

## Problem Recap

A number is **numerically balanced** if for every digit `d` in the number, the digit `d` appears exactly `d` times.

Examples:
* `1` is balanced (digit 1 appears 1 time)
* `22` is balanced (digit 2 appears 2 times)
* `122` is balanced (digit 1 appears 1 time, digit 2 appears 2 times)
* `1333` is balanced (digit 1 appears 1 time, digit 3 appears 3 times)
* `123` is NOT balanced (digit 2 appears only 1 time, not 2)

Given an integer `n`, return the **smallest** numerically balanced number **strictly greater than** `n`.

**Constraints:** `1 <= n <= 10^6`

---

## 1. Brute-force Approach (Simple Iteration)

### Idea

Start from `n + 1` and check each number to see if it's numerically balanced. Return the first one found.

### Pseudocode

```java
public int nextBeautifulNumber(int n) {
    for (int num = n + 1; ; num++) {
        if (isBeautiful(num)) return num;
    }
}

private boolean isBeautiful(int num) {
    int[] count = new int[10];
    int temp = num;
    while (temp > 0) {
        count[temp % 10]++;
        temp /= 10;
    }
    for (int d = 0; d < 10; d++) {
        if (count[d] != 0 && count[d] != d) return false;
    }
    return true;
}
```

### Analysis

* ✅ Simple and straightforward
* ❌ Very slow for large gaps between `n` and the next balanced number
* ❌ Worst case could iterate through many numbers
* **Time Complexity:** Potentially O(k * log k) where k is the distance to next balanced number

---

## 2. Optimized Approach (Pre-generate All Balanced Numbers)

### Key Insight

Since `n <= 10^6`, all numerically balanced numbers up to 7 digits are limited. We can:
1. **Pre-generate** all possible balanced numbers
2. **Sort** them
3. **Binary search** or **iterate** to find the smallest one greater than `n`

### Why This Works

**Maximum digits:** For `n <= 10^6`, we need at most 7 digits.
**Digit constraint:** A digit `d` must appear exactly `d` times:
- Digit 1 appears 1 time
- Digit 2 appears 2 times
- ...
- Digit 7 appears 7 times

This limits the total combinations significantly!

### Your Solution (Backtracking + Permutation Generation)

```java
public int nextBeautifulNumber(int n) {
    List<Integer> list = new ArrayList<>();
    generate(0, new int[10], list);
    Collections.sort(list);
    for (int num : list) {
        if (num > n) return num;
    }
    return -1;
}

private void generate(long num, int[] count, List<Integer> list) {
    // If we have a valid balanced number, add it
    if (num > 0 && isBeautiful(count)) {
        list.add((int) num);
    }
    // Prune: max balanced number is 1224444
    if (num > 1224444) return;

    // Try adding each digit 1-7
    for (int d = 1; d <= 7; d++) {
        if (count[d] < d) {  // Can still add this digit
            count[d]++;
            generate(num * 10 + d, count, list);
            count[d]--;  // Backtrack
        }
    }
}

private boolean isBeautiful(int[] count) {
    for (int d = 1; d <= 7; d++) {
        if (count[d] != 0 && count[d] != d) return false;
    }
    return true;
}
```

### How It Works

1. **Backtracking:** Build numbers digit by digit
2. **Count Array:** Track how many times each digit appears
3. **Validation:** Check if current count pattern is balanced
4. **Pruning:** Stop at 1224444 (largest 7-digit balanced number)

### Example Walkthrough

Building balanced numbers:
1. Start with `count = [0,0,0,...]`
2. Try digit `1`: `count[1] = 1` → `num = 1` → valid ✓ → add to list
3. Try digit `2`: need 2 copies → `22` → valid ✓
4. Try combinations: `count[1]=1, count[2]=2` → generates `122`, `212`, `221` → all valid ✓
5. Continue for all valid combinations...

**Observation:**
* Not all generated numbers are unique (e.g., adding digits in different orders)
* Sorting helps find the answer quickly
* One-time generation, then O(1) lookup for any query

---

## 3. Faster Approach (Combination + Permutation)

The commented-out solution in your code uses a more sophisticated approach:

### Idea

1. **Choose which digits to include** (e.g., use digit 1 once, digit 2 twice, skip digit 3, etc.)
2. **Generate all permutations** of those digits
3. **Pre-compute once**, then use binary search for queries

### Key Differences

| Aspect | Your Solution | Faster Approach |
|--------|---------------|-----------------|
| Number Building | Digit by digit (backtracking) | Choose combinations, then permute |
| Duplicate Handling | Natural (may generate same number via different paths) | Uses Set to avoid duplicates |
| Efficiency | Simpler code, slightly more iterations | More complex, minimal duplicates |
| Pre-computation | Moderate | Optimized with permutation algorithm |

### Code Structure (Faster Approach)

```java
private void backtrack(int d, int totalLen, int[] counts, Set<Integer> set) {
    if (d == 10) {
        // Build all permutations of selected digits
        char[] arr = buildDigitArray(counts, totalLen);
        do {
            int val = arrayToNumber(arr);
            set.add(val);
        } while (nextPermutation(arr));
        return;
    }
    
    // Option 1: skip digit d
    backtrack(d + 1, totalLen, counts, set);
    
    // Option 2: include digit d (d times)
    if (totalLen + d <= MAX_LEN) {
        counts[d] = d;
        backtrack(d + 1, totalLen + d, counts, set);
        counts[d] = 0;
    }
}
```

**Benefits:**
* Generates fewer intermediate states
* Uses efficient permutation generation
* Better for very large inputs (though not needed for this problem)

---

## Complexity Analysis

### Your Solution (Backtracking)
* **Pre-computation Time:** O(M * log M) where M is the number of balanced numbers (around 2000)
* **Pre-computation Space:** O(M)
* **Query Time:** O(M) to find first number > n (can be optimized to O(log M) with binary search)
* **Overall:** Very fast given the constraint `n <= 10^6`

### Faster Approach (Combination + Permutation)
* **Pre-computation Time:** O(M) with better constants
* **Pre-computation Space:** O(M)
* **Query Time:** O(log M) with binary search
* **Overall:** Slightly better for repeated queries

---

## Key Insights

1. **Pre-computation wins:** For limited range problems, generate all answers once
2. **Constraint analysis is crucial:** Knowing `n <= 10^6` means we only need 7-digit numbers max
3. **Pruning matters:** Stopping at 1224444 saves unnecessary computation
4. **Trade-off:** Small increase in space for massive decrease in query time

---

## Example Trace

**Input:** `n = 122`

**Execution:**
1. Pre-generate all balanced numbers: `[1, 22, 122, 212, 221, 333, 1333, 3133, 3313, 3331, ...]`
2. Sort: Already sorted in this case
3. Find first number `> 122`: `212`
4. **Return:** `212`

**Why 212?**
- Digit 2 appears 2 times ✓
- Digit 1 appears 1 time ✓
- It's the smallest balanced number > 122

---

## Summary

| Approach | Time | Space | Best For |
|----------|------|-------|----------|
| Brute-force iteration | O(k * log k) | O(1) | Small n, educational |
| Pre-generation (your solution) | O(M) query | O(M) | This problem's constraints |
| Combination + Permutation | O(log M) query | O(M) | Multiple queries |

**Your solution is excellent for this problem!** It balances simplicity with efficiency and handles the constraints perfectly.

---

## Takeaways

1. **Analyze constraints first** → `n <= 10^6` suggests pre-computation
2. **Problem-specific insights** → "balanced" definition limits total possibilities
3. **Backtracking with pruning** → Efficient for generating all valid states
4. **Sort once, query many** → Classic optimization pattern

---
