# 3093. Longest Common Suffix Queries

For each query, we want the index of the shortest word in `wordsContainer` that shares the longest common suffix with that query. If there are ties, the smaller index wins.

## Approach 1: Reverse trie with best candidate tracking (`Solution`)

- Insert every container word into a trie built from right to left.
- At each trie node, store the best word seen so far: the shortest length, and if tied, the smallest index.
- While inserting a word, update the best candidate at the root and every node on its reversed path.
- For a query, walk the trie from the end of the string until the path breaks.
- The last visited node stores the answer for the longest suffix match available.

## Approach 2: Bounded reverse trie (`Solution2`)

- First find the maximum query length.
- Build a reverse trie from container words, but only keep as much suffix depth as any query could ever need.
- Each trie node stores the index of the shortest word reachable at that suffix.
- For each query, traverse from the end and return the stored best index at the deepest reachable node.

## Why it works

Both solutions convert suffix matching into prefix matching on reversed strings.
That lets us share common suffix paths efficiently and attach the best answer directly to each trie node.

## Example Walkthrough

Suppose:
- `wordsContainer = ["abcd", "bcd", "xbcd", "xyz"]`
- `wordsQuery = ["cd", "zzcd", "yz"]`

The trie stores reversed words, so the suffix `cd` is represented by the path `d -> c`.

1. Build the trie from `wordsContainer`.
	- `"abcd"` has length `4`.
	- `"bcd"` has length `3`.
	- `"xbcd"` also ends with `bcd`, length `4`.
	- `"xyz"` is its own suffix path.

2. Query `"cd"`.
	- Walk from the end: `d`, then `c`.
	- That path exists for words ending in `cd`.
	- Among matching words, the shortest is `"bcd"`, so its index is returned.

3. Query `"zzcd"`.
	- Walk from the end: `d`, `c`.
	- The longer prefix `zz` does not matter once the suffix path breaks.
	- The best match is still the shortest word ending in `cd`, so the same index is returned.

4. Query `"yz"`.
	- Walk from the end: `z`, then `y`.
	- That path matches `"xyz"`.
	- So the answer is the index of `"xyz"`.

This shows how the trie naturally returns the shortest container word with the longest matching suffix.

## Complexity

- Time: roughly `O(total container chars + total query chars)`
- Space: `O(total container chars)` for the trie
