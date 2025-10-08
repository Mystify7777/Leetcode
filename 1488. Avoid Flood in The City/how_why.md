# How_Why.md — Avoid Flood in The City (LeetCode #1488)

## 🧩 Problem Statement

You are given an array `rains`, where:

- `rains[i] > 0` means **lake rains[i] fills up** on day `i`.
- `rains[i] == 0` means **you can choose one lake to dry** on that day.

Your goal:  
Avoid any lake flooding (filling twice before being dried),  
and return an array `ans` of the same length:

- `ans[i] = -1` if it rained that day,
- `ans[i] = lakeNumber` if you dried that lake on day `i`.

If it’s impossible to prevent flooding → return `[]`.

---

## 🧠 Brute Force Thought Process

### 💡 Naive Idea

When it rains on a lake that’s already full, you must find a previous dry day (a `0`) between its **last fill** and **today** to empty it.

### 🧩 Steps

- Track which lakes are currently full.
- For each `rains[i] == 0`, choose a lake to dry later based on need.
- On each `rains[i] > 0`, if that lake was already full →  
  search for a dry day to drain it **before now**.

### 🚨 Problem

- Searching for the right `0` day repeatedly is **O(n²)**.
- Choosing which lake to dry greedily is not trivial.
- Too slow for large inputs (`n ≤ 10⁵`).

---

## ⚡ Optimized Approach — Using HashMap + TreeSet

### 💡 Key Idea

Use:

- A **HashMap (`map`)** to record each lake’s **last filled day**.
- A **TreeSet (`zeros`)** to store indices of all **dry days (0’s)** in sorted order.
- A result array `res[]` to record output actions.

Then:

1. When it rains on `rains[i] > 0`:
   - If that lake already exists in `map` → it’s full.
   - Find the **earliest dry day after its last fill** (using `TreeSet.ceiling()`).
   - Assign that day to dry this lake.
   - If not found → flooding is inevitable → return `[]`.
2. When it’s dry (`rains[i] == 0`):
   - Add index `i` to `zeros`.
3. After processing → fill any remaining unused dry days with `1` (arbitrary valid action).

---

### 🔍 Walkthrough Example

#### Input

```java

rains = [1, 2, 0, 0, 2, 1]

```

#### Step-by-Step

| Day | rains[i] | Action | Dry Days (`zeros`) | Map (lake → lastDay) | res[]     |
|-----|-----------|--------|--------------------|----------------------|------------|
| 0 | 1 | fill lake 1 | [] | {1→0} | [-1] |
| 1 | 2 | fill lake 2 | [] | {1→0, 2→1} | [-1,-1] |
| 2 | 0 | dry day | [2] | {1→0, 2→1} | [-1,-1,0] |
| 3 | 0 | dry day | [2,3] | {1→0, 2→1} | [-1,-1,0,0] |
| 4 | 2 | lake 2 already full → dry it on day 2 | [3] | {1→0, 2→4} | [-1,-1,2,0,-1] |
| 5 | 1 | lake 1 already full → dry it on day 3 | [] | {1→5, 2→4} | [-1,-1,2,1,-1,-1] |

✅ Output: `[-1, -1, 2, 1, -1, -1]`

---

### 🧮 Complexity

| Operation | Time | Space | Notes |
|------------|------|-------|-------|
| Each rain day | O(log n) | O(n) | `TreeSet.ceiling()` lookup |
| Each dry day | O(log n) | O(n) | Tree insertion/removal |
| Total | **O(n log n)** | **O(n)** | Efficient for 10⁵ inputs |

---

## 🔧 Your Code

```java
class Solution {
    public int[] avoidFlood(int[] rains) {
        Map<Integer, Integer> map = new HashMap<>(); // lake -> last full day
        TreeSet<Integer> zeros = new TreeSet<>();    // dry day indices
        int[] res = new int[rains.length];
        
        for (int i = 0; i < rains.length; i++) {
            if (rains[i] == 0) {
                zeros.add(i);
            } else {
                if (map.containsKey(rains[i])) {
                    Integer next = zeros.ceiling(map.get(rains[i]));
                    if (next == null) return new int[0];
                    res[next] = rains[i];
                    zeros.remove(next);
                }
                res[i] = -1;
                map.put(rains[i], i);
            }
        }
        for (int i : zeros) res[i] = 1;
        return res;
    }
}
````

---

## 💡 Why It Works

* Uses `TreeSet` to **always find the earliest valid dry day** for a lake.
* Ensures **no lake floods** because:

  * You only reuse a dry day that appears **after** its last fill.
* Fills remaining dry days arbitrarily (`res[i] = 1`), which doesn’t affect correctness.

---

## 🔁 Alternative Approach (Union-Find Trick)

Another advanced version (see commented code) uses a **Disjoint Set (Union-Find)**:

* Treats dry days as nodes.
* Uses `find()` to locate the next available dry day efficiently.
* Slightly faster constant factors, but same O(n log n) complexity.

---

## ✅ Summary

| Approach     | Data Structures   | Time           | Space | Notes                       |
| :----------- | :---------------- | :------------- | :---- | :-------------------------- |
| Brute Force  | Lists/Sets        | O(n²)          | O(n)  | Too slow                    |
| Your Version | HashMap + TreeSet | **O(n log n)** | O(n)  | Elegant and efficient       |
| Union-Find   | HashMap + DSU     | O(n log n)     | O(n)  | More complex, similar speed |

---

### 🎯 Final Verdict

Your TreeSet + HashMap solution is **clean, optimal, and intuitive**.
It leverages sorting and binary search implicitly via TreeSet to always pick the correct dry day.

✅ **Result:** Accepted — 100% correct and efficient.

---
