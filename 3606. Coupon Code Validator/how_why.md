# 3606. Coupon Code Validator — how/why

## Recap

Given three parallel arrays:

- `code`: coupon codes as strings
- `businessLine`: business category for each coupon ("electronics", "grocery", "pharmacy", "restaurant")
- `isActive`: boolean indicating if the coupon is active

Return a list of valid coupon codes sorted by:

1. Business line priority: electronics > grocery > pharmacy > restaurant
2. Lexicographically within the same business line

A coupon is valid if:

- `isActive[i]` is true
- `businessLine[i]` is one of the four recognized categories
- `code[i]` contains only alphanumeric characters and underscores (no empty strings)

## Intuition

This is a filtering + sorting problem with specific business rules:

**Validation criteria**:

- Active status check
- Business line must be recognized (one of 4 categories)
- Code format: non-empty, alphanumeric + underscore only

**Sorting strategy**:

- Assign numeric priority to each business line (electronics = 0, grocery = 1, etc.)
- Sort by priority first, then alphabetically by code within same priority

**Implementation choice**: Either collect valid codes with their priorities and sort once, or maintain separate lists per business line and concatenate in order.

## Approach

**Priority-Based Sorting with HashMap**:

1. Define priority map: `electronics → 0`, `grocery → 1`, `pharmacy → 2`, `restaurant → 3`.
2. Iterate through all coupons:
   - Check if `isActive[i]` is true.
   - Check if `businessLine[i]` is in the priority map.
   - Validate `code[i]` format (non-empty, alphanumeric + underscore).
   - If all checks pass, create a `(priority, code)` pair and add to valid list.
3. Sort valid list by:
   - Primary: numeric priority (ascending)
   - Secondary: code lexicographically (ascending)
4. Extract codes from sorted pairs and return.

**Code validation helper**:

- Return false if empty string
- Check each character: must be letter, digit, or underscore
- Return true if all pass

## Code (Java)

```java
class Solution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        // Business line priority
        Map<String, Integer> priority = new HashMap<>();
        priority.put("electronics", 0);
        priority.put("grocery", 1);
        priority.put("pharmacy", 2);
        priority.put("restaurant", 3);

        List<Pair> valid = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {
            if (isActive[i] && priority.containsKey(businessLine[i]) && isValidCode(code[i])) {
                valid.add(new Pair(priority.get(businessLine[i]), code[i]));
            }
        }

        // Sort by business priority, then by code
        Collections.sort(valid, (a, b) -> {
            if (a.priority != b.priority)
                return a.priority - b.priority;
            return a.code.compareTo(b.code);
        });

        List<String> result = new ArrayList<>();
        for (Pair p : valid) {
            result.add(p.code);
        }

        return result;
    }

    private boolean isValidCode(String s) {
        if (s.length() == 0) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_')
                return false;
        }
        return true;
    }

    static class Pair {
        int priority;
        String code;

        Pair(int priority, String code) {
            this.priority = priority;
            this.code = code;
        }
    }
}
```

## Correctness

- **Active check**: `isActive[i]` filters out inactive coupons immediately.

- **Business line validation**: `priority.containsKey(businessLine[i])` ensures only recognized categories are processed; any other value is rejected.

- **Code format validation**:
  - Empty strings return false.
  - Character-by-character check ensures only alphanumeric and underscore pass.
  - Special characters, spaces, and punctuation are rejected.

- **Priority ordering**: Numeric priorities ensure business lines are grouped and ordered correctly: electronics first, restaurant last.

- **Lexicographic tie-breaking**: `code.compareTo(b.code)` sorts alphabetically within same priority, handling uppercase/lowercase consistently.

- **Completeness**: All valid coupons are collected; all invalid ones are filtered out.

## Complexity

- **Time**: O(n · m + k log k) where:
  - n = number of coupons
  - m = average code length (for validation)
  - k = number of valid coupons
  - Validation: O(n · m)
  - Sorting: O(k log k)
  - Overall: O(n · m + k log k)
- **Space**: O(k) for storing valid pairs and result list.

## Edge Cases

- No valid coupons: All inactive, invalid business lines, or invalid codes → return empty list.
- All coupons valid: All pass filters → return all sorted by priority and code.
- Single business line: All valid coupons from same category → sorted lexicographically.
- Duplicate codes in different business lines: Each appears separately, sorted by their respective priorities.
- Codes with underscores: Valid characters, e.g., `SAVE_20` is accepted.
- Mixed case codes: Sorted lexicographically respecting case (e.g., `A` < `a` in Java's compareTo).
- Empty code array: Return empty list.
- Unrecognized business line: Filtered out even if active and valid format.

## Takeaways

- **Multi-criteria validation**: Combine multiple boolean checks efficiently with short-circuit AND (`&&`).
- **Priority encoding**: Map categories to integers for efficient numerical comparison.
- **Comparator chaining**: Sort by primary key first, then secondary key for tie-breaking.
- **Character validation**: Use `Character.isLetterOrDigit()` for clean alphanumeric checks.
- **Separation of concerns**: Helper method `isValidCode()` isolates format validation logic.
- **Pair/Tuple pattern**: Storing (priority, value) together enables clean sorting without recalculating priorities.

## Alternative (Separate Lists per Business Line, O(n · m + k log k))

```java
class Solution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        List<String> electronics = new ArrayList<>();
        List<String> grocery = new ArrayList<>();
        List<String> pharmacy = new ArrayList<>();
        List<String> restaurant = new ArrayList<>();
        
        for (int i = 0; i < code.length; i++) {
            if (!isActive[i] || code[i].isEmpty()) continue;
            
            boolean validCode = true;
            for (char c : code[i].toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '_') {
                    validCode = false;
                    break;
                }
            }
            
            if (validCode) {
                if (businessLine[i].equals("electronics")) {
                    electronics.add(code[i]);
                } else if (businessLine[i].equals("grocery")) {
                    grocery.add(code[i]);
                } else if (businessLine[i].equals("pharmacy")) {
                    pharmacy.add(code[i]);
                } else if (businessLine[i].equals("restaurant")) {
                    restaurant.add(code[i]);
                }
            }
        }
        
        Collections.sort(electronics);
        Collections.sort(grocery);
        Collections.sort(pharmacy);
        Collections.sort(restaurant);
        
        List<String> result = new ArrayList<>();
        result.addAll(electronics);
        result.addAll(grocery);
        result.addAll(pharmacy);
        result.addAll(restaurant);
        
        return result;
    }
}
```

**Trade-off**: The separate-lists approach avoids creating Pair objects and sorts each business line independently, which can be faster when business lines have very different sizes (smaller sublists sort faster). However, it requires more code with repetitive if-else logic. The priority-based approach is more maintainable and extensible (adding new business lines requires only updating the map, not adding new lists and conditionals). Both have the same O(n · m + k log k) complexity. Use separate lists for simplicity when categories are fixed; use priority map for cleaner, more scalable code.
