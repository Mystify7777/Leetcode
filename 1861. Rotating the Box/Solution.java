// 1861. Rotating the Box
// https://leetcode.com/problems/rotating-the-box/
class Solution {
     public char[][] rotateTheBox(char[][] box) {
        int rows = box.length;
        int cols = box[0].length;

        for (int i = 0; i < rows; i++) {
            int empty = cols - 1; 
            for (int j = cols - 1; j >= 0; j--) {
                if (box[i][j] == '#') { 
                    box[i][j] = '.';
                    box[i][empty] = '#';
                    empty--;
                } else if (box[i][j] == '*') { 
                    empty = j - 1; 
                }
            }
        }

        
        char[][] rotatedBox = new char[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                rotatedBox[i][j] = box[rows - 1 - j][i];
            }
        }

        return rotatedBox;
    }
}

class Solution2 {
    public char[][] rotateTheBox(char[][] boxGrid) {
        
        // n x m rotated grid
        char[][] rotatedGrid = new char[boxGrid[0].length][boxGrid.length];

        for (int i = 0; i < boxGrid.length; i++) {
            processRow(boxGrid[i], rotatedGrid, boxGrid.length - 1 - i);
        }

        return rotatedGrid;
    }


    private void processRow(char[] row, char[][] rotatedGrid, int x) {

        int lowestBarrier = row.length;
        for (int curr = row.length - 1; curr >= 0; curr--) {
            
            if (row[curr] == '#') {
                lowestBarrier -= 1;
                rotatedGrid[lowestBarrier][x] = '#';

                if (curr != lowestBarrier) rotatedGrid[curr][x] = '.';
            } else if (row[curr] == '*') {
                lowestBarrier = curr;
                rotatedGrid[lowestBarrier][x] = '*';
            } else {
                rotatedGrid[curr][x] = '.';
            }

        }
    }
}
