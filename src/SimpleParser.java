package src;
import java.util.*;
/*
A Grammar for scheme application with integers and arithmetic operators +, *

<s> -> <expr>
<expr> -> ( + <operands> ) | ( * <operands> ) | <id>
<operands> -> <expr> <operands> | <id>
<id> -> <integer>  
 */

public class SimpleParser {
    static List<String> validOperators = Arrays.asList("+", "*");

    static boolean isS(String expression) {
        // return true if s is a valid expression. expression could be nested
        ArrayList<String> tokens = Tokenizer.tokenize(expression);
        tokens.forEach(t -> System.out.print(t + " "));
        System.out.println();
        return isExpr(tokens, 0, tokens.size() - 1);
    }

    static boolean isExpr(List<String> tokens, int start, int end) {
        if (start > end) return false;
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            return isOperator(tokens, start + 1, start + 1) && isOperands(tokens, start + 2, end - 1);
        }
        return isId(tokens, start, end);
    }

    static boolean isOperator(List<String> tokens, int start, int end) {
        return (validOperators.contains(combineList(tokens, start, end)));
    }

    static boolean isOperands(List<String> tokens, int start, int end) {
        // add your code
        boolean check = false;
        String explicitTokens = "*+ ";
        for (int i = start; i <= end; i++) {
            // if(!explicitTokens.contains(tokens.get(i)))
            if (tokens.get(i).equals("(")) {
                check = isExpr(tokens, i, combineList(tokens).indexOf(")", i));
                i = combineList(tokens).indexOf(")", i);
                if (!check)
                    break;
            } else {
                if (!explicitTokens.contains(tokens.get(i))) {
                    check = isId(tokens, i, combineList(tokens).indexOf(")", i));
                    if (!check && !tokens.get(i).equals(" "))
                        break;
                }
            }
        }
        return check;
    }

    static boolean isId(List<String> tokens, int start, int end) {
        // add your code
        if (tokens.get(start).equals(")") && start == end && combineList(tokens).indexOf("(", 1) > 0) {
            return true;
        }
        return isInteger(tokens, start, end) || isFloat(tokens, start, end);
    }

    static boolean isInteger(List<String> tokens, int start, int end) {
        // add your code
        try {
            Integer.parseInt(tokens.get(start));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	static boolean isFloat(List<String> tokens, int start, int end) {
        // add your code
        try {
            Double.parseDouble(tokens.get(start));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // helper methods
    static String combineList(List<String> tokens) {
        return combineList(tokens, 0, tokens.size() - 1);
    }

    static String combineList(List<String> tokens, int start, int end) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i <= end; i++) {
            result.append(tokens.get(i));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("True for these");
        System.out.println(isS("234"));
        System.out.println(isS("2.34"));
        System.out.println(isS("(+ 20)"));
        System.out.println(isS("(+ 1 234)"));
        System.out.println(isS("(+ 2 10 200)"));
        System.out.println(isS("(* (+ 1 2) (+ 1 3))"));
        System.out.println(isS("(* (+ 1 2) (+ 1 3) (* 2 3))"));

        System.out.println("False for these");
        System.out.println(isS("(* 2")); // Missing a closing parenthesis
        System.out.println(isS("(* 2))")); // Extra closing parenthesis
        System.out.println(isS("(+ 2 (3 4))"));
        System.out.println(isS("(* (+ 1 2) (1 + 3) (* 2 3))"));
    }
}