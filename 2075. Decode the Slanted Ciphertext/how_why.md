# 2075. Decode the Slanted Ciphertext - How Why Explanation

## Goal

Decode a slanted ciphertext string that was written into a matrix with a fixed number of rows, then read diagonally from top-left toward bottom-right. Return the decoded text without trailing spaces.

## Idea in 3 lines

- The encoded text is row-major over a rows x cols matrix, where cols = encodedText.length / rows.
- The original message is recovered by starting from each top-row column and walking diagonally down-right.
- The decoding may end with padding spaces, so trim only trailing spaces.

## Algorithm (matches Solution)

1. If rows is 1, return encodedText directly.
2. Compute cols = n / rows.
3. For each start column c from 0 to cols-1:
	- Set r = 0, j = c.
	- While r < rows and j < cols, append encodedText[r * cols + j], then increment both r and j.
4. Remove trailing spaces from the built string.
5. Return the trimmed result.

## Why it works

- Index formula r * cols + j maps 2D matrix coordinates to the encoded 1D string because the matrix is stored row-major.
- Starting from each top-row column covers every valid diagonal used during encoding.
- Only trailing spaces are artificial padding; internal spaces belong to the message and are preserved.

## Complexity

- Time: O(n), each matrix position is visited at most once while traversing diagonals.
- Space: O(n) for the output builder.

## Note on SolutionFaster

The alternate version explicitly rebuilds the 2D matrix first, then does the same diagonal walk. It is equivalent in logic, but uses extra matrix storage O(rows * cols), while the direct-index method avoids that additional memory.
