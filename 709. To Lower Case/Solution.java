// 709. To Lower Case
class Solution {
    public String toLowerCase(String string) {
        
        char[] array = new char[string.length()];

        int index = 0;
        for (char ch : string.toCharArray()) {
            
            if (ch >= 'A' & ch <= 'Z') {
                array[index++] = (char) ((ch - 'A') + 'a');
            } else {
                array[index++] = ch;
            }
        }
        return new String(array);
    }
}

//compare
/**
class Solution {
    public String toLowerCase(String str) {
        char[] result = str.toCharArray();
        for (int i = 0; i < result.length; i++) {
        
            if (result[i] >= 'A' && result[i] <= 'Z') {
               
                result[i] = (char) (result[i] + 32);
            }
        }
        return new String(result);
    }
}
 */