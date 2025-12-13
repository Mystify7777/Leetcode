//copypasted
//https://leetcode.com/problems/coupon-code-validator/
//3606. Coupon Code Validator
class Solution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {

        // Business line priority
        Map<String, Integer> priority = new HashMap<>();
        priority.put("electronics", 0);
        priority.put("grocery", 1);
        priority.put("pharmacy", 2);
        priority.put("restaurant", 3);

        List<Pair> valid = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {
            if (isActive[i] && priority.containsKey(businessLine[i]) && isValidCode(code[i])) {
                valid.add(new Pair(priority.get(businessLine[i]), code[i]));
            }
        }

        // Sort by business priority, then by code
        Collections.sort(valid, (a, b) -> {
            if (a.priority != b.priority)
                return a.priority - b.priority;
            return a.code.compareTo(b.code);
        });

        List<String> result = new ArrayList<>();
        for (Pair p : valid) {
            result.add(p.code);
        }

        return result;
    }

    private boolean isValidCode(String s) {
        if (s.length() == 0) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_')
                return false;
        }
        return true;
    }

    // Helper class to store (priority, code)
    static class Pair {
        int priority;
        String code;

        Pair(int priority, String code) {
            this.priority = priority;
            this.code = code;
        }
    }
}
//another approach

/**
class Solution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        List<String> electronics = new ArrayList<>();
        List<String> grocery = new ArrayList<>();
        List<String> pharmacy = new ArrayList<>();
        List<String> restaurant = new ArrayList<>();
        
        for (int i = 0; i < code.length; i++) {
            if (!isActive[i] || code[i].isEmpty()) continue;
            
            boolean validCode = true;
            for (char c : code[i].toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '_') {
                    validCode = false;
                    break;
                }
            }
            
            if (validCode) {
                if (businessLine[i].equals("electronics")) {
                    electronics.add(code[i]);
                }
                else if (businessLine[i].equals("grocery")){
                    grocery.add(code[i]);
                }
                else if (businessLine[i].equals("pharmacy")){
                    pharmacy.add(code[i]);
                }
                else if (businessLine[i].equals("restaurant")){
                    restaurant.add(code[i]);
                }
            }
        }
        
        Collections.sort(electronics);
        Collections.sort(grocery);
        Collections.sort(pharmacy);
        Collections.sort(restaurant);
        
        List<String> result = new ArrayList<>();
        result.addAll(electronics);
        result.addAll(grocery);
        result.addAll(pharmacy);
        result.addAll(restaurant);
        
        return result;
    }
}
 */