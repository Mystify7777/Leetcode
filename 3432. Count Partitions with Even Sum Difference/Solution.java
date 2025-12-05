// 3432. Count Partitions with Even Sum Difference
// https://leetcode.com/problems/count-partitions-with-even-sum-difference/
class Solution {
    public int countPartitions(int[] A) {
        int total = Arrays.stream(A).sum();
        return (total & 1) == 0 ? A.length - 1 : 0;
    }
}
/**
class Solution {
    public int countPartitions(int[] nums) {
        int total = 0;
for (int num : nums) total += num;

int leftSum = 0;
int count = 0;

for (int i = 0; i < nums.length - 1; i++) {
    leftSum += nums[i];
    int rightSum = total - leftSum;

    if ((leftSum % 2) == (rightSum % 2)) {
        count++;
    }
}
            return count;
        }
    }
 */