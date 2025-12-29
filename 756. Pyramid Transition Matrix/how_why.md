# 756. Pyramid Transition Matrix - Explanation

## Problem Understanding

We need to build a pyramid from a bottom string where each level has one fewer element than the level below. For every two consecutive elements in a level, we can place a specific character above them based on allowed transitions.

**Key Points:**

- Input: `bottom` string and `allowed` list of transitions (e.g., "ABC" means if we have 'A' and 'B' side by side, we can place 'C' above them)
- Output: Boolean indicating if we can build a complete pyramid (until we reach a single character at the top)

**Example:**

```shell
bottom = "AABA"
allowed = ["AAA", "AAB", "ABA", "ABB", "BAC"]

Pyramid:
    X        (level 3 - 1 char)
   Y Z       (level 2 - 2 chars)
  B C D      (level 1 - 3 chars)
 A A B A     (level 0 - 4 chars/bottom)
```

## Approach: DFS with Memoization

### Algorithm Overview

1. **Build a Transition Map**: Create a 2D map where `map[char1][char2]` stores all possible characters that can be placed above the pair (char1, char2).

2. **DFS with Backtracking**:
   - Start with the bottom string
   - At each level, generate all valid possible next rows
   - If we reach a single character, we've successfully built the pyramid
   - Use memoization to avoid recalculating the same rows

3. **Generate Next Row**: For each pair of consecutive characters in the current row, find all valid characters that can be placed above them using the transition map.

### Why This Approach Works

- **Exhaustive Search**: We explore all possible pyramid configurations. If any configuration leads to a single character, the answer is true.
- **Memoization**: If we encounter the same row again, we don't need to recalculate - we already know if it leads to a valid pyramid or not.
- **Early Termination**: If a row has no valid transitions at any position, we return false immediately.

## Code Walkthrough

```java
public boolean pyramidTransition(String bottom, List<String> allowed) {
    // Step 1: Build transition map
    List<Character>[][] map = new List[6][6];  // 6 possible letters (A-F)
    Map<String, Boolean> memo = new HashMap<>();
    
    for (String al : allowed) {
        int a = al.charAt(0) - 'A';
        int b = al.charAt(1) - 'A';
        if (map[a][b] == null) map[a][b] = new ArrayList<>();
        map[a][b].add(al.charAt(2));  // The result character
    }
    
    // Step 2: Start DFS from bottom
    return dfs(bottom.toCharArray(), map, memo);
}

private boolean dfs(char[] row, List<Character>[][] map, Map<String, Boolean> memo) {
    int n = row.length;
    
    // Base case: reached top (single character)
    if (n == 1) return true;
    
    // Memoization check
    String key = new String(row);
    if (memo.containsKey(key)) return memo.get(key);
    
    // Generate all valid next rows
    List<char[]> nextRows = new ArrayList<>();
    getNextRows(row, map, 0, new char[n-1], nextRows);
    
    // Try each possible next row
    for (char[] next : nextRows) {
        if (dfs(next, map, memo)) {
            memo.put(key, true);
            return true;
        }
    }
    
    memo.put(key, false);
    return false;
}

private void getNextRows(char[] row, List<Character>[][] map, int idx, 
                        char[] current, List<char[]> result) {
    // Base case: built complete next row
    if (idx == row.length - 1) {
        result.add(current.clone());
        return;
    }
    
    // Get allowed characters for pair row[idx] and row[idx+1]
    int a = row[idx] - 'A';
    int b = row[idx + 1] - 'A';
    
    if (map[a][b] == null) return;  // No valid transitions
    
    // Try each possible character
    for (char c : map[a][b]) {
        current[idx] = c;
        getNextRows(row, map, idx + 1, current, result);
    }
}
```

## Complexity Analysis

**Time Complexity**: O(T^N) in worst case

- T = number of allowed transitions per pair (typically small, ≤ 5)
- N = height of pyramid (length of bottom string)
- With memoization, we only calculate each unique row once
- Number of unique rows is bounded by the possible character combinations

**Space Complexity**: O(N² × rows)

- Memo table stores results for all unique rows
- Each row stored requires O(N) space
- Maximum N levels in pyramid
- Recursive call stack depth: O(N)

## Key Insights

1. **Backtracking**: `getNextRows` uses backtracking to explore all valid character combinations for the next row.

2. **Early Pruning**: If a pair has no allowed transitions, the entire branch is pruned.

3. **String Memoization**: Memoizing by row string prevents redundant calculations when the same row appears via different paths.

4. **Constraint**: The problem guarantees only letters A-F, so we use a fixed-size 2D array for the transition map.

## Example Trace

```java
Input: bottom = "AABA", allowed = ["AAA","AAB","ABA","ABB","BAC"]

Transition Map:
AA -> [A, B]
AB -> [B]
BA -> [C]

DFS(AABA):
  getNextRows(AABA):
    AA -> try A: A[0] = A, recurse
    AB -> try B: A[1] = B, recurse
    BA -> try C: A[2] = C, recurse
    Result: [ABC]
  
  DFS(ABC):
    getNextRows(ABC):
      AB -> try B: B[0] = B, recurse
      BC -> null (no transition)
    Result: [] (no valid next rows)
    Return false
  
  (Continue trying other combinations...)
```

## Optimization Considerations

The second commented solution in the file uses integer encoding instead of string memoization for better performance:

- Encodes each row as a single integer (base-6 encoding)
- Uses a 3D cache array indexed by (length, encoded_value)
- Reduces memoization overhead and memory usage
