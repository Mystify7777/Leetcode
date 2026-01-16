// https://leetcode.com/problems/maximum-square-area-by-removing-fences-from-a-field/
class Solution {
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        List<Integer> hList = new ArrayList<>();
        for (int h : hFences) hList.add(h);
        hList.add(1);
        hList.add(m);

        List<Integer> vList = new ArrayList<>();
        for (int v : vFences) vList.add(v);
        vList.add(1);
        vList.add(n);

        Set<Integer> stt = new HashSet<>();
        long ans = 0;

        for (int i = 0; i < hList.size(); i++) {
            for (int j = i + 1; j < hList.size(); j++) {
                stt.add(Math.abs(hList.get(j) - hList.get(i)));
            }
        }

        for (int i = 0; i < vList.size(); i++) {
            for (int j = i + 1; j < vList.size(); j++) {
                int val = Math.abs(vList.get(j) - vList.get(i));
                if (stt.contains(val)) {
                    ans = Math.max(ans, val);
                }
            }
        }

        if (ans == 0) {
            return -1;
        }

        long MOD = 1_000_000_007;
        return (int) ((ans * ans) % MOD);
    }
}

// import java.util.*;
class Solution2 {
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        long mod = 1000000007L;
        long maxLen=0L;
        long temp = 0L;


        int numY = 2+ hFences.length;
        int numX = 2+ vFences.length;

        int[] coordy = new int[numY];
        int[] coordx = new int[numX];
        coordy[0] = 1; coordy[numY-1] = m; coordx[0] = 1; coordx[numX-1] = n;

        Set<Long> diffs = new HashSet<Long>();

        //build coordy and coordx so we can check pairs
        for(int i = 1; i <= hFences.length; i++){
            coordy[i]=hFences[i-1];
        }
        for(int j = 1; j <= vFences.length; j++){
            coordx[j]=vFences[j-1];
        }

        Arrays.sort(coordy);
        Arrays.sort(coordx);

        //first calculate horizontatl distances
        for(int i =0; i<coordx.length; i++){
            for(int j =i; j<coordx.length; j++){
                long temp1 = (long)coordx[i]-coordx[j];
                diffs.add(temp1);
            }
        }
        for(int a =0; a < coordy.length; a++){
            for(int b =a; b < coordy.length; b++){
                long temp2 = (long) coordy[a]-coordy[b];
                if(temp2*temp2 <= maxLen) 
                    continue;
                boolean same = diffs.contains(temp2);
                temp = temp2*temp2;
                if(same){
                    maxLen = Math.max(maxLen, temp);
                }
            }
        }
        if(maxLen == 0){
            return -1;
        }
        return (int) (maxLen % mod);

    }
}