# Number of Ways to Assign Edge Weights II

**LeetCode 3559**

---

# Problem Overview

We are given an **undirected tree** consisting of `n` nodes.

For each query

```
(u, v)
```

we consider the unique path between `u` and `v`.

The task is to determine **how many different ways** we can assign edge weights on that path while satisfying the condition specified in the problem.

The mathematical result simplifies to

```
If path length = L

Answer =

0                  if u == v

2^(L-1)            otherwise
```

Therefore, the real challenge is **efficiently finding the distance between two nodes** for many queries.

---

# Key Observation

A tree has

- exactly one path between any two nodes.

Therefore,

the answer depends **only on the number of edges on that path**.

Suppose

```
distance(u,v) = L
```

Then

```
answer = 2^(L-1)
```

So the original problem becomes

> Compute the distance between many pairs of nodes efficiently.

---

# Converting the Problem

Instead of thinking about edge weights,

think about

```
How many edges exist on the path?
```

Once we know

```
L
```

the answer is immediate.

---

# Formula

```
u == v

↓

distance = 0

↓

answer = 0
```

Otherwise

```
distance = L

↓

answer = 2^(L-1)
```

Since there may be many queries,

powers of two are precomputed.

```java
p2[i] = 2^i mod MOD
```

---

# Why Precompute Powers?

Without precomputation,

every query would require fast exponentiation.

Instead,

compute once

```
1
2
4
8
16
...
```

Then every query becomes

```
O(1)
```

lookup.

---

# Two Different Solutions

The repository contains two approaches.

---

# Solution 1

## Binary Lifting LCA

This is the easier and more common solution.

---

## Observation

Distance between two nodes is

```
depth[u]

+

depth[v]

-

2 × depth[LCA]
```

Therefore,

if we can compute

```
LCA(u,v)
```

quickly,

distance becomes trivial.

---

# Preprocessing

The tree is rooted at

```
1
```

using DFS.

During DFS we compute

```
depth[node]
```

and

```
parent[node][0]
```

which stores the immediate parent.

---

# Binary Lifting Table

Instead of storing only the parent,

store

```
2^0 ancestor

2^1 ancestor

2^2 ancestor

2^3 ancestor

...
```

Meaning

```
parent[x][k]

=

2^k-th ancestor
```

The recurrence is

```
parent[x][k]

=

parent[
    parent[x][k-1]
][k-1]
```

This allows us to jump upward in powers of two.

---

# Finding the LCA

There are two steps.

---

## Step 1

Raise the deeper node until both nodes have equal depth.

Example

```
depth(x)=9

depth(y)=5

Raise x by 4
```

using binary jumps.

---

## Step 2

Raise both nodes together.

Starting from the largest jump,

if their ancestors differ,

lift both.

Eventually,

they become immediate children of the LCA.

Return

```
parent[x][0]
```

---

# Computing Distance

Once

```
LCA
```

is known,

distance is

```
depth[u]

+

depth[v]

-

2 × depth[lca]
```

Finally,

```
answer = 2^(distance-1)
```

---

# Complexity

Preprocessing

```
O(N log N)
```

Each query

```
O(log N)
```

Very efficient for large inputs.

---

# Solution 2

## Offline Tarjan LCA

The second solution uses **Tarjan's Offline Lowest Common Ancestor Algorithm** together with **Disjoint Set Union (Union-Find)**.

Unlike Binary Lifting,

this processes **all queries simultaneously** in one DFS.

---

# Why Offline?

Suppose there are

```
100000
```

queries.

Instead of answering each independently,

collect all queries first.

During DFS,

whenever both endpoints of a query have been visited,

their LCA can be determined immediately.

---

# Data Structures

The implementation uses

```
Adjacency List
```

for the tree

```
head[]
to[]
next[]
```

to avoid Java object overhead.

Queries are also stored as adjacency lists.

```
qHead[]

qTo[]

qNext[]
```

Every query appears twice,

once for each endpoint.

---

# DFS Traversal

Each node has three states.

```
0

Not visited
```

```
1

Currently in DFS
```

```
2

Finished
```

These are stored in

```
color[]
```

---

# Union-Find

Each node belongs to a DSU set.

Initially

```
parent[x]=x
```

After completely processing a subtree,

the subtree is merged into its parent.

```
parent[child]

=

parentNode
```

This represents

```
"The deepest processed ancestor."
```

---

# Answering Queries

Suppose we are finishing

```
u
```

and encounter a query

```
(u,v)
```

If

```
v
```

has already been visited,

its DSU representative corresponds to

```
LCA(u,v)
```

Then

```
distance

=

depth[u]

+

depth[v]

-

2×depth[lca]
```

Finally

```
answer

=

2^(distance-1)
```

---

# Why Does Tarjan Work?

Tarjan's algorithm has an important invariant.

When DFS finishes processing a node,

its entire subtree has already been processed.

After union operations,

the DSU representative of a processed subtree always points to its nearest processed ancestor.

Therefore,

when both endpoints of a query have been visited,

the DSU representative correctly identifies their LCA.

---

# Iterative DFS

Instead of recursion,

the implementation uses an explicit stack.

```
stack[]
```

stores nodes.

```
edgeStack[]
```

stores the next edge to process.

Advantages

- avoids recursion depth limits
- prevents stack overflow
- suitable for trees with up to 100,000 nodes

---

# Why Both Solutions Work

Both rely on the same mathematical fact.

```
Answer

=

2^(distance-1)
```

The only difference is

**how distance is computed.**

Binary Lifting

```
distance

↓

LCA

↓

depth formula
```

Tarjan

```
Offline DFS

↓

Union-Find

↓

LCA

↓

depth formula
```

The mathematical computation afterward is identical.

---

# Complexity Comparison

## Binary Lifting

Preprocessing

```
O(N log N)
```

Each Query

```
O(log N)
```

Memory

```
O(N log N)
```

---

## Tarjan Offline

DFS

```
O(N)
```

Union-Find

```
Nearly O(1)
```

Queries

```
O(Q)
```

Overall

```
O(N + Q)
```

Memory

```
O(N + Q)
```

---

# Summary

The problem reduces to computing the **distance between two nodes** in a tree.

Once the path length is known,

the number of valid assignments is simply

```
2^(distance-1)
```

The repository demonstrates two classic tree algorithms:

1. **Binary Lifting**, which preprocesses ancestors for fast online LCA queries.
2. **Tarjan's Offline LCA**, which answers all queries in a single DFS using Union-Find.

Although their implementations differ significantly, both compute the same quantity:

1. Find the Lowest Common Ancestor.
2. Compute the path length using node depths.
3. Return `2^(distance - 1)` modulo `1,000,000,007`.