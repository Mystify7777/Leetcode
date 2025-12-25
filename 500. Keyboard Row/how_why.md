# Recap

Given an array of strings `words`, return the words that can be typed using letters of the alphabet on only one row of a standard American keyboard (QWERTY layout).

The keyboard rows are:

- **Row 1:** `qwertyuiop`
- **Row 2:** `asdfghjkl`
- **Row 3:** `zxcvbnm`

## Intuition

For each word, we need to check if all its characters belong to the same keyboard row. We can map each letter to its row number, then verify that all letters in a word have the same row mapping.

## Approach

1. **Create a row mapping array:**
   - Map each letter `a-z` to its keyboard row (1, 2, or 3).
   - Use an integer array where `rows[i]` represents the row number for the `i-th` letter of the alphabet.

2. **For each word:**
   - Convert to lowercase (to handle case-insensitive checking).
   - Get the row of the first character.
   - Iterate through remaining characters and check if they belong to the same row.
   - If all characters are from the same row, add the original word to the result list.

3. **Return the result** as a string array.

## Code (Java)

```java
class Solution {
    public String[] findWords(String[] words) {
        // Row mapping: a=2, b=3, c=3, d=2, e=1, f=2, g=2, h=2, i=1, j=2, k=2, l=2,
        //              m=3, n=3, o=1, p=1, q=1, r=1, s=2, t=1, u=1, v=3, w=1, x=3, y=1, z=3
        int[] rows = {2,3,3,2,1,2,2,2,1,2,2,2,3,3,1,1,1,1,2,1,1,3,1,3,1,3};
        List<String> list = new ArrayList<>();
        
        for(String word: words){
            String s = word.toLowerCase();
            boolean isValid = true;
            int row = rows[s.charAt(0) - 'a'];
            
            for(int i = 1; i < s.length(); i++){
                if(rows[s.charAt(i) - 'a'] != row){
                    isValid = false;
                    break;
                }
            }
            
            if(isValid){
                list.add(word);
            }
        }
        
        return list.toArray(new String[0]);
    }
}
```

## Complexity Analysis

- **Time Complexity:** `O(n * m)` where `n` is the number of words and `m` is the average length of each word. We iterate through each character of each word once.
- **Space Complexity:** `O(n)` for storing the result list in the worst case (if all words are valid).

## Key Observations

1. **Row mapping array:** The array `{2,3,3,2,1,2,2,2,1,2,2,2,3,3,1,1,1,1,2,1,1,3,1,3,1,3}` represents the keyboard rows for letters a-z:
   - Indices 0-25 correspond to letters 'a'-'z'
   - Values 1, 2, 3 correspond to keyboard rows 1, 2, 3

2. **Case-insensitive:** Converting to lowercase ensures that both 'A' and 'a' are treated the same way.

3. **Early termination:** Using the `break` statement when a character from a different row is found improves efficiency.

4. **Preserve original case:** We add the original `word` to the result, not the lowercase version.

## Example Walkthrough

**Input:** `words = ["Hello", "Alaska", "Dad", "Peace"]`

1. **"Hello":**
   - Lowercase: "hello"
   - h=2, e=1 → Different rows → Invalid ❌

2. **"Alaska":**
   - Lowercase: "alaska"
   - a=2, l=2, a=2, s=2, k=2, a=2 → All row 2 → Valid ✓

3. **"Dad":**
   - Lowercase: "dad"
   - d=2, a=2, d=2 → All row 2 → Valid ✓

4. **"Peace":**
   - Lowercase: "peace"
   - p=1, e=1, a=2 → Different rows → Invalid ❌

**Output:** `["Alaska", "Dad"]`

## Alternative Approaches

### Using HashSet

Instead of an array, you could use three HashSets containing the characters of each row, then check set membership:

```java
Set<Character> row1 = new HashSet<>(Arrays.asList('q','w','e','r','t','y','u','i','o','p'));
Set<Character> row2 = new HashSet<>(Arrays.asList('a','s','d','f','g','h','j','k','l'));
Set<Character> row3 = new HashSet<>(Arrays.asList('z','x','c','v','b','n','m'));
```

This is more readable but slightly less efficient due to set lookup overhead compared to direct array indexing.

### Using Regex

You could use regex patterns to match words that contain only characters from one row:

```java
Pattern pattern = Pattern.compile("^[qwertyuiop]+$|^[asdfghjkl]+$|^[zxcvbnm]+$", Pattern.CASE_INSENSITIVE);
```

This is more concise but potentially harder to understand and debug.
