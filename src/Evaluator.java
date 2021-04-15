package src;

import java.util.*;

/*
 * Only +, * operators with integers
 * So all values are still integers
 */
public class Evaluator {

    //	static final boolean debug = true;
    static final boolean debug = false;

    static Integer evalS(String expression) {
        // return true if s is a valid expression. expression could be nested
        ArrayList<String> tokens = Tokenizer.tokenize(expression);
        if (debug) tokens.forEach(System.out::println);
        return evalExpr(tokens, 0, tokens.size() - 1);
    }

    static Integer evalExpr(List<String> tokens, int start, int end) {
        // <expr> -> ( + <operands> ) | ( * <operands> ) | <id>
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            String operator = tokens.get(start + 1);
            ArrayList<Integer> operands = getOperands(tokens, start + 2, end - 1);
            switch (operator) {
                case "+":
                    return sum(operands);
                case "*":
                    return mult(operands);
                default:
                    return null;
            }
        } else if (start == end) {
            return Integer.parseInt(tokens.get(start));
        }
        return null;
    }

    static ArrayList<Integer> getOperands(List<String> tokens, int start, int end) {
        //<operands> -> <expr> <operands> | <id>

        // add your code
        ArrayList<Integer> operands = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                List<String> subList = tokens.subList(i, tokens.size() - 1);
                int iOfEnding = subList.indexOf(")") + i;
                operands.add(evalExpr(tokens, i, iOfEnding));
                i = iOfEnding;
            } else if (SimpleParser.isInteger(tokens, i, i)) {
                operands.add(Integer.parseInt(token));
            }
        }

        return operands;
    }

    static Integer evalId(List<String> tokens, int start, int end) {
        // add your code
        return 0;
    }

    static Integer evalInteger(List<String> tokens, int start, int end) {
        // add your code
        return 0;
    }

    // helper functions
    public static int sum(List<Integer> list) {
        int sum = 0;
        for (int i: list) {
            sum += i;
        }
        return sum;
    }

    public static int mult(List<Integer> list) {
        int total = 1;
        for (int i: list) {
            total *= i;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(evalS("234")); // 234
        System.out.println(evalS("(+ 20)")); // 20
        System.out.println(evalS("(+ 1 234)")); // 235
        System.out.println(evalS("(+ 2 10 200)")); // 212
        System.out.println(evalS("(* (+ 1 2) (+ 1 3))")); // 12
        System.out.println(evalS("(* (+ 1 2) (+ 1 3) (* 2 3))")); // 72

        System.out.println(evalS("(+ 20")); // null
    }
}