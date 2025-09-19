// 3484. Design Spreadsheet
import java.util.HashMap;
class Spreadsheet {
    HashMap<String, Integer> mpp = new HashMap<>();
    public Spreadsheet(int rows) {}
    public void setCell(String cell, int value) {
        mpp.put(cell, value);
    }
    public void resetCell(String cell) {
        mpp.put(cell, 0);
    }
    public int getValue(String formula) {
        formula = formula.substring(1);
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '+') {
                String s1 = formula.substring(0, i), s2 = formula.substring(i + 1);
                int left = Character.isUpperCase(s1.charAt(0)) ? mpp.getOrDefault(s1, 0) : Integer.parseInt(s1);
                int right = Character.isUpperCase(s2.charAt(0)) ? mpp.getOrDefault(s2, 0) : Integer.parseInt(s2);
                return left + right;
            }
        }
        return 0;
    }
}

/**
class Spreadsheet {

    public Spreadsheet(int rows) {
        
    }

    Map<String, Integer> map = new HashMap<>(); 
    
    public void setCell(String cell, int value) {
        map.put(cell, value);
    }
    
    public void resetCell(String cell) {
        map.remove(cell);
    }
    
    public int getValue(String formula) {
        int io = formula.indexOf('+');
        String cell1 = formula.substring(1, io);     
        String cell2 = formula.substring(io + 1); 
        
        int val1;
        if (cell1.charAt(0) > '9') {
            val1 = map.getOrDefault(cell1, 0);
        } else {
            val1 = Integer.parseInt(cell1);
        }

        int val2;
        if (cell2.charAt(0) > '9') {
            val2 = map.getOrDefault(cell2, 0);
        } else {
            val2 = Integer.parseInt(cell2);
        }

        return val1 + val2;
    }
}
 */