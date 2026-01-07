# How_Why.md: Open the Lock

## Problem

You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: `'0', '1', '2', ..., '9'`. The wheels can rotate freely and wrap around: for example `'9'` becomes `'0'` going forward, and `'0'` becomes `'9'` going backward.

The lock initially starts at `"0000"`, a string representing the state of the 4 wheels.

You are given a list of `deadends` dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.

Given a `target` representing the value of the wheels that will unlock the lock, return the **minimum number of turns** required to open the lock, or **-1** if it is impossible.

**Example:**

```c
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation: "0000" → "1000" → "1100" → "1200" → "1201" → "1202" → "0202"

Input: deadends = ["8888"], target = "0009"
Output: 1
Explanation: "0000" → "0009"
```

---

## Approach: BFS (Breadth-First Search)

### Idea

* Treat each lock state as a **node in a graph**
* Each state can reach **8 neighbors** (4 wheels × 2 directions)
* Use **BFS** to find shortest path from `"0000"` to `target`
* Track visited states to avoid cycles
* Skip deadend states

### Code

```java
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadendSet = new HashSet<>(Arrays.asList(deadends));
        
        // Early termination: start is deadend
        if (deadendSet.contains("0000")) {
            return -1;
        }
        
        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>("0000", 0));
        
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        
        while (!queue.isEmpty()) {
            Pair<String, Integer> current = queue.poll();
            String currentCombination = current.getKey();
            int moves = current.getValue();
            
            // Found target
            if (currentCombination.equals(target)) {
                return moves;
            }
            
            // Generate all 8 neighbors (4 wheels × 2 directions)
            for (int i = 0; i < 4; i++) {
                for (int delta : new int[]{-1, 1}) {
                    // Calculate new digit with wraparound
                    int newDigit = (currentCombination.charAt(i) - '0' + delta + 10) % 10;
                    
                    // Build new combination
                    String newCombination = currentCombination.substring(0, i) +
                                             newDigit +
                                             currentCombination.substring(i + 1);
                    
                    // Add to queue if not visited and not deadend
                    if (!visited.contains(newCombination) && !deadendSet.contains(newCombination)) {
                        visited.add(newCombination);
                        queue.offer(new Pair<>(newCombination, moves + 1));
                    }
                }
            }
        }
        
        return -1; // Target is not reachable
    }
}
```

### Why This Works

* **BFS Guarantees Shortest Path:**
  - Explores states level by level
  - First time we reach target = minimum moves

* **Example Walkthrough:**

  ```math
  Start: "0000", target: "0009"
  
  Level 0: "0000" (moves = 0)
  
  Level 1 (moves = 1):
    Wheel 0: "1000", "9000"
    Wheel 1: "0100", "0900"
    Wheel 2: "0010", "0090"
    Wheel 3: "0001", "0009" ← Found target! ✓
  
  Return: 1
  ```

* **Detailed Example with Deadends:**

  ```math
  deadends = ["0001"], target = "0009"
  
  Level 0: "0000"
  
  Level 1:
    "0001" → SKIP (deadend)
    "0009" → SKIP (need 2 moves through "0001")
    "1000", "9000", "0100", "0900", "0010", "0090" → Add to queue
  
  Level 2:
    From "0090": Can reach "0091", "0089", etc.
    From "0010": Can reach "0011", "0009" ← Found! ✓
  
  Return: 2
  ```

* **Wraparound Logic:**

  ```java
  (digit + delta + 10) % 10
  
  Examples:
  - digit=0, delta=-1: (0-1+10)%10 = 9 ✓
  - digit=9, delta=1:  (9+1+10)%10 = 0 ✓
  - digit=5, delta=1:  (5+1+10)%10 = 6 ✓
  ```

* **Time Complexity:** **O(10^4)** = **O(10000)** - at most 10,000 unique states
* **Space Complexity:** **O(10^4)** - visited set and queue

---

## Approach 2: Bidirectional BFS (Optimized)

### Idea*

* Search from **both directions**: start → target AND target → start
* Alternate between the two searches, always expanding the smaller frontier
* Meet in the middle = found path

### Code (Simplified Concept)

```java
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        if (deadSet.contains("0000") || deadSet.contains(target)) return -1;
        if (target.equals("0000")) return 0;
        
        Set<String> frontSet = new HashSet<>();
        Set<String> backSet = new HashSet<>();
        Set<String> visited = new HashSet<>();
        
        frontSet.add("0000");
        backSet.add(target);
        
        int steps = 0;
        
        while (!frontSet.isEmpty() && !backSet.isEmpty()) {
            // Always expand smaller frontier
            if (frontSet.size() > backSet.size()) {
                Set<String> temp = frontSet;
                frontSet = backSet;
                backSet = temp;
            }
            
            Set<String> nextLevel = new HashSet<>();
            
            for (String current : frontSet) {
                if (backSet.contains(current)) return steps; // Met!
                if (visited.contains(current)) continue;
                
                visited.add(current);
                
                // Generate all neighbors
                for (int i = 0; i < 4; i++) {
                    for (int delta : new int[]{-1, 1}) {
                        int newDigit = (current.charAt(i) - '0' + delta + 10) % 10;
                        String next = current.substring(0, i) + newDigit + current.substring(i + 1);
                        
                        if (!deadSet.contains(next) && !visited.contains(next)) {
                            if (backSet.contains(next)) return steps + 1; // Met!
                            nextLevel.add(next);
                        }
                    }
                }
            }
            
            steps++;
            frontSet = nextLevel;
        }
        
        return -1;
    }
}
```

**Why Faster:**

```markdown
Regular BFS: Explores up to 10,000 states
Bidirectional: Meet in middle → √10,000 + √10,000 ≈ 200 states

Example: Start="0000", Target="9999"
- Regular BFS: 0000→...→9999 (explore many states)
- Bidirectional: 0000→...→4999 ← 9999 (meet halfway)
```

**Time Complexity:** **O(√(10^4))** ≈ **O(100)** - much faster!
**Space Complexity:** **O(10^4)** - visited set

---

## Variant: Integer-Encoded BFS (Array-based)

Instead of strings, you can encode states as integers in `[0..9999]` and use arrays for speed:

- Encode `"d0d1d2d3"` as `d0*1000 + d1*100 + d2*10 + d3`.
- Use `boolean[] dead` and `boolean[] seen` for O(1) checks and low overhead.
- For each state `curr`, iterate wheel positions `j ∈ {1,10,100,1000}`:
  - Extract digit: `mask = (curr % (10*j)) / j`
  - Zero that digit: `masked = curr - mask*j`
  - Neighbors are `masked + ((mask + 1) % 10)*j` and `masked + ((mask + 9) % 10)*j` (±1 with wraparound)
- Standard BFS on integers ensures minimal turns; stop when reaching `target`.

This avoids substring creation and hash work on strings, improving constants while keeping the same `O(10^4)` bounds.

---

## Comparison

| Approach | Time | Space | Notes |
| -------- | ---- | ----- | ----- |
| Regular BFS | O(10^4) | O(10^4) | **Simple**, easy to implement |
| Bidirectional BFS | O(√(10^4)) | O(10^4) | **Faster**, more complex |

---

## Visual Example (deadends = [], target = "0002")

```markdown
Graph representation:
"0000" can reach:
  - "1000", "9000" (wheel 0)
  - "0100", "0900" (wheel 1)
  - "0010", "0090" (wheel 2)
  - "0001", "0009" (wheel 3)

BFS Levels:
Level 0: "0000"
Level 1: "1000", "9000", "0100", "0900", "0010", "0090", "0001", "0009"
Level 2: "0002" ← Found via "0001" → "0002"

Path: "0000" → "0001" → "0002"
Answer: 2 moves
```

---

## Why This Approach

* **Optimal:** BFS guarantees shortest path
* **Graph Modeling:** Clever way to represent state space
* **Efficient:** Avoids exploring all 10,000 states with visited set
* **Practical:** Common pattern for shortest path in unweighted graphs
* **Scalable:** Bidirectional BFS improves performance significantly

**Key Takeaway:** When finding shortest path with uniform edge weights, BFS is the gold standard!
