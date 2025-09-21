// 39. Combination Sum
import java.util.ArrayList;
import java.util.List;
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();

        makeCombination(candidates, target, 0, new ArrayList<>(), 0, res);
        return res;        
    }

    private void makeCombination(int[] candidates, int target, int idx, List<Integer> comb, int total, List<List<Integer>> res) {
        if (total == target) {
            res.add(new ArrayList<>(comb));
            return;
        }

        if (total > target || idx >= candidates.length) {
            return;
        }

        comb.add(candidates[idx]);
        makeCombination(candidates, target, idx, comb, total + candidates[idx], res);
        comb.remove(comb.size() - 1);
        makeCombination(candidates, target, idx + 1, comb, total, res);
    }    
}

/**
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        return new java.util.AbstractList(){
            List<List<Integer>> result;
            private void init(){
                result = new ArrayList();
                backtrack(0, target, candidates, new ArrayList(), result);
            }

            @Override
            public int size(){
                if(result == null){
                    init();
                }
                return result.size();
            }

            @Override
            public List<Integer> get(int position){
                return result.get(position);
            }
        };
    }

    private void backtrack(int index, int target, int[] candidates, List<Integer> list, List<List<Integer>> result){
        if(target == 0){
            result.add(new ArrayList(list));
            return;
        }
        for(int i = index; i < candidates.length; i++){
            int remain = target - candidates[i];
            if(remain >= 0){
                list.add(candidates[i]);
                backtrack(i, remain, candidates, list, result);
                list.remove(list.size() - 1);
            }
        }
    }
} */