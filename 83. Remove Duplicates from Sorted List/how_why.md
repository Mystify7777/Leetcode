# How & Why — LeetCode 83: Remove Duplicates from Sorted List

Remove duplicate nodes from a sorted singly-linked list so that each value appears only once. Because the list is sorted, duplicate values appear consecutively and can be removed by a single pass.

---

## Idea

Since equal values are adjacent, iterate through the list once and remove consecutive nodes with the same value by changing the `next` pointers. This is done in-place with constant extra space.

---

## Algorithm (iterative)

1. Initialize `curr = head`.
2. While `curr != null` and `curr.next != null`:
   - If `curr.val == curr.next.val`, set `curr.next = curr.next.next` (skip the duplicate).
   - Else, set `curr = curr.next`.
3. Return `head`.

This visits each node at most once and adjusts pointers without additional data structures.

---

## Java implementation

```java
// Definition for singly-linked list.
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) {
                // Skip duplicate node
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }
}
```

---

## Why this works

- The list is already sorted, so duplicates are adjacent — removing `curr.next` when it's equal to `curr` eliminates the duplicate while keeping the list connected.
- The loop checks each pair of adjacent nodes once, ensuring O(n) time.

---

## Complexity

- Time: O(n) — each node is visited at most once.
- Space: O(1) — only a few pointers are used.

---

## Example walkthrough

Input:

```
1 -> 1 -> 2 -> 3 -> 3
```

Step-by-step (showing `curr` and the list state after each action):

| Step | curr.val | curr.next.val | Action                     | List after step       |
|------|----------|----------------|----------------------------|-----------------------|
| 1    | 1        | 1              | duplicate -> skip next     | 1 -> 2 -> 3 -> 3      |
| 2    | 1        | 2              | advance curr               | 1 -> 2 -> 3 -> 3      |
| 3    | 2        | 3              | advance curr               | 1 -> 2 -> 3 -> 3      |
| 4    | 3        | 3              | duplicate -> skip next     | 1 -> 2 -> 3           |

Result: `1 -> 2 -> 3`

Notes:
- When duplicates tie, we remove the second occurrence (i.e., keep the first), which preserves the first occurrence of each value.
- Edge cases such as `head == null` or a single-node list are handled naturally (the loop will not execute and `head` is returned as-is).

---

## Alternate approaches

- Recursive: process `head.next` recursively then compare `head.val` to `head.next.val`; skip as needed. Works but uses recursion stack (O(n) worst-case space for deep lists).
- HashSet: track seen values. Works for unsorted lists but uses O(n) extra space and is unnecessary here.

---

✅ The iterative single-pass pointer method is the recommended solution for this problem.

