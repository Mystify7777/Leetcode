# 3633. Earliest Finish Time for Land and Water Rides I

## Problem in short
You must ride **one land ride** and **one water ride** (in either order — land first or water first). Each ride `i` has a `startTime` (the earliest it can begin) and a `duration`. You can only board a ride once you're free **and** the ride's start time has arrived — so your actual start on a ride is `max(current free time, ride's startTime)`, and it finishes at `start + duration`.

Find the **minimum possible time** by which you can finish both rides, choosing the best ride of each type and the best order.

## Key Insight (the "why")
There are exactly **two strategies**, and the answer is the best of the two:

1. **Land ride → Water ride**
2. **Water ride → Land ride**

For a fixed order, say land-first:
- To finish the *first* ride (land) as early as possible, you simply want the land ride with the **smallest finish time** (`startTime + duration`) — call this `min`. There's no reason to consider anything but the earliest-finishing land ride here, since finishing land earlier can only help (or be equal for) the second ride.
- Once you're free at time `min`, you then pick whichever **water ride** minimizes `max(min, waterStartTime[j]) + waterDuration[j]`. You must try every water ride `j` because a ride with a later start time but shorter duration might still beat one with an earlier start but longer duration.

The same logic applies symmetrically for water-first: find `minW` (earliest possible water-ride finish), then scan every land ride for the best combined finish time.

## Why we don't need to search all pairs
A brute-force approach would check all `n * m` land/water pairs for both orders — `O(n*m)`. But notice: for the **first** ride in either order, only its **own** finish time matters (to become your "free" time) — it doesn't matter which land ride specifically, only the earliest finishing one, because an earlier free time is never worse for the second ride. This collapses the first ride selection from "any of `n` choices" down to a single number (`min` or `minW`), turning what looks like `O(n*m)` into `O(n + m)`.

## Line-by-line

```java
int min=3000, minW=min, res=minW;
```
`min` will hold the **earliest finish time among all land rides**. `minW` will hold the **earliest finish time among all water rides**. `res` is the running best (final) answer. `3000` is just a safe "infinity" sentinel bigger than any possible finish time (time bounds in this problem are small, so 3000 works as a max cap).

```java
for(int i=0;i<n;i++){
    min=Math.min(min, landStartTime[i]+landDuration[i]);
}
```
Compute the smallest possible finish time if you ride a land ride **first** (with nothing before it, so `start = landStartTime[i]` directly).

```java
for(int i=0;i<m;i++){
    minW=Math.min(minW, waterStartTime[i]+waterDuration[i]);
    res=Math.min(res, Math.max(min, waterStartTime[i])+waterDuration[i]);
}
```
Two things happen in the same loop:
- Track `minW`, the earliest finish time if a water ride is ridden **first**.
- Simultaneously, evaluate the **land-then-water** strategy: having finished the earliest land ride at time `min`, try boarding *this* water ride `i`. You can only start at `max(min, waterStartTime[i])` (whichever is later — either you're not free yet, or the ride hasn't opened yet). Update `res` if this is better.

```java
for(int i=0;i<n;i++){
    res=Math.min(res, Math.max(minW, landStartTime[i])+landDuration[i]);
}
```
Now evaluate the **water-then-land** strategy: having finished the earliest water ride at time `minW`, try boarding each land ride `i`, starting at `max(minW, landStartTime[i])`.

```java
return res;
```
`res` ends up holding the minimum finish time across *both* orders and *all* choices of second ride — the answer.

## Step-by-step example
`landStartTime = [4,2], landDuration = [3,1]`
`waterStartTime = [3,1], waterDuration = [2,4]`

**Land finish times:** ride 0 → `4+3=7`, ride 1 → `2+1=3` → `min = 3`
**Water finish times:** ride 0 → `3+2=5`, ride 1 → `1+4=5` → `minW = 5`

**Land → Water** (start water at `max(3, waterStartTime)`):
- Water 0: `max(3,3)+2 = 5`
- Water 1: `max(3,1)+4 = 7`
- Best here: `5`

**Water → Land** (start land at `max(5, landStartTime)`):
- Land 0: `max(5,4)+3 = 8`
- Land 1: `max(5,2)+1 = 6`
- Best here: `6`

**Overall answer:** `min(5, 6) = 5`

## Complexity
- **Time:** `O(n + m)` — one pass over land rides, one pass over water rides (each done once or twice, still linear).
- **Space:** `O(1)` — only a few scalar variables are used.

## Common pitfall this solution avoids
Trying to track "the best land ride" as a single fixed choice for *both* orders would be wrong — as the *first* ride, only its finish time matters (so pick the earliest-finishing one), but as the *second* ride, every ride must be individually checked against `max(freeTime, itsStartTime) + itsDuration`, since the best second ride depends on both its start time and duration, not just its own finish time in isolation.
