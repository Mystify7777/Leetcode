
# How & Why: LeetCode 1877 - Minimize Maximum Pair Sum in Array

## Problem

Given an even-length array, pair up elements into `n/2` pairs. Minimize the maximum pair sum across all pairs. Return that minimized maximum.

## Intuition

- To minimize the worst pair sum, we should pair the smallest numbers with the largest numbers. Sorting and pairing from both ends (two-pointer) achieves the optimal balance: every large element is offset by the smallest remaining, minimizing the maximum sum.

## Brute Force Approach

- **Idea:** Enumerate all perfect matchings and take the best maximum sum.
- **Complexity:** Factorial/exponential; impossible for non-trivial `n`.

## My Approach (Sort + Two Pointers) — from Solution.java

- **Idea:** Sort ascending. Pair `nums[left]` with `nums[right]`, track the max of these sums while moving inward.
- **Complexity:** Time $O(n \log n)$ (sort), Space $O(1)$ extra (in-place sort).
- **Core snippet:**

```java
Arrays.sort(nums);
int l=0, r=n-1, ans=0;
while (l<r) {
	ans = Math.max(ans, nums[l]+nums[r]);
	l++; r--;
}
return ans;
```

## Most Optimal Approach

- Counting sort / frequency two-pointer (Solution2) can achieve $O(n + K)$ where K is value range, but same greedy pairing idea. For arbitrary values, sort+two-pointer is optimal in simplicity and asymptotics.

## Edge Cases

- All equal elements → answer = 2 * value.
- Already sorted or reverse-sorted both work; sorting normalizes.
- Large values: use `int` per constraints; `ans` fits in `int`.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Exhaustive pairing | Try all matchings | Exponential | Exponential | Impractical |
| Sort + two pointers (used) | Pair min with max | O(n log n) | O(1) extra | Simple and optimal |
| Counting-sort two-pointer | Use freq array to avoid full sort | O(n+K) | O(K) | Good when K is small |

## Example Walkthrough

`nums = [3,5,2,3]`

- Sort → [2,3,3,5]
- Pairs: (2,5)=7, (3,3)=6 → max=7 → answer 7.

## Insights

- Pairing extremes minimizes the maximum pair sum; same greedy works for minimizing maximum sum in similar pairing problems.

## References to Similar Problems

- 435. Assign Cookies (greedy pairing of sorted lists)
- 948. Bag of Tokens (two-pointer greedies on sorted arrays)
