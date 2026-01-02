// 459. Repeated Substring Pattern
class Solution {
    public boolean repeatedSubstringPattern(String str) {
        int len = str.length();
    	for(int i=len/2 ; i>=1 ; i--) {
    		if(len%i == 0) {
    			int m = len/i;
    			String subS = str.substring(0,i);
    			int j;
    			for(j=1;j<m;j++) {
    				if(!subS.equals(str.substring(j*i,i+j*i))) break;
    			}
    			if(j==m)
    			    return true;
    		}
    	}
    	return false;
    }
}

//compare
/**
public boolean repeatedSubstringPattern(String str) {
	int l = str.length();
	for(int i=l/2;i>=1;i--) {
		if(l%i==0) {
			int m = l/i;
			String subS = str.substring(0,i);
			StringBuilder sb = new StringBuilder();
			for(int j=0;j<m;j++) {
				sb.append(subS);
			}
			if(sb.toString().equals(str)) return true;
		}
	}
	return false;
}
 */

 //compare this too
 /**
 class Solution {
    public boolean repeatedSubstringPattern(String s) {
        return (s + s).substring(1, s.length() * 2 - 1).contains(s);
    }
  */