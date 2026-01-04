// 130. Four Divisors
class Solution {
    public int sumFourDivisors(int[] nums) {
        int res = 0;

        for(int num : nums){
            if(sumOne(num) != -1)res+=sumOne(num);
        }

        return res;
    }

    private int sumOne(int n) {
        // Case 1: p^3
        int p = (int) Math.round(Math.cbrt(n));
        if ((long) p * p * p == n && isPrime(p)) {
            return 1 + p + p * p + p * p * p;
        }

        // Case 2: p * q
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                int a = i;
                int b = n / i;
                if (a != b && isPrime(a) && isPrime(b)) {
                    return 1 + a + b + n;
                }
                return -1;
            }
        }
        return -1;
    }

    private boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}

/**
class Solution {
    public int sumFourDivisors(int[] nums) {
        int totalSum =0;
        for(int n:nums){
            if(n<6) continue;

            int p =(int)Math.round(Math.cbrt(n));
            if(p*p*p ==n && isPrime(p)){
              totalSum+= 1+p+p*p+n;
              continue;
            }

            for(int i=2; i*i<=n;i++){
                if(n%i==0){
                    int p1=i;
                    int p2 =n/i;
                

                if(p1!=p2 && isPrime(p1) && isPrime(p2)){
                    totalSum+= 1+p1+p2+n;
                }
                break;
            }
            }
        }
        return totalSum;
    }
        private boolean isPrime(int num){
            if(num<=1) return false;

            if(num<=3) return true;

            if(num%2==0 || num%3 ==0) return false;

            for(int i=5; i*i<=num; i+=6){
                if(num%i==0 || num%(i+2)==0){
                    return false;
                }
            }
            return true;
        }
    }
 */