# 3635. Earliest Finish Time for Land and Water Rides II

## Problem in short
Same rules as version I: ride one land ride and one water ride, in either order. Boarding a ride means starting at `max(currentFreeTime, ride.startTime)` and finishing at `start + duration`. Find the minimum possible time to finish both rides.

The difference from **Version I** is purely in **constraints** — `n` and `m` (number of rides) are much larger here, so the solution must be strictly linear and can't afford any hidden inefficiency, nested loop, or repeated work. This solution is essentially the same greedy idea as Version I, but refactored into a clean, reusable helper so the same logic is applied for both orders without duplicating code.

## Key Insight (the "why")
Exactly as in Version I:
1. Whichever ride type goes **first**, only its **earliest possible finish time** matters — pick the single ride of that type with the smallest `startTime + duration`.
2. Whichever ride type goes **second**, you must check **every** ride of that type individually, since the best one depends on both its `startTime` and `duration` relative to your free time.
3. The overall answer is the **minimum** across both orders: (land first, water second) and (water first, land second).

This solution captures that pattern in a single `calculate(...)` helper that computes "best finish time when the first array's ride type goes first, second array's ride type goes second," then calls it twice with the arguments swapped.

## Line-by-line

```java
public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
    return Math.min(
        calculate(landStartTime, landDuration, waterStartTime, waterDuration),
        calculate(waterStartTime, waterDuration, landStartTime, landDuration)
    );
}
```
- First call: `calculate(land, water)` → treats **land as "goes first"**, **water as "goes second"** → computes the best land→water finish time.
- Second call: `calculate(water, land)` → the arguments are swapped, so inside the helper the *roles* swap too — now water is treated as "goes first" and land as "goes second" → computes the best water→land finish time.
- The final answer is the smaller of the two.

This is the elegant part: rather than writing two nearly-identical blocks of code (like Version I did, with two separate loops directly in the main method), the logic is written **once** as a generic helper, and simply called with swapped parameters to get the mirrored strategy.

```java
private int calculate(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
    int plan = Integer.MAX_VALUE, minTime = Integer.MAX_VALUE, waterMinTime = Integer.MAX_VALUE;
```
Inside `calculate`, the parameter names `landStartTime`/`landDuration` refer to whichever ride type is meant to go **first** (not necessarily the actual "land" ride — this is just reused naming from copy-pasting the signature). Similarly `waterStartTime`/`waterDuration` refer to whichever type goes **second**.
- `minTime`: earliest finish time among "first" rides.
- `plan`: best overall finish time for this order.
- `waterMinTime`: declared but unused — a harmless leftover variable.

```java
for(int i=0;i<landStartTime.length;i++) {
    minTime = Math.min(minTime, landStartTime[i]+landDuration[i]);
}
```
Find the earliest possible finish time among all "first" rides — you want to be free as early as possible before tackling the second ride.

```java
for(int i=0;i<waterStartTime.length;i++) {
    plan = Math.min(plan, Math.max(minTime, waterStartTime[i])+waterDuration[i]);
}
```
For every "second" ride `i`, compute the finish time if you board it right after becoming free at `minTime` (or later, if the ride itself hasn't started yet): `max(minTime, waterStartTime[i]) + waterDuration[i]`. Keep the minimum across all such rides.

```java
return plan;
```
Return the best achievable finish time for this particular order (first-type then second-type).

## Why this refactor matters for larger constraints
Version I's solution wrote the same "find min finish time, then scan the other array" logic **twice inline** in the main method — once for land→water and once for water→land — each essentially duplicated code. Version II's larger constraints don't change the *algorithmic* complexity (both are `O(n + m)`), but the helper-function refactor:
- Avoids code duplication and reduces the chance of a copy-paste bug (e.g. forgetting to swap a variable in one of the two blocks).
- Keeps each pass simple and linear — a single loop to find the "first-ride" minimum, a single loop to scan "second-ride" options — which is essential since with much larger `n`/`m`, any accidental `O(n*m)` nested loop (e.g. checking every land–water pair directly) would time out.

## Step-by-step example
`landStartTime = [4,2], landDuration = [3,1]`
`waterStartTime = [3,1], waterDuration = [2,4]`

**Call 1: `calculate(land, water)`** (land first, water second)
- Land finish times: `4+3=7`, `2+1=3` → `minTime = 3`
- Water 0: `max(3,3)+2 = 5`
- Water 1: `max(3,1)+4 = 7`
- `plan = 5`

**Call 2: `calculate(water, land)`** (water first, land second)
- Water finish times: `3+2=5`, `1+4=5` → `minTime = 5`
- Land 0: `max(5,4)+3 = 8`
- Land 1: `max(5,2)+1 = 6`
- `plan = 6`

**Final answer:** `min(5, 6) = 5`

## Complexity
- **Time:** `O(n + m)` total — each `calculate` call does one pass over the "first" array and one pass over the "second" array; called twice, so still linear overall (`O(2(n+m))` = `O(n+m)`).
- **Space:** `O(1)` — only a handful of scalar variables per call.

## Note on the unused variable
`waterMinTime` is declared in `calculate` but never used — it's dead code, likely a leftover from an earlier draft of the solution. It doesn't affect correctness or performance, but it could be removed for cleanliness.