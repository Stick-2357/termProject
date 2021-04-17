package src;

import java.util.*;

/*
A Grammar for scheme application with integers and arithmetic operators +, *
<s> -> <expr> | <define>
<define> -> ( define <id> <expr> )
<expr> -> ( + <operands> ) | ( * <operands> ) | <id>
<operands> -> <expr> <operands> | <id>
<id> -> <integer>  | <var>
 */

public class Interpreter {
    static boolean debug = false;
    static Scanner scan;
    static HashMap<String, Integer> variables = new HashMap<>();

    static boolean interpret(String s) {
        // <S> -> <expr> | <define>
        ArrayList<String> tokens = Tokenizer.tokenize(s);
        return (
                define(tokens, 0, tokens.size() - 1) ||
                        evaluate(tokens, 0, tokens.size() - 1)
        );
    }

    static boolean define(ArrayList<String> tokens, int start, int end) {
        // "(", "define", "key", "value", ")"
        if (tokens.get(start).equals("(")
                && tokens.get(start + 1).equals("define")
                && isValidKey(tokens.get(start + 2))
                && isId(tokens.get(start + 3))
                && tokens.get(start + 4).equals(")")
                && end - start == 4) {
            variables.put(tokens.get(start + 2), Integer.parseInt(tokens.get(start + 3)));
            return true;
        }
        return false;
    }

    static boolean isValidKey(String s) {
        return !(s.equals("(") || s.equals(")") || isId(s));
    }

    static boolean evaluate(ArrayList<String> tokens, int start, int end) {
        if (isIdentifier(tokens.get(start))) {
            System.out.println(evalVar(tokens, start, end));
            return true;
        }
        Integer r = evalExpr(tokens, 0, tokens.size() - 1);
        if (r == null) return false;
        System.out.println(r);
        return true;
    }

    static Integer evalExpr(ArrayList<String> tokens, int start, int end) {
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            String operator = tokens.get(start + 1);
            ArrayList<Integer> operands = getOperands(tokens, start + 2, end - 1);
            switch (operator) {
                case "+":
                    return Evaluator.sum(operands);
                case "*":
                    return Evaluator.mult(operands);
                default:
                    return null;
            }
        } else if (start == end) {
            return Integer.parseInt(tokens.get(start));
        }
        return null;
    }

    static ArrayList<Integer> getOperands(ArrayList<String> tokens, int start, int end) {
        ArrayList<Integer> operands = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                List<String> subList = tokens.subList(i, tokens.size() - 1);
                int iOfEnding = subList.indexOf(")") + i;
                operands.add(evalExpr(tokens, i, iOfEnding));
                i = iOfEnding;
            } else if (isIdentifier(token)) {
                operands.add(evalVar(tokens, i, i));
            } else if (SimpleParser.isInteger(tokens, i, i)) {
                operands.add(Integer.parseInt(token));
            }
        }

        return operands;
    }

    static boolean isId(String s) {

        return isInteger(s);
    }

    static Integer evalId(ArrayList<String> tokens, int start, int end) {
        return null;
    }

    static boolean isInteger(String s) {
        return evalInteger(s) != null;
    }

    static Integer evalInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Integer evalVar(ArrayList<String> tokens, int start, int end) {
        if (debug) System.out.println("evalVar(" + start + "," + end + ")");
        if (start != end) return null;
        String s = tokens.get(start);
        if (isIdentifier(s)) {
            Integer r = variables.get(s);
            if (r == null)
                System.out.println("Unknown symbol: " + s);
            else return r;
        }
        return null;
    }

    static boolean isIdentifier(String s) {
        return variables.containsKey(s);
    }

    public static void main(String[] args) {
        System.out.println("Eli and Alex Chez Scheme 0.9. Nothing much supported.");
        scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String userInput = scan.nextLine().trim();
            if (userInput.equals(""))
                continue;
            else if (userInput.equals("(quit)"))
                break;
            else if (userInput.equals("(local)"))
                System.out.println(variables);
            else if (!interpret(userInput))
                System.out.println("Syntax Error !!");
        }
        scan.close();
    }
}