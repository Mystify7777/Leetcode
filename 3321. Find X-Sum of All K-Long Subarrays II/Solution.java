// 3321. Find X-Sum of All K-Long Subarrays II
// https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-ii/
class Solution 
{
    public long[] findXSum(int[] nums, int k, int x) 
    {
        int n = nums.length;
        long[] ans = new long[n - k + 1];

        // Step 1: Initialize structures
        HashMap<Integer, Integer> freq = new HashMap<>();
        Comparator<Integer> cmp = (a, b) -> {
            int fa = freq.getOrDefault(a, 0), fb = freq.getOrDefault(b, 0);
            if (fa != fb) return Integer.compare(fa, fb);
            return Integer.compare(a, b);
        };

        TreeSet<Integer> topX = new TreeSet<>(cmp);
        TreeSet<Integer> rest = new TreeSet<>(cmp);
        long sumTop = 0L;

        // Step 2: Define ordering / comparator
        // (Defined above in cmp)

        for (int i = 0; i < n; i++) 
        {
            int v = nums[i];

            // Step 3: Remove-before-update rule (for incoming element)
            int old = freq.getOrDefault(v, 0);
            if (old > 0) 
            {
                if (topX.remove(v)) 
                {
                    sumTop -= (long) old * v;
                } 
                else 
                {
                    rest.remove(v);
                }
            }

            // Update frequency for v
            freq.put(v, old + 1);
            // Insert into rest
            rest.add(v);

            // Step 4: Rebalance routine
            sumTop = rebalance(topX, rest, freq, x, sumTop);

            // Step 5 & 6: Add incoming and Remove outgoing if needed
            if (i >= k) 
            {
                int u = nums[i - k];
                int oldU = freq.get(u);

                if (topX.remove(u)) 
                {
                    sumTop -= (long) oldU * u;
                } 
                else 
                {
                    rest.remove(u);
                }

                if (oldU == 1) 
                {
                    freq.remove(u);
                } 
                else 
                {
                    freq.put(u, oldU - 1);
                    rest.add(u);
                }

                sumTop = rebalance(topX, rest, freq, x, sumTop);
            }

            // Step 7: Main sliding-window loop – record result
            if (i >= k - 1) 
            {
                ans[i - k + 1] = sumTop;
            }
        }

        // Step 9: Return result
        return ans;
    }

    public long rebalance(TreeSet<Integer> topX, TreeSet<Integer> rest, Map<Integer,Integer> freq, int x, long sumTop) 
    {
        // Step 4 logic inline
        while (topX.size() < x && !rest.isEmpty()) 
        {
            int best = rest.last();
            rest.remove(best);
            topX.add(best);
            sumTop += (long) freq.get(best) * best;
        }

        while (topX.size() > x) 
        {
            int worst = topX.first();
            topX.remove(worst);
            rest.add(worst);
            sumTop -= (long) freq.get(worst) * worst;
        }

        while (!topX.isEmpty() && !rest.isEmpty()) 
        {
            int worstTop = topX.first();
            int bestRest = rest.last();
            int fw = freq.get(worstTop), fr = freq.get(bestRest);

            if (fr > fw || (fr == fw && bestRest > worstTop)) 
            {
                topX.remove(worstTop);
                rest.remove(bestRest);
                topX.add(bestRest);
                rest.add(worstTop);
                sumTop += (long) fr * bestRest - (long) fw * worstTop;
            } 
            else 
            {
                break;
            }
        }
        
        return sumTop;
    }
}

//a faster approach
/**
class Solution {
    private int x;
    private long sum = 0L;
    private Map<Integer, Integer> freq;
    private final TreeSet<int[]> active = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]), inactive = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
    public long[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        this.x = x;
        freq = new HashMap<>(n);
        long[] ans = new long[n - k + 1];
        for(int i = 0; i < n; i++) {
            int count = freq.merge(nums[i], 1, Integer::sum);
            remove(count - 1, nums[i]);
            add(count, nums[i]);
            if(i + 1 >= k) {
                ans[i - k + 1] = sum;
                count = freq.merge(nums[i - k + 1], -1, Integer::sum);
                remove(count + 1, nums[i - k + 1]);
                add(count, nums[i - k + 1]);
            }
        }
        return ans;
    }
    private void add(int count, int num) {
        if(count == 0) return;
        int[] val = new int[] {count, num};
        if(active.size() < x) {
            active.add(val);
            sum += (long)count * num;
            return;
        }
        int[] temp = active.first();
        if(temp[0] > count || temp[0] == count && temp[1] >= num) {
            inactive.add(val);
            return;
        }
        sum += (long)count * num - (long)temp[0] * temp[1];
        inactive.add(active.pollFirst());
        active.add(val);
    }
    private void remove(int count, int num) {
        if(count == 0) return;
        int[] val = new int[] {count, num};
        if(inactive.contains(val)) {
            inactive.remove(val);
            return;
        }
        
        active.remove(val);
        sum -= (long)count * num;
        if(inactive.isEmpty()) return;

        int[] temp = inactive.pollLast();
        sum += (long)temp[0] * temp[1];
        active.add(temp);
    }
}
 */