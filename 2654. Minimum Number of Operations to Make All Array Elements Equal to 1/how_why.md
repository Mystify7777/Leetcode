# 2654. Minimum Number of Operations to Make All Array Elements Equal to 1

## Recap

You have a positive integer array `nums`. In one operation you choose an index `i` (where `0 <= i < n - 1`) and replace **either** `nums[i]` or `nums[i+1]` with `gcd(nums[i], nums[i+1])`. Repeat until all elements are `1`, or determine if it's impossible. Return the minimum number of operations required, or `-1` if impossible.

## Key Insight (GCD Properties)

1. **Eventual Feasibility**: If the GCD of the entire array is greater than `1`, then every element shares a common factor > 1. Since the gcd operation can only produce divisors of the participating numbers, we can never reduce any element to `1`. Hence impossible → return `-1`.

2. **Existing Ones**: If at least one `1` already exists in the array, we can "spread" it to adjacent elements via `gcd(1, x) = 1`. Each non-one element needs exactly one operation to become `1` by pairing with an existing `1` (we can spread the `1` left/right step by step). Total operations = `n - num1` where `num1` is the count of existing ones.

3. **Creating the First One**: If no `1` exists but the overall GCD is `1`, we must first create a `1` by repeatedly taking gcd within some contiguous subarray until we obtain `gcd = 1`. The minimum number of operations to create the first `1` in a subarray of length `k` is `k - 1` (replace elements iteratively until one becomes `1`). After that, we need `n - 1` more operations to spread that single `1` to all other positions.

## Approach

```text
1. Count existing ones (num1) and compute overall GCD (g).
2. If num1 > 0: answer = n - num1 (spread existing ones).
3. Else if g > 1: impossible → return -1.
4. Else (g == 1 but no 1s yet):
     Find the shortest contiguous subarray with gcd = 1.
     Let its length = minLen.
     Operations to create first 1 = minLen - 1.
     Operations to spread that 1 to rest = n - 1.
     Total = (minLen - 1) + (n - 1) = minLen + n - 2.
```

## Code (Java)

```java
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int num1 = 0;
        int g = 0;
        for (int x : nums) {
            if (x == 1) num1++;
            g = gcd(g, x);
        }
        if (num1 > 0) return n - num1;
        if (g > 1) return -1;

        int minLen = n;
        for (int i = 0; i < n; i++) {
            int currentGcd = 0;
            for (int j = i; j < n; j++) {
                currentGcd = gcd(currentGcd, nums[j]);
                if (currentGcd == 1) {
                    minLen = Math.min(minLen, j - i + 1);
                    break;
                }
            }
        }
        return minLen + n - 2;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
```

## Correctness

- **GCD of entire array > 1 ⇒ impossible**: No sequence of gcd operations can break the common factor.
- **At least one 1 exists**: Each non-one can be turned into `1` by a single gcd with an adjacent `1` (we propagate the `1` through the array). Total = number of non-ones.
- **No 1 but overall GCD = 1**: We must manufacture a `1` first. The minimum cost subarray with gcd = 1 of length `k` requires `k - 1` operations to collapse it into a single `1` (each operation reduces the count of distinct elements by merging two into their gcd). Then `n - 1` more operations spread that `1` across all positions. Formula: `(k - 1) + (n - 1) = k + n - 2`.

## Complexity

- **Time**: `O(n^2 * log(max(nums)))` — two nested loops over subarrays, each gcd call is `O(log(max))`.
- **Space**: `O(1)` auxiliary.

## Edge Cases

- All elements already `1`: `num1 = n` → answer = `0`.
- Single element array with value `1`: `n = 1`, `num1 = 1` → answer = `0`.
- Array `[2, 4, 8]`: overall gcd = 2 → impossible → return `-1`.
- Array `[2, 3]`: no `1`, but gcd = 1; shortest subarray with gcd = 1 has length `2` → operations = `2 + 2 - 2 = 2`.
  - Step 1: replace one element with gcd(2,3)=1 → `[1, 3]` (1 op).
  - Step 2: replace 3 with gcd(1,3)=1 → `[1, 1]` (1 op). Total = 2. ✓

## Why Shortest Subarray Matters

Among all subarrays with gcd = 1, choosing the shortest minimizes `k - 1` (the cost to create the first `1`). Since the subsequent spread cost `n - 1` is fixed, minimizing `k` minimizes total operations.

## Takeaways

- Leverage GCD properties: if `gcd(entire array) > 1`, no operation chain can reach `1`.
- Once you have a single `1`, it spreads linearly via adjacent gcd operations.
- When no `1` exists, find the minimal contiguous subarray that can produce `gcd = 1` and collapse it first.
- The problem reduces to a combinatorial/greedy search over subarrays for the shortest gcd-1 segment.
