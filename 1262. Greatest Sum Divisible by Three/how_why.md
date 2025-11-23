# Recap

Given an integer array `nums`, return the maximum possible sum of elements of the array such that it is divisible by three. You can select any subset of elements (including none or all).

## Intuition

Start by computing the total sum of all elements. If that sum is already divisible by 3, we're done. Otherwise, the sum has a remainder (1 or 2). To make it divisible by 3, we must remove the smallest subset of elements whose remainders sum to the current remainder modulo 3. The key insight: we only need to track the two smallest elements with remainder 1 and the two smallest with remainder 2.

## Approach

1. **Compute total sum** and track:
   - `min1`, `min11`: smallest and second-smallest elements with `x % 3 == 1`.
   - `min2`, `min22`: smallest and second-smallest elements with `x % 3 == 2`.

2. **Check remainder** `rem = sum % 3`:

   - **Case `rem == 0`**: Sum is already divisible, return `sum`.

   - **Case `rem == 1`**: Need to remove elements totaling remainder 1 (mod 3). Two options:
     - Remove one element with remainder 1: cost = `min1`.
     - Remove two elements with remainder 2 (since 2+2 ≡ 1 mod 3): cost = `min2 + min22`.
     - Choose minimum cost removal.

   - **Case `rem == 2`**: Need to remove elements totaling remainder 2 (mod 3). Two options:
     - Remove one element with remainder 2: cost = `min2`.
     - Remove two elements with remainder 1 (since 1+1 ≡ 2 mod 3): cost = `min1 + min11`.
     - Choose minimum cost removal.

3. **Return** `sum - minRemoval`, or `0` if removal is impossible (no valid elements exist).

## Code (Java)

```java
class Solution {
    public int maxSumDivThree(int[] nums) {
        int sum = 0;

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int min11 = Integer.MAX_VALUE;
        int min22 = Integer.MAX_VALUE;

        for (int x : nums) {
            sum += x;
            int r = x % 3;

            if (r == 1) {
                if (x < min1) { min11 = min1; min1 = x; }
                else if (x < min11) min11 = x;
            } 
            else if (r == 2) {
                if (x < min2) { min22 = min2; min2 = x; }
                else if (x < min22) min22 = x;
            }
        }

        int rem = sum % 3;

        if (rem == 0) return sum;

        if (rem == 1) {
            int remove1 = min1;
            int remove2 = (min2 == Integer.MAX_VALUE || min22 == Integer.MAX_VALUE)
                            ? Integer.MAX_VALUE : min2 + min22;
            int remove = Math.min(remove1, remove2);
            return (remove == Integer.MAX_VALUE) ? 0 : sum - remove;
        } 
        else {
            int remove1 = min2;
            int remove2 = (min1 == Integer.MAX_VALUE || min11 == Integer.MAX_VALUE)
                            ? Integer.MAX_VALUE : min1 + min11;
            int remove = Math.min(remove1, remove2);
            return (remove == Integer.MAX_VALUE) ? 0 : sum - remove;
        }
    }
}
```

## Correctness

- **Greedy removal is optimal:** When the sum's remainder is 1, we need to reduce it by 1 (mod 3). The cheapest way is either removing one remainder-1 element or two remainder-2 elements (2+2 ≡ 4 ≡ 1 mod 3). Removing the smallest such elements maximizes the remaining sum.

- **Exhaustiveness:** By tracking two smallest elements of each remainder type, we can always form the optimal removal set when needed (either 1 element or 2 elements).

- **Remainder arithmetic:**
  - To cancel remainder 1: remove 1 element (rem=1) or 2 elements (rem=2 each).
  - To cancel remainder 2: remove 1 element (rem=2) or 2 elements (rem=1 each).

- **Edge case handling:** If removal is impossible (e.g., all elements are divisible by 3 but sum remainder is nonzero—which can't happen, or insufficient elements of required type), return 0.

## Complexity

- **Time:** `O(n)` — single pass through the array.
- **Space:** `O(1)` — constant extra variables.

## Edge Cases

- All elements divisible by 3: sum is divisible, return sum.
- Single element not divisible by 3: may need to exclude it, return 0.
- Array with only remainder-1 elements and sum remainder is 2: need to remove two, check if two exist.
- Array with only remainder-2 elements and sum remainder is 1: need to remove two, check if two exist.
- Large values: since we only track remainders and smallest values, no overflow concerns (sum fits in int for typical constraints).
- Empty array: sum = 0, divisible by 3, return 0.

## Takeaways

- **Remainder-based optimization:** Many divisibility problems reduce to tracking element remainders and making greedy choices.
- **Tracking k smallest:** Maintaining the two smallest elements of each category allows efficient greedy removal.
- **Modular arithmetic:** Understanding that 1+1 ≡ 2 and 2+2 ≡ 1 (mod 3) gives the two removal strategies.
- **Maximize by minimal removal:** When starting from the total sum, removing the smallest disruptive elements yields the maximum valid result.
- This approach generalizes to other moduli (e.g., divisibility by 5, 7) with appropriate remainder tracking.

## Alternative (Dynamic Programming)

A DP approach maintains `dp[i]` = maximum sum achievable with remainder `i` (mod 3):

```java
class Solution {
    public int maxSumDivThree(int[] nums) {
        int[] dp = new int[3];
        for (int x : nums) {
            int[] temp = dp.clone();
            for (int i = 0; i < 3; i++) {
                int newRem = (temp[i] + x) % 3;
                dp[newRem] = Math.max(dp[newRem], temp[i] + x);
            }
        }
        return dp[0];
    }
}
```

- **Time:** `O(n)` with `O(1)` space (3 states).
- **Trade-off:** More general (works for any subset selection problem with modular constraints), but the greedy approach is simpler for this specific problem since we can always use all elements and then remove minimal ones.
