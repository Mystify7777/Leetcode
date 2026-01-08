# 297. Serialize and Deserialize Binary Tree — How & Why

## Problem

- Design `serialize()` and `deserialize()` for a binary tree. Ensure correctness for arbitrary shapes (including missing children), and aim for simplicity and consistent format.

## Approaches in This Repo

### 1) BFS (Level-Order) with Null Markers

- `serialize(TreeNode root)`:
	- Level-order traversal using a queue; append each node’s value to a string separated by spaces.
	- Use a null marker (e.g., `"n"`) when a node is missing so the structure is preserved.
	- Example output: `"1 2 3 n n 4 5 "`.
- `deserialize(String data)`:
	- Split by spaces; the first token is the root.
	- For each parent polled from the queue, consume two tokens for left and right: if token != `"n"`, create child and enqueue; else leave child as null.

Implementation details from [297. Serialize and Deserialize Binary Tree/Solution.java](297.%20Serialize%20and%20Deserialize%20Binary%20Tree/Solution.java):

- Uses `StringBuilder` and `Queue<TreeNode>` for `serialize`, appending `"n "` for nulls.
- In `deserialize`, checks for empty string as empty tree, then builds nodes with a queue in pairs.
- Note: `if (data == "")` compares references; prefer `data.isEmpty()` for robustness.
- The loop does `for (int i = 1; i < values.length; i++)` and increments `i` again inside when reading right child; ensure indices remain in-bounds.

### 2) DFS (Preorder) with Sentinel

- `serialize(TreeNode root)`:
	- Preorder traversal; write `val` for nodes and a sentinel (e.g., `Integer.MAX_VALUE` or `#`) for nulls.
	- Produces a sequence that can be replayed to reconstruct tree uniquely.
- `deserialize(List<Integer> data)` (or string tokens):
	- Use a moving index to consume tokens in preorder; create nodes until sentinel encountered.

Implementation details captured in comments in the solution:

- A variant uses `List<Integer>` and sentinel `Integer.MAX_VALUE`, with `dfsSerialize` and `dfsDeserialize`.
- Another common string variant uses `SEP=","` and `NULL="#"` with preorder.

## Trade-offs

- BFS:
	- Pros: Intuitive, mirrors level structure; simple parent-children reconstruction.
	- Cons: May include many trailing null markers for sparse trees; output can be longer.
- DFS (preorder):
	- Pros: Compact for sparse trees; easy to implement with recursion.
	- Cons: Needs clear sentinel and careful token consumption; recursion depth equals tree height.

## Complexity

- Time: `O(n)` for both serialization and deserialization (each node processed once).
- Space:
	- BFS: `O(n)` output size; queue up to `O(n)` in worst case.
	- DFS: `O(n)` output size; recursion stack `O(h)` (or `O(1)` if using iterative/explicit stack).

## Edge Cases

- Empty tree → serialize to empty string (or a sentinel-only representation) and deserialize to `null`.
- Single node; skewed trees; repeated values → handled naturally by markers.
- Token parsing robustness → prefer `isEmpty()` over `== ""`; validate bounds when advancing index.

## Why It Works

- Null markers preserve structural gaps so that children positions remain unambiguous.
- The chosen traversal order (level-order or preorder) and deterministic tokenization ensure a one-to-one mapping between tree and serialized form.

## Reference

- See the implementations and variants in [297. Serialize and Deserialize Binary Tree/Solution.java](297.%20Serialize%20and%20Deserialize%20Binary%20Tree/Solution.java).
