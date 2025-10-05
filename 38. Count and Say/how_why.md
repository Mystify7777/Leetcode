# How_Why.md — Count and Say (LeetCode 38)

---

## ❌ Brute Force (Conceptual)

### **Idea**

We could recursively generate each term by:

* Describing the previous term **digit by digit**.
* For example:

  * Term 1 → “1”
  * Term 2 → “one 1” → “11”
  * Term 3 → “two 1s” → “21”
  * Term 4 → “one 2, one 1” → “1211”

But directly using recursion for string building is inefficient because:

* Each recursive call rebuilds the entire string.
* String concatenation is costly.

### **Why It’s Bad**

* Exponential time due to repeated string creation.
* High memory usage for recursive calls.

---

## ✅ Optimized Iterative Approach (Your Code)

### **Core Idea**

Build the sequence **iteratively** from `1` up to `n`,
each time “describing” the previous term.

For each string:

* Count consecutive same digits.
* Append `count` + `digit` to form the next term.

---

### **Example Walkthrough**

Let’s generate `n = 5`:

| Step | Current Term | Description (spoken) | Next Term  |
| ---- | ------------ | -------------------- | ---------- |
| 1    | `"1"`        | one 1                | `"11"`     |
| 2    | `"11"`       | two 1s               | `"21"`     |
| 3    | `"21"`       | one 2, one 1         | `"1211"`   |
| 4    | `"1211"`     | one 1, one 2, two 1s | `"111221"` |

✅ **Answer for n=5 → "111221"**

---

### **Code**

```java
class Solution {
    public String countAndSay(int n) {
        String res = "1";
        for (int i = 1; i < n; i++) {
            StringBuilder temp = new StringBuilder();
            int count = 1;
            
            for (int j = 1; j < res.length(); j++) {
                if (res.charAt(j) == res.charAt(j - 1)) {
                    count++;
                } else {
                    temp.append(count).append(res.charAt(j - 1));
                    count = 1;
                }
            }
            
            // Append the last group
            temp.append(count).append(res.charAt(res.length() - 1));
            res = temp.toString();
        }
        return res;
    }
}
```

---

### **Complexity Analysis**

| Type     | Complexity | Explanation                                                                     |
| -------- | ---------- | ------------------------------------------------------------------------------- |
| ⏱ Time   | O(L × n)   | L = average length of each term; each character is processed once per iteration |
| 💾 Space | O(L)       | For the temporary string builder at each step                                   |

---

### **Key Insights**

* The “Count and Say” sequence is essentially a **run-length encoding** of digits.
* Iterative processing avoids recursion overhead.
* Using `StringBuilder` prevents costly string concatenations.
* Each term is derived entirely from the previous — no need to store all intermediate terms.

---
