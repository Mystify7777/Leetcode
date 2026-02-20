# How Why Explanation - 761. Special Binary String

## Problem

Given a **special** binary string `s` (same length of `1`s and `0`s, and every prefix has `1`s ≥ `0`s), reorder its non-overlapping special substrings to obtain the lexicographically largest possible special string. Return that largest string.

## Intuition

Special strings behave like balanced parentheses (`1` = `(`, `0` = `)`). Any special string can be decomposed into top-level balanced chunks. Inside each chunk, the substring between the outer `1` and `0` is itself special. The lexicographically largest result comes from recursively maximizing each inner part and then sorting the top-level chunks in descending order before concatenation.

## Brute Force (Not Used)

- Generate all permutations of decomposed chunks and recurse inside each—factorial blow-up.
- Or DFS over all valid reorderings—exponential.

## Approach (Recursive chunking + sort)

1. Scan `s` with a balance counter: `+1` for `1`, `-1` for `0`.
2. Whenever balance returns to 0, you found a top-level special chunk `s[i..j]`.
3. For that chunk, recursively maximize the inner substring `s[i+1..j-1]`, then wrap it with outer `1` and `0`, and push to a list.
4. After processing the full string, sort the list of chunks in **reverse lexicographic** order and concatenate.
5. Return the concatenated result.

Why it works: The Dyck-like structure ensures each top-level chunk is independent for ordering. Lexicographic maximality is achieved by placing larger chunks first; recursion guarantees each chunk is maximized internally.

## Complexity

- Time: $O(n^2 \log k)$ worst-case (substring copies + sorting `k` chunks per level); in practice fine for constraints.
- Space: $O(n)$ for recursion and chunk storage (excluding output).

## Edge Cases

- Length 0 or 2: already maximal.
- Input guaranteed special; no need to validate balance.

## Comparison Table

| Aspect | Recursive + sort (Solution) | Priority-queue variant (AlternateSolution) |
| --- | --- | --- |
| Decomposition | Balance scan into chunks | Same, but pushes to max-heap |
| Sorting | `Collections.sort` reverse | PQ poll in order |
| Base cases | Implicit via recursion | Early returns for short strings |

## Key Snippet (Java)

```java
int count = 0, i = 0;
List<String> res = new ArrayList<>();
for (int j = 0; j < s.length(); j++) {
	count += s.charAt(j) == '1' ? 1 : -1;
	if (count == 0) {
		res.add('1' + makeLargestSpecial(s.substring(i + 1, j)) + '0');
		i = j + 1;
	}
}
Collections.sort(res, Collections.reverseOrder());
return String.join("", res);
```

## Example Walkthrough

`s = "11011000"`

- Scan: top-level chunks are `1100` and `1000`.
- Recurse: `1100` inner `10` → already maximal → `1100`; `1000` inner `00`? Actually `1000` is special with inner `00` → becomes `1000`.
- Sort chunks descending: `1100` > `1000`; concatenate → `11001000`.

## Insights

- Mapping to balanced parentheses clarifies the chunking and recursion.
- Sorting at each level is sufficient; no global backtracking needed.

## References

- Solution implementation in [761. Special Binary String/Solution.java](761.%20Special%20Binary%20String/Solution.java)
