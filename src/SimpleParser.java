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
    // static final boolean debug = true;
    static final boolean debug = true;

    static boolean isS(String expression) {
        // return true if s is a valid expression. expression could be nested
        ArrayList<String> tokens = Tokenizer.tokenize(expression);
        tokens.forEach(System.out::print);
        System.out.println();
        return isExpr(tokens, 0, tokens.size() - 1);
    }

    static boolean isExpr(ArrayList<String> tokens, int start, int end) {
        // <expr> -> ( + <operands> ) | ( * <operands> ) | <id>
        // if(debug) System.out.println("isApp("+start+","+end+")");
        if (start > end) return false;
        String expr = combineList(tokens, start, end);
        if (expr.chars().filter(ch -> ch == '(').count() != expr.chars().filter(ch -> ch == ')').count()) return false;
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            return (tokens.get(start + 1).equals("+") || tokens.get(start + 1).equals("*")) && isOperands(tokens, start + 2, end - 1);
        }
        return isId(tokens, start, end);
    }

    static boolean isOperands(ArrayList<String> tokens, int start, int end) {
        // add your code
        boolean check = false;
        String explicitTokens = "*+ ";
        for (int i = start; i <= end; i++) {
            // if(!explicitTokens.contains(tokens.get(i)))
            if (tokens.get(i).equals("(")) {
                check = isExpr(tokens, i, combineList(tokens).indexOf(")", i));
            } else {
                if (!explicitTokens.contains(tokens.get(i))) {
                    check = isId(tokens, i, combineList(tokens).indexOf(")", i));
                }
            }
        }
        return check;
    }

    static boolean isId(ArrayList<String> tokens, int start, int end) {
        // add your code
        return isInteger(tokens, start, end);
    }

    static boolean isInteger(ArrayList<String> tokens, int start, int end) {
        // add your code
        try {
            int intValue = Integer.parseInt(tokens.get(start));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // helper methods
	static String combineList(ArrayList<String> tokens) {
		return combineList(tokens, 0, tokens.size() - 1);
	}

	static String combineList(ArrayList<String> tokens, int start, int end) {
		StringBuilder result = new StringBuilder();
		for (int i = start; i <= end; i++) {
			result.append(tokens.get(i));
		}
		return result.toString();
	}

	static boolean isInnermostExpr(String expr) {
    	int start = expr.indexOf('(');
    	int nextOpenIndex = expr.substring(start).indexOf('(');
    	int nextCloseIndex = expr.substring(start).indexOf(')');
    	return nextOpenIndex > nextCloseIndex;
	}

    public static void main(String[] args) {
        // true for these
//         System.out.println(isS("234"));
//         System.out.println(isS("(+ 20)"));
//         System.out.println(isS("(+ 1 234)"));
//         System.out.println(isS("(+ 2 10 200)"));
         System.out.println(isS("(* (+ 1 2) (+ 1 3))"));
         System.out.println(isS("(* (+ 1 2) (+ 1 3) (* 2 3))"));

        // false for the followings
//		System.out.println(isS("(* 2")); // Missing a closing parenthesis
//        System.out.println(isS("(* 2))")); // Extra closing parenthesis
//		System.out.println(isS("(+ 2 (3 4))"));
//		System.out.println(isS("(* (+ 1 2) (1 + 3) (* 2 3))"));
    }
}