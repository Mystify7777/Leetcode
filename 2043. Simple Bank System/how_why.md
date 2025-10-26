# How & Why — LeetCode 2043: Simple Bank System

Design a simple bank with accounts and three operations in O(1): deposit, withdraw, and transfer. Accounts are 1-indexed in the API but we store them 0-indexed internally.

---

## Problem recap

Implement a class `Bank` with:

- Constructor: `Bank(long[] balance)` — `balance[i]` is the starting balance of account `i+1`.
- `boolean deposit(int account, long money)` — add `money` to `account`.
- `boolean withdraw(int account, long money)` — subtract `money` from `account` if sufficient funds.
- `boolean transfer(int account1, int account2, long money)` — move `money` from `account1` to `account2` if `account1` has enough funds.

Return `false` for any invalid account index or insufficient balance; otherwise `true`.

Constraints (condensed):

- 1 ≤ number of accounts ≤ 105
- 1 ≤ operations ≤ 105
- 0 ≤ balance[i], money ≤ 1012 (use `long`)

---

## Approach

Use a plain array `long[] bal` to store balances. Since the API uses 1-based account numbers, map `account -> account-1`. Validate the account is within `[1, n]` before any operation. All operations then become O(1) reads/writes on the array.

Why `long`? The balances and money can exceed `int` range; `long` avoids overflow.

---

## Implementation (matches Solution.java)

```java
class Bank {
	private final long[] bal;
	private final int n;

	public Bank(long[] bal) {
		this.bal = bal;
		this.n = bal.length;
	}

	public boolean transfer(int from, int to, long amt) {
		if (!valid(from) || !valid(to) || bal[from - 1] < amt) return false;
		bal[from - 1] -= amt;
		bal[to - 1] += amt;
		return true;
	}

	public boolean deposit(int acc, long amt) {
		if (!valid(acc)) return false;
		bal[acc - 1] += amt;
		return true;
	}

	public boolean withdraw(int acc, long amt) {
		if (!valid(acc) || bal[acc - 1] < amt) return false;
		bal[acc - 1] -= amt;
		return true;
	}

	private boolean valid(int acc) {
		return acc > 0 && acc <= n;
	}
}
```

Notes:

- `valid()` centralizes the index check (1 ≤ acc ≤ n).
- In `transfer`, check both validity and sufficient funds on the source before mutating.
- Order of operations matters: subtract first, then add, after validations.

---

## Why this works

- Using a flat array gives O(1) access/updates.
- Mapping 1-based → 0-based eliminates off-by-one errors when consistently applied.
- Guard checks ensure we never index outside the array and never allow negative balances.
- `long` ensures arithmetic safety within constraints.

---

## Complexity

- Time per operation: O(1)
- Space: O(n) for the balances array

---

## Edge cases and validations

- Invalid account indices: `account <= 0` or `account > n` → return `false`.
- Insufficient funds on `withdraw`/`transfer` → return `false`.
- Zero money: depositing/withdrawing/transferring `0` is allowed by spec and should be a no-op that returns `true` if accounts are valid.
- Large values: operations near 1012 stay within `long` range.

---

## Example walkthrough

Initial balances: `[10, 100, 20, 50, 30]` (accounts 1..5)

1) `transfer(2, 5, 20)` → valid, `bal[1] = 80`, `bal[4] = 50` → `[10, 80, 20, 50, 50]`

2) `withdraw(2, 100)` → insufficient funds (80 < 100) → `false`, balances unchanged

3) `deposit(3, 10)` → `[10, 80, 30, 50, 50]`

4) `transfer(5, 1, 25)` → `[35, 80, 30, 50, 25]`

---

## Alternative design note

You can implement `transfer` by composing `withdraw` and `deposit`:

```java
public boolean transfer(int a, int b, long m) {
	if (!withdraw(a, m)) return false;
	if (!deposit(b, m)) { // deposit can only fail on invalid account
		// optional: rollback (withdraw succeeded). Here spec never requires it because we validate first.
		return false;
	}
	return true;
}
```

This improves reuse but may introduce a temporary state if you plan to add rollback semantics. The direct approach shown earlier validates first and mutates once, which is simpler here.

---

## Key takeaways

1) Keep operations O(1) with an array; avoid maps/sets.

2) Normalize indices at the API boundary (1-based → 0-based) and validate early.

3) Use `long` for money to avoid overflow.

4) Order validations before mutations to maintain invariants.

---
