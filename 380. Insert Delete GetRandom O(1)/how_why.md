# How_Why.md – Insert Delete GetRandom O(1) (LeetCode 380)

## ❌ Brute Force Idea

We need three operations:

1. Insert a value.
2. Remove a value.
3. Return a random value (uniformly).

**Naïve choices:**

* Use an **ArrayList** → Insert = O(1), GetRandom = O(1), but Remove = O(n) (shifting elements).
* Use a **HashSet** → Insert = O(1), Remove = O(1), but GetRandom = O(n) (need to convert to array first).

**Why it fails:** none of these gives all operations in **O(1)**.

---

## ✅ Your Approach (ArrayList + HashMap Trick)

Key insight:

* Use a **list** to store values (for random access).
* Use a **map** to store each value’s index in the list (for O(1) lookup).
* When removing:

  * Swap the target with the last element.
  * Update its index in the map.
  * Pop the last element.

This avoids O(n) shifting.

### Implementation Details

* **Insert(val)**: Add to end of list + update map.
* **Remove(val)**: Swap with last element → update map → pop last → remove from map.
* **GetRandom()**: Use `Random` to pick index from `list`.

**Time Complexity:**

* Insert: O(1)
* Remove: O(1)
* GetRandom: O(1)

**Space Complexity:** O(n) for storing values.

---

## 🚀 Other Optimized Approaches

1. **Dynamic array + map (your version)** – cleanest and most common.
2. **Manually managed array with resizing (second version in code)** – avoids ArrayList overhead but more boilerplate.
3. **LinkedHashSet + random indexing** – possible but random is O(n). Not acceptable for constraints.

---

## 🔎 Example Walkthrough

Operations:

```
RandomizedSet rs = new RandomizedSet();
rs.insert(10);  // true
rs.insert(20);  // true
rs.insert(30);  // true
rs.remove(20);  // true
rs.getRandom(); // returns 10 or 30 with equal probability
```

### Step 1 – Insert(10)

* list = [10]
* map = {10 → 0}

### Step 2 – Insert(20)

* list = [10, 20]
* map = {10 → 0, 20 → 1}

### Step 3 – Insert(30)

* list = [10, 20, 30]
* map = {10 → 0, 20 → 1, 30 → 2}

### Step 4 – Remove(20)

* Index of 20 = 1.
* Last element = 30.
* Swap → list = [10, 30, 20].
* Pop last → list = [10, 30].
* Update map = {10 → 0, 30 → 1}.

### Step 5 – GetRandom()

* Random index from `[0,1]`.
* Returns either `10` or `30` uniformly.

---

## ✅ Key Takeaways

* The **swap-with-last trick** is the heart of O(1) deletion.
* ArrayList alone or HashSet alone can’t solve the problem.
* This hybrid structure is known as a **RandomizedSet** or “O(1) Insert Delete GetRandom” data structure.

---
