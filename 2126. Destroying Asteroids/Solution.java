// 2126. Destroying Asteroids
// https://leetcode.com/problems/destroying-asteroids/

class Solution {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        int maxasteroid = 0;
        for(int a : asteroids) {
            if(a > maxasteroid) maxasteroid = a;
        }
        
        int[] freq = new int[maxasteroid + 1];
        for(int a : asteroids) {
            freq[a]++;
        }
        
        long currentmass = mass;
        for(int i = 1; i <= maxasteroid; i++) {
            if(freq[i] > 0) {
                if(i > currentmass) return false;
                currentmass += (long) i * freq[i];
            }
        }
        
        return true;
    }
}

class Solution2 {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        long 
            previous_sum = mass,
            sum = mass;

        int len = asteroids.length, left = 0, right = len-1, temp;

        while(left != len){
            while(left <= right){
                if(sum >= asteroids[left]){ 
                    sum = sum + asteroids[left]; 
                    left ++;
                }else if(asteroids[right]>sum){
                    right --;
                }else{
                    temp = asteroids[left];
                    asteroids[left] = asteroids[right];
                    asteroids[right] = temp;

                    sum = sum + asteroids[left];
                    left ++;
                }
            }

            if(previous_sum == sum){ return false; }

            previous_sum = sum;
            right = len -1;
            
        }

        return true;


    }
}
