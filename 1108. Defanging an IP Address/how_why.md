# Recap

Given a valid IPv4 string `address`, replace every `.` with `[.]` and return the defanged string.

## Intuition

The task is a direct character substitution. Since we want every dot to become the three-character sequence `[.]`, a single pass replace is the simplest and clearest option.

## Approach

1. Call `address.replace(".", "[.]")` to substitute every literal dot.
2. Return the resulting string.

This uses Java's built-in replace for a straightforward, single-pass transformation.

## Code (Java)

```java
class Solution {
    public String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }
}
```

## Correctness

- `replace(".", "[.]")` scans the input once and substitutes each dot with `[.]`.
- All non-dot characters are left untouched, so the original order and content are preserved.
- Because replace processes every occurrence, the output has every dot defanged as required.

## Complexity

- **Time:** $O(n)$ where $n$ is the length of `address`, since each character is visited once.
- **Space:** $O(n)$ for the new string produced by replacement.

## Edge Cases

- No dots (already defanged or malformed input): returns the same string.
- Leading/trailing/multiple consecutive dots: all are replaced consistently.
- Minimum length like `"0.0.0.0"` and maximum-length IPv4 strings: handled uniformly.

## Takeaways

- Prefer built-in string replacement for simple deterministic substitutions.
- For larger transformations, a streaming `StringBuilder` pass is an alternative, but here `replace` is both concise and clear.

## Example Walkthrough

1) Input: `"1.1.1.1"` → replace each `.` with `[.]` → output: `"1[.]1[.]1[.]1"`.
2) Input: `"255.100.50.0"` → dots at positions 4, 8, 11 become `[.]` → output: `"255[.]100[.]50[.]0"`.

## Related Problems

- [125. Valid Palindrome/how_why.md](125.%20Valid%20Palindrome/how_why.md)
- [151. Reverse Words in a String/how_why.md](151.%20Reverse%20Words%20in%20a%20String/how_why.md)
- [166. Fraction to Recurring Decimal/how_why.md](166.%20Fraction%20to%20Recurring%20Decimal/how_why.md)
