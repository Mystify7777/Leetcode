# 2483. Minimum Penalty for a Shop - Solution Explanation

## Problem Understanding

You need to find the best hour to close a shop to minimize the penalty. The penalty is calculated as:

- For each customer who arrives before closing, you gain 1 point if they were satisfied ('Y') or lose 1 point if dissatisfied ('N')
- For each customer who arrives after closing, you lose 1 point if satisfied ('Y') or gain 1 point if dissatisfied ('N')

## Main Approach (Optimal)

### Code

```java
public int bestClosingTime(String customers) {
    int max_score = 0, score = 0, best_hour = -1;
    for(int i = 0; i < customers.length(); ++i) {
        score += (customers.charAt(i) == 'Y') ? 1 : -1;
        if(score > max_score) {
            max_score = score;
            best_hour = i;
        }
    }
    return best_hour + 1;
}
```

### How It Works

This approach uses the **Kadane's Algorithm** principle to find the maximum subarray sum.

**Key Insight:** Reframe the problem as a cumulative score:

- For each customer, calculate: +1 if 'Y' (happy before closing), -1 if 'N' (unhappy before closing)
- Track the maximum cumulative score - this represents the closing hour that maximizes satisfaction

**Why This Works:**

- When you close at hour `i`, you serve customers `0` to `i` with their given satisfaction
- After hour `i`, you lose 1 point for each 'Y' (they become unhappy) and gain 1 for each 'N' (they become happy)
- The cumulative score at each position tells you the net benefit of closing at that hour
- The hour with the highest cumulative score is optimal

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

## Alternate Approach (Less Efficient)

### Code2

```java
public int bestClosingTime(String customers) {
    byte[] cs = customers.getBytes(java.nio.charset.Charset.forName("ISO-8859-1"));
    int bestTime = -1;
    int customersLeft = 0;
    for (int i = 0; i < cs.length; i++) {
        if (cs[i] == 89) {  // 89 is ASCII code for 'Y'
            customersLeft++;
            if(customersLeft > 0) {
                bestTime = i;
                customersLeft = 0;
            }
        } else {
            customersLeft--;
        }
    }
    return bestTime + 1;
}
```

### How It Works_

This approach is more convoluted and has flawed logic:

- Converts the string to bytes (where 'Y' = 89 in ASCII)
- Uses a `customersLeft` counter that alternates between incrementing and resetting
- Attempts to track the best time, but the logic is fundamentally broken

**Problems with this approach:**

1. **Inefficient:** Converting to bytes is unnecessary overhead
2. **Flawed Logic:** The condition `if(customersLeft > 0)` followed by `customersLeft = 0` means `bestTime` is only updated when we encounter a 'Y' and the counter just became positive
3. **Not General:** This doesn't properly calculate the penalty; it just happens to work for some test cases due to luck, not correct algorithm design
4. **Hard to Understand:** Using byte values (89 for 'Y') is cryptic and reduces readability

### Why the Main Approach is Better

- **Clearer Intent:** The main approach directly calculates cumulative profit/loss
- **Mathematically Sound:** Uses well-known algorithmic principle (Kadane's algorithm)
- **More Maintainable:** Easy to understand and debug
- **Correct:** Always produces the optimal result

## Example Walkthrough

**Input:** `"YYNY"`

**Main Approach:**

```java
i=0, Y: score = 1,  max_score = 1, best_hour = 0
i=1, Y: score = 2,  max_score = 2, best_hour = 1
i=2, N: score = 1,  max_score stays 2
i=3, Y: score = 2,  max_score stays 2
Result: best_hour + 1 = 1 + 1 = 2
```

**Penalty if close at hour 2:** Serve "YY" (+2), miss "NY" (-1 + 1 = 0) = 2 penalty units avoided = optimal!
