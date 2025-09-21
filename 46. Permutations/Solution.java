// 46. Permutations
/*
class Solution {
       public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();

        backtrack(ret, new ArrayList<>(), new HashSet<>(), nums);

        return ret;

    }
    
    private void backtrack(List<List<Integer>> ret, List<Integer> tmpList, Set<Integer> tmpSet, int[] nums) {
        if (tmpSet.size() == nums.length) {
            ret.add(new ArrayList<>(new ArrayList<>(tmpList)));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (tmpSet.contains(nums[i])) continue;
            
            tmpSet.add(nums[i]);
            tmpList.add(nums[i]);
            
            backtrack(ret, tmpList, tmpSet, nums);
            
            tmpSet.remove(tmpList.get(tmpList.size()-1));
            tmpList.remove(tmpList.size()-1);
        }
    }
}
*/
import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, 0, nums);

        return result;
    }

    void backtrack(List<List<Integer>> res, int pos, int[] nums) {
        if (pos == nums.length) {
            List<Integer> t = new ArrayList<>();
            for (int n: nums) t.add(n);
            res.add(t);
            return;
        }

        for (int j=pos; j<nums.length; j++) {
            int t = nums[pos];
            nums[pos] = nums[j];
            nums[j] = t;
            backtrack(res, pos+1, nums);
            t = nums[pos];
            nums[pos] = nums[j];
            nums[j] = t;
        }

    }


}