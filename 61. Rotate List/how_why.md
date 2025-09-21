# How\_Why.md: Rotate List (LeetCode 61)

## Problem

Given the head of a singly-linked list and an integer `k`, rotate the list to the right by `k` places.

**Example:**

```java
Input: 1->2->3->4->5->NULL, k = 2
Output: 4->5->1->2->3->NULL
```

---

## Brute-force Approach

### Idea

* Move the last element to the front **`k` times**.
* For each rotation:

  1. Traverse to the second-to-last node.
  2. Move the last node to the front.
* Repeat `k` times.

### Code

```java
public ListNode rotateRightBF(ListNode head, int k) {
    if (head == null || head.next == null) return head;
    int length = 0;
    ListNode temp = head;
    while (temp != null) { length++; temp = temp.next; }
    
    k = k % length;
    for (int i = 0; i < k; i++) {
        ListNode prev = null, curr = head;
        while (curr.next != null) {
            prev = curr;
            curr = curr.next;
        }
        prev.next = null;
        curr.next = head;
        head = curr;
    }
    return head;
}
```

### Example Walkthrough

```java
Input: 1->2->3->4->5, k=2
Step 1: Move last node 5 → front: 5->1->2->3->4
Step 2: Move last node 4 → front: 4->5->1->2->3
```

**Limitations:**

* **Time Complexity:** O(k \* n) — inefficient for large `k` or long lists.
* Repeated traversals of the list make it slow.

---

## User Approach (Two-pointer + List Length)

### Idea_

1. Compute the **length** of the list.
2. Use **two pointers** (`slow` and `fast`) to locate the node before the new head.
3. Make the list **circular** temporarily.
4. Break the circle at the correct position.

### Code_

```java
public ListNode rotateRight(ListNode head, int n) {
    if (head == null || head.next == null) return head;

    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode fast = dummy, slow = dummy;

    int length = 0;
    while (fast.next != null) { 
        fast = fast.next; 
        length++; 
    }

    int steps = length - n % length;
    for (int i = 0; i < steps; i++) slow = slow.next;

    fast.next = dummy.next; // make circular
    dummy.next = slow.next; // new head
    slow.next = null;       // break circle

    return dummy.next;
}
```

### Example Walkthrough_

```java
Input: 1->2->3->4->5, k=2
Step 1: length = 5
Step 2: steps = 5 - 2 % 5 = 3
Step 3: slow moves 3 steps → points to 3
Step 4: fast points to 5 → link 5.next = 1 (circular)
Step 5: new head = slow.next = 4
Step 6: break circle: slow.next = null
Result: 4->5->1->2->3
```

**Advantages:**

* **Time Complexity:** O(n)
* **Space Complexity:** O(1)
* Handles large `k` efficiently using modulo.

---

## Optimized Approach

* The user approach is **already optimal**:

  * Only traverses the list twice: once to get the length, once to reach the rotation point.
  * Handles edge cases:

    * `k >= length`
    * Empty list or single-node list
* No extra data structures needed.

---

### Key Takeaways

1. Avoid brute-force by using **list length + modulo**.
2. Two-pointer technique locates the split point efficiently.
3. Temporarily converting to a circular list simplifies rotation logic.
4. Always handle edge cases: `head == null`, `head.next == null`, `k >= length`.

---
