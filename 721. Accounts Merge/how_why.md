# Recap

Given a list of accounts where each account contains a name and a list of emails, **merge accounts that share the same email**. Return the merged accounts where each account contains:

1. Account name (at index 0)
2. All unique emails sorted in lexicographical order

**Key insight:** If account A and account B share an email, they belong to the same person and should be merged. This is a **transitive relationship** - if A shares email with B, and B shares email with C, then A, B, C all belong to the same person.

**Example:** 

- Input: `[["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","john00@mail.com","john_newyork@mail.com"]]`
- Output: `[["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"]]`

## Problem Analysis

This is a **graph connectivity problem** disguised as an account merging task:

- **Nodes:** Account indices (0 to n-1)
- **Edges:** Two accounts share an edge if they have a common email
- **Goal:** Find all connected components (groups of accounts that share emails transitively)
- **Output:** All emails in each component, grouped by account

**Why Union-Find?** Union-Find (Disjoint Set Union / DSU) is perfect for this because:

1. We need to efficiently merge groups
2. We need to find which group each element belongs to
3. All operations (union, find) are nearly constant time with path compression

## Approach Overview

Both solutions follow the same **3-step pattern:**

**Step 1: Build the email-to-account mapping**

- For each account, iterate through all emails
- If email was seen before, **union** (merge) current account with previous account
- This creates connected components of accounts

**Step 2: Group emails by merged account**

- For each email, find its **root** (representative of its component)
- Collect all emails under the same root
- This gives us which emails belong to which merged account

**Step 3: Format output**

- Sort emails alphabetically
- Add account name at the front
- Return the final result

## Solution 1: Object-Oriented Union-Find with Weighted Union

```java
class UnionFind {
    int[] parent;
    int[] weight;
    
    public UnionFind(int num) {
        parent = new int[num];
        weight = new int[num];
        
        for(int i = 0; i < num; i++) {
            parent[i] = i;
            weight[i] = 1;
        }
    }
    
    public void union(int a, int b) {
        int rootA = root(a);
        int rootB = root(b);
        
        if (rootA == rootB) return;
        
        // Weighted union: attach smaller tree under larger tree
        if (weight[rootA] > weight[rootB]) {
            parent[rootB] = rootA;
            weight[rootA] += weight[rootB];
        } else {
            parent[rootA] = rootB;
            weight[rootB] += weight[rootA];
        }
    }
    
    public int root(int a) {
        if (parent[a] == a) return a;
        parent[a] = root(parent[a]);  // Path compression
        return parent[a];
    }
}

public List<List<String>> accountsMerge(List<List<String>> accounts) {
    int size = accounts.size();
    UnionFind uf = new UnionFind(size);

    // Step 1: Build email -> account index mapping and union accounts
    HashMap<String, Integer> emailToId = new HashMap<>();
    for(int i = 0; i < size; i++) {
        List<String> details = accounts.get(i);
        for(int j = 1; j < details.size(); j++) {
            String email = details.get(j);
            
            if (emailToId.containsKey(email)) {
                // Email seen before: merge current account with previous
                uf.union(i, emailToId.get(email));
            } else {
                emailToId.put(email, i);
            }
        }
    }
    
    // Step 2: Group emails by their root account
    HashMap<Integer, List<String>> idToEmails = new HashMap<>();
    for(String email : emailToId.keySet()) {
        int root = uf.root(emailToId.get(email));
        
        idToEmails.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
    }
    
    // Step 3: Format output
    List<List<String>> mergedDetails = new ArrayList<>();
    for(Integer id : idToEmails.keySet()) {
        List<String> emails = idToEmails.get(id);
        Collections.sort(emails);
        emails.add(0, accounts.get(id).get(0));  // Add account name at front
        mergedDetails.add(emails);
    }
    
    return mergedDetails;
}
```

### Key Features of Solution 1:

- **Weighted Union:** Keeps tree flatter by attaching smaller trees to larger ones
- **Path Compression:** In `root()` method, redirects nodes to root, speeding up future lookups
- **Clean abstraction:** UnionFind is a separate class, making code modular and reusable
- **Intuitive:** Easy to understand the three-step approach

## Solution 2: Lightweight Union-Find with Implicit Parent Weights

```java
public List<List<String>> accountsMerge(List<List<String>> accounts) {
    int counter = 0;
    Map<String, String> emailToName = new HashMap<>();
    Map<String, Integer> emailToId = new HashMap<>();
    
    int[] parents = new int[10000];
    for (int i = 0; i < 10000; i++) parents[i] = -1;  // -1 means it's a root
    
    // Step 1: Assign IDs to emails and union by first email in each account
    int firstID;
    for (List<String> account: accounts) {
        String name = account.get(0);
        String firstEmail = account.get(1);
        
        if (!emailToId.containsKey(firstEmail)) {
            emailToId.put(firstEmail, counter++);
            emailToName.put(firstEmail, name);
        }
        firstID = emailToId.get(firstEmail);
        
        // Union all emails in this account with the first email
        for (int i = 2; i < account.size(); i++) {
            String email = account.get(i);
            
            if (!emailToId.containsKey(email)) {
                emailToId.put(email, counter++);
                emailToName.put(email, name);
            }
            
            int id = emailToId.get(email);
            union(parents, firstID, id);
        }
    }
    
    // Step 2: Group emails by root using TreeSet for automatic sorting
    Map<Integer, TreeSet<String>> grouped = new HashMap<>();
    for (Map.Entry<String, Integer> entry: emailToId.entrySet()) {
        String email = entry.getKey();
        int id = entry.getValue();
        int root = find(parents, id);
        
        grouped.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
    }
    
    // Step 3: Format output
    List<List<String>> ans = new ArrayList<>();
    for (TreeSet<String> entry: grouped.values()) {
        String user = emailToName.get(entry.first());
        
        List<String> list = new ArrayList<>();
        list.add(user);
        list.addAll(entry);  // TreeSet is already sorted
        ans.add(list);
    }
    
    return ans;
}

public int find(int[] parents, int i) {
    while (parents[i] >= 0) {
        i = parents[i];
    }
    return i;
}

public void union(int[] parents, int i, int j) {
    if (i == j) return;
    
    int parentI = find(parents, i);
    int parentJ = find(parents, j);
    
    if (parentI == parentJ) return;
    
    // Weighted union: parent array stores negative weight
    if (parents[parentI] > parents[parentJ]) {
        parents[parentJ] += parents[parentI];
        parents[parentI] = parentJ;
    } else {
        parents[parentI] += parents[parentJ];
        parents[parentJ] = parentI;
    }
}
```

### Key Features of Solution 2:

- **Compact representation:** Uses single `parents` array where `parents[i] < 0` means root with weight = `|parents[i]|`
- **Implicit weight:** Weight is stored as **negative value** in parent array (clever encoding!)
- **TreeSet optimization:** Uses TreeSet to automatically keep emails sorted, avoiding separate sort step
- **Space efficient:** Single array instead of separate parent and weight arrays
- **No path compression:** The `find()` method is iterative (not recursive), avoiding stack overflow risk

## Detailed Comparison: Solution 1 vs Solution 2

### **Union-Find Implementation Details**

| Aspect | Solution 1 | Solution 2 |
|--------|-----------|-----------|
| **Storage** | `parent[i]` (pointer), `weight[i]` (size) | `parents[i]` (pointer as positive, weight as negative) |
| **Root check** | `parent[a] == a` | `parents[i] >= 0` (false for root) |
| **Path compression** | Yes (recursive) | No (iterative find) |
| **Weighted union** | Explicit `weight[]` array | Encoded in `parents[]` (negative values) |
| **Memory per node** | 2 integers | 1 integer |
| **Tree traversal** | Recursive (could stack overflow) | Iterative (safer) |

### **Algorithm Differences**

| Aspect | Solution 1 | Solution 2 |
|--------|-----------|-----------|
| **Union strategy** | Union any two accounts sharing email | Union all emails with first email in account |
| **Email IDs** | Assigned implicitly (tied to account index) | Assigned explicitly with counter |
| **Sorting** | Sort emails at the end | Use TreeSet (auto-sorted) |
| **Space** | O(n + m) for two HashMaps + UFds | O(m) for HashMaps, O(10000) for fixed array |

### **Where each solution excels:**

**Solution 1 is better when:**

- ✅ You want **clean, readable code** with clear separation of concerns
- ✅ You prefer **explicit state** (separate parent and weight arrays are clear)
- ✅ You want **path compression** (significantly speeds up repeated operations)
- ✅ You need to **reuse UnionFind** in other problems
- ✅ Code review and maintenance are priorities
- ❌ Slightly more memory overhead (two arrays)

**Solution 2 is better when:**

- ✅ You want **memory efficiency** (single array, TreeSet avoids sorting)
- ✅ You want **guaranteed safety** (no recursion stack overflow)
- ✅ You prefer **clever optimizations** (weight encoding, TreeSet)
- ✅ You're dealing with **millions of accounts** (space matters)
- ✅ Performance benchmarks matter
- ❌ Code is harder to understand at first glance

## Deep Dive: Union-Find Optimizations

### Path Compression (Solution 1)

```java
public int root(int a) {
    if (parent[a] == a) return a;
    parent[a] = root(parent[a]);  // Redirect to root
    return parent[a];
}
```

**Why it matters:** After first lookup, all nodes point directly to root. Subsequent lookups are O(1).

**Example:**

```math
Before: 0 -> 1 -> 2 -> 3 (root)
After:  0 -> 3
        1 -> 3
        2 -> 3
```

### Weighted Union

Both solutions use weighted union to keep trees flat:

- Attach smaller tree under larger tree
- **Claim:** Without path compression, O(log n) per operation
- **Claim:** With path compression, nearly O(1) amortized per operation
- **Together:** Nearly optimal performance

## Why Emails, Not Accounts?

A critical insight: We could union accounts directly OR emails. **Why choose emails?**

1. **Email is unique identifier:** Same person uses same email across accounts
2. **Natural grouping:** Emails in same account must be grouped
3. **Simpler state:** `emailToId` hash is simpler than tracking email ownership

**Alternative (accounts-only approach):** Would require building full graph of accounts first, then finding components. Less elegant.

## Complexity Analysis

### Time Complexity

- **Union-Find operations:** Nearly O(1) per operation with path compression + weighted union (inverse Ackermann function α(n), practically < 5)
- **Building emailToId:** O(E) where E is total emails
- **Union operations:** O(E × α(n)) ≈ O(E)
- **Grouping emails:** O(E × α(n)) ≈ O(E)
- **Sorting:** O(E log E) in worst case (single merged account with all emails)
- **Total:** O(E log E) dominated by sorting

### Space Complexity

- **Solution 1:** O(n) for UnionFind + O(E) for maps + O(E) for result = **O(n + E)**
- **Solution 2:** O(10000) fixed array + O(E) for maps + O(E) for result = **O(E)** (assuming E ≤ 10000)

Both are effectively **O(E)** in practice.

## Edge Cases & Tricky Scenarios

1. **Single account with multiple emails:** No merging needed. Each account processed normally.

2. **Accounts with only name (no emails):** Neither solution handles this explicitly, but it wouldn't occur per problem constraints.

3. **Very long chain of emails:** Without path compression, repeated root lookups could be slow. Solution 1's path compression handles this better.

4. **All emails unique:** No merging. Each account stays separate. Both solutions handle correctly.

5. **All emails same:** All accounts merge into single account. Both solutions correctly group all.

6. **Email appears in 3+ accounts:** Transitive property works:
   - Account A has email1
   - Account B has email1 and email2
   - Account C has email2
   - All three accounts should merge ✓ Both solutions handle this!

## Why This Problem Tests Union-Find Knowledge

This problem is a **classic Union-Find application** because:

1. **Transitive relationships:** If A↔B and B↔C, then A↔C must hold. Union-Find naturally handles this.

2. **Dynamic connectivity:** We incrementally add edges (emails) and need to query components (merged accounts).

3. **Optimization testing:** Unoptimized Union-Find would be O(n²); optimized version is O(n log n).

4. **Multiple data structures:** Combining Union-Find with HashMaps requires architectural thinking.

## Key Takeaways

1. **Union-Find is powerful for connectivity problems:** Graph components, LCA (Lowest Common Ancestor), Kruskal's algorithm, etc.

2. **Path compression is crucial:** Changes worst-case from O(n) to nearly O(1) per operation.

3. **Weighted union prevents tall trees:** Without it, trees become chains. With it, they stay balanced.

4. **Clever encoding (Solution 2) saves space:** But sacrifices readability. Trade-off decision depends on constraints.

5. **TreeSet is underrated for sorted output:** Eliminates separate sort step, cleaner code.

6. **Email-centric thinking:** Think about what connects entities (emails), not the entities themselves (accounts). This naturally leads to the Union-Find structure.

7. **Inverse Ackermann magic:** Union-Find with both optimizations achieves practically O(1) per operation due to inverse Ackermann function. It's one of the most elegant algorithms in computer science!

## Recommended Usage

**In interviews:**

1. First, explain the email connectivity insight
2. Implement Solution 1 (cleaner to code live)
3. Mention path compression and weighted union optimizations
4. If asked about space optimization, explain Solution 2's approach

**In production:**

- Use Solution 1 for **maintainability** (most teams choose this)
- Use Solution 2 only if **profiling shows it's a bottleneck**

**In learning:**

- Master both implementations—they represent two ends of the optimization spectrum
- Understand the trade-offs: clarity vs. performance, recursion vs. iteration, implicit vs. explicit state

This problem showcases why **algorithm selection matters**. A naive O(n²) approach might TLE, while Union-Find solves it elegantly in near-linear time!
