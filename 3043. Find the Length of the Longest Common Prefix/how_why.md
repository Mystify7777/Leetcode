# 3043. Find the Length of the Longest Common Prefix

Goal: find the maximum digit-prefix length shared by any number from `arr1` and any number from `arr2`.

## Approach 1: Prefix Set (Solution)

- Insert every decimal prefix of each number in `arr1` into a hash set.
- For each number in `arr2`, strip digits from right to left and check when the current prefix exists in the set.
- The first match while shrinking is the longest prefix for that number.

Why it works:
- Any common prefix must be a prefix of some number in `arr1`.
- By storing all such prefixes, membership check is `O(1)` average.

## Approach 2: Trie with Math Digits (Solution2)

- Build a digit trie from `arr1`.
- For each number in `arr2`, walk digits from most significant to least significant and count matched depth.
- Keep the global maximum depth.

This avoids storing many integer prefixes explicitly and uses trie traversal for prefix length.

## Approach 3: Trie with String Digits (Solution3)

- Another trie variant using string conversion for simplicity.
- Insert digits of each number from `arr1`.
- Search digits of each number from `arr2` to compute matched prefix length.

## Example Walkthrough

Suppose:
- `arr1 = [1234, 98, 561]`
- `arr2 = [12, 5678, 90]`

Using the prefix-set approach:

1. Build all prefixes from `arr1`:
	 - `1234` -> `1234`, `123`, `12`, `1`
	 - `98` -> `98`, `9`
	 - `561` -> `561`, `56`, `5`

	 Set now contains: `{1234, 123, 12, 1, 98, 9, 561, 56, 5}`

2. Check each value in `arr2`:
	 - `12` (len = 2):
		 - `12` is in set -> best for this number is `2`
	 - `5678` (len = 4):
		 - `5678` not in set
		 - `567` not in set
		 - `56` in set -> best for this number is `2`
	 - `90` (len = 2):
		 - `90` not in set
		 - `9` in set -> best for this number is `1`

3. Global maximum prefix length is `max(2, 2, 1) = 2`.

So the answer is `2`.

## Complexity

Let `n = arr1.length`, `m = arr2.length`, and `d` be max digits per number (at most 9 here).

- Prefix-set approach: `O((n + m) * d)` time, `O(n * d)` space.
- Trie approaches: `O((n + m) * d)` time, `O(n * d)` space.
