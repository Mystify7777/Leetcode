# How & Why: LeetCode 2327 - Number of People Aware of a Secret

---

## Problem Restatement
You are given three integers:
- **n**: total number of days,
- **delay**: the number of days after learning the secret before a person can start sharing it,
- **forget**: the number of days after which a person forgets the secret.

On day 1, only one person knows the secret. Each day:
- Anyone who knows the secret and is past the `delay` period can share it with others.
- Anyone who reaches `forget` days after learning the secret will completely forget it.

Return the number of people who **still know the secret on day n** modulo `1e9+7`.

---

## How to Solve
We use **Dynamic Programming (DP)** to model the spread of the secret:

### Step 1: Define DP State
Let:
```
dp[t] = number of people who learn the secret on day t
```

- Initially, only day 1 has one person who knows the secret:
  ```java
  dp[1] = 1;
  ```

### Step 2: Track How Many Can Share
We maintain a variable `share`:
- `share` = total number of people who **can share the secret** on day `t`.

At each day `t`:
1. If someone learned the secret on `t - delay`, they become eligible to share.
   ```java
   if (t - delay > 0)
       share = (share + dp[t - delay]) % MOD;
   ```

2. If someone learned the secret on `t - forget`, they forget it and must be removed from sharers.
   ```java
   if (t - forget > 0)
       share = (share - dp[t - forget] + MOD) % MOD;
   ```

3. The number of people who learn the secret on day `t` is exactly those who are sharing:
   ```java
   dp[t] = share;
   ```

### Step 3: Count Who Still Knows the Secret on Day n
- At the end, we need to count only those who have not forgotten the secret by day `n`.
- That means we sum over `dp[i]` for:
  ```
  i in [n - forget + 1, n]
  ```

This gives us the total number of people who still know the secret.

---

## Why This Works
1. **DP Modeling**: We use `dp[t]` to store exact counts of new learners each day, preventing overcounting.
2. **Sliding Window Sharing**: The `share` variable works like a sliding window:
   - Add new sharers when they reach `delay`.
   - Remove old sharers when they reach `forget`.
3. **Efficiency**: Instead of recalculating eligible sharers each day (which would be O(n²)), we incrementally update `share` in O(1) per day.
4. **Final Sum**: We only sum the valid window `[n - forget + 1, n]`, ensuring only active secret holders are counted.

---

## Complexity Analysis
- **Time Complexity**: O(n) → We iterate through days once.
- **Space Complexity**: O(n) → For storing `dp`.

---

## Example Walkthrough
Suppose:
```
n = 6, delay = 2, forget = 4
```

- Day 1: dp[1] = 1 (1 person knows the secret)
- Day 2: share = 0 → dp[2] = 0
- Day 3: share += dp[1] (eligible after 2 days) → share = 1 → dp[3] = 1
- Day 4: share += dp[2] (0), share -= dp[0] (ignore) → share = 1 → dp[4] = 1
- Day 5: share += dp[3] (1), share -= dp[1] (forgot after 4 days) → share = 1 → dp[5] = 1
- Day 6: share += dp[4] (1), share -= dp[2] (0) → share = 2 → dp[6] = 2

Now count survivors:
- Valid range = [n - forget + 1, n] = [3, 6]
- dp[3] + dp[4] + dp[5] + dp[6] = 1 + 1 + 1 + 2 = 5

**Answer = 5**

---

## Key Insight
The trick lies in efficiently tracking who can share using a **running balance (`share`)** instead of recomputing eligibility every day. This ensures a clean O(n) solution.

---

