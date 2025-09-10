# How & Why: LeetCode 1733 - Minimum Number of People to Teach

---

## Problem Restatement
We are given:
- `totalLanguages`: total number of languages in the world.
- `userLanguages`: a list where `userLanguages[i]` contains the set of languages known by the **i-th user**.
- `friendships`: a list of friendship pairs, where users can only communicate if they share at least one common language.

We need to determine the **minimum number of users** we must teach a **single new language** so that all friendships can communicate.

---

## How to Solve

### Step 1: Identify Problematic Friendships
- A friendship is problematic if the two friends **do not share any common language**.
- For each such pair, mark both users as **candidates who may need to be taught**.

```java
Set<Integer> usersToTeach = new HashSet<>();
for (int[] friendship : friendships) {
    int u1 = friendship[0] - 1;
    int u2 = friendship[1] - 1;
    boolean canCommunicate = false;

    for (int lang1 : userLanguages[u1]) {
        for (int lang2 : userLanguages[u2]) {
            if (lang1 == lang2) {
                canCommunicate = true;
                break;
            }
        }
        if (canCommunicate) break;
    }

    if (!canCommunicate) {
        usersToTeach.add(u1);
        usersToTeach.add(u2);
    }
}
```

This ensures we focus **only** on the set of users that are part of at least one non-communicating friendship.

---

### Step 2: Try Teaching Each Language
Now, for each possible language `lang`:
- Count how many of the problematic users **do not know this language**.
- That count represents how many users we must teach if we pick `lang` as the common language.

```java
int minUsersToTeach = userLanguages.length + 1;

for (int lang = 1; lang <= totalLanguages; lang++) {
    int count = 0;
    for (int user : usersToTeach) {
        boolean knows = false;
        for (int l : userLanguages[user]) {
            if (l == lang) {
                knows = true;
                break;
            }
        }
        if (!knows) count++;
    }
    minUsersToTeach = Math.min(minUsersToTeach, count);
}
```

Finally, return `minUsersToTeach`.

---

## Why This Works
1. **Focus on Relevant Users**: Only users involved in non-communicating friendships matter, reducing unnecessary checks.
2. **Language Simulation**: By simulating the teaching of each language, we ensure we explore all possibilities and select the minimal teaching set.
3. **Greedy Strategy Validity**: Since the chosen language must be the same for everyone taught, trying all languages ensures we don't miss the optimal choice.

---

## Complexity Analysis
- Let `U = number of users`, `F = number of friendships`, `L = totalLanguages`.
- Step 1 (checking friendships): `O(F × A²)` where `A` = average number of languages per user.
- Step 2 (testing languages): `O(L × |usersToTeach| × A)`.

This is efficient enough for constraints (users ≤ 500, languages ≤ 500).

---

## Example Walkthrough
Suppose:
```
totalLanguages = 3
userLanguages = [[2], [1,2], [3]]
friendships = [[1,2], [1,3], [2,3]]
```

- Friendship (1,2): share language `2` → OK.
- Friendship (1,3): user1 knows {2}, user3 knows {3} → conflict → add {1,3}.
- Friendship (2,3): user2 knows {1,2}, user3 knows {3} → conflict → add {2,3}.

So `usersToTeach = {1,2,3}`.

Try each language:
- Language 1: teach {1,3} → 2 users.
- Language 2: teach {3} → 1 user.
- Language 3: teach {1,2} → 2 users.

Minimum = **1**.

---

## Optimal Method
The above solution is good, but we can optimize further:
- Instead of checking each user’s languages repeatedly, **precompute sets of languages per user**.
- For each candidate language `lang`, we count how many of `usersToTeach` already know it.
- Then the answer is:

```
minUsersToTeach = |usersToTeach| - maxUsersThatAlreadyKnowLang
```

This avoids recomputation and runs in **O(|usersToTeach| × A + L)** instead of `O(L × |usersToTeach| × A)`.

---

## Key Insight
The crux of the problem is realizing:
- Only users in non-communicating friendships matter.
- Teaching one language to cover maximum users minimizes effort.
- The problem reduces to finding the language that is **most widely known among problematic users**, then teaching the rest.

