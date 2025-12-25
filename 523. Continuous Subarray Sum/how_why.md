# Recap

Given an integer array `nums` and an integer `k`, determine whether there exists a continuous subarray of length at least 2 whose sum is a multiple of `k`.

Return `true` if such a subarray exists, otherwise `false`.

## Intuition

Use prefix sums with modulo arithmetic. Let $S(i)$ be the sum of the first `i` elements (`S(0) = 0`). A subarray `nums[l..r]` has sum $S(r+1) - S(l)$. If this sum is a multiple of $k$, then:

$$ S(r+1) \equiv S(l) \pmod{k} $$

So, if two prefix sums have the same remainder modulo $k$ at indices whose distance is at least 2, the subarray between them has sum divisible by $k$.

## Approach (HashMap on remainders)

Store the earliest index where each remainder `rem = S(i) % k` occurs. While scanning:

- Maintain running sum `sum` and reduce `sum %= k` each step.
- Two success cases:
  1. `sum == 0 && i > 0`: the prefix up to index `i` (length ≥ 2) is divisible by `k`.
  2. Previously seen the same remainder `sum` at index `j` and `i - j > 1`: the subarray `(j+1..i)` length ≥ 2 is divisible by `k`.
- Record only the first occurrence of each remainder to maximize subarray length.

### Code (Java)

```java
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {      
        // maintain a hash map to store <sum % k, index>
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sum %= k; 
            // case 1: prefix of length >= 2 divisible by k
            if (sum == 0 && i > 0) {
                return true;
            }
            // case 2: same remainder seen before far enough apart
            if (map.containsKey(sum) && i - map.get(sum) > 1) { 
                return true;
            }
            // store earliest index for this remainder
            if (!map.containsKey(sum)) {
                map.put(sum, i); 
            }
        }
        return false;
    }
}
```

## Why It Works

- If `sum % k` repeats at indices `j` and `i` with `i - j > 1`, then
  $$ S(i+1) \equiv S(j+1) \pmod{k} \Rightarrow S(i+1) - S(j+1) \equiv 0 \pmod{k} $$
  which means the subarray from `(j+1 .. i)` has sum divisible by `k` and length at least 2.
- The `sum == 0 && i > 0` case captures a divisible prefix of length ≥ 2 without needing the map.
- Storing the earliest index of each remainder ensures we check the longest possible distance first (shorter ones also satisfy the condition, but earliest gives the best chance).

## Complexity

- **Time:** `O(n)` — single pass over `nums` with `O(1)` map ops per step.
- **Space:** `O(min(n, k))` — at most one entry per remainder; upper bounded by `n` distinct remainders in practice.

## Example

`nums = [23, 2, 4, 6, 7]`, `k = 6`

- Running `sum % k`: `[5, 1, 5, 5, 0]`
- Remainder `5` seen at index `0` and again at `2` with gap `2` → subarray `[2,4]` sums to `6`, divisible by `6`.
- Or `sum == 0` at index `4` → prefix sum divisible by `6` with length ≥ 2.

## Edge Cases

- `nums` length < 2 → always `false` (constraint requires at least 2 elements).
- Consecutive zeros: if `nums[i] == 0` and `nums[i+1] == 0`, sum of `[i, i+1]` is `0`, which is divisible by any non-zero `k` → returns `true` via the two cases above.
- `k == 0`: the condition reduces to “exists subarray of length ≥ 2 with sum `0`.” Handle by checking consecutive zeros. The provided code assumes valid `k != 0`; to fully support `k == 0`, guard before using `% k`.
- Negative `k`: typically normalize remainder to non-negative (`(sum % k + k) % k`). Problem usually guarantees `k > 0`.

## Alternate Approach (Prefix sums + nested scan)

```java
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        int n=nums.length;

        int p[] = new int[n];
        p[0] = nums[0];
        for(int i=1; i<n ;i++) {
            p[i] = p[i-1] + nums[i];
        }

        for(int r= 1; r<n ;r++) {
            if(nums[r] == 0 && nums[r-1] == 0) return true;

            if(p[r]%k == 0) return true;

            for(int l = r - 2; l>=0 && p[r]-p[l]>=k; l--) {
                if((p[r] - p[l])%k ==0) {
                    return true;
                }
            }

        }

        return false;
    }
}
```

- **Idea:** Build prefix sums `p[]` and check all eligible `(l, r)` pairs for divisibility.
- **Time:** Worst-case `O(n^2)` due to the inner loop scanning `l` per `r`.
- **Space:** `O(n)` for the prefix array.

### Comparison

- **HashMap modulo approach:** `O(n)` time, `O(min(n, k))` space; finds repeated remainders to certify divisible subarrays without scanning pairs.
- **Nested scan approach:** Simpler conceptually but slower (`O(n^2)`), suitable only for small inputs.

## Takeaways

- Remainder repetition on prefix sums is the key: equal remainders imply the difference is a multiple of $k$.
- Store the earliest index per remainder and enforce subarray length ≥ 2 with `i - j > 1`.
- Prefer the hashmap approach for efficiency and correctness on large arrays.
