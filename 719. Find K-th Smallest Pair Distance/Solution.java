// 719. Find K-th Smallest Pair Distance
// https://leetcode.com/problems/find-k-th-smallest-pair-distance/
class Solution {
    public int smallestDistancePair(int[] numbers, int k) {
        Arrays.sort(numbers);
        int minDistance = 0;
        int maxDistance = numbers[numbers.length - 1] - numbers[0];
        
        while (minDistance < maxDistance) {
            int midDistance = minDistance + (maxDistance - minDistance) / 2;
            int pairsCount = countPairsWithinDistance(numbers, midDistance);
            
            if (pairsCount < k) {
                minDistance = midDistance + 1;
            } else {
                maxDistance = midDistance;
            }
        }
        
        return minDistance;
    }

    private int countPairsWithinDistance(int[] numbers, int targetDistance) {
        int count = 0;
        int left = 0;
        
        for (int right = 1; right < numbers.length; right++) {
            while (numbers[right] - numbers[left] > targetDistance) {
                left++;
            }
            count += right - left;
        }
        
        return count;
    }
}
/**
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        
        int l = 0, r = nums[nums.length-1];
        int result = 0;
        int pairs = 0;
        
        while(l <= r) {
            int mid = l + (r - l)/2;
            int cp = slidingWindow(nums, mid);
            if(cp < k) {
                l = mid + 1;
            }
            else {
                result = mid;
                r = mid - 1;
            }
        }
        return result;
    }
    
    public int slidingWindow(int[] nums, int d) {
        int i = 0;
        int j = i+1;
        int countOfPairs = 0;
        while(j < nums.length) {
            while (nums[j] - nums[i] > d) {
                i++;
            }
            countOfPairs += (j - i);
            j++;
        }
        return countOfPairs;
    }
} */