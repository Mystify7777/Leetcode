
# 📘 How & Why – Design Spreadsheet (LeetCode 3484)

## 🔹 Problem Restatement

We need to design a simplified **spreadsheet system** with the following operations:

1. **`setCell(cell, value)`**
   Store an integer value in a cell (e.g., `"A1"`).

2. **`resetCell(cell)`**
   Reset the cell (i.e., remove its value, equivalent to setting it to 0).

3. **`getValue(formula)`**
   Evaluate a formula string like `"=A1+5+B2"`, where each operand can be:

   * A **cell reference** (lookup from the spreadsheet), or
   * A **number** (use directly).
     The formula only supports **addition**.

---

## 🔹 Approaches to Solve

### 1. **Naive Implementation (Fixed Two Operands)**

```java
public int getValue(String formula) {
    int io = formula.indexOf('+');
    String cell1 = formula.substring(1, io);
    String cell2 = formula.substring(io + 1);

    int val1 = Character.isDigit(cell1.charAt(0)) ? 
               Integer.parseInt(cell1) : map.getOrDefault(cell1, 0);

    int val2 = Character.isDigit(cell2.charAt(0)) ? 
               Integer.parseInt(cell2) : map.getOrDefault(cell2, 0);

    return val1 + val2;
}
```

✅ **Pros**:

* Simple parsing with `indexOf('+')`.
* Easy to follow.

❌ **Cons**:

* Only works for **exactly two operands**.
* Doesn’t handle `=A1+5+B2`.

---

### 2. **Naive with Iteration (Two Terms, Detect by Uppercase)**

```java
public int getValue(String formula) {
    formula = formula.substring(1);
    for (int i = 0; i < formula.length(); i++) {
        if (formula.charAt(i) == '+') {
            String left = formula.substring(0, i);
            String right = formula.substring(i + 1);

            int val1 = Character.isUpperCase(left.charAt(0)) ?
                       map.getOrDefault(left, 0) : Integer.parseInt(left);

            int val2 = Character.isUpperCase(right.charAt(0)) ?
                       map.getOrDefault(right, 0) : Integer.parseInt(right);

            return val1 + val2;
        }
    }
    return 0;
}
```

✅ **Pros**:

* Works with **numbers + cells**.
* Logic is explicit.

❌ **Cons**:

* Still supports only **two operands**.
* Assumes cell names always start with uppercase letters (fragile).

---

### 3. **Best Solution (Handles Multiple Terms)**

```java
public int getValue(String formula) {
    formula = formula.substring(1); // remove '='
    String[] parts = formula.split("\\+");

    int sum = 0;
    for (String part : parts) {
        if (Character.isLetter(part.charAt(0))) {
            sum += map.getOrDefault(part, 0);
        } else {
            sum += Integer.parseInt(part);
        }
    }
    return sum;
}
```

✅ **Pros**:

* Handles formulas like `=A1+5+B2+10+C3`.
* Robust cell vs number detection.
* Easy to extend for other operators (`-`, `*`, `/`).

❌ **Cons**:

* Requires splitting and iterating (tiny overhead, but negligible).

---

## 🔹 Why the Best Method Works

* It **splits the formula** into tokens (operands).
* Each operand is checked:

  * If it starts with a **letter**, it’s a cell reference → lookup in map.
  * If it starts with a **digit** (or `-`), it’s an integer → parse it.
* Then we **sum all tokens** together.

This method ensures:

* ✅ Flexibility (any number of terms).
* ✅ Correctness (works for both numbers and cells).
* ✅ Simplicity (straightforward parsing).

---

## 🔹 Recommendation

The **multiple-operand version** is the best.
It’s clean, extensible, and directly reflects how real spreadsheets like Excel/Google Sheets evaluate formulas.

### Suggested Final Class

```java
import java.util.*;

class Spreadsheet {
    private Map<String, Integer> map = new HashMap<>();

    public Spreadsheet(int rows) {}

    public void setCell(String cell, int value) {
        map.put(cell, value);
    }

    public void resetCell(String cell) {
        map.remove(cell);
    }

    public int getValue(String formula) {
        formula = formula.substring(1); // skip '='
        String[] parts = formula.split("\\+");

        int sum = 0;
        for (String part : parts) {
            if (Character.isLetter(part.charAt(0))) {
                sum += map.getOrDefault(part, 0);
            } else {
                sum += Integer.parseInt(part);
            }
        }
        return sum;
    }
}
```

---

✨ This strikes the right balance between **simplicity**, **scalability**, and **readability**.

---
