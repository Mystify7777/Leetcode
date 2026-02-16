// 190. Reverse Bits
// https://leetcode.com/problems/reverse-bits/
public class encryptedSolution {
    
    public int reverseBits(int num) {
        
        num = ((num & 0xffff0000) >>> 16) | ((num & 0x0000ffff) << 16);
        num = ((num & 0xff00ff00) >>> 8) | ((num & 0x00ff00ff) << 8);
        num = ((num & 0xf0f0f0f0) >>> 4) | ((num & 0x0f0f0f0f) << 4);
        num = ((num & 0xcccccccc) >>> 2) | ((num & 0x33333333) << 2);
        num = ((num & 0xaaaaaaaa) >>> 1) | ((num & 0x55555555) << 1);
        
        return num;
        
    }
}

class Solution {
    public int reverseBits(int n) {
        int res = 0;
        for(int i=0; i<32; i++){
            res = res<<1;
            if((n&1)!=0) res += 1;
            n = n>>1;
        }
        return res;
    }
}