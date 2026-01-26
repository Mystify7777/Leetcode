# How Why - Explanation 1200. Minimum Absolute Difference

[1200. Minimum Absolute Difference](https://leetcode.com/problems/minimum-absolute-difference/)

## Problem

Given an integer array `arr`, return all pairs `[a, b]` with the minimum absolute difference `|a - b|`, sorted by their order of appearance after sorting the array.

## Intuition

After sorting, the smallest absolute difference must occur between some adjacent elements. We can scan once to find the minimum gap and collect all adjacent pairs that achieve it.

## Brute Force (Not Used)

- Check every unordered pair, compute `|a - b|`, track the minimum, then collect all pairs with that value.
- Complexity: $O(n^2)$ time, $O(1)$ extra space beyond output.

## Approach (Sort + Adjacent Scan)

1. Sort `arr` ascending.
2. One pass over adjacent pairs: compute `diff = arr[i + 1] - arr[i]`.
3. If `diff < minDiff`, reset the result list to this pair; if `diff == minDiff`, append the pair.
4. Return the collected pairs.

Why it works: sorting guarantees the minimal absolute difference is realized by neighbors; non-adjacent elements have equal or larger gaps.

## Complexity

- Time: $O(n \log n)$ for sorting + $O(n)$ scan.
- Space: $O(1)$ extra (output excluded) if sorting in place.

## Optimality

The scan is linear; sorting dominates. Without assumptions on value range, $O(n \log n)$ is optimal. The counting-sort variant in `Solution2` achieves $O(n + R)$ when the value range $R$ is small.

## Edge Cases

- `n == 2`: single adjacent pair is the answer.
- Duplicates: a zero gap is immediately minimal; collect all zero-gap pairs.
- Negative values: sorting handles them; differences remain non-negative due to ordering.

## Comparison Table

| Aspect | Sort + scan | Counting sort + scan |
| --- | --- | --- |
| Time | $O(n \log n)$ | $O(n + R)$ with range $R$ |
| Space | $O(1)$ extra (in-place sort) | $O(R)$ for buckets |
| Stability need | Not required | Not required |
| Worst-case behavior | Deterministic | Depends on $R$ magnitude |

## Key Snippet (Java)

```java
Arrays.sort(arr);
int min = Integer.MAX_VALUE;
List<List<Integer>> res = new ArrayList<>();
for (int i = 0; i < arr.length - 1; i++) {
    int diff = arr[i + 1] - arr[i];
    if (diff < min) {
        min = diff;
        res.clear();
        res.add(Arrays.asList(arr[i], arr[i + 1]));
    } else if (diff == min) {
        res.add(Arrays.asList(arr[i], arr[i + 1]));
    }
}
return res;
```

## Example Walkthrough

Input: `arr = [4, 2, 1, 3]`

1. Sort -> `[1, 2, 3, 4]`
2. Adjacent diffs: `(1,2)=1`, `(2,3)=1`, `(3,4)=1`; minDiff = 1.
3. Collect pairs: `[1,2]`, `[2,3]`, `[3,4]`.
4. Output: `[[1, 2], [2, 3], [3, 4]]`.

## Insights

- The minimum absolute difference is realized by adjacent elements after sorting; this collapses pair checking to a single pass.
- Bucketizing (counting sort) is attractive only when the numeric range is tight; otherwise, comparison sort is simpler and robust.

## References

- Solution implementation in [1200. Minimum Absolute Difference/Solution.java](1200.%20Minimum%20Absolute%20Difference/Solution.java)
