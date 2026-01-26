// 1200. Minimum Absolute Difference
// https://leetcode.com/problems/minimum-absolute-difference/
class Solution {
        public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> res = new ArrayList();
        //sort elements
        Arrays.sort(arr);
        //init our min difference value
        int min = Integer.MAX_VALUE;
        //start looping over array to find real min element. Each time we found smaller difference
        //we reset resulting list and start building it from scratch. If we found pair with the same
        //difference as min - add it to the resulting list
        for (int i = 0; i < arr.length - 1; i++) {
            int diff = arr[i + 1] - arr[i];
            if (diff < min) {
                min = diff;
                res.clear();
                res.add(Arrays.asList(arr[i], arr[i + 1]));
            } else if (diff == min) {
                res.add(Arrays.asList(arr[i], arr[i + 1]));
            }
        }
        return res;
    }
}

class Solution2 {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : arr) {
            max = Math.max(num, max);
            min = Math.min(num, min);
        }
        int k = max - min + 1;
        boolean[] count = new boolean[k];
        for (int num : arr) {
            count[num - min] = true;
        }
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            while(!count[j]) j++;
            arr[i] = j++ + min;
        }
        List<List<Integer>> result = new ArrayList<>();
        int dif = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length-1; i++) {
            int curDif = arr[i+1] - arr[i];
            if (curDif < dif) {
                result.clear();
                dif = curDif;
            }
            if (curDif == dif) result.add(Arrays.asList(arr[i], arr[i+1]));
        }
        return result;
    }
}