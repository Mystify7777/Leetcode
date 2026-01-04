# How_Why.md: Generate Parentheses

## Problem

Given `n` pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

**Example:**

```java
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]

Input: n = 1
Output: ["()"]
```

---

## Approach: Backtracking with String Concatenation

### Idea

* Use **backtracking** to build valid combinations character by character
* **Key Rules:**
  - Can add `(` when we have used less than `n` opening parentheses
  - Can add `)` only when we have more opening than closing parentheses
* Use **DFS** to explore all valid paths

### Code

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        dfs(0, 0, "", n, res);
        return res;        
    }

    private void dfs(int openP, int closeP, String s, int n, List<String> res) {
        // Base case: valid complete combination
        if (openP == closeP && openP + closeP == n * 2) {
            res.add(s);
            return;
        }

        // Add opening parenthesis if we haven't used all n
        if (openP < n) {
            dfs(openP + 1, closeP, s + "(", n, res);
        }

        // Add closing parenthesis only if it doesn't exceed opening
        if (closeP < openP) {
            dfs(openP, closeP + 1, s + ")", n, res);
        }
    }    
}
```

### Why This Works

* **Decision Tree Example (n = 2):**

```tree
    Start: ""
            |
        add "("
            |
        "(" (open=1, close=0)
        /              \
    add "("         add ")"
        |                |
    "((" (1,0)      "()" (1,1)
    /     \            |
    "(()"  "((("      add "("
    /        |           |
    add ")"  invalid   "()(" (2,1)
    |                   |
    "(())" ✓           add ")"
                        |
                    "()()" ✓
```

* **Valid Path Example:**

  ```text
  Step 1: open=0, close=0, s="" → add '(' → open=1
  Step 2: open=1, close=0, s="(" → add '(' → open=2
  Step 3: open=2, close=0, s="((" → add ')' → close=1
  Step 4: open=2, close=1, s="(()" → add ')' → close=2
  Final: open=2, close=2, s="(())" ✓ Valid!
  ```

* **Time Complexity:** **O(4^n / √n)** - Catalan number bound
* **Space Complexity:** **O(n)** - recursion depth

---

## Approach 2: Backtracking with StringBuilder (Optimized)

### Idea*

* Same backtracking logic but use **StringBuilder** for better performance
* **StringBuilder** avoids creating new string objects at each step
* Use **backtrack with deletion** to reuse the same StringBuilder

### Code*

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(0, 0, n, ans, new StringBuilder());
        return ans;
    }

    private void backtrack(int start, int end, int n, List<String> ans, StringBuilder sb) {
        // Base case: valid complete combination
        if (start == n && end == n) {
            ans.add(sb.toString());
            return;
        }

        // Try adding opening parenthesis
        if (start < n) {
            sb.append("(");
            backtrack(start + 1, end, n, ans, sb);
            sb.deleteCharAt(sb.length() - 1);  // Backtrack
        }

        // Try adding closing parenthesis
        if (end < start) {
            sb.append(")");
            backtrack(start, end + 1, n, ans, sb);
            sb.deleteCharAt(sb.length() - 1);  // Backtrack
        }
    }
}
```

### Why This is Better

* **Memory Efficiency:**
  - String concatenation: Creates new string object each time
  - StringBuilder: Reuses same object, just appends/deletes

* **Performance Example (n = 3):**
  
  ```text
  String approach: 
    "" → "(" → "((" → "(((" → back to "((" → "(()" ...
    Each step creates NEW string object
  
  StringBuilder approach:
    sb = ""
    append '(' → sb = "("
    append '(' → sb = "(("
    append '(' → sb = "((("
    delete last → sb = "(("
    append ')' → sb = "(()"
    Same object throughout!
  ```

* **Time Complexity:** **O(4^n / √n)** - same as Approach 1
* **Space Complexity:** **O(n)** - less string object creation

---

## Comparison

| Approach | Time | Space | Notes |
| -------- | ---- | ----- | ----- |
| String Concatenation | O(4^n / √n) | O(n) | **Simple**, easier to understand |
| StringBuilder | O(4^n / √n) | O(n) | **More efficient**, better for interviews |

---

## Visual Example Walkthrough (n = 2)

```text
All valid combinations for n=2: ["(())", "()()"]

DFS Tree:
                    ""
                    |
                   "("
                  /   \
              "(("     "()"
               |         |
             "(()"      "()("
               |         |
            "(())" ✓   "()()" ✓

Key Rules Applied:
1. At "": openP=0 < n → add '('
2. At "(": openP=1 < n → can add '(' OR closeP=0 < openP → can add ')'
3. At "((": openP=2 = n → can't add more '(', only ')'
4. At "(()": closeP=1 < openP=2 → add ')'
5. At "(())": openP=2, closeP=2 → DONE ✓
```

---

## Why This Approach

* **Elegant:** Uses simple backtracking rules
* **Complete:** Generates all valid combinations without duplicates
* **Efficient:** Prunes invalid paths early (can't add ')' if closeP >= openP)
* **Interview Favorite:** Classic backtracking problem demonstrating recursion mastery
* **Optimization Ready:** Easy to improve with StringBuilder

**Key Takeaway:** When generating all combinations with constraints, backtracking with early pruning is the way to go!
