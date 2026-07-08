# 2144. Minimum Cost of Buying Candies With Discount

## Problem in short
For every 2 candies you pay for, you can get **1 additional candy free**. You choose which candies are free. Return the minimum total cost to buy all candies.

## Key Insight (the "why")
To minimize cost, you want the **most expensive** candies to be the ones you pay for, and the **cheapest** candies to be the ones you get for free.

Why? Because the discount is "buy 2, get 1 free" — no matter which candy you make free, you still buy the other two. So the free slot should always go to the **smallest available cost**, since that's the value you "save". If you sort all costs in **descending order** and walk through them in groups of 3, the pattern that maximizes savings is:

```
pay, pay, free, pay, pay, free, pay, pay, free, ...
```

i.e. in every consecutive group of 3 (when sorted descending), the **3rd (smallest) one in that group is free**.

## Why frequency array + counting from 100 down to 1 works
Instead of sorting the array directly, the code builds a **frequency/counting array** (`freq[101]`, since `1 <= cost[i] <= 100`), then iterates costs from **100 down to 1**. This effectively simulates walking through the sorted (descending) array without paying the `O(n log n)` cost of a full sort — it's an `O(n + 100)` counting-sort-style trick.

For each distinct cost value `i`, we process it `freq[i]` times (once per occurrence), which is exactly like visiting that value that many times in the descending sorted sequence.

## Why the `bal` (balance) variable of 2
`bal` tracks how many candies we've paid for **since the last free one**.

- Start `bal = 2` (we owe the "free slot" after paying for 2 candies).
- Every time we encounter a candy:
  - If `bal == 0`, it means we've already paid for 2 candies in this group — so **this candy is free**. We reset `bal = 2` and `continue` (skip adding to `ans`).
  - Otherwise, we **pay** for this candy: add it to `ans` and decrement `bal`.

This naturally cycles: pay (bal 2→1), pay (bal 1→0), free (bal reset to 2), pay, pay, free, ...
— exactly matching the "every 3rd (smallest) candy in a descending-sorted triplet is free" pattern.

## Why iterate from high to low (100 → 1)
We must always prioritize paying for the **most expensive remaining** candies first, so that when the "free" turn comes up, it lands on the **cheapest** available candy. Iterating cost values from `100` down to `1` (and within each value, processing all its occurrences) guarantees we always deal with candies in non-increasing order of cost — which is required for the greedy strategy to be correct.

## Step-by-step example
`cost = [1, 6, 5, 8, 8, 2, 3]`

Sorted descending: `[8, 8, 6, 5, 3, 2, 1]`

| Step | Candy | bal before | Action        | bal after | ans |
|------|-------|------------|---------------|-----------|-----|
| 1    | 8     | 2          | pay           | 1         | 8   |
| 2    | 8     | 1          | pay           | 0         | 16  |
| 3    | 6     | 0          | **free**      | 2         | 16  |
| 4    | 5     | 2          | pay           | 1         | 21  |
| 5    | 3     | 1          | pay           | 0         | 24  |
| 6    | 2     | 0          | **free**      | 2         | 24  |
| 7    | 1     | 2          | pay           | 1         | 25  |

**Answer: 25**

This matches the intuition: pay for the two biggest in each triplet, get the smallest of that triplet free.

## Complexity
- **Time:** `O(n + 100)` — one pass to build the frequency array, one pass (bounded by 100) to compute the answer.
- **Space:** `O(100)` → effectively `O(1)`, for the frequency array.

## Why this beats a naive sort-based approach
A typical solution sorts the `cost` array descending (`O(n log n)`) and then loops with the same `bal`-style logic. Using a fixed-size frequency array instead avoids the sorting step entirely, since the cost values are bounded (`1 to 100`), trading a small constant-size array for a full comparison sort — a classic **counting sort** optimization.