# 3612. Process String with Special Operations I

## Problem Summary

Given a string `s` containing lowercase letters and special characters `*`, `#`, and `%`, process it character by character according to these rules:

- **Lowercase letter:** append it to the result buffer.
- **`*`:** delete the last character from the result buffer (if non-empty).
- **`#`:** double the result buffer (append a copy of the current buffer to itself).
- **`%`:** reverse the result buffer in place.

Return the final string after processing all characters.

---

## Approach: Direct Simulation with `StringBuilder`

### How it works

The solution is a straightforward **simulation** — it processes `s` left to right, one character at a time, maintaining a `StringBuilder sb` that represents the "current result" at any point. Each character maps to a direct `StringBuilder` operation:

| Character | Operation | `StringBuilder` call |
|---|---|---|
| `a`–`z` | Append letter | `sb.append(ch)` |
| `*` | Delete last character (if non-empty) | `sb.deleteCharAt(sb.length() - 1)` |
| `#` | Append a copy of itself (double) | `sb.append(sb)` |
| `%` | Reverse in place | `sb.reverse()` |

After the loop, `sb.toString()` gives the final result.

### Why it works

- **`StringBuilder` as a mutable buffer:** `String` in Java is immutable, so every modification would create a new object — `O(n)` allocation per operation. `StringBuilder` provides all four needed operations natively and efficiently, making it the natural fit for a simulation like this.

- **`*` — delete last:** `deleteCharAt(sb.length() - 1)` removes the most recently appended character. The guard `sb.length() != 0` prevents an `IndexOutOfBoundsException` when `*` appears on an already-empty buffer, matching the "no-op if empty" semantic.

- **`#` — self-append to double:** `sb.append(sb)` appends the current content of `sb` to itself, which is exactly "duplicate". One subtlety: calling `append` with the same `StringBuilder` as both source and target could theoretically be unsafe in some implementations if the buffer is reallocated mid-copy — Java's `AbstractStringBuilder.append(CharSequence s, int start, int end)` handles this correctly, but relying on it implicitly is a mild defensive concern. A safer (and equally readable) alternative would be `sb.append(sb.toString())`, which snapshots the current content as an immutable `String` before appending. Both work correctly in practice on the standard JDK.

- **`%` — in-place reverse:** `StringBuilder.reverse()` handles this in `O(k)` where `k` is the current buffer length, reversing character by character using Java's built-in implementation (which also correctly handles surrogate pairs, though irrelevant here since input is restricted to `a`–`z` and ASCII special characters).

- **Linear scan is sufficient:** There's no lookahead or dependency between non-adjacent characters — each character's effect is determined solely by the current buffer state at the time it's encountered. A single left-to-right pass is therefore both correct and complete.

### Complexity

Let `n = s.length()` and `k` = the maximum buffer size reached during processing.

- **Time:** `O(n * k)` in the worst case. A single letter or `*` takes `O(1)`. A `%` reversal takes `O(k)`. A `#` doubling takes `O(k)` (the append), but also *doubles* the buffer — so if doubling happens repeatedly (e.g., `"a#######"`), `k` grows exponentially in the number of `#` characters, and subsequent operations on the buffer become proportionally more expensive. For the problem's "Part I" constraints this is not an issue.
- **Space:** `O(k)` for the `StringBuilder` buffer — same exponential growth concern if `#` appears frequently.

### Design Notes

- This is a clean simulation with one-to-one operation mapping, appropriate for a "Part I" problem where constraints are small enough that no optimization beyond direct simulation is needed.
- For a "Part II" version at larger scale, a naive `StringBuilder` simulation would likely be too slow due to the exponential blowup of `#`. A more sophisticated representation — such as a **rope** or a lazy tree of operations — could handle doubling and reversal without materializing the full string at each step.
- The `else if` chain with the lowercase letter check (`ch >= 'a' && ch <= 'z'`) is defensive — it ensures any unexpected character outside the defined set is silently ignored rather than corrupting the buffer, which is a reasonable safe-default behavior.