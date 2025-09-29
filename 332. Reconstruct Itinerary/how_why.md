# 332. Reconstruct Itinerary — How & Why

## Problem Restated

You are given a list of airline tickets represented as pairs of departure and arrival airports.  
The task is to **reconstruct the itinerary** in order, starting from `"JFK"`, such that:

1. All tickets are used exactly once.
2. If multiple valid itineraries exist, return the lexicographically smallest one.

**Example:**

```java

Input: [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]

```

---

## 1. Brute Force Approach

**Idea**:  

- Generate all possible itineraries using **backtracking**:
  - Start at `"JFK"`.
  - Recursively pick the next unused ticket.
  - If at the end all tickets are used → valid itinerary.
- Keep track of the lexicographically smallest result.

**Walkthrough (same example)**:

- Start: JFK → MUC → LHR → SFO → SJC (all tickets used, valid).
- Only one itinerary, so result is `["JFK","MUC","LHR","SFO","SJC"]`.

**Complexity**:

- In worst case (N tickets), factorial search: **O(N!)**
- Way too slow for N ~ 300.

---

## 2. Graph + Hierholzer’s Algorithm (Your Solution)

**Key Insight**:  
This problem is essentially finding an **Eulerian path** (a path that uses every edge exactly once).  

- Build a graph where:
  - **Nodes** = airports.
  - **Edges** = tickets (directed).
- Since multiple destinations may exist, use a **PriorityQueue** to always pick the lexicographically smallest airport first.
- Apply **DFS** (Hierholzer’s algorithm):
  - While current node has outgoing edges, recurse on the smallest next airport.
  - Once no more edges, add airport to the front of itinerary.
- Reverse postorder ensures tickets are used exactly once in the correct order.

**Step-by-step Walkthrough** (example: `["JFK","MUC"],["MUC","LHR"],["LHR","SFO"],["SFO","SJC"]`):

1. Build graph:

    ```java

    JFK -> [MUC]
    MUC -> [LHR]
    LHR -> [SFO]
    SFO -> [SJC]

    ```

2. DFS:

    - Start at `"JFK"`.
    - Go → `"MUC"` → `"LHR"` → `"SFO"` → `"SJC"`.
    - No more edges → add `"SJC"` to itinerary.
    - Backtrack → `"SFO"`, `"LHR"`, `"MUC"`, `"JFK"`.

3. Itinerary built in reverse → final = `["JFK","MUC","LHR","SFO","SJC"]`.

**Complexity**:

- Building graph: **O(N log N)** (due to PriorityQueue inserts).
- DFS traversal: **O(N log N)** (each edge removed once, with heap operations).
- Space: **O(N)** for graph + recursion stack.

---

## 3. Alternative Optimized Approach — Iterative Stack

Instead of recursion:

- Use a **stack** to simulate DFS:
- Push airports as long as outgoing edges exist.
- When stuck, pop from stack and prepend to itinerary.
- Equivalent to Hierholzer’s algorithm but avoids deep recursion.

**Tradeoff**:

- Safer for very large inputs (avoids stack overflow).
- Same complexity as recursive version.

---

## ✨ Conclusion

- **Brute Force**: Tries all permutations, not feasible.  
- **DFS + Trie-like PriorityQueue** (Your Code): Efficiently finds Eulerian path with lexicographic ordering.  
- **Iterative Stack**: Same logic as DFS but avoids recursion depth limits.  

✅ Your solution is essentially **Hierholzer’s algorithm with lexicographic priority**, which is the optimal approach.

---
