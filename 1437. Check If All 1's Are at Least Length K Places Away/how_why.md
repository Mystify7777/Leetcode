# 1437. Check If All 1's Are at Least Length K Places Away

## Recap

You are given a binary array `nums` and an integer `k`. Return `true` if every pair of consecutive `1` values in the array is separated by at least `k` zeros (i.e. the number of indices strictly between them is `>= k`), otherwise return `false`.

Formally for any two indices `i < j` with `nums[i] = nums[j] = 1` and no other `1` between them, we require `j - i - 1 >= k`.

## Intuition

We only care about gaps between successive `1`s. As we scan the array, the moment we find a `1` we can compare its distance from the previous `1`. If that gap (exclusive of endpoints) is smaller than `k`, we fail early. If we finish scanning without violation, the condition holds.

## Approach

1. Keep `lastIndex` of the previous `1` (initially `-1` meaning none seen yet).
2. Iterate `i` from `0 .. n-1`:
   - If `nums[i] == 1`:
     - If `lastIndex != -1` and `i - lastIndex - 1 < k`, return `false`.
     - Update `lastIndex = i`.
3. Return `true` if no violation was found.

Why `i - lastIndex - 1`? Because `i - lastIndex` counts total distance including both `1`s; subtract 1 to count the zeros between them.

## Code (Java)

```java
class Solution {
    public boolean kLengthApart(int[] nums, int k) {
        int lastIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (lastIndex != -1 && i - lastIndex - 1 < k) {
                    return false;
                }
                lastIndex = i;
            }
        }
        return true;
    }
}
```

## Correctness

- The algorithm examines exactly the gaps between consecutive `1`s; no other index matters.
- If any gap is shorter than `k`, we immediately return `false` (necessary condition violated).
- If all gaps are `>= k`, the definition is satisfied; there is no hidden case because gaps are independent and the array order is fixed.

## Complexity

- Time: `O(n)` one pass.
- Space: `O(1)` auxiliary.

## Edge Cases

- No `1` or only one `1`: vacuously true.
- `k = 0`: always true (gap requirement is empty).
- Adjacent ones (`...1,1...`) with `k > 0`: immediate false.
- Long arrays with sparse ones: handled efficiently since scan is linear.

## Takeaways

- When constraints relate only to consecutive occurrences, track last seen position.
- Subtracting 1 from index distance isolates the number of elements between endpoints.
- Early exit improves performance in negative cases without extra overhead.
