# 3433. Count Mentions Per User — how/why

## Recap

You are given `numberOfUsers` and a list of `events`. Each event is of the form:

- `["MESSAGE", time, content]` where `content` is a space-separated list of tokens among:
  - `ALL`: mentions all users
  - `HERE`: mentions all currently online users
  - `idX`: mentions the specific user with id `X` (e.g., `id0`, `id17`)
- `["OFFLINE", time, id]` meaning user `id` goes offline for the next 60 minutes starting at `time`.

Events can share timestamps and arrive in arbitrary order. Return an array of size `numberOfUsers` with the mention counts per user.

## Intuition

Two things matter:

- Correct time ordering: process events sorted by timestamp.
- Online state per user: `OFFLINE` makes a user offline until `time + 60`; they automatically come back online at or after that moment.

When processing a timestamp `t` with multiple events:

1. First, bring back any users whose `offlineUntil <= t`.
2. Then, apply all `OFFLINE` events at `t` (they become offline until `t + 60`).
3. Finally, process `MESSAGE` events at `t` using the current online set:
   - `ALL`: every user gets +1 mention.
   - `HERE`: every online user gets +1 mention.
   - `idX`: that single user gets +1 mention (whether online or offline).

This ordering ensures `HERE` respects users who just went offline at the same timestamp.

## Approach

**Sweep by time + state tracking**:

1. Group events by timestamp and sort timestamps ascending (e.g., `TreeMap`).
2. Maintain:
   - `mentions[i]`: total mentions for user `i`.
   - `isOnline[i]`: whether user `i` is online now (initially true).
   - `offlineUntil[i]`: the timestamp when user `i` will come back online (0 if online).
3. For each timestamp `t`:
   - Reactivate users with `!isOnline[i] && offlineUntil[i] <= t`.
   - Apply all `OFFLINE` events at `t`: set `isOnline[id] = false; offlineUntil[id] = t + 60`.
   - Apply all `MESSAGE` events at `t`:
   - Tokenize `content` by spaces.
   - For token `ALL`: `mentions[i]++` for all users.
   - For token `HERE`: `mentions[i]++` for all `isOnline[i]` users.
   - For token starting with `id`: parse the id and do `mentions[id]++` if in range.

## Code (Java)

```java
class Solution {
	public int[] countMentions(int numberOfUsers, List<List<String>> events) {
		TreeMap<Integer, List<List<String>>> byTime = new TreeMap<>();
		for (List<String> ev : events) {
			int t = Integer.parseInt(ev.get(1));
			byTime.computeIfAbsent(t, k -> new ArrayList<>()).add(ev);
		}

		int[] mentions = new int[numberOfUsers];
		boolean[] isOnline = new boolean[numberOfUsers];
		int[] offlineUntil = new int[numberOfUsers];
		Arrays.fill(isOnline, true);

		for (Map.Entry<Integer, List<List<String>>> entry : byTime.entrySet()) {
			int t = entry.getKey();
			List<List<String>> evs = entry.getValue();

			// 1) auto-reactivate users whose offline period ended
			for (int i = 0; i < numberOfUsers; ++i) {
				if (!isOnline[i] && offlineUntil[i] <= t) {
					isOnline[i] = true;
					offlineUntil[i] = 0;
				}
			}

			// 2) apply OFFLINE at t
			for (List<String> ev : evs) {
				if (ev.get(0).equals("OFFLINE")) {
					int id = Integer.parseInt(ev.get(2));
					if (0 <= id && id < numberOfUsers) {
						isOnline[id] = false;
						offlineUntil[id] = t + 60;
					}
				}
			}

			// 3) apply MESSAGE at t
			for (List<String> ev : evs) {
				if (!ev.get(0).equals("MESSAGE")) continue;
				String mentionsStr = ev.get(2);
				String[] tokens = mentionsStr.split("\\s+");
				for (String token : tokens) {
					if (token.equals("ALL")) {
						for (int i = 0; i < numberOfUsers; ++i) mentions[i]++;
					} else if (token.equals("HERE")) {
						for (int i = 0; i < numberOfUsers; ++i)
							if (isOnline[i]) mentions[i]++;
					} else if (token.startsWith("id")) {
						int id = Integer.parseInt(token.substring(2));
						if (id >= 0 && id < numberOfUsers) mentions[id]++;
					}
				}
			}
		}

		return mentions;
	}
}
```

## Correctness

- **Time ordering**: Sorting by timestamp guarantees that online/offline windows are honored chronologically.
- **Within-timestamp ordering**: Reactivation first, then `OFFLINE`, then `MESSAGE` ensures `HERE` excludes users who go offline at time `t`.
- **Online window**: A user is offline strictly in the interval `(t, t + 60)` and returns at `t + 60` (reactivated when `offlineUntil <= currentTime`).
- **Mentions semantics**:
  - `ALL` always counts for everyone.
  - `HERE` respects current `isOnline`.
  - `idX` targets only the specified user.
- **Bounds safety**: `id` parsed from `idX` is range-checked.

## Complexity

- **Time**: O(E log U + U * T + E * U) worst-case with naive loops per timestamp, but typically:
  - Sorting timestamps: O(E log E).
  - Per timestamp work is proportional to number of events at that timestamp and users scanned.
  - For typical constraints, overall O(E log E + E + U) with small constants.
- **Space**: O(U + E) for state arrays and event grouping.

## Edge Cases

- Multiple events at same timestamp: ordering ensures `OFFLINE` affects `HERE` processed later.
- `HERE` when everyone offline: yields zero additions.
- `idX` for invalid `X`: safely ignored.
- User goes offline repeatedly before reactivation: `offlineUntil` updated to the latest `t + 60`.
- Reactivation at exact boundary: user is online when `currentTime == offlineUntil`.

## Takeaways

- **Event-sweep pattern**: Sort by time and maintain state; common in scheduling/timeline problems.
- **Careful intra-timestamp ordering**: The relative processing order of event types matters.
- **State with expiry**: Track `offlineUntil` to model temporary status.
- **String token handling**: Prefer simple splitting and pattern checks (`ALL`, `HERE`, `idX`).
