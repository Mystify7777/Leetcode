// 3013. Divide an Array Into Subarrays With Minimum Cost II
// https://leetcode.com/problems/divide-an-array-into-subarrays-with-minimum-cost-ii/
// cp
class Solution {
    static class SmartWindow {
        int K;
        TreeMap<Integer, Integer> low = new TreeMap<>();
        TreeMap<Integer, Integer> high = new TreeMap<>();
        long sumLow = 0;
        int szLow = 0, szHigh = 0;
        SmartWindow(int k){
            this.K = k;
        }
        int windowSize(){
            return szLow + szHigh;
        }
        private void addMap(TreeMap<Integer, Integer> mp, int x){
            mp.put(x, mp.getOrDefault(x, 0) + 1);
        }
        private boolean removeMap(TreeMap<Integer, Integer> mp, int x){
            Integer c = mp.get(x);
            if (c == null) return false;
            if (c == 1) mp.remove(x);
            else mp.put(x, c - 1);
            return true;
        }
        private int popLast(TreeMap<Integer, Integer> mp){
            int x = mp.lastKey();
            removeMap(mp, x);
            return x;
        }
        private int popFirst(TreeMap<Integer, Integer> mp){
            int x = mp.firstKey();
            removeMap(mp, x);
            return x;
        }
        void rebalance(){
            int need = Math.min(K, windowSize());
            while(szLow > need){
                int x = popLast(low);
                szLow --;
                sumLow -= x;
                addMap(high, x);
                szHigh ++;
            }
            while(szLow < need && szHigh > 0){
                int x = popFirst(high);
                szHigh --;
                addMap(low, x);
                szLow ++;
                sumLow += x;
            }
        }
        void add(int x){
            if(szLow == 0){
                addMap(low, x);
                szLow ++;
                sumLow += x;
            }
            else{
                int mxLow = low.lastKey();
                if(x <= mxLow){
                    addMap(low, x);
                    szLow ++;
                    sumLow += x;
                }
                else {
                    addMap(high, x);
                    szHigh ++;
                }
            }
            rebalance();
        }
        void remove(int x){
            if(removeMap(low, x)){
                szLow --;
                sumLow -= x;
            }
            else if(removeMap(high, x)){
                szHigh --;
            }
            rebalance();
        }
        long query(){
            return sumLow;
        }
    }

    public long minimumCost(int[] nums, int k, int dist) {
        int n = nums.length;
        k -= 1;
        SmartWindow window = new SmartWindow(k);

        for(int i = 1; i <= 1 + dist; i ++){
            window.add(nums[i]);
        }
        long ans = window.query();

        for(int i = 2; i + dist < n; i ++){
            window.remove(nums[i - 1]);
            window.add(nums[i + dist]);
            ans = Math.min(ans, window.query());
        }
        return ans + nums[0];
    }
}

class Solution2 {
    public long minimumCost(int[] nums, int k, int dist) {
        int n = nums.length;
        //维护长度固定为dist+1的滑动窗口的前1~k-1小数的和
        PriorityQueue<Integer> pq_left = new PriorityQueue<>((a, b)->b-a);
        PriorityQueue<Integer> pq_right = new PriorityQueue<>();
        Map<Integer, Integer> map = new HashMap<>();
        int valid_left = 0;
        long sum_left = 0;

        long res = Long.MAX_VALUE;

        for(int i=1; i<n; i++){
            //删除旧元素，此时堆顶元素合法
            if(i>=dist+2){
                int v = nums[i-dist-1];  //旧元素
                if(v<pq_left.peek()){
                    map.merge(v, 1, Integer::sum);
                    valid_left--;
                    sum_left -= v;
                }
                else if(v==pq_left.peek()){
                    pq_left.poll();
                    valid_left--;
                    sum_left -= v;
                }
                else if(v==pq_right.peek()){
                    pq_right.poll();
                }
                else{
                    map.merge(v, 1, Integer::sum);
                }
            }


            if(i<=k-1 || nums[i]<=pq_left.peek()){
                pq_left.offer(nums[i]);
                valid_left++;
                sum_left += nums[i];
            }
            else{
                pq_right.offer(nums[i]);
            }

            if(i>k-1){
                if(valid_left<k-1){
                    int v = pq_right.poll();
                    pq_left.offer(v);
                    valid_left++;
                    sum_left += v;
                }
                else if(valid_left>k-1){
                    int v = pq_left.poll();
                    valid_left--;
                    sum_left -= v;
                    pq_right.offer(v);
                }
            }


            while(!pq_left.isEmpty() && map.getOrDefault(pq_left.peek(), 0)>0){
                int v = pq_left.poll();
                map.merge(v, -1, Integer::sum);
            }
            while(!pq_right.isEmpty() && map.getOrDefault(pq_right.peek(), 0)>0){
                int v = pq_right.poll();
                map.merge(v, -1, Integer::sum);
            }

            if(i>=dist+1){
                res = Math.min(res, sum_left);
            }
        }
        return res+nums[0];
    }
}