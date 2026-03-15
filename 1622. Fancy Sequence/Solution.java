//cp
// 1622. Fancy Sequence
// https://leetcode.com/problems/fancy-sequence/
class Fancy {
    private static final int MOD = 1000000007;
    private ArrayList<Long> val;  
    private long a, b;   

    public Fancy() {
        val = new ArrayList<>(); 
        a = 1;
        b = 0;
    }

    private long modPow(long x, long y, long mod) {
        long res = 1;
        x = x % mod;
        while (y > 0) {
            if (y % 2 == 1) {
                res = (res * x) % mod;
            }
            y = y / 2;
            x = (x * x) % mod;
        }
        return res;
    }

    public void append(int val) {
        long x = (val - b + MOD) % MOD;
        this.val.add((x * modPow(a, MOD - 2, MOD)) % MOD);  
    }

    public void addAll(int inc) {
        b = (b + inc) % MOD;
    }

    public void multAll(int m) {
        a = (a * m) % MOD;
        b = (b * m) % MOD;
    }

    public int getIndex(int idx) {
        if (idx >= val.size()) return -1; 
        return (int)((a * val.get(idx) + b) % MOD);
    }
}

/**
 * Your Fancy object will be instantiated and called as such:
 * Fancy obj = new Fancy();
 * obj.append(val);
 * obj.addAll(inc);
 * obj.multAll(m);
 * int param_4 = obj.getIndex(idx);
 */

 class Fancy2 
{
    private static final int MOD = 1000000007;

    // cache inverse values for 0-100
    private static final int[] INV = IntStream.range(0, 101).map(Fancy::modInverse).toArray();

    // Modular multiplicative inverse x => a * x % MOD == 1
    private static int modInverse(int a) 
    {
        int m = MOD, y = 0, x = 1;
        while (a > 1) 
        {
            int q = a / m;
            int t = m;
            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }
        return x < 0 ? x + MOD : x;
    }

    private long mul = 1; // cumulative multiplication (%MOD)
    private long add = 0; // cumulative addition (%MOD)

    private long rmul = 1; // reverse cumulative multiplication (%MOD)
    
    // store base values, i.e. reverse cumulative transform are applied before addition 
    private final List<Integer> list = new ArrayList<>();
    
    public void append(int val) 
    {
        list.add((int) (((MOD - add + val) * rmul) % MOD));
    }

    public void addAll(int inc) 
    {
        add = (add + inc) % MOD;
    }

    public void multAll(int m) 
    {
        mul = (mul * m) % MOD;
        rmul = (rmul * INV[m]) % MOD;
        add = (add * m) % MOD;
    }

    public int getIndex(int idx) 
    {
        if (idx < list.size()) return (int) (((list.get(idx) * mul) + add) % MOD);
        else return -1;
    }
}

/**
 * Your Fancy object will be instantiated and called as such:
 * Fancy obj = new Fancy();
 * obj.append(val);
 * obj.addAll(inc);
 * obj.multAll(m);
 * int param_4 = obj.getIndex(idx);
 */