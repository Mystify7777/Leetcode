# How & Why – 141. Linked List Cycle

## Problem

Given the head of a linked list, determine if the list contains a cycle.

A cycle exists if some node in the list can be reached again by continuously following the `next` pointer.

---

## 1. Brute Force Approach ❌

**Idea:**

* Keep a `HashSet` of visited nodes.
* Traverse the list.
* If we ever see the same node again → cycle.

**Code (sketch):**

```java
Set<ListNode> visited = new HashSet<>();
while (head != null) {
    if (visited.contains(head)) return true;
    visited.add(head);
    head = head.next;
}
return false;
```

**Complexity:**

* Time = O(n)
* Space = O(n) (because we store visited nodes)

---

## 2. Optimized Approach → Floyd’s Cycle Detection ✅

**Idea:**

* Use **two pointers**:

  * **slow** moves 1 step at a time.
  * **fast** moves 2 steps at a time.
* If there is a cycle, they will eventually meet.
* If `fast` reaches `null` → no cycle.

**Why it works:**

* Think of it as a circular track: a faster runner will always lap the slower runner if they keep moving.

**Complexity:**

* Time = O(n)
* Space = O(1)

---

## Example Walkthrough

### Case 1: No Cycle

List: `1 → 2 → 3 → null`

* slow: 1 → 2 → 3
* fast: 1 → 3 → null
* fast hits null → return `false`.

---

### Case 2: Cycle

List: `1 → 2 → 3 → 4 → 2 (back to node 2)`

Steps:

* slow: 1 → 2 → 3 → 4 → 2 ...
* fast: 1 → 3 → 2 → 4 → 2 ...

At some point → `slow == fast` → return `true`.

---

## Takeaway

* **HashSet method** is simpler but uses O(n) space.
* **Floyd’s Tortoise & Hare** is optimal: O(n) time, O(1) space.
* The provided solution is the **standard optimal approach**.

---

