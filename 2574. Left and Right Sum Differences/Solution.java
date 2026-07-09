// 2574. Left and Right Sum Differences
// https://leetcode.com/problems/left-and-right-sum-differences/
class Solution{
    public int[] leftRightDifference(int[] nums){
        int n = nums.length;
        int[] answer = new int[n];

        int totalSum = 0;
        for(int i=0;i<n;i++){
            totalSum += nums[i];
        }
        int leftSum = 0;

        for(int i=0;i<n;i++){
            int rightSum = totalSum - leftSum - nums[i];

            if(leftSum > rightSum){
                answer[i] = leftSum - rightSum;
            }else{
                answer[i] = rightSum - leftSum;
            }
            leftSum+=nums[i];
        }
        return answer;
    }
}
class Solution2 {
    public int[] leftRightDifference(int[] arr) {
        int n=arr.length;
        long pre[]=new long[n];
        long suf[]=new long[n];
        int res[]=new int[n];
        pre[0]=0;
        suf[n-1]=0;
        for(int i=1;i<n;i++){
            pre[i]=pre[i-1]+arr[i-1];
        }
        for(int i=n-2;i>=0;i--){
            suf[i]=suf[i+1]+arr[i+1];
        }
for(int i=0;i<n;i++){
    res[i]=(int)Math.abs(pre[i]-suf[i]);
}
return res;

        
    }
}
