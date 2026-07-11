# 2130. Maximum Twin Sum of a Linked List

## Problem Summary

You're given the head of a **singly linked list** with an **even** number of nodes, `n`. For each node `i` (0-indexed), its **twin** is node `n - 1 - i`. The **twin sum** of a pair is the sum of their values. Return the **maximum twin sum** across all pairs.

Both solutions solve this in `O(n)` time and `O(1)` extra space using the classic **fast/slow pointer** technique to find the midpoint, then reversing one half so twins line up for a single pass.

---

## Approach 1: Reverse the First Half In-Place (`Solution2`)

### How it works

1. Use `slow` and `fast` pointers starting at `head`. While advancing `fast` two steps at a time, **simultaneously reverse the first half** of the list in place:
   - `temp = slow.next` (save the next node to move to).
   - `slow.next = prev` (reverse the link — point backward).
   - `prev = slow`, `slow = temp` (advance both `prev` and `slow` forward).
2. When the loop ends (`fast == null` or `fast.next == null`), `slow` points to the start of the **second half** of the list, and `prev` points to the **head of the reversed first half** (i.e., the *last* original node, which is now first).
3. Walk `prev` and `slow` forward together: `prev.val` gives twins in reverse order (last-to-first of the original first half) while `slow.val` walks the second half forward — which is exactly the correct twin pairing. Track the max sum seen.

### Why it works

- **Fast/slow pointer** is the standard trick to find the middle of a linked list in one pass: `fast` moves 2x speed, so when it reaches the end, `slow` is at the midpoint.
- Reversing the first half *while* finding the midpoint (rather than as a separate pass) saves a traversal — it's done "for free" using the same loop.
- After reversal, walking `prev` (reversed first half, now pointing from the middle backward toward the original head) and `slow` (second half, forward from the middle) **in lockstep** naturally pairs up twins: the node that was originally at position `i` from the start now aligns with the node at position `i` from the end.
- This approach reuses the original list's nodes and pointers — no extra data structure needed, `O(1)` space.

### Complexity

- **Time:** `O(n)` — single pass to find the midpoint and reverse, plus a second `O(n/2)` pass to compute twin sums.
- **Space:** `O(1)` — pointer reversal is done in place, no array or stack.

### Caveats

- This **mutates the original list structure** (the first half's links are permanently reversed). If the caller needs the original list intact afterward, this is destructive and would require restoring it or working on a copy.

---

## Approach 2: Find Middle, Reverse Second Half (`Solution`)

### How it works

1. **Find the middle** using fast/slow pointers, but with a twist: `fast` conditionally advances two nodes only if `fast.next != null`, and `slow` only advances if `fast` was still non-null after that step. This carefully lands `slow` at the **start of the second half** (for an even-length list, `slow` ends up at index `n/2`).
2. **Detach and reverse the second half:** `twinNode = reverse(slow.next)` reverses everything after `slow`, then `slow.next = null` cuts the first half off from the second, leaving two independent lists:
   - First half: `head` → ... → `slow` (in original order).
   - Second half (now reversed): `twinNode` → ... → (original last node reversed to point toward the middle).
3. Walk `node` (from `head`) and `twinNode` (from the reversed second half) **together**, summing `node.val + twinNode.val` at each step and tracking the max. Since the second half was reversed, `twinNode` visits nodes in exactly the twin order needed to match `node`.

### Why it works

- The two-pointer logic (`fast = fast.next != null ? fast.next.next : fast.next`) is a careful variant that guarantees, for an **even-length list**, `slow` stops precisely at the boundary between the two halves — no off-by-one issues.
- Separating into "find middle" → "reverse second half" → "merge-walk" mirrors the standard **palindrome linked list** pattern, just repurposed to compute a sum instead of an equality check.
- Because the second half is fully reversed before the pairing pass, iterating both halves forward simultaneously automatically produces correct twin pairs (first node with last, second with second-to-last, etc.), without any index math or extra storage.
- The static initializer block (`static { ... }`) that "warms up" the method by running it 500 times on a dummy list is unrelated to correctness — it's a JIT warm-up trick, likely added to reduce cold-start latency in a specific benchmark/judge environment. It has no bearing on the algorithm's logic.

### Complexity

- **Time:** `O(n)` — one pass to find the middle, `O(n/2)` to reverse the second half, `O(n/2)` to sum twin pairs. All linear, so `O(n)` overall.
- **Space:** `O(1)` — reversal is done via pointer rewiring, no auxiliary storage.

### Caveats

- Also **mutates the original list** (the second half is reversed and detached via `slow.next = null`), same tradeoff as Approach 1.
- The static warm-up block is a micro-optimization hack specific to certain competitive judges (e.g., to avoid JIT compilation overhead being counted in the timed run) — it's not idiomatic production code and would be unusual/wasteful outside that context.

---

## Comparing the Two Approaches

| | `Solution2` (reverse-while-finding-middle) | `Solution` (find-middle-then-reverse) |
|---|---|---|
| **Core idea** | Reverse first half *during* the middle-finding traversal | Find middle first, then reverse second half as a separate step |
| **Passes over first half** | 1 (reversal folded into midpoint search) | 1 (midpoint search) + 1 (reversal of second half) |
| **Pointer style** | Reverses **first** half, pairs `prev` (reversed first half) with `slow` (second half) | Reverses **second** half, pairs `node` (first half) with `twinNode` (reversed second half) |
| **Extra logic** | None beyond standard fast/slow | Conditional fast-pointer advance to precisely land on second-half start |
| **Time** | `O(n)` | `O(n)` |
| **Space** | `O(1)` | `O(1)` |

Both solutions reduce to the same core insight: **reverse one half of the list so that walking both halves forward in lockstep yields correct twin pairs**, differing only in *which* half gets reversed and *when* (interleaved vs. sequential). Either way, the destructive reversal is the price paid for achieving `O(1)` space instead of using an array/stack to store values for later pairing.