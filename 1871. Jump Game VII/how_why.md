# 1871. Jump Game VII

The string contains `0` and `1`, where you can jump only onto positions with `0`, and each jump must be within `[minJump, maxJump]`.

## Approach 1: Sliding Reach Count (`Solution`)

- Treat each reachable index as contributing to future reachable positions.
- Maintain a rolling count of how many reachable indices are in the current jump window.
- For each position `i`, update the window by adding the index that just entered and removing the one that left.
- If the window has at least one reachable index and `s[i] == '0'`, then `i` becomes reachable.

This avoids checking every possible previous index for each position.

## Approach 2: BFS-like DP Expansion (`Solution2`)

- Mark index `0` as reachable.
- For every reachable index, mark all valid `0` positions in its jump range as reachable.
- Use `start` and `end` to avoid rescanning already processed positions.

This is easier to reason about, though the sliding-window version is usually more efficient.

## Example Walkthrough

Suppose:
- `s = "011010"`
- `minJump = 2`
- `maxJump = 3`

We start at index `0`, which is always reachable.

1. From index `0`, we can jump to indices `2` and `3`.
	- `s[2] = '1'`, so index `2` is blocked.
	- `s[3] = '0'`, so index `3` becomes reachable.

2. From index `3`, we can jump to indices `5` and `6`.
	- Index `5` is within bounds and `s[5] = '0'`, so it becomes reachable.
	- Index `6` is out of bounds, so ignore it.

3. Index `5` is the last position, so we can reach the end.

Both solutions discover the same reachable positions, just with different bookkeeping.

## Complexity

- Sliding window approach: `O(n)` time, `O(n)` space.
- Range expansion approach: `O(n)` amortized time, `O(n)` space.
