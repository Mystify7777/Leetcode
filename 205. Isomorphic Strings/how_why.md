# 205. Isomorphic Strings - How & Why

## Problem Overview

Determine if two strings are isomorphic. Two strings `s` and `t` are isomorphic if the characters in `s` can be replaced to get `t`, while preserving the order and ensuring a one-to-one character mapping (no two characters map to the same character, and each character maps to exactly one character).

**Example:**

- `"egg"` and `"add"` → true (e→a, g→d)
- `"foo"` and `"bar"` → false (o→o initially, but o→a conflicts)
- `"paper"` and `"title"` → true (p→t, a→i, e→l, r→e)

## Algorithm Explanation

### Key Insight

Use **two mapping arrays** to track:

1. What each character in `s` maps to (by storing position/index)
2. What each character in `t` maps to (by storing position/index)

By storing the **same index value** in both arrays for corresponding characters, we ensure a **bijective (one-to-one) mapping**.

### Step-by-Step Logic

1. **Initialize two arrays** (`map1` and `map2`) of size 200 to cover extended ASCII
2. **Check length match** - If lengths differ, strings can't be isomorphic
3. **Iterate through both strings simultaneously:**
   - If `map1[s.charAt(i)]` ≠ `map2[t.charAt(i)]`, the mapping is inconsistent → return false
   - Update both mappings with `i+1` (using `i+1` instead of `i` to avoid default 0 confusion)
4. **Return true** if all characters maintain consistent mappings

### Why This Works

- We use **position indexing** (`i+1`) as a "timestamp" to ensure characters map consistently
- If `s[i]='e'` and `t[i]='a'`, we set `map1['e']=i+1` and `map2['a']=i+1`
- On the next occurrence of 'e', if it doesn't map to 'a', the values will differ

### Time & Space Complexity

- **Time:** O(n) where n = length of strings (single pass through both strings)
- **Space:** O(1) - Fixed size arrays (200 elements)

## Is the Second Implementation Better?

**No, the first implementation is significantly better!**

### Comparison

#### First Implementation (Better ✅)

```java
int map1[]=new int[200];
int map2[]=new int[200];

for(int i=0;i<s.length();i++) {
    if(map1[s.charAt(i)]!=map2[t.charAt(i)])
        return false;
    map1[s.charAt(i)]=i+1;
    map2[t.charAt(i)]=i+1;
}

```

- **Time Complexity:** O(n)
- **Two arrays** to ensure bidirectional mapping
- **Efficient:** Constant time lookups

#### Second Implementation (Worse ❌)

```java
char[] seen = new char[128];

for (int i = 0; i < len; i++) {
    char c = s.charAt(i);
    if (seen[c] == 0) {
        // Check if t[i] is already mapped to another char
        for (char ch : seen) {  // ⚠️ O(128) nested loop!
            if (ch == t.charAt(i)) {
                return false;
            }
        }
        seen[c] = t.charAt(i);
    }
}
```

- **Time Complexity:** O(n × 128) = O(n) but with much larger constant
- **Only one array:** Must iterate through entire array to check reverse mapping
- **Inefficient:** Nested loop checks all 128 positions for every new character

### Why First is Better

1. **No Nested Loops** - First implementation has O(n) with clean constant factor, second has nested loops

2. **Bidirectional Tracking** - Two arrays naturally handle both mappings without extra work

3. **Better Performance** - First avoids iterating through 128 elements repeatedly

4. **Cleaner Logic** - Symmetrical design makes the code more intuitive

### Performance Impact

- For string `"abcdefg"` mapping to `"hijklmn"`:
  - **First:** 7 comparisons total
  - **Second:** 7 + (0+1+2+3+4+5+6) = 28 array iterations

## Code Walkthrough (First Implementation)

```java
int map1[]=new int[200];  // Maps s characters to positions
int map2[]=new int[200];  // Maps t characters to positions

if(s.length()!=t.length())
    return false;

for(int i=0;i<s.length();i++) {
    // Check if current mapping is consistent
    if(map1[s.charAt(i)]!=map2[t.charAt(i)])
        return false;
    
    // Update both mappings with current position (+1 to avoid 0 default)
    map1[s.charAt(i)]=i+1;
    map2[t.charAt(i)]=i+1;
}
return true;
```

## Example Walkthrough

**Input:** `s = "egg"`, `t = "add"`

| i | s[i] | t[i] | Before Check | map1[s[i]] | map2[t[i]] | After Update |
| --- | ------ | ------ | -------------- | ------------ | ------------ | -------------- |
| 0 | 'e' | 'a' | map1['e']=0, map2['a']=0 | 0 | 0 | map1['e']=1, map2['a']=1 ✅ |
| 1 | 'g' | 'd' | map1['g']=0, map2['d']=0 | 0 | 0 | map1['g']=2, map2['d']=2 ✅ |
| 2 | 'g' | 'd' | map1['g']=2, map2['d']=2 | 2 | 2 | map1['g']=3, map2['d']=3 ✅ |

**Result:** All checks pass → Return true

**Counter-example:** `s = "foo"`, `t = "bar"`

| i | s[i] | t[i] | map1[s[i]] | map2[t[i]] | Status |
| --- | ------ | ------ | ------------ | ------------ | -------- |
| 0 | 'f' | 'b' | 0 | 0 | map1['f']=1, map2['b']=1 ✅ |
| 1 | 'o' | 'a' | 0 | 0 | map1['o']=2, map2['a']=2 ✅ |
| 2 | 'o' | 'r' | 2 | 0 | 2 ≠ 0 → Return false ❌ |

'o' was already mapped to 'a', but now it's trying to map to 'r' → Not isomorphic!

## Edge Cases

1. **Different lengths** - Return false immediately
2. **Empty strings** - Return true (vacuously isomorphic)
3. **Single character** - Always return true
4. **Self-mapping** - `"ab"` → `"aa"` should return false (caught by bidirectional check)
5. **Reverse mapping issue** - `"ab"` → `"ca"` vs `"aa"` → `"ab"` (two arrays catch this)

## Key Takeaways

- **Two arrays are better than one** when checking bidirectional mappings
- Using **position as a value** is a clever way to track consistency
- The first implementation is **optimal** for this problem
- Avoid nested loops when a second array can solve the problem in O(1) space
