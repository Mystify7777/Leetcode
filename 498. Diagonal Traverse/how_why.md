# Recap

Given an `m x n` matrix, return an array containing all elements in the order of diagonal traversal: alternating between moving up-right (↗︎) and down-left (↙︎), starting from the top-left.

Example direction pattern on indices: `(0,0) ↗︎ (0,1) ↙︎ (1,0) ↗︎ (2,0) ...` while bouncing off borders.

## Intuition

Every step moves along a diagonal. The parity of `row + col` determines the current direction:

- If `row + col` is even → move up-right (↗︎).
- If `row + col` is odd → move down-left (↙︎).

When a move would cross a border, we “bounce” by changing direction and stepping along the border (either down or right, depending on which border was hit).

## Approach (Iterative Simulation)

Maintain `row`, `col`, and write into `result[i]` for `i = 0..m*n-1`:

1. Write `matrix[row][col]` to output.
2. If `(row + col) % 2 == 0` (moving ↗︎):
   - If `col == n - 1`: go down → `row++`.
   - Else if `row == 0`: go right → `col++`.
   - Else: go up-right → `row--, col++`.
3. Else (moving ↙︎):
   - If `row == m - 1`: go right → `col++`.
   - Else if `col == 0`: go down → `row++`.
   - Else: go down-left → `row++, col--`.
4. Repeat until we place `m * n` elements.

This matches the control flow in the provided solution and ensures no out-of-bounds access while preserving the required order.

### Code (Java)

```java
public class Solution {
	public int[] findDiagonalOrder(int[][] matrix) {
		if (matrix == null || matrix.length == 0) return new int[0];

		int m = matrix.length, n = matrix[0].length;
		int[] result = new int[m * n];
		int row = 0, col = 0;

		for (int i = 0; i < m * n; i++) {
			result[i] = matrix[row][col];

			if ((row + col) % 2 == 0) {
				if (col == n - 1) row++;
				else if (row == 0) col++;
				else { row--; col++; }
			} else {
				if (row == m - 1) col++;
				else if (col == 0) row++;
				else { row++; col--; }
			}
		}

		return result;
	}
}
```

## Alternate Approach (Recursive)

Another version drives traversal via a recursive helper with a direction flag `dir = +1` for ↗︎ and `-1` for ↙︎. At each step:

- Write `grid[i][j]` to `res[itr]` and increment `itr`.
- Compute the next cell by `dir` (↗︎: `i-1, j+1`; ↙︎: `i+1, j-1`).
- If the next cell is out-of-bounds, snap to the appropriate border cell and flip `dir`.
- Recurse until `itr == m*n`.

### Differences and Performance

- **Direction control:**
  - Iterative uses parity `row + col` to infer direction and a compact boundary check.
  - Recursive explicitly tracks `dir` and resolves multiple boundary cases via conditionals.

- **Time complexity:** Both visit each cell exactly once → `O(m * n)`.

- **Space complexity:**
  - Iterative: `O(1)` extra space (besides output).
  - Recursive: `O(m * n)` worst-case call stack depth, plus minor fields (`res`, `itr`, `dir`).

- **Practical performance:** Iterative tends to be faster due to no function-call overhead or recursion stack management. The recursive approach is more verbose and can be harder to reason about and debug, though it encodes the same movement rules.

## Example Walkthrough

Matrix:

```math
1  2  3
4  5  6
7  8  9
```

Traversal order: `1, 2, 4, 7, 5, 3, 6, 8, 9`.

Steps (iterative reasoning):

- `(0,0)=1` ↗︎ hits top → right to `(0,1)`.
- `(0,1)=2` ↙︎ to `(1,0)=4`, then ↙︎ hits left → down to `(2,0)=7`.
- `(2,0)` ↗︎ to `(1,1)=5`, ↗︎ to `(0,2)=3`, ↗︎ hits right → down to `(1,2)=6`.
- `(1,2)` ↙︎ to `(2,1)=8`, ↙︎ hits bottom → right to `(2,2)=9`.

## Edge Cases

- Empty matrix → `[]`.
- Single row or single column → straight pass through elements.
- Non-rectangular inputs are not allowed by problem constraints (always `m x n`).

## Why This Works

All diagonals are traversed exactly once. The parity rule ensures we alternate directions correctly, and border checks ensure we “bounce” to the next valid start for the following diagonal, covering the entire grid without repetition or omission.
