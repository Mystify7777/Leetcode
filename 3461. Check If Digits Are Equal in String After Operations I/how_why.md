
# ✅ `how_why.md`

## **3461. Check If Digits Are Equal in String After Operations I — How & Why**

### 🧠 **Problem Understanding**

We are given a numeric string `s`. In each operation:

* Replace every digit with **(digit + next digit) % 10**
* The string shrinks by 1 each time
* Continue until only **two digits remain**
* Finally, check whether **both digits are equal**

Example transformation:

```java
s = "1234"
→ [1,2,3,4]
→ [3,5,7]        (1+2, 2+3, 3+4)
→ [8,2]          (3+5, 5+7)
Check: 8 == 2 ? NO
```

The question simply asks: **after repeatedly reducing, do the last two digits become equal?**

---

## 🪜 **Approach 1 — Brute Force (Simulation)**

### **Idea**

Simulate exactly as the problem describes:

* Convert string → int array
* Repeatedly replace `arr[i] = (arr[i] + arr[i+1]) % 10`
* Stop when only 2 are left
* Compare them

### **Code**

```java
class Solution {
  public boolean hasSameDigits(String s) {
    int[] arr = new int[s.length()];

    for (int i = 0; i < arr.length; i++)
      arr[i] = s.charAt(i) - '0';
    
    for (int length = arr.length; length > 2; length--) {
      for (int i = 0; i < length - 1; i++) {
        arr[i] = (arr[i] + arr[i + 1]) % 10;
      }
    }
    return arr[0] == arr[1];
  }
}
```

### **Example Walkthrough**

`s = "572"`

```java
[5,7,2]
→ [2,9]        (5+7, 7+2)
Check: 2 == 9 ? No
```

### **Complexity**

| Time  | Space |
| :---- | :---- |
| O(n²) | O(n)  |

### ✅ **When to use**

* For small/medium constraints
* When simplicity and readability matter

---

## 🧮 **Approach 2 — Mathematical (Binomial Mod Trick)**

### **Idea**

Each final number is a combination of original digits:

After full reduction:

```java
final_left  = Σ C(n-1, i) * s[i]
final_right = Σ C(n-1, i-1) * s[i]
```

So instead of simulating,
we can compute using **binomial coefficients modulo 10**.

Your original code uses:

* **Lucas theorem (base 2 and base 5 decomposition)**
* `mod2 + mod5` combination
* Precomputed Pascal block to get `C(n,k) % 10` efficiently

### ✅ Why it works

Because repeated pair-sum operations behave like Pascal’s triangle coefficients.

### ❌ Why it is *overkill here*

* `n` is small in version I
* Harder to read
* More complex for no real performance benefit

> This mathematical form matters more for **Version II or larger constraints**, not here.

### **Complexity**

| Time       | Space      |
| :--------- | :--------- |
| O(n log n) | O(1) extra |

---

## 🔥 **Best Pick for this Problem**

✅ **Approach 1 (Simulation)** — Clear, maintainable, fast enough.

---

## 🆚 **Comparison Table**

| Approach                | Time       | Space | Difficulty | Best For               |
| ----------------------- | ---------- | ----- | ---------- | ---------------------- |
| Brute Simulation (best) | O(n²)      | O(n)  | Easy       | LeetCode I             |
| Binomial Math           | O(n log n) | O(1)  | Hard       | Very large constraints |

---

## 📌 **Key Takeaways**

* The problem is essentially **Pascal’s triangle + mod 10**
* For Version I → **simulate**
* For harder versions → **math formula is powerful**
* Always match solution complexity to constraint size — not more, not less

---
