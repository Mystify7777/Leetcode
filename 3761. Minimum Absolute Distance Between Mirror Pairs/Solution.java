// 3761. Minimum Absolute Distance Between Mirror Pairs
// https://leetcode.com/problems/minimum-absolute-distance-between-mirror-pairs/
class Solution {
    public int minMirrorPairDistance(int[] nums) {
        int res = 100000, i = 0;
        HashMap<Integer, Integer> seen = new HashMap<>();

        for (int n : nums) {
            int r;
            if (seen.containsKey(n))
                res = Math.min(res, i - seen.get(n));

            for (r = 0; n > 0; n /= 10)
                r = r * 10 + (n % 10);

            seen.put(r, i++);
        }

        return res == 100000 ? -1 : res;
    }
}
/*
 我们要找 nums[i] == reverse(nums[j]) 
 并且让 j - i 最小

 hashmap里存什么
 key   = reverse(nums[i])
 value = i 的最大下标
 意思是：
左边某个数的 reverse 结果，在什么位置出现
 
*/ 

class ChineseSolution {
    public int minMirrorPairDistance(int[] nums) {
        int n = nums.length; 
        int ans = n; 

        // lastIndex：key = reverse(某个左侧元素 nums[i])，value = 这个 i 的下标（取最新/最大下标）
        // 预分配容量 n，装载因子 1，减少扩容和 rehash（性能优化，非必须）
        Map<Integer, Integer> lastIndex = new HashMap<>(n,1.0f);

        for(int j =0; j < n ; j++){
            // 查询：左边有没有某个 i，使得 reverse(nums[i]) == nums[j]
            // 因为我们把 reverse(nums[i]) 当 key 存在 lastIndex 里，所以直接用 x 查
            int x = nums[j];
            Integer i = lastIndex.get(x); 

            if(i!= null){  // 如果找到了镜像对
                int dist = j-i;  // 计算distance
                ans = Math.min(ans, dist);  // 用更小的距离更新答案
            }


             // ====== 下面：更新哈希表（把当前元素贡献给“未来的右端点”）======

            int t = x;                            // 用 t 保存 x 的副本（因为下面要反复除 10 改变变量）
            int rev = 0;                          // rev 用来计算 reverse(x)



             // 计算 reverse(t)：例如 t=123 -> rev=321
            while (t > 0) {                       // 只要 t 还有数字
                int digit = t % 10;               // 取出 t 的最后一位数字
                rev = rev * 10 + digit;           // 把 digit 拼到 rev 的末尾
                t = t / 10;                       // 去掉 t 的最后一位
            }


             // 把 reverse(nums[j]) 作为 key 存起来，并记录当前下标 j
            // 这里会覆盖旧值：保证 value 是“最大/最新下标”，这样 j-i 才可能更小
            lastIndex.put(rev, j);                // lastIndex[rev] = j

        }

        // 如果 ans 仍然是 n，说明从未更新过（没找到任何镜像对），返回 -1
        // 否则返回最小距离 ans
        if (ans < n) {                            // 找到过镜像对
            return ans;                           // 返回最小的镜像距离
        } else {                                  // 没找到镜像对
            return -1;                            // 按题意返回 -1
        }
        
    }
}