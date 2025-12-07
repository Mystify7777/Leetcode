// 1523. Count Odd Numbers in an Interval Range
// https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/
class Solution {
     public int countOdds(int low, int high) {
       int nums=high-low+1;  //counting total numbers in range
 
		if(low%2!=0 && high%2!=0)  
            return nums/2 + 1;
        
        else
		return nums/2;
    }
}