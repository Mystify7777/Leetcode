# 1590. Make Sum Divisible by P — how/why

## Recap

Given an array of positive integers `nums` and a positive integer `p`, remove the smallest subarray (possibly empty) such that the sum of the remaining elements is divisible by `p`. Return the length of the smallest subarray to remove, or `-1` if it's impossible.

## Intuition

Let `S` be the total sum. If `S % p == 0`, we're done (return 0). Otherwise, let `rem = S % p`. We need to find the smallest subarray with sum `≡ rem (mod p)` to remove. Using prefix sums: `sum[i..j] = prefix[j+1] - prefix[i]`. We want `(prefix[j+1] - prefix[i]) % p == rem`. Rearranging: `prefix[i] % p == (prefix[j+1] - rem) % p`. For each position `j`, we look for the most recent `i` where this holds, using a hashmap to track prefix remainders.

## Approach

1. **Compute total sum** and find `rem = totalSum % p`.
   - If `rem == 0`, return 0 (already divisible).

2. **Use prefix sum with modulo tracking**:
   - Maintain a hashmap `prefixMod` mapping `(prefix % p)` → index.
   - Initialize: `prefixMod[0] = -1` (handles case where prefix itself equals `rem`).

3. **Iterate through array**:
   - Update `prefixSum += nums[i]`.
   - Compute `currentMod = prefixSum % p`.
   - Find `targetMod = (currentMod - rem + p) % p`.
     - This is the remainder we need at some earlier prefix position.
   - If `prefixMod` contains `targetMod`:
     - Subarray length = `i - prefixMod[targetMod]`.
     - Update `minLength`.
   - Store `prefixMod[currentMod] = i`.

4. **Return result**: If `minLength == n`, return `-1` (impossible); otherwise return `minLength`.

**Key insight**: `(prefix[j+1] - prefix[i]) % p == rem` is equivalent to `prefix[i] % p == (prefix[j+1] - rem) % p`. By tracking prefix remainders, we can find valid subarrays in O(n) time.

## Code (Java)

```java
class Solution {
    public int minSubarray(int[] nums, int p) {
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // Find remainder when total sum is divided by p
        int rem = (int)(totalSum % p);
        if (rem == 0) return 0; // Already divisible

        HashMap<Integer, Integer> prefixMod = new HashMap<>();
        prefixMod.put(0, -1);  // Handle full prefix case
        long prefixSum = 0;
        int minLength = nums.length;

        for (int i = 0; i < nums.length; ++i) {
            prefixSum += nums[i];
            int currentMod = (int)(prefixSum % p);
            int targetMod = (currentMod - rem + p) % p;

            if (prefixMod.containsKey(targetMod)) {
                minLength = Math.min(minLength, i - prefixMod.get(targetMod));
            }

            prefixMod.put(currentMod, i);
        }

        return minLength == nums.length ? -1 : minLength;
    }
}
```

## Correctness

- **Modular arithmetic**: For any subarray `[i+1..j]`, its sum modulo `p` equals `(prefix[j] - prefix[i]) % p`. We need this to equal `rem`.

- **Target calculation**: From `(currentMod - prefix[i] % p) % p == rem`, we derive `prefix[i] % p == (currentMod - rem + p) % p = targetMod`.

- **Hashmap lookup**: By storing the most recent index for each remainder, we find the longest prefix (shortest subarray) that matches our target.

- **Initialization**: `prefixMod[0] = -1` handles the case where the prefix sum itself has remainder `rem` (remove prefix from index 0 to current).

- **Impossible case**: If no valid subarray exists, `minLength` remains `n`, and we return `-1`.

## Complexity

- **Time**: `O(n)` — single pass with O(1) hashmap operations per element.
- **Space**: `O(min(n, p))` — hashmap stores at most `min(n, p)` distinct remainders.

## Edge Cases

- Sum already divisible by `p`: return 0.
- Single element with `nums[0] % p == totalSum % p`: return 1.
- Entire array must be removed: return `-1` (impossible since we need non-empty remainder).
- Large numbers: use `long` for sums to prevent overflow.
- `p = 1`: sum always divisible, return 0.
- No valid subarray: return `-1`.
- Multiple subarrays with same remainder: hashmap keeps most recent index, giving shortest subarray for each position.

## Takeaways

- **Prefix sum + hashmap pattern**: Classic technique for subarray sum problems with modulo constraints.
- **Modular arithmetic rearrangement**: Transform `(a - b) % p == target` into `b % p == (a - target) % p` to enable hashmap lookup.
- **Index tracking**: Store indices (not counts) to compute subarray lengths.
- **Careful with modulo of negative numbers**: Use `(x % p + p) % p` to ensure non-negative remainders.
- This pattern extends to other "find subarray with sum property" problems (e.g., sum equals k, sum divisible by k).

## Alternative (Early Exit Optimization)

```java
class Solution {
    public int minSubarray(int[] nums, int p) {
        long sum = 0;
        for (int num : nums) sum += num;
        
        long target = sum % p;
        if (target == 0) return 0;
        
        // Check single elements first (early exit)
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % p == target) return 1;
        }
        
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        sum = 0;
        int res = Integer.MAX_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int a = (int)(sum % p);
            int b = (int)((sum - target + p) % p);
            
            if (map.containsKey(b)) {
                res = Math.min(res, i - map.get(b));
            }
            map.put(a, i);
        }
        
        return res >= nums.length ? -1 : res;
    }
}
```

**Trade-off**: Adds an extra O(n) pass to check single elements, which can give early exit for best case but adds overhead. The main algorithm is already optimal at O(n).
