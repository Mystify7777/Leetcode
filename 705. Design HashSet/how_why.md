# @Mystify7777

## How & Why: LeetCode 705 — Design HashSet

Two simple implementations are present: a naïve singly linked list (behaves like an unordered set with O(n) ops) and a direct-address boolean array (true O(1) under given constraints). Both satisfy the LeetCode interface, but the array version is the practical pick for this problem.

---

### Problem

Implement a HashSet with the operations `add(key)`, `remove(key)`, and `contains(key)` for keys in the range `[0, 10^6]`.

---

## Approach 1: Direct-Address Table (Boolean Array) ⭐ Recommended

- **Key idea:** The key space is small and dense (`<= 1_000_000`), so we can map each key directly to an index in a boolean array. No hashing collisions, fully O(1).

- **Algorithm**

  - Allocate `boolean[1_000_001] set` in the constructor.
  - `add(key)`: `set[key] = true`
  - `remove(key)`: `set[key] = false`
  - `contains(key)`: `return set[key]`

- **Complexity**

  - Time: O(1) for all operations.
  - Space: O(U) where U = 1_000_001 booleans (~1 MB), acceptable for the constraints.

- **Why it works:** Direct addressing sidesteps hashing entirely; the constraint on key range makes this feasible and optimal for this problem.

---

## Approach 2: Singly Linked List (Naïve Set)

- **Key idea:** Maintain a simple linked list of unique keys. `contains` scans the list; `add` appends if missing; `remove` relinks to delete. This is not a real hash set but passes because input sizes are small.

- **Algorithm (pseudocode)**
  - `add(key)`: if list empty → new node as head; else if `contains` false → walk to tail and append.
  - `remove(key)`: handle head deletion, else scan until `next.val == key` and bypass.
  - `contains(key)`: linearly scan nodes checking `val`.

- **Complexity**
  - Time: O(n) per operation (n = current set size).
  - Space: O(n) nodes.

**Why it works here:** Simplicity; no hashing logic. However, performance degrades as n grows, so prefer Approach 1 for production or larger inputs.

---

### Code References

- Array-based set (within comments in Solution.java): direct-address table with `boolean[1_000_001]`.
- Linked-list set (active implementation): `Node` class plus `add/remove/contains` on `head`.

---

### Pitfalls

- Forgetting the key range: without bounded keys, the array approach would be infeasible.
- Integer division is not an issue here, but watch for null checks in linked list operations (head deletion and tail traversal).
- For the array approach, ensure size `1_000_001` to include key `1_000_000`.

---

### When to use which

1) **Direct-address table**: Use when keys are small, dense, and bounded (this problem). Fast and simple.
2) **Linked list**: Only as a teaching/placeholder implementation; avoid for larger input due to O(n) operations.

---

### Example (array approach)

- `add(5)` sets `set[5] = true`.
- `contains(5)` returns `true`; `contains(7)` returns `false`.
- `remove(5)` sets `set[5] = false`; subsequent `contains(5)` is `false`.
