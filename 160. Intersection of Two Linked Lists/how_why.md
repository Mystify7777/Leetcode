# How & Why — LeetCode 160: Intersection of Two Linked Lists

Given the heads of two singly linked lists `headA` and `headB`, return the node where the two lists intersect. If the two linked lists have no intersection, return `null`.

---

## Key idea

If two singly linked lists intersect, they share the same tail starting at the intersection node. The main difficulty is differing list lengths; align the lists so pointers traverse the same remaining distance.

---

## Approach 1 — Two-pointer switching (recommended)

- Initialize `a = headA` and `b = headB`.
- Move each pointer one step at a time.
- When a pointer reaches the end, redirect it to the head of the other list:
  - `a = (a == null) ? headB : a.next`
  - `b = (b == null) ? headA : b.next`
- Eventually `a == b` will be the intersection node, or both will be `null` if there's no intersection.

This works because each pointer traverses `lengthA + lengthB` nodes total, so they align for the final sweep.

### Java (two-pointer switching)

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}

class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }
        return a; // can be intersection node or null
    }
}
```

---

## Approach 2 — Length difference (explicit alignment)

- Compute `lenA` and `lenB`.
- Advance the longer-list pointer by `|lenA - lenB|` steps.
- Move both pointers together until they meet or both reach `null`.

### Java (length difference)

```java
class Solution {
    private int length(ListNode head) {
        int len = 0;
        while (head != null) { len++; head = head.next; }
        return len;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = length(headA), lenB = length(headB);
        ListNode a = headA, b = headB;
        if (lenA > lenB) {
            int diff = lenA - lenB;
            while (diff-- > 0) a = a.next;
        } else {
            int diff = lenB - lenA;
            while (diff-- > 0) b = b.next;
        }
        while (a != null && b != null && a != b) {
            a = a.next; b = b.next;
        }
        return (a == b) ? a : null;
    }
}
```

---

## Complexity

- Time: O(m + n) — each list is traversed at most twice.
- Space: O(1) — only constant extra pointers.

---

## Example walkthrough

Input lists (ASCII):

```
List A: 4 -> 1 -> 8 -> 4 -> 5
List B: 5 -> 6 -> 1 -> 8 -> 4 -> 5
Intersection at node with value 8
```

Two-pointer switching trace (high-level):

- Start: `a=4`, `b=5`
- Move step-by-step; when `a` reaches end, jump to headB; when `b` reaches end, jump to headA.
- After switching, both pointers traverse equal total lengths and align at node `8`.
- Return node `8`.

Edge cases:
- No intersection → both pointers become `null` and the function returns `null`.
- One or both lists empty → return `null`.

---

## Alternate approaches

- HashSet: record visited nodes from one list, then scan the other — O(m + n) time but O(m) or O(n) space.
- Brute force: for each node in A, check every node in B — O(m*n), not acceptable.

---

✅ Recommendation: use the two-pointer switching method for clarity and O(1) space.

