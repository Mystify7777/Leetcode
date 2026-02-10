// 3719. Longest Balanced Subarray I
// https://leetcode.com/problems/longest-balanced-subarray-i/
class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            Set<Integer> e = new HashSet<>();
            Set<Integer> o = new HashSet<>();
            for (int j = i; j < n; j++) {
                if (nums[j] % 2 == 0) e.add(nums[j]);
                else o.add(nums[j]);
                if (e.size() == o.size()) ans = Math.max(ans, j - i + 1);
            }
        }
        return ans;
    }
}

class Solution2 {
    // Arrays ko bahar rakho taaki baar-baar allocate na ho
    int[] s1 = new int[100001]; 
    int[] s2 = new int[100001];
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int max = 0;
        int timer = 0; // Har naye test case ke liye reset (Optional)
        for (int i = 0; i < n; i++) {
            // "new" karne ke bajaye bas timer badhao
            timer++; 
            int ev = 0;
            int od = 0;

            // Optimization: Agar bacha hua rasta max se chota h toh skip
            if (max >= n - i) break; 

            for (int j = i; j < n; j++) {
                int val = nums[j];
                if (val % 2 == 0) {
                    // Agar s1[val] timer ke barabar nahi h, matlab is loop me naya h
                    if (s1[val] != timer) {
                        s1[val] = timer;
                        ev++;
                    }
                } else {
                    if (s2[val] != timer) {
                        s2[val] = timer;
                        od++;
                    }
                }

                if (ev == od) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }
}