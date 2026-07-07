# 2126. Destroying Asteroids

We start with an initial mass and a list of asteroids. If our current mass is at least the asteroid's size, we can destroy it and gain that asteroid's mass.

## Approach 1: Frequency-based greedy (`Solution`)

- Find the maximum asteroid size.
- Count how many asteroids of each size exist.
- Process asteroid sizes from smallest to largest.
- If the current mass is smaller than the next asteroid size, the process fails.
- Otherwise, absorb all asteroids of that size and increase the mass accordingly.

Why it works:
- Destroying smaller asteroids first is always safe and helps grow mass as early as possible.
- Since only the total mass matters, grouping equal sizes with a frequency array is enough.

## Approach 2: Two-pointer style greedy (`Solution2`)

- Keep a running total mass.
- Try to consume asteroids from the left when possible.
- If the left asteroid is too large, compare with the right side and rearrange to keep progress moving.
- The `previous_sum` check detects when no progress is made anymore.

This version is more experimental, but it follows the same greedy goal: keep growing mass whenever possible.

## Example Walkthrough

Suppose:
- `mass = 10`
- `asteroids = [3, 9, 19, 5, 21]`

Using the frequency-based greedy idea:

1. Start with `currentmass = 10`.
2. Process asteroid size `3`.
	- `10 >= 3`, so destroy it.
	- New mass becomes `13`.
3. Process asteroid size `5`.
	- `13 >= 5`, so destroy it.
	- New mass becomes `18`.
4. Process asteroid size `9`.
	- `18 >= 9`, so destroy it.
	- New mass becomes `27`.
5. Process asteroid size `19`.
	- `27 >= 19`, so destroy it.
	- New mass becomes `46`.
6. Process asteroid size `21`.
	- `46 >= 21`, so destroy it.
	- New mass becomes `67`.

Since every asteroid can be destroyed in increasing order of size, the answer is `true`.

The key point is that each successful destruction makes the next one easier, so handling smaller asteroids first is optimal.

## Complexity

- Frequency approach: `O(n + M)` time, where `M` is the largest asteroid size.
- Two-pointer approach: depends on the input order, but is intended to be linear-ish in practice.
- Space: `O(M)` for the frequency array in the first solution.
