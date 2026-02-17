// 401. Binary Watch
// https://leetcode.com/problems/binary-watch/
class Solution {
    public List<String> readBinaryWatch(int k) {
        if (k == 0) return List.of("0:00");
        int mask = (1 << 6) - 1;
        int q = (1 << k) - 1;
        int limit = q << (10 - k);
        List<String> res = new ArrayList<>();

        while (q <= limit) {
            int min = q & mask;
            int hour = q >> 6;
            if (hour < 12 && min < 60)
                //res.add(String.format("%d:%02d", hour, min));
                res.add(hour + ":" + (min < 10 ? "0" : "") + min);
            int r = q & -q;
            int n = q + r;
            q = (((q ^ n) / r) >> 2) | n;
        }
        return res;
    }
}

//
class Solution2 {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> ans = new ArrayList<>();
        if(turnedOn > 8){
            return ans;
        }
        recurhour(ans , new StringBuilder() , 0 , 4 , turnedOn , 0);
        return ans;
    }
    void recurhour( List<String> ans , StringBuilder ds , int total , int bitno , int limit , int on){
        // System.out.println(total);
        if(bitno == 0){
            if(total > 11){
                return;
            }
            if(total == 11){
                ds.append("11");
            }
            else if(total == 10){
                ds.append("10");
            }else{
                ds.append((char)(total + '0'));
            }
            ds.append(':');
            recurmin(ans , ds , 0 , 6, limit , on , ds.length());
            ds.setLength(0);
            return;
        }
        int ntotal = total + (1<<(bitno-1));
        if(on < limit){
        recurhour(ans , ds , ntotal , bitno-1 , limit , on+1);
        }
        recurhour(ans  , ds , total  , bitno-1, limit , on);
        return;
    }
    void recurmin( List<String> ans , StringBuilder ds , int total , int bitno , int limit , int on , int len){
        if(bitno == 0){
            if(on!=limit){
                return;
            }
            if(total > 59){
                return;
            }
            
            if(total >= 10){
                // ds.append("10");
                ds.append((char)(total/10 + '0'));
                ds.append((char)(total%10 + '0'));
            }else{
                ds.append((char)('0'));
                ds.append((char)(total + '0'));
            }
            ans.add(ds.toString());
            ds.setLength(len);
            return;
        }
        int ntotal = total + (1<<(bitno-1));
        if(on < limit){
        recurmin(ans , ds , ntotal , bitno-1, limit , on+1 , len);
        }
        
        recurmin(ans  , ds , total  , bitno-1, limit , on , len);
        return;
    }
}