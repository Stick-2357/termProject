import java.util.*;

/*
 * Only +, * operators with integers
 * So all values are still integers
 */
public class Evaluator {

    Number evalS(String expression) {
        // return true if s is a valid expression. expression could be nested
        List<String> tokens = Tokenizer.tokenize(expression);
        return evalExpr(tokens, 0, tokens.size() - 1);
    }

    Number evalExpr(List<String> tokens, int start, int end) {
        // <expr> -> ( + <operands> ) | ( * <operands> ) | <id>
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            String operator = tokens.get(start + 1);
            List<Number> operands = getOperands(tokens, start + 2, end - 1);
            switch (operator) {
                case "+":
                    return sum(operands);
                case "*":
                    return mult(operands);
                case "/":
                    return div(operands);
                default:
                    return null;
            }
        } else if (start == end && SimpleParser.isInteger(tokens, start, end)) {
            return Integer.parseInt(tokens.get(start));
        } else if (start == end && SimpleParser.isFloat(tokens, start, end)) {
            return Float.parseFloat(tokens.get(start));
        }
        return null;
    }

    List<Number> getOperands(List<String> tokens, int start, int end) {
        //<operands> -> <expr> <operands> | <id>
        List<Number> operands = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                List<String> subList = tokens.subList(i, tokens.size() - 1);
                int iOfEnding = subList.indexOf(")") + i;
                operands.add((float)evalExpr(tokens, i, iOfEnding));
                i = iOfEnding;
            } else if (SimpleParser.isInteger(tokens, i, i)) {
                operands.add(Integer.parseInt(token));
            } else if (SimpleParser.isFloat(tokens, i, i)) {
                operands.add(Float.parseFloat(token));
            }
        }

        return operands;
    }

    Float evalId(String token) {
        Float Float = evalInteger(token);
        return Float;
    }

    Float evalInteger(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // helper functions
    public Number sum(List<Number> list) {
        float sum = 0;
        boolean floatCheck = false;
        for (Number i : list) {
            if(i instanceof Integer)
                sum += i.intValue();
            else if(i instanceof Float) {
                sum += i.floatValue();
                floatCheck = true;
            }
        }
        if(floatCheck) return sum;
        else return (int)sum;
    }

    public Number mult(List<Number> list) {
        float total = 1;
        boolean floatCheck = false;
        for (Number i : list) {
            if(i instanceof Integer)
                total *= i.intValue();
            else if(i instanceof Float) {
                total *= i.floatValue();
                floatCheck = true;
            }
        }
        if(floatCheck) return total;
        else return (int)total;
    }
    
    public Number div(List<Number> list) {
        float denom = 1;
        float total;
        Number numerator = list.get(0);
        boolean floatCheck = false;
        for (int i = 1; i <= list.size() - 1; i++) {
            if(list.get(i) instanceof Integer)
                denom *= list.get(i).intValue();
            else if(list.get(i) instanceof Float) {
                denom *= list.get(i).floatValue();
                floatCheck = true;
            }
        }
        if(numerator instanceof Integer) {
            int num = numerator.intValue();
            total = num / denom;
            if(floatCheck) return total;
            else return (int)total;
        }
        else if(numerator instanceof Float) {
            float num = numerator.floatValue();
            total = num / denom;
            return total;
        }
        else return null;
    }

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        // System.out.println(evaluator.evalS("234")); // 234
        System.out.println(evaluator.evalS("(/ 5 2.0)")); // 234
        // System.out.println(evaluator.evalS("(+ 20)")); // 20
        // System.out.println(evaluator.evalS("(+ 1 234)")); // 235
        // System.out.println(evaluator.evalS("(+ 2 10 200)")); // 212
        // System.out.println(evaluator.evalS("(* (+ 1 2) (+ 1 3))")); // 12
        // System.out.println(evaluator.evalS("(* (+ 1 2) (+ 1 3) (* 2 3))")); // 72

        // System.out.println(evaluator.evalS("(+ 20")); // null
    }
}