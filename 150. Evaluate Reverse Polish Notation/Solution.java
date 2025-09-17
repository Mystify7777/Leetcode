// 150. Evaluate Reverse Polish Notation
import java.util.Stack;

public class Solution {
    public int evalRPN(String[] tokens) {
        int a,b;
		Stack<Integer> S = new Stack<Integer>();
		for (String s : tokens) {
			if(s.equals("+")) {
				S.add(S.pop()+S.pop());
			}
			else if(s.equals("/")) {
				b = S.pop();
				a = S.pop();
				S.add(a / b);
			}
			else if(s.equals("*")) {
				S.add(S.pop() * S.pop());
			}
			else if(s.equals("-")) {
				b = S.pop();
				a = S.pop();
				S.add(a - b);
			}
			else {
				S.add(Integer.parseInt(s));
			}
		}	
		return S.pop();
	}
}

/**
class Solution {
    int i;
    public int evalRPN(String[] tokens) {
        i = tokens.length;
        return eval(tokens);
    }
    public int eval(String[] tokens) {
        String currentString = tokens[--i];
        int k = 0, num = 0, sign = 1;
        char s = currentString.charAt(0);
        if (currentString.length() == 1) {
            switch (s) {
                case '+':
                    return eval(tokens) + eval(tokens);
                case '-':
                    return -eval(tokens) + eval(tokens);
                case '*':
                    return eval(tokens) * eval(tokens);
                case '/':
                    int second = eval(tokens);
                    int first = eval(tokens);
                    return first / second;
                default:
                    return s - '0';
            }
        }
        else {
            if (s == '-') {
                sign = -1;
                k++;
            }
            while (k < currentString.length()) {
                s = currentString.charAt(k++);
                num = num * 10 + s - '0';
            }
            return num * sign;
        }
    }
}

 */