// 3867. Sum of GCD of Formed Pairs
// https://leetcode.com/problems/sum-of-gcd-of-formed-pairs
class Solution2 {
    private int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

    public long gcdSum(int[] A) {
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            max = Math.max(max, A[i]);
            A[i] = gcd(A[i], max);
        }

        Arrays.sort(A);

        long res = 0;        
        for (int i = 0, j = A.length - 1; i < j; i++, j--)
            res += gcd(A[i], A[j]);

        return res;
    }
}
class Solution {
   
    public long gcdSum(int[] arr) {
        int[] prefi = new int[arr.length];
        int mx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > mx) {
                mx = arr[i];
            }
            prefi[i] = gcd(mx, arr[i]);
        }
        Arrays.sort(prefi);
        int i = 0;
        int j = arr.length - 1;
        long sum = 0;
        while (i < j) {
            sum += gcd(prefi[i], prefi[j]);
            i++;
            j--;
        }
        return sum;
    }

     public int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}