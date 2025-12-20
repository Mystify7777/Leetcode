// 944. Delete Columns to Make Sorted
//  https://leetcode.com/problems/delete-columns-to-make-sorted/
class Solution {
    public int minDeletionSize(String[] strs) {
        if(strs == null || strs.length == 0) return 0;
        int deletions = 0;
        for(int column = 0; column < strs[0].length(); column++){
            char character = strs[0].charAt(column);
            for(int word = 0; word < strs.length; word++){
                if(strs[word].charAt(column) < character){
                    deletions++;
                    break;
                }
                character = strs[word].charAt(column);
            }
        }

        return deletions;
    }
}

//slightly faster?
/**
class Solution {
    public int minDeletionSize(String[] strs) {
        int m = strs.length, count = 0;
        char[][] matrix = new char[m][];

        for (int i = 0; i < m; i++) {
            matrix[i] = strs[i].toCharArray();
        }
        int n = matrix[0].length;

        for (int i = 0; i < n; i++) {
            if(!isSort(matrix,i))count++;
        }

        return count;
    }

    private boolean isSort(char[][] mat,int col){
        for (int i = 0; i < mat.length - 1; i++) {
            if (mat[i][col] > mat[i + 1][col]) {
                return false;
            }
        }
        return true;
    }
}
 */