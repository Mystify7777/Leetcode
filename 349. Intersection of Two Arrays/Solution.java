import java.util.HashSet;
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> SetA = new HashSet<>();
        HashSet<Integer> SetB = new HashSet<>();

        for(int num: nums1){
            SetA.add(num);
        }
        for(int num: nums2){
            if(SetA.contains(num)){
                SetB.add(num);
            }
        }
        int result[] = new int[SetB.size()];
        int i = 0;
        for(int num: SetB){
            result[i++] = num;
        }
        return result;
    }
}