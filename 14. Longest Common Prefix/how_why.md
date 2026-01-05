# Recap

Given an array of strings, return the longest common prefix (LCP) shared by all strings. If none exists, return an empty string.

## Intuition

If the strings are sorted lexicographically, only the first and last strings can differ the most. Any common prefix across the entire array must also be a prefix of both the first and the last strings after sorting.

## Approach

1. Sort the array lexicographically.
2. Let `first = v[0]` and `last = v[v.length - 1]`.
3. Compare `first` and `last` character by character until characters differ or one ends; accumulate matching characters.
4. The collected characters form the LCP for the whole array.

## Code (Java)

```java
class Solution {
    public String longestCommonPrefix(String[] v) {
        StringBuilder ans = new StringBuilder();
        Arrays.sort(v);
        String first = v[0];
        String last = v[v.length - 1];

        for (int i = 0; i < Math.min(first.length(), last.length()); i++) {
            if (first.charAt(i) != last.charAt(i)) return ans.toString();
            ans.append(first.charAt(i));
        }
        return ans.toString();
    }
}
```

## Correctness

- Sorting orders strings so that any divergence across the set appears between the lexicographically smallest and largest strings.
- Any prefix common to all strings must be common to `first` and `last`; comparing only these two is sufficient to find the maximal shared prefix.
- The loop stops at the first mismatch or at the shorter string’s end, ensuring the returned prefix is valid for all strings.

## Complexity

- **Time:** $O(n \log n + m)$ where $n$ is the number of strings (sorting cost) and $m$ is the length of the common prefix check between `first` and `last` (bounded by the shorter string length). Without counting sort, the dominant term is sorting.
- **Space:** $O(1)$ auxiliary (in-place sort; output string uses proportional space to prefix length).

## Edge Cases

- Empty array: not expected per problem constraints; would return empty string.
- Single string: sorting is no-op; returns the string itself.
- No common prefix: first comparison fails immediately; returns empty string.
- Mixed lengths: comparison naturally stops at the shorter boundary.

## Takeaways

- Sorting can reduce a multi-string prefix comparison to just two boundary strings.
- For large `n` with small per-string length, this approach is simple and fast enough; for huge per-string length, vertical scanning or prefix-binary-search avoids the sort cost.

## Alternate Approach (Binary Search on Prefix Length)

Instead of sorting, binary search on the possible prefix length from `1` to the minimum string length.

### Steps

1. Compute `minLen` of all strings; if array empty, return `""`.
2. Binary search `low=1, high=minLen`.
3. For a candidate `mid`, take the first `mid` characters of `strs[0]` and check whether every other string starts with that prefix.
4. If all match, move `low = mid + 1` (try longer); otherwise move `high = mid - 1` (try shorter).
5. Final LCP length is `(low + high) / 2`; return that prefix of `strs[0]`.

### Code (Java)*

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        int minLen = Integer.MAX_VALUE;
        for (String s : strs) minLen = Math.min(minLen, s.length());

        int low = 1, high = minLen;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (isCommonPrefix(strs, mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    private boolean isCommonPrefix(String[] strs, int len) {
        String p = strs[0].substring(0, len);
        for (int i = 1; i < strs.length; i++) {
            if (!strs[i].startsWith(p)) return false;
        }
        return true;
    }
}
```

### Why This Can Be Fast

- Avoids sorting cost `O(n \log n)` when that dominates (large `n`, modest lengths).
- Uses $O(\log m)$ prefix-length probes (where $m$ is the minimum string length) instead of scanning every character length linearly; helpful when strings are long but diverge early or when sort is expensive.

### Complexity*

- **Time:** $O(n \cdot m \cdot \log m)$ — each probe checks up to `m` characters across `n` strings, with $\log m$ probes.
- **Space:** $O(1)$ auxiliary.

## Example Walkthrough

1) `['flower', 'flow', 'flight']` → after sort: `['flight', 'flow', 'flower']`; compare `flight` vs `flower`: `f` matches, `l` matches, third char differs → LCP `"fl"`.
2) `['dog', 'racecar', 'car']` → after sort: `['car', 'dog', 'racecar']`; first char differs (`c` vs `d`) so LCP is `""`.

## Related Problems

- [125. Valid Palindrome/how_why.md](125.%20Valid%20Palindrome/how_why.md)
- [139. Word Break/how_why.md](139.%20Word%20Break/how_why.md)
- [140. Word Break II/how_why.md](140.%20Word%20Break%20II/how_why.md)
- [151. Reverse Words in a String/how_why.md](151.%20Reverse%20Words%20in%20a%20String/how_why.md)
