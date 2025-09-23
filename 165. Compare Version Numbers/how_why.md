# 165. Compare Version Numbers – How & Why

## Problem Recap

You’re given two version strings like `"1.01"` and `"1.001"`, or `"1.0"` and `"1.0.0"`.

You need to:

* Compare them *revision by revision* (split by `.`).
* Return `1` if `version1 > version2`, `-1` if `version1 < version2`, else `0`.

Leading zeros should **not** matter (e.g., `"001"` = `1`).

---

## 1. Brute-force Approach

* Split both strings using `"."`.
* Compare corresponding numbers one by one.
* Pad shorter version with extra `0`s if needed.

```java
String[] v1 = version1.split("\\.");
String[] v2 = version2.split("\\.");
int n = Math.max(v1.length, v2.length);

for (int i = 0; i < n; i++) {
    int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
    int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
    if (num1 != num2) return num1 > num2 ? 1 : -1;
}
return 0;
```

* ✅ Easy to implement
* ❌ Uses extra space (arrays + parsing overhead)

---

## 2. Your Optimized Solution (Pointer Parsing)

```java
public int compareVersion(String version1, String version2) {
    int i = 0, j = 0;
    int len1 = version1.length(), len2 = version2.length();

    while (i < len1 || j < len2) {
        int temp1 = 0, temp2 = 0;

        // parse number from version1
        while (i < len1 && version1.charAt(i) != '.') {
            temp1 = temp1 * 10 + (version1.charAt(i++) - '0');
        }

        // parse number from version2
        while (j < len2 && version2.charAt(j) != '.') {
            temp2 = temp2 * 10 + (version2.charAt(j++) - '0');
        }

        if (temp1 > temp2) return 1;
        if (temp1 < temp2) return -1;

        // skip the dot
        i++;
        j++;
    }
    return 0;
}
```

### How it works

* **Parse revision numbers** manually, digit by digit (`char - '0'`).
* Compare immediately → no need to store substrings.
* Handles unequal lengths (`"1.0"` vs `"1.0.0"`) because of `while (i<len1 || j<len2)`.

### Example Walkthrough

`version1 = "1.01"`, `version2 = "1.001"`

* i=0 → temp1=1, temp2=1 → equal → skip `.`
* i=2, j=2 → parse `01 → 1`, `001 → 1` → equal
* Done → return `0`.

✅ Correctly ignores leading zeros.

---

## Complexity

* **Time:** O(max(len1, len2)) (linear scan of both strings).
* **Space:** O(1) (constant extra space, no split arrays).

---

## Why this approach is better

* Avoids creating arrays (`split()` creates extra objects).
* Constant space.
* Works directly on raw strings → efficient for large inputs.

---
