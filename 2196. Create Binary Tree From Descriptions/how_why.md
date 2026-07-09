# 2196. Create Binary Tree From Descriptions

## Problem in short
You're given a list of `[parent, child, isLeft]` triples describing edges of a binary tree (`isLeft == 1` means `child` is the parent's left child, `0` means right child). Build the actual tree and return its **root** node — the one node that never appears as anyone's child.

## Key Insight (the "why")
Two things need to happen:
1. **Create every node exactly once**, even though the same value might appear across multiple rows (as a parent in one row, a child in another, or both).
2. **Identify the root**: since every row describes a parent→child edge, the root is simply the *one value that never shows up in the "child" column* across all rows. Both solutions below use this same idea, just implemented with different data structures.

---

## Solution — fixed-size array + "have I seen this as a child yet?" trick

```java
TreeNode[] nodes = new TreeNode[100001];

for (int[] row: descriptions) {
    nodes[row[1]] = new TreeNode(row[1]);
}
```
First pass: create a `TreeNode` for **every value that ever appears as a child**, and store it directly at index `row[1]` in the array (values are used as direct array indices — this works because the problem guarantees node values fit in a small range, up to `10^5`, so `nodes[value]` doubles as a hash map with `O(1)` array access instead of needing an actual `HashMap`).

At this point, any value that is *only* ever a parent (never a child) is still `null` in the array — this is exactly the property used to detect the root in the next loop.

```java
TreeNode root = null;

for (int[] row: descriptions) {
    if (nodes[row[0]] == null) {
        root = nodes[row[0]] = new TreeNode(row[0]);
    }
    ...
}
```
Second pass: for each row, check whether the **parent** value already has a node. If it's still `null`, that means this parent value was never seen as a child in the first loop — i.e., it's a candidate for being the root. Since a valid tree has exactly one true root, this condition will only ever trigger for the actual root (every other parent value will already have been created as somebody's child in the first pass, so this branch is skipped for them). The node is created and simultaneously assigned to both `root` and `nodes[row[0]]`.

```java
    if (row[2] == 1) {
        nodes[row[0]].left = nodes[row[1]];
    } else {
        nodes[row[0]].right = nodes[row[1]];
    }
}
return root;
```
Wire up the edge: attach the (already-created) child node to the parent's `left` or `right` field depending on the third value in the row. By the time this line runs, both `nodes[row[0]]` and `nodes[row[1]]` are guaranteed to exist (parent from either the first-pass child-creation or the just-above root check; child always from the first pass).

**Why the array size is `100001`:** the problem's constraints cap node values at `10^5` (`1 <= value <= 10^5`), so a plain array indexed directly by value is safe and avoids the overhead of a `HashMap`.

---

## Solution2 — HashMap + explicit "children" set

```java
Map<Integer, TreeNode> map = new HashMap<>();
Set<Integer> children = new HashSet<>();
```
Instead of relying on array-index presence/absence, this version explicitly tracks two things: a map from value → node (a general-purpose "get or create" node lookup), and a separate set of every value that has ever been seen as a child.

```java
for(int[] description : descriptions) {
    int parent = description[0];
    int child = description[1];
    TreeNode parentNode = null, childNode = null;
    if(map.containsKey(parent)) {
        parentNode = map.get(parent);
    } else {
        parentNode = new TreeNode(parent);
        map.put(parent, parentNode);
    }

    if(map.containsKey(child)) {
        childNode = map.get(child);
    } else {
        childNode = new TreeNode(child);
        map.put(child, childNode);
    }
    ...
    children.add(child);
}
```
For every row, **get-or-create** both the parent node and the child node (whichever one hasn't been created yet gets created now, whichever already exists gets reused) — this handles the "create every node exactly once" requirement generically, without depending on which column (parent or child) a value happens to appear in first. Then record that `child` has been seen as somebody's child.

```java
    if(description[2] == 1)
        parentNode.left = childNode;
    else 
        parentNode.right = childNode;
```
Wire up the edge exactly as before, based on the `isLeft` flag.

```java
for (Map.Entry<Integer, TreeNode> entry : map.entrySet()) {
    if(!children.contains(entry.getKey()))
        return entry.getValue();
}
return null;
```
Finally, scan every value that ever got a node created, and return the first one that was **never** recorded in the `children` set — that's the root, by the same logic as before (the root is the only value that's a parent somewhere but a child nowhere).

---

## Comparing the two

| | `Solution` | `Solution2` |
|---|---|---|
| Node lookup | Fixed-size array indexed directly by value | `HashMap<Integer, TreeNode>` |
| Root detection | Implicit — "is this parent's array slot still empty?", checked *during* the edge-building pass | Explicit — a separate `Set<Integer>` of children, checked in a *final* pass over all created nodes |
| Extra space | `O(100001)` array regardless of how many nodes are actually used | `O(number of distinct values)` — proportional to actual input size |
| Relies on value range | Yes — needs values to fit in a bounded array (`<= 10^5` here) | No — works for arbitrary integer values |

Both run in `O(n)` time where `n` is the number of description rows. `Solution` is slightly more memory-efficient in practice when values are small and dense (no `HashMap` overhead, just direct array indexing), but it hard-codes an assumption about the value range; `Solution2` is more general-purpose and would still work correctly even if node values were arbitrarily large or sparse.

## Step-by-step example
`descriptions = [[20,15,1],[20,17,0],[50,20,1],[50,80,0],[80,19,1]]`

- Root is `50`, since it never appears in the child column.
- Tree:
  ```
          50
         /  \
       20    80
      /  \   /
    15   17 19
  ```

Both solutions build this exact tree and return the node for `50`.

## Complexity
- **Both solutions:** Time `O(n)` where `n = descriptions.length` (each row processed a constant number of times); the final root-search loop in `Solution2` is `O(number of distinct nodes)`, still linear overall.
- **Space:** `Solution` uses `O(100001)` fixed array space; `Solution2` uses `O(distinct values)` for the map and set.