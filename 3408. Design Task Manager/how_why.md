
# How_Why.md

## Problem

You are asked to design a **Task Manager** that:

* Stores tasks with:

  * `taskId`
  * `userId`
  * `priority`
* Supports the following operations:

  1. **add(userId, taskId, priority)** → Add a new task.
  2. **edit(taskId, newPriority)** → Update a task’s priority.
  3. **rmv(taskId)** → Remove a task.
  4. **execTop()** → Execute and remove the task with the **highest priority** (if tie, higher `taskId` first). Return the `userId` of that task, or `-1` if no tasks remain.

Constraints:

* Must handle many operations efficiently.
* Should work in-place, without rebuilding all data every time.

---

## How (Step-by-Step Solution)

### Approach: **Max-Heap with Lazy Deletion**

1. **Data Structures**

   * **Max-Heap (`PriorityQueue`)** → stores `(priority, taskId)`, sorted by:

     * Higher priority first,
     * If tie, higher taskId first.
   * **Maps**:

     * `taskPriority`: stores the *current valid priority* of each task.
     * `taskOwner`: stores which user owns each task.

2. **Adding a Task**

   * Push `(priority, taskId)` into the heap.
   * Update `taskPriority` and `taskOwner`.

3. **Editing a Task**

   * Push a new entry `(newPriority, taskId)` into the heap.
   * Update `taskPriority[taskId] = newPriority`.
   * Old heap entry becomes *stale*.

4. **Removing a Task**

   * Mark task as removed: `taskPriority[taskId] = -1`.

5. **Executing Top Task**

   * Pop from heap until you find an entry `(priority, taskId)` that matches the latest `taskPriority[taskId]`.
   * If valid, mark task as removed and return its owner.
   * If heap is empty, return `-1`.

---

## Why (Reasoning)

* A **max-heap** naturally gives us the highest priority element in **O(1)** peek, **O(log n)** pop.
* Directly editing or deleting arbitrary heap entries is **O(n)** in Java’s `PriorityQueue`.
* To solve this:

  * We use **lazy deletion**: keep all versions of a task in the heap, but only trust the latest one (tracked in `taskPriority`).
  * When an outdated/stale version surfaces, we skip it.

This keeps all operations at **O(log n)** time complexity.

---

## Complexity Analysis

* **Add**: O(log n) (heap insert).
* **Edit**: O(log n) (heap insert).
* **Remove**: O(1) (mark as deleted).
* **ExecTop**: Amortized O(log n) (heap pops until valid).

Space: O(n), storing all tasks and possibly stale entries in heap.

---
Great 😃 let’s do a **visual walkthrough with diagrams**. I’ll show how the **heap** and the **maps** (`taskPriority`, `taskOwner`) evolve after each operation.

---

## Task Manager — Visual Walkthrough

---

### Initial Tasks

```java
tasks = [[1,101,5], [2,102,7]];
TaskManager tm = new TaskManager(tasks);
```

#### Heap

```text
(pq: max-heap by priority, then taskId)

[(7,102), (5,101)]
```

#### Maps

```text
taskPriority = {101:5, 102:7}
taskOwner    = {101:1, 102:2}
```

---

### Operation 1: execTop()

* Pop `(7,102)`.
* Matches `taskPriority[102]=7`.
* Mark as removed → `taskPriority[102] = -1`.
* Return owner → `2`.

#### Heap_

```text
[(5,101)]
```

#### Maps_

```text
taskPriority = {101:5, 102:-1}
taskOwner    = {101:1, 102:2}
```

---

### Operation 2: edit(101, 8)

* Insert `(8,101)` into heap.
* Update `taskPriority[101] = 8`.

#### Heap__

```text
[(8,101), (5,101)]
```

> Note: `(5,101)` is now **stale**.

#### Maps__

```text
taskPriority = {101:8, 102:-1}
taskOwner    = {101:1, 102:2}
```

---

### Operation 3: execTop()

* Pop `(8,101)` → valid (matches `taskPriority[101]=8`).
* Mark as removed → `taskPriority[101] = -1`.
* Return owner → `1`.

#### Heap___

```text
[(5,101)]   // stale entry left behind
```

#### Maps___

```text
taskPriority = {101:-1, 102:-1}
taskOwner    = {101:1, 102:2}
```

---

### Operation 4: execTop()

* Pop `(5,101)` → **stale**, since `taskPriority[101]=-1`.
* Skip and continue.
* Heap empty → return `-1`.

#### Heap____

```text
[]
```

#### Maps____

```text
taskPriority = {101:-1, 102:-1}
taskOwner    = {101:1, 102:2}
```

---

### Why This Works (Visually)

* The **heap** may contain multiple versions of the same task.
* The **maps** always hold the latest valid state.
* On `execTop()`, we keep polling until we find a valid entry.
* Outdated entries are **lazily ignored**.

---

## Example Walkthrough

Input tasks:

```java
tasks = [[1,101,5], [2,102,7]];
TaskManager tm = new TaskManager(tasks);
```

* Heap: `{(7,102), (5,101)}`
* `execTop()` → executes `(7,102)` → returns `2`.

Now edit:

```java
tm.edit(101, 8);
```

* Heap: `{(8,101), (7,102), (5,101)}`
* `taskPriority[101] = 8`
* `execTop()` skips `(5,101)` (stale), executes `(8,101)` → returns `1`.

---

## Alternate Approaches

1. **Balanced BST (TreeMap)**

   * Store `(priority, taskId)` as keys in a TreeMap.
   * Supports add, edit, remove in O(log n).
   * More direct but less common in interviews vs heap+lazy deletion.

2. **Rebuild Heap on Every Edit/Remove**

   * Each update reconstructs heap.
   * Too slow: O(n log n) per edit.

3. **Heap + Lazy Deletion (Chosen)**

   * Efficient and clean.
   * Widely used in practice when direct removal is costly.

---

## Best Method

✅ **Heap + Lazy Deletion** is the best approach here.

* Keeps operations at **O(log n)**.
* Easy to implement with Java’s `PriorityQueue`.
* Well-suited for dynamic task updates.

---

## Implementation

```java
class TaskManager {
    private PriorityQueue<int[]> pq;
    private Map<Integer,Integer> taskPriority;
    private Map<Integer,Integer> taskOwner;

    public TaskManager(List<List<Integer>> tasks) {
        pq = new PriorityQueue<>((a,b) -> {
            if (b[0] != a[0]) return b[0] - a[0]; // priority
            return b[1] - a[1]; // taskId
        });
        taskPriority = new HashMap<>();
        taskOwner = new HashMap<>();
        for (List<Integer> t : tasks) add(t.get(0), t.get(1), t.get(2));
    }

    public void add(int userId, int taskId, int priority) {
        pq.add(new int[]{priority, taskId});
        taskPriority.put(taskId, priority);
        taskOwner.put(taskId, userId);
    }

    public void edit(int taskId, int newPriority) {
        pq.add(new int[]{newPriority, taskId});
        taskPriority.put(taskId, newPriority);
    }

    public void rmv(int taskId) {
        taskPriority.put(taskId, -1);
    }

    public int execTop() {
        while (!pq.isEmpty()) {
            int[] t = pq.poll();
            int p = t[0], id = t[1];
            if (taskPriority.getOrDefault(id, -2) == p) {
                taskPriority.put(id, -1);
                return taskOwner.getOrDefault(id, -1);
            }
        }
        return -1;
    }
}
```

---
