# How & Why: LeetCode 966 - Vowel Spellchecker

---

## Problem Restatement
We are given:
- `wordlist`: a list of valid words.
- `queries`: a list of words we need to check.

For each query, we need to return the matching word from `wordlist` according to the following priority rules:
1. **Exact Match**: If the query exactly matches a word in `wordlist`, return it.
2. **Case-Insensitive Match**: If no exact match, but a case-insensitive match exists, return the first match from `wordlist`.
3. **Vowel Error Match**: If no case match, treat all vowels (`a, e, i, o, u`) as interchangeable and return the first match from `wordlist`.
4. **No Match**: If none of the above apply, return an empty string.

---

## How to Solve

### Step 1: Build Data Structures
We prepare three structures from `wordlist`:
1. **Exact Set**:
   ```java
   Set<String> exact = new HashSet<>(Arrays.asList(wordlist));
   ```
   Allows O(1) lookup for exact matches.

2. **Case Map**:
   ```java
   Map<String, String> caseMap = new HashMap<>();
   ```
   Key = lowercase word, Value = original word (first occurrence).
   Handles case-insensitive matches.

3. **Vowel Map**:
   ```java
   Map<String, String> vowelMap = new HashMap<>();
   ```
   Key = lowercase word with all vowels replaced by `*`, Value = original word.
   Handles vowel error matches.

### Step 2: Preprocess Wordlist
For each word:
- Store lowercase version in `caseMap`.
- Store de-voweled version in `vowelMap`.

```java
for (String w : wordlist) {
    String lower = toLower(w);
    String devowel = deVowel(lower);
    caseMap.putIfAbsent(lower, w);
    vowelMap.putIfAbsent(devowel, w);
}
```

### Step 3: Process Queries
For each query `q`:
1. Check **exact match**:
   ```java
   if (exact.contains(q)) result[i] = q;
   ```
2. Else check **case-insensitive match**:
   ```java
   else if (caseMap.containsKey(lower)) result[i] = caseMap.get(lower);
   ```
3. Else check **vowel match**:
   ```java
   else if (vowelMap.containsKey(devowel)) result[i] = vowelMap.get(devowel);
   ```
4. Else return empty string.

---

## Why This Works
1. **Priority Order**: By checking exact > case-insensitive > vowel, we ensure rules are followed correctly.
2. **Efficient Lookups**: Using `Set` and `Map` ensures constant-time lookups for all cases.
3. **Preprocessing First Occurrence**: `putIfAbsent` guarantees the first valid word from `wordlist` is returned, satisfying problem constraints.

---

## Complexity Analysis
- **Preprocessing**: O(n × L), where `n` = wordlist size, `L` = average word length.
- **Query Processing**: O(m × L), where `m` = number of queries.
- **Space Complexity**: O(n × L) for storing maps.

---

## Example Walkthrough
Input:
```
wordlist = ["KiTe", "kite", "hare", "Hare"]
queries = ["kite", "Kite", "KiTe", "HARE", "Hear", "keti", "keet", "keto"]
```

Process:
- Query `"kite"`: exact match → `"kite"`.
- Query `"Kite"`: case match → `"KiTe"` (first occurrence in wordlist).
- Query `"Hear"`: de-vowel `h**r` matches `hare` → `"hare"`.
- Query `"keet"`: de-vowel `k**t` matches none → `""`.

Output:
```
["kite","KiTe","KiTe","hare","","KiTe","","KiTe"]
```

---

## Alternate Approaches
1. **Regex Matching**: Build regex patterns for vowel replacement and apply to queries. This is slower and less efficient.
2. **Trie Data Structure**: Store words in a trie with special handling for vowels. This could be faster for very large datasets but overkill for constraints.

### Optimal Choice
The **map-based preprocessing approach** is optimal:
- Clean O(n + m) structure.
- Minimal overhead.
- Matches problem’s required priority naturally.

---

## Key Insight
The problem is about **layered matching rules**:
- Exact → Case-insensitive → Vowel-error.
By preprocessing `wordlist` into specialized maps, we ensure constant-time query handling while respecting match priority.

