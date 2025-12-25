# Recap

Given a binary array `nums` (only 0s and 1s), find the maximum length of a contiguous subarray with an equal number of 0s and 1s.

## Intuition

Equal 0s and 1s in a subarray means their difference is zero. A classic trick is to treat `0` as `-1` and keep `1` as `+1`. Then any subarray with equal 0s and 1s has sum `0`.

So the problem reduces to: longest subarray with sum `0`.

## Approach (Prefix sum + earliest index)

- Traverse `nums`, maintain a running `count` where:
  - `count += 1` for `1`
  - `count += -1` for `0`
- If the same `count` value appears again at index `i` after previously seen at index `j`, then the subarray `(j+1 .. i)` has sum `0` (equal 0s and 1s). Track the maximum distance `i - j`.
- Seed the map with `count = 0` at index `-1` to allow subarrays starting at index `0`.
- Store only the earliest index per `count` to maximize length.

## Code (Java)

```java
class Solution {
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> first = new HashMap<>();
        int count = 0, best = 0;
        first.put(0, -1); // prefix sum 0 seen before array starts
        for (int i = 0; i < nums.length; i++) {
            count += (nums[i] == 1 ? 1 : -1);
            if (first.containsKey(count)) {
                best = Math.max(best, i - first.get(count));
            } else {
                first.put(count, i);
            }
        }
        return best;
    }
}
```

## Why It Works

- Let `count[i]` be the transformed prefix sum up to `i`. If `count[i] == count[j]` for `i > j`, then the sum from `(j+1 .. i)` is `0`, i.e., equal numbers of 0s and 1s.
- Recording the earliest index for each `count` gives the longest subarray when that `count` repeats.
- Initializing `count=0` at `-1` lets us capture subarrays starting at index `0`.

## Complexity

- **Time:** `O(n)` — single pass.
- **Space:** `O(n)` — in the worst case, `count` takes on `n` distinct values.

## Example

`nums = [0, 1, 0]`

- Transform as `[-1, +1, -1]`
- Prefix `count` values: `[-1, 0, -1]`
- `count=0` seen at `-1` and `1` → subarray length `1 - (-1) = 2` (`[0,1]`)
- `count=-1` seen at `0` and `2` → subarray length `2 - 0 = 2` (`[1,0]`)
- Answer: `2`

## Edge Cases

- All `0`s or all `1`s → no equal subarray except possibly length `0`; result is `0`.
- Multiple repeated patterns — earliest index rule ensures longest span.

## Alternate Formulation

- Instead of mapping `count`, you can directly keep `prefixSum` where `0 -> -1` and `1 -> +1`. The logic is identical: longest subarray with zero sum using earliest index map.

## Takeaways

- Transform `0` to `-1` to convert the problem to longest zero-sum subarray.
- Use a hash map from `count` to earliest index, seeded with `0 -> -1`.
- Update `best` whenever the same `count` reappears.
