# How & Why — LeetCode 2125: Number of Laser Beams in a Bank

A clever counting problem that simulates laser beams between security devices in a bank's grid layout.

---

## Problem Recap

You're given a bank floor plan as a 2D grid represented by an array of binary strings:
* `'0'` = empty cell
* `'1'` = security device

**Laser beams** are formed between security devices:
* Beams connect devices in **different rows** only (never within the same row)
* A device in row `i` connects to **all** devices in the **next row that has devices**
* Empty rows (all `'0'`) are skipped—they don't block beams

**Goal:** Return the total number of laser beams in the bank.

### Example

**Input:**
```
bank = ["011001","000000","010100","001000"]
```

**Visualization:**
```
Row 0: 011001  → 3 devices
Row 1: 000000  → 0 devices (skipped)
Row 2: 010100  → 2 devices
Row 3: 001000  → 1 device
```

**Calculation:**
* Row 0 (3 devices) → Row 2 (2 devices): 3 × 2 = 6 beams
* Row 2 (2 devices) → Row 3 (1 device): 2 × 1 = 2 beams
* **Total:** 6 + 2 = **8 beams**

---

## Naive Approach (Track All Device Positions)

### Idea

Store the positions of all devices in each row, then iterate through consecutive non-empty rows and count all pairs.

### Pseudocode

```java
public int numberOfBeams(String[] bank) {
    List<List<Integer>> deviceRows = new ArrayList<>();
    
    for (String row : bank) {
        List<Integer> devices = new ArrayList<>();
        for (int i = 0; i < row.length(); i++) {
            if (row.charAt(i) == '1') devices.add(i);
        }
        if (!devices.isEmpty()) deviceRows.add(devices);
    }
    
    int total = 0;
    for (int i = 0; i < deviceRows.size() - 1; i++) {
        total += deviceRows.get(i).size() * deviceRows.get(i + 1).size();
    }
    
    return total;
}
```

### Analysis

* ✅ Conceptually clear
* ❌ **Extra space:** Stores positions of all devices
* ❌ **Unnecessary:** Device positions don't matter, only counts
* **Time:** O(m × n) where m = rows, n = cols
* **Space:** O(m × n) in worst case

---

## Optimized Approach (Count Only)

### Key Insight

**Device positions are irrelevant!** Only the **count** of devices per row matters.

If row `i` has `a` devices and the next non-empty row `j` has `b` devices:
* Total beams between them = `a × b`

**Why?** Each device in row `i` connects to each device in row `j`.

### Your Solution

```java
public int numberOfBeams(String[] bank) {
    int prevRowCount = 0;
    int total = 0;

    for (String row : bank) {
        int curRowCount = calc(row);
        if (curRowCount == 0) 
            continue;  // Skip empty rows

        total += curRowCount * prevRowCount;
        prevRowCount = curRowCount;
    }
    return total;
}

private int calc(String s) {
    int count = 0;
    for (char c : s.toCharArray()) 
        count += c - '0';  // '1' - '0' = 1, '0' - '0' = 0
    return count;
}
```

### How It Works

1. **Initialize:** `prevRowCount = 0`, `total = 0`
2. **Iterate through rows:**
   * Count devices in current row using `calc()`
   * **If empty** (count = 0): Skip it (don't update `prevRowCount`)
   * **If non-empty:**
     * Add `curRowCount × prevRowCount` to total
     * Update `prevRowCount = curRowCount` for next iteration
3. **Return total**

### Example Walkthrough

**Input:** `["011001", "000000", "010100", "001000"]`

| Row | String | Count | Calculation | prevRowCount | total |
|-----|--------|-------|-------------|--------------|-------|
| 0 | 011001 | 3 | 3 × 0 = 0 | 3 | 0 |
| 1 | 000000 | 0 | skip | 3 | 0 |
| 2 | 010100 | 2 | 2 × 3 = 6 | 2 | 6 |
| 3 | 001000 | 1 | 1 × 2 = 2 | 1 | 8 |

**Output:** `8` ✓

### Why `prevRowCount` Starts at 0

First non-empty row multiplies by 0, contributing nothing—correct because there's no previous row to connect to!

---

## Alternate Approach (Inline Assignment)

The commented solution uses a clever inline assignment:

```java
public int numberOfBeams(String[] bank) {
    int number = 0, last = countDevices(bank[0]);
    for (int i = 1; i < bank.length; i++) {
        int current = countDevices(bank[i]);
        if (current == 0) continue;
        number += (last * (last = current));  // Multiply, then update!
    }
    return number;
}
```

**Key difference:** `last * (last = current)`
* Evaluates `last * current` (using old `last`)
* Then assigns `current` to `last`
* More compact but slightly less readable

Both approaches are **functionally identical**!

---

## Why This Works

**Mathematical Principle:**
* Beams form a **complete bipartite graph** between consecutive non-empty rows
* For sets of size `a` and `b`, total edges = `a × b`

**Optimization:**
* Skip empty rows (they don't participate)
* Only track the **most recent** non-empty row count
* Each non-empty row connects only to the **previous** non-empty row

---

## Complexity Analysis

### Your Solution
* **Time:** O(m × n) where m = number of rows, n = row length
  * Iterate through all rows: O(m)
  * Count devices per row: O(n)
* **Space:** O(1) — only three integer variables

### Naive Approach
* **Time:** O(m × n)
* **Space:** O(m × n) — stores all device positions

**Winner:** Your solution is optimal! 🏆

---

## Edge Cases

1. **All empty rows:**
   ```
   bank = ["000", "000", "000"]
   ```
   Output: `0` (no devices, no beams)

2. **Single row:**
   ```
   bank = ["111"]
   ```
   Output: `0` (devices in same row don't connect)

3. **No consecutive non-empty rows:**
   ```
   bank = ["101", "000", "000", "101"]
   ```
   Output: `2 × 2 = 4` (Row 0 connects to Row 3)

4. **All rows have devices:**
   ```
   bank = ["11", "11", "11"]
   ```
   Rows 0→1: `2×2=4`  
   Rows 1→2: `2×2=4`  
   Output: `8`

---

## Visual Understanding

```
Row 0: X X X X    (4 devices)
Row 1: - - - -    (0 devices, skipped)
Row 2: X - X -    (2 devices)
Row 3: X - - -    (1 device)

Beams:
Row 0 → Row 2: 4 × 2 = 8 beams
Row 2 → Row 3: 2 × 1 = 2 beams

Total: 10 beams
```

Each `X` in row 0 connects to each `X` in row 2 (next non-empty).  
Each `X` in row 2 connects to each `X` in row 3.

---

## Comparison

| Aspect | Naive (Store Positions) | Optimized (Count Only) |
|--------|-------------------------|------------------------|
| Space | O(m × n) | O(1) |
| Clarity | More verbose | Clean and simple |
| Performance | Same time, worse space | Optimal |
| Device positions | Stored | Ignored |

---

## Key Takeaways

1. **Identify what matters:** Positions don't matter, only counts
2. **Skip irrelevant data:** Empty rows don't affect the result
3. **Use multiplication:** Recognize the complete bipartite graph pattern
4. **Optimize space:** No need to store everything when you can process incrementally
5. **Character arithmetic:** `c - '0'` converts digit character to integer

---

## Alternative Counting Method

Instead of `c - '0'`, you could use:

```java
private int calc(String s) {
    int count = 0;
    for (char c : s.toCharArray()) {
        if (c == '1') count++;
    }
    return count;
}
```

Both work, but `c - '0'` is more compact (and works for any digit, not just binary).

---

## Problem Pattern

This problem teaches:
* **Graph thinking:** Recognize implicit graph structures (bipartite graph)
* **Space optimization:** Don't store what you don't need
* **Streaming processing:** Process row by row without looking back
* **Product rule:** Count all pairs between two sets efficiently

---

## Real-World Application

Similar logic applies to:
* Network connections between server clusters
* Social network friend suggestions (mutual connections)
* Recommendation systems (user-item interactions)
* Any scenario involving pairwise connections between groups

---
