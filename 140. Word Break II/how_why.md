# 🧠 How_Why.md — Word Break II (LeetCode 140)

---

## ❌ 1️⃣ Brute Force Approach

### Idea

Try every possible way to break the string by inserting spaces.
Check if every resulting substring exists in the given dictionary.

---

### Algorithm

1. Start from index `0`.
2. At each index `i`, try every possible cut `s.substring(i, j)`.
3. If substring exists in dictionary → recursively process `s[j:]`.
4. If end of string is reached → store that sentence.

---

### Example

```c
s = "catsanddog"
dict = ["cat", "cats", "and", "sand", "dog"]
```

Explore all partitions:

```c
"cat" + "sand" + "dog"  ✅
"cats" + "and" + "dog" ✅
Others ❌
```

---

### **Limitations**

* Repeats same checks for overlapping substrings (e.g., `"anddog"` multiple times)
* Exponential time complexity

**⛔ Time:** O(2ⁿ)
**⛔ Space:** O(n) recursion stack

---

## ✅ 2️⃣ Your Approach — Backtracking with HashSet

### **Core Idea**

Use **DFS + Backtracking** to generate all valid sentences.

At each position:

* Try all substrings starting at `i`.
* If the substring exists in the dictionary, recurse for the remaining string.
* Backtrack after each recursive call.

---

### Algorithm Steps

1. Convert word list to a **HashSet** for O(1) lookup.
2. Start recursion from index `0`.
3. If you reach end of string, combine all collected words and add to result.
4. Backtrack after exploring each valid path.

---

### Code

```java
class Solution {
    private void helper(String s, int i, Set<String> dict, List<String> cur, List<String> res) {
        if (i == s.length()) {
            if (cur.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < cur.size(); j++) {
                    if (j > 0) sb.append(' ');
                    sb.append(cur.get(j));
                }
                res.add(sb.toString());
            }
            return;
        }

        for (int j = i + 1; j <= s.length(); j++) {
            if (dict.contains(s.substring(i, j))) {
                cur.add(s.substring(i, j));
                helper(s, j, dict, cur, res);
                cur.remove(cur.size() - 1); // backtrack
            }
        }
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        List<String> res = new ArrayList<>();
        helper(s, 0, dict, new ArrayList<>(), res);
        return res;
    }
}
```

---

### **Walkthrough Example**

#### Input

```c
s = "catsanddog"
dict = ["cat", "cats", "and", "sand", "dog"]
```

#### Trace

```c
Start at i = 0
→ "cat" ✅ → recurse from i = 3
     → "sand" ✅ → recurse from i = 7
          → "dog" ✅ → result: "cat sand dog"
← backtrack
→ "cats" ✅ → recurse from i = 4
     → "and" ✅ → recurse from i = 7
          → "dog" ✅ → result: "cats and dog"
```

✅ Output:

```c
["cat sand dog", "cats and dog"]
```

---

### **Complexity**

| Metric | Value | Reason                                              |
| :----- | :---- | :-------------------------------------------------- |
| Time   | O(2ⁿ) | Each index can branch into multiple substring paths |
| Space  | O(n)  | Recursion stack & current path storage              |

---

## ⚡ 3️⃣ Optimized Approach — Backtracking + Memoization (DP)

### **Why Improve**

Even though backtracking works, it **recomputes subproblems** like
“what are all valid sentences starting from index i = 3?” multiple times.

We can store and reuse these results using **Memoization**.

---

### Core Idea

* Cache the results for each start index `i`.
* If a sentence starting from index `i` was already computed → reuse it.

---

### Algorithm Steps

1. Create a `Map<Integer, List<String>> memo` where key = start index.
2. For each index `i`, explore substrings `s[i:j]` that exist in dictionary.
3. Recurse on `j` and get all possible suffix sentences.
4. Combine current word with each valid suffix.
5. Store results in `memo` before returning.

---

### Code

```java
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        return dfs(s, 0, dict, new HashMap<>());
    }

    private List<String> dfs(String s, int start, Set<String> dict, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) return memo.get(start);

        List<String> res = new ArrayList<>();
        if (start == s.length()) {
            res.add("");
            return res;
        }

        for (int end = start + 1; end <= s.length(); end++) {
            String prefix = s.substring(start, end);
            if (dict.contains(prefix)) {
                List<String> suffixList = dfs(s, end, dict, memo);
                for (String suffix : suffixList) {
                    res.add(prefix + (suffix.isEmpty() ? "" : " " + suffix));
                }
            }
        }

        memo.put(start, res);
        return res;
    }
}
```

---

### Walkthrough Example

#### Input

```c
s = "catsanddog"
dict = ["cat", "cats", "and", "sand", "dog"]
```

#### Trace

```c
dfs(0):
    "cat" ✅ → dfs(3)
        dfs(3):
            "sand" ✅ → dfs(7)
                dfs(7):
                    "dog" ✅ → ["dog"]
                → combine → ["sand dog"]
        → combine → ["cat sand dog"]
    "cats" ✅ → dfs(4)
        dfs(4):
            "and" ✅ → dfs(7)
                already computed → ["dog"]
            → combine → ["and dog"]
        → combine → ["cats and dog"]
memo = { 7: ["dog"], 4: ["and dog"], 3: ["sand dog"], 0: ["cat sand dog", "cats and dog"] }
```

✅ Final result:

```c
["cat sand dog", "cats and dog"]
```

---

### Complexity

| Metric | Value | Reason                                                         |
| :----- | :---- | :------------------------------------------------------------- |
| Time   | O(n³) | substring extraction (O(n)) × recursion for each index (O(n²)) |
| Space  | O(n²) | memoization map + recursion stack                              |

---

## 🧩 Summary Comparison

| Approach               | Time  | Space | Method                 | Notes                                             |
| :--------------------- | :---- | :---- | :--------------------- | :------------------------------------------------ |
| **Brute Force**        | O(2ⁿ) | O(n)  | Pure recursion         | Tries all partitions blindly                      |
| **Your Backtracking**  | O(2ⁿ) | O(n)  | Backtracking + HashSet | Efficient pruning, clear logic                    |
| **Optimized Memoized** | O(n³) | O(n²) | Backtracking + DP      | Caches overlapping subproblems, practical optimal |

---

## 💡 Key Takeaways

* **Word Break II** is about *generating all combinations*, not just checking validity — so exponential output is unavoidable.
* **HashSet lookup** speeds up substring validation.
* **Memoization** reduces redundant recursion, making it usable for large inputs.
* This is a **classic “DFS + memoization”** pattern used in many string segmentation problems.

---
