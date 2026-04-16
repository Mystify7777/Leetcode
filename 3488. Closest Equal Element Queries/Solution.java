// 3488. Closest Equal Element Queries
// https://leetcode.com/problems/closest-equal-element-queries/
class Solution {
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();

        // store indices
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        List<Integer> ans = new ArrayList<>();

        for (int q : queries) {
            List<Integer> v = map.get(nums[q]);

            // only one time present
            if (v.size() == 1) {
                ans.add(-1);
                continue;
            }

            int pos = Collections.binarySearch(v, q);
            int res = Integer.MAX_VALUE;

            // left neighbor
            int left = v.get((pos - 1 + v.size()) % v.size());
            int d1 = Math.abs(q - left);
            res = Math.min(res, Math.min(d1, n - d1));

            // right neighbor
            int right = v.get((pos + 1) % v.size());
            int d2 = Math.abs(q - right);
            res = Math.min(res, Math.min(d2, n - d2));

            ans.add(res);
        }

        return ans;
    }
}

class Solution2 {
    private int circularDistance(int from, int to, int n) {
        if(from <= to)
            return to - from;
        return n - from + to;
    }
    public List<Integer> solveQueries(int[] nums, int[] queries) {
         int[] distances = new int[nums.length];
         Map<Integer, int[]> lastSeen = new HashMap<>();
         for(int i=0; i<nums.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            if(lastSeen.containsKey(nums[i])) {
                // calculate distance on the old item
                int lastSeenIdxLeft = lastSeen.get(nums[i])[0];
                int lastSeenIdxRight = lastSeen.get(nums[i])[1];
                distances[lastSeenIdxRight] = Math.min(distances[lastSeenIdxRight], circularDistance(lastSeenIdxRight, i, nums.length));
                distances[lastSeenIdxLeft] = Math.min(distances[lastSeenIdxLeft], circularDistance(i, lastSeenIdxLeft, nums.length));
                distances[i] = Math.min(circularDistance(lastSeenIdxRight, i, nums.length),
                circularDistance(i, lastSeenIdxLeft, nums.length));
                lastSeen.put(nums[i], new int[] {lastSeen.get(nums[i])[0],i});
            } else {
                lastSeen.put(nums[i], new int[] {i,i});
            }
         }
        
        List<Integer> result = new ArrayList<>();
        for(int queryIdx : queries) {
            result.add(distances[queryIdx] == Integer.MAX_VALUE ? -1 : distances[queryIdx]);
        }
        return result;
    }
}