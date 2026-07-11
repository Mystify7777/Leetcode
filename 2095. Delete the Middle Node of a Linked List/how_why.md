# 2095. Delete the Middle Node of a Linked List

## Problem Summary

Given the head of a singly linked list, delete the **middle node** and return the head of the modified list. The middle node is defined as the node at index `⌊n / 2⌋` (0-indexed) where `n` is the list length — i.e., for even-length lists, the *second* of the two middle nodes.

Both solutions use the classic **fast/slow (tortoise and hare) pointer** technique to find the middle in a single pass, without first computing the list length. The key challenge both must solve: to actually *delete* a node, you need a reference to its **predecessor** — so the pointer setup is engineered so `slow` (or an equivalent) lands one step *before* the true middle, not on it.

---

## Approach 1: Offset Fast Pointer by One Step (`Solution`)

### How it works

1. Handle the trivial case: if the list has only one node (`head.next == null`), deleting the middle means the list becomes empty — return `null`.
2. Initialize `slow = head`, but start `fast` **two nodes ahead**: `fast = slow.next.next`. This is a one-time offset (not part of the loop), deliberately putting `fast` two steps ahead of `slow` from the very beginning, rather than the more common pattern of starting both at `head`.
3. Loop while `fast != null && fast.next != null`: advance `slow` by 1 and `fast` by 2 each iteration.
4. When the loop ends, `slow` has landed exactly **one node before** the true middle node (because of the initial 2-step head start given to `fast`).
5. Delete the middle by skipping over it: `slow.next = slow.next.next`.

### Why it works

- Normally, fast/slow pointers both starting at `head` with `fast` moving 2x speed land `slow` **exactly on** the middle node when `fast` runs out. But to delete a node in a singly linked list, you need its **predecessor** (since there's no `prev` pointer to walk backward). 
- By starting `fast` two steps ahead of `slow` (instead of at the same node), the entire race is shifted: when `fast` reaches the end, `slow` is now **one node behind** where it would otherwise land — i.e., exactly at the predecessor of the middle node. This is a neat way to solve the "need the previous node" problem *without* an explicit dummy/sentinel node.
- The loop condition `fast != null && fast.next != null` ensures `fast` stops as soon as it either reaches the end or can't safely take another 2-step jump, correctly handling both odd- and even-length lists.

### Complexity

- **Time:** `O(n)` — single pass with two pointers.
- **Space:** `O(1)` — no auxiliary data structures.

### Caveats

- The initial `fast = slow.next.next` line assumes `head.next != null` (i.e., at least 2 nodes) — this is guaranteed by the early return for the single-node case, but if that check were removed or reordered, this line would throw a `NullPointerException` on a single-node or empty list.

---

## Approach 2: Dummy Node + Same-Speed Pointer Tracking (`Solution2`)

### How it works

1. Same trivial-case handling: single node → return `null`.
2. Introduce a **dummy sentinel node** `mid = new ListNode(-1)` whose `next` points to `head`. This dummy acts as a "virtual predecessor" to the head, simplifying edge cases.
3. Use `curr = head` as the fast-moving pointer, and `mid` as the slow-moving pointer — but `mid` starts **one step behind** `curr` (since `mid.next == head == curr` initially, `mid` is conceptually the predecessor of `curr`).
4. Loop while `curr != null && curr.next != null`: advance `curr` by 2 (`curr = curr.next.next`) and `mid` by 1 (`mid = mid.next`) each iteration.
5. When the loop ends, `mid` has ended up at the **predecessor of the middle node** — because `mid` started one step behind `curr`, and both advanced at the same relative rates as Approach 1's `slow`/`fast`, just with different starting offsets and variable names.
6. Delete the middle: `mid.next = mid.next.next`.

### Why it works

- This is functionally the **same fast/slow race** as Approach 1, just restructured: instead of giving `fast` (`curr`) a two-node head start over `slow` (`mid`), it gives `mid` a **one-node head start in the dummy** *before* the loop, while `curr` starts exactly at `head`. Since `mid` begins pointing at a virtual node just before `head`, and both pointers move at the same 1x/2x relative speeds inside the loop, the end result is identical: `mid` lands on the true predecessor of the middle node when the loop finishes.
- Using a dummy node is a common defensive pattern for linked-list deletion problems — it means there's no special-casing needed even if the node to delete were the head itself (not an issue in *this* specific problem since the middle of a 1-node list is already handled separately, but it's a generally robust pattern).
- The loop condition (`curr != null && curr.next != null`) is identical in spirit to Approach 1's `fast` condition, just checking `curr` instead of `fast` — both stop the race at the same relative point.

### Complexity

- **Time:** `O(n)` — single pass.
- **Space:** `O(1)` extra space — the one dummy node is a constant-size allocation, not proportional to input size.

### Caveats

- Slightly more allocation overhead than Approach 1 (creates one extra `ListNode` object for the dummy), though this is negligible in practice.
- The dummy node is reassigned via `mid = mid.next` inside the loop rather than being kept as a fixed sentinel with a separate traversal pointer — this is a bit unconventional (typically you'd keep `dummy` fixed and use a separate `prev` pointer that starts at `dummy`), but it works correctly here because `mid` is never needed to "return to the start" — it's only used to find and hold the final predecessor position.

---

## Comparing the Two Approaches

| | `Solution` (offset fast pointer) | `Solution2` (dummy node) |
|---|---|---|
| **Core idea** | Start `fast` two steps ahead of `slow` so `slow` naturally lands on the predecessor | Use a dummy node one step behind `head` so the slow pointer (`mid`) lands on the predecessor |
| **Extra allocation** | None | One dummy `ListNode` |
| **Edge-case handling** | Relies on the single-node early return before dereferencing `slow.next.next` | Same early return; dummy node is a generally safer pattern for deletion problems |
| **Readability** | Slightly less intuitive due to the manual pointer offset before the loop | Slightly more standard/idiomatic pattern (dummy + prev/curr) for list-deletion problems |
| **Time** | `O(n)` | `O(n)` |
| **Space** | `O(1)` | `O(1)` (plus one constant-size dummy node) |

Both solutions are really the **same algorithm** — the fast/slow pointer race — with the offset needed to land on the *predecessor* (rather than the middle itself) achieved in two equivalent ways: shifting `fast`'s starting position forward by one extra step, versus shifting `slow`'s starting position backward by one step using a dummy node. Either technique sidesteps the fundamental limitation of singly linked lists (no backward traversal) without needing a separate "remember the previous node" variable inside the loop.