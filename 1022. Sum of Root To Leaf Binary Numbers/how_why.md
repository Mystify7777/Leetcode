# 1022. Sum of Root To Leaf Binary Numbers — How/Why

## Problem

Given a binary tree where each node has value `0` or `1`, every root-to-leaf path forms a binary number (root as highest bit). Return the sum of all root-to-leaf binary numbers.

## Intuition

As you descend the tree, you only need the numeric value of the current path. Shift the running value left (multiply by 2) and add the current bit; when at a leaf, that accumulated value is the path’s binary number.

## Approach

- Depth-first traversal carrying `sum`, the numeric value of the path so far.
- At each node: update `sum = sum * 2 + node.val` (equivalent to left shift then OR).
- If the node is a leaf, return this `sum` because it represents the full root-to-leaf binary number.
- Otherwise, recurse on children and return the sum of their results.

## Correctness

- The update `sum = 2 * sum + bit` appends the current bit to the path value in base 2, so `sum` always equals the binary value of the path from root to the current node.
- For a leaf, no further bits follow; thus the accumulated `sum` is exactly that root-to-leaf path’s number.
- Summing results from left and right subtrees accumulates all root-to-leaf numbers. By structural induction on the tree, the algorithm returns the required total.

## Complexity

- Time: $O(n)$ where $n$ is the number of nodes (each visited once).
- Space: $O(h)$ for recursion depth, where $h$ is tree height (worst-case $O(n)$, best-case $O(\log n)$ for balanced trees).

## Edge Cases

- Empty tree (`root == null`): sum is `0`.
- Single node: returns that node’s value (either `0` or `1`).
- Skewed tree: recursion depth reaches height $h$; logic still holds.

## Key Code

- DFS with path accumulation and leaf return: [1022. Sum of Root To Leaf Binary Numbers/Solution.java](1022.%20Sum%20of%20Root%20To%20Leaf%20Binary%20Numbers/Solution.java#L17-L25)
