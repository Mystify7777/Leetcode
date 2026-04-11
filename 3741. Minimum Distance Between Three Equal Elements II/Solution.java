// 3741. Minimum Distance Between Three Equal Elements II
// https://leetcode.com/problems/minimum-distance-between-three-equal-elements-ii/
class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map =new HashMap<>();
        for(int i=0; i<nums.length; i++){
            int val = nums[i];
            if(!map.containsKey(val)){
                List<Integer> list =new ArrayList<>();
                list.add(i);
                map.put(val,list);
            }
            else{
                map.get(val).add(i);
            }
        }
        int result = Integer.MAX_VALUE;
        for(int key: map.keySet()){
            List<Integer> list = map.get(key);
            if(list.size()<3)continue;
            for(int i=0; i<=list.size()-3; i++){
                int sum = Math.abs(list.get(i+2)-list.get(i+1))+Math.abs(list.get(i+1)-list.get(i))+Math.abs(list.get(i)-list.get(i+2));
                result = Math.min(result,sum);
            }
        }
        return result==Integer.MAX_VALUE?-1:result;
    }
}
class Solution2 {
    public int minimumDistance(int[] nums) {
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        int prev1[] = new int[n+1];
        int prev2[] = new int[n+1];
        for(int i=0;i<n+1;i++){
            prev1[i] = prev2[i] = -1;
        }
        
        for(int i=0;i<n;i++){
            int value = nums[i];
            if(prev2[value] != -1){
                ans = Math.min(ans, (i-prev2[value]));
            }
            prev2[value] = prev1[value];
            prev1[value] = i;
        }
        return (ans==Integer.MAX_VALUE)? -1: 2*ans;
    }
}