package src;

import java.util.*;

/*
 * Only +, * operators with integers
 * So all values are still integers
 */
public class Evaluator {

    Integer evalS(String expression) {
        // return true if s is a valid expression. expression could be nested
        List<String> tokens = Tokenizer.tokenize(expression);
        return evalExpr(tokens, 0, tokens.size() - 1);
    }

    Integer evalExpr(List<String> tokens, int start, int end) {
        // <expr> -> ( + <operands> ) | ( * <operands> ) | <id>
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            String operator = tokens.get(start + 1);
            List<Integer> operands = getOperands(tokens, start + 2, end - 1);
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

    List<Integer> getOperands(List<String> tokens, int start, int end) {
        //<operands> -> <expr> <operands> | <id>
        List<Integer> operands = new ArrayList<>();
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

    Integer evalId(String token) {
        Integer integer = evalInteger(token);
        return integer;
    }

    Integer evalInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // helper functions
    public int sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    public int mult(List<Integer> list) {
        int total = 1;
        for (int i : list) {
            total *= i;
        }
        return total;
    }

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        System.out.println(evaluator.evalS("234")); // 234
        System.out.println(evaluator.evalS("(+ 20)")); // 20
        System.out.println(evaluator.evalS("(+ 1 234)")); // 235
        System.out.println(evaluator.evalS("(+ 2 10 200)")); // 212
        System.out.println(evaluator.evalS("(* (+ 1 2) (+ 1 3))")); // 12
        System.out.println(evaluator.evalS("(* (+ 1 2) (+ 1 3) (* 2 3))")); // 72

        System.out.println(evaluator.evalS("(+ 20")); // null
    }
}